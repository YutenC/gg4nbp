package gg.nbp.web.shop.shopproduct.service.impl;


import gg.nbp.web.shop.shopproduct.common.backgroundtask.BackgroundFactory;
import gg.nbp.web.shop.shopproduct.common.backgroundtask.BackgroundHandler;
import gg.nbp.web.shop.shopproduct.common.schedulertask.SchedulerEntity;
import gg.nbp.web.shop.shopproduct.common.schedulertask.SchedulerFactory;
import gg.nbp.web.shop.shopproduct.common.schedulertask.SchedulerTasks;
import gg.nbp.web.shop.shopproduct.dao.ProductDao;
import gg.nbp.web.shop.shopproduct.dao.ProductImageDao;
import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.entity.ProductImage;
import gg.nbp.web.shop.shopproduct.pojo.ProductPojo;
import gg.nbp.web.shop.shopproduct.service.ProductManagerService;
import gg.nbp.web.shop.shopproduct.util.ConstUtil;
import gg.nbp.web.shop.shopproduct.util.CreateProductDB;
import gg.nbp.web.shop.shopproduct.util.ProductState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Callable;

@Service
//@Transactional
public class ProductManagerServiceImpl implements ProductManagerService {

    @Autowired
    ProductDao productDao;

    @Autowired
    ProductImageDao productImageDao;

    @Autowired
    CreateProductDB<Product, ProductImage> createProductDB;

    public ProductManagerServiceImpl() {
    }

    @Override
    public void createProductFromcsv() {
        try {
            List<Product> products = createProductDB.readCSV();
            for (Product value : products) {
                productDao.insert(value);
            }

            createProductDB.createImgEntity();

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void longTimeProcess() {
        BackgroundHandler backgroundHandler = BackgroundFactory.getBackgroundHandler("shopProductBackground");

        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);
                return "ok";
            }
        };

        backgroundHandler.addTask("readCSV", task);
    }

    @Override
    public void addProduct(ProductPojo productPojo) {
        Product product = productPojo.getNewProduct();
        product.setBuyTimes(0);
        product.setRate(0);
        product.setRevieweCount(0);

        Integer productId = productDao.insert(product);
        product.setId(productId);

        List<ProductImage> productImages = productPojo.getNewProduct().getProductImages();

        for (int i = 0; i < productImages.size(); i++) {
            ProductImage productImage = productImages.get(i);

            String fileName = "";
            byte[] fromBase64str = Base64.getDecoder().decode(productImage.getImage().split(",")[1]);
            FileOutputStream fos = null;
            try {
                if (i == 0) {
                    fileName = productId + "_index" + ".png";
                    fos = new FileOutputStream(ConstUtil.getDESIMGPATH() + fileName);
                } else {
                    fileName = productId + "_" + i + ".png";
                    fos = new FileOutputStream(ConstUtil.getDESIMGPATH() + fileName);
                }

                fos.write(fromBase64str);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            productImage.setImage(ConstUtil.getREL_DESIMGPATH() + fileName);
            productImage.setProduct(product);
            productImageDao.insert(productImage);
        }

    }

    @Override
    public void takeOnProduct(Integer id) {
        Product product = productDao.selectById(id);
        product.setState(ProductState.TakeOning.getValue());
        productDao.updateProductState(product);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Integer productId = id;
                Product product = productDao.selectById(productId);
                product.setState(ProductState.TakeOn.getValue());
                productDao.updateProductState(product);

                System.out.println("take On" + product.getProductName());
                cancel();
                SchedulerTasks schedulerTasks = SchedulerFactory.getSchedulerTasks("takeOnProduct");
                schedulerTasks.removeTimerTask(productId + "takeOn");
                schedulerTasks.clear();
            }
        };
        System.out.println("product.getLaunchTime(): " + product.getLaunchTime());
        SchedulerTasks schedulerTasks = SchedulerFactory.getSchedulerTasks("takeOnProduct");
        schedulerTasks.addTimerTask(product.getId() + "takeOn", new SchedulerEntity(product.getLaunchTime(), timerTask));

    }

    @Override
    public void cancelTakeOnProduct(Integer id) {
        Product product = productDao.selectById(id);

        SchedulerTasks schedulerTasks = SchedulerFactory.getSchedulerTasks("takeOnProduct");
        SchedulerEntity schedulerEntity = schedulerTasks.getTimerTask(product.getId() + "takeOn");
        if (schedulerEntity != null) {
            schedulerEntity.getTimerTask().cancel();
        }

    }

    @Override
    public void takeOffProduct(Integer id) {
        Product product = productDao.selectById(id);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Integer productId = id;
                Product product = productDao.selectById(productId);

                product.setState(ProductState.TakeOff.getValue());
                productDao.updateProductState(product);

                System.out.println("take Off" + product.getProductName());
                cancel();
                SchedulerTasks schedulerTasks = SchedulerFactory.getSchedulerTasks("takeOffProduct");
                schedulerTasks.removeTimerTask(productId + "takeOff");
                schedulerTasks.clear();
            }
        };

        SchedulerTasks schedulerTasks = SchedulerFactory.getSchedulerTasks("takeOffProduct");
        schedulerTasks.addTimerTask(product.getId() + "takeOff", new SchedulerEntity(product.getTakeoffTime(), timerTask));
        product.setState(ProductState.TakeOffing.getValue());
        productDao.updateProductState(product);
    }

    @Override
    public void removeTakeOningProduct(Integer id) {
        Product product = productDao.selectById(id);

        if (product.getState() == ProductState.TakeOning.getValue()) {
            String key = id + "takeOn";
            SchedulerTasks schedulerTasks = SchedulerFactory.getSchedulerTasks("takeOnProduct");

            try {
                schedulerTasks.getTimerTask(key).getTimerTask().cancel();
                schedulerTasks.removeTimerTask(key);
                schedulerTasks.clear();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }


            product.setState(ProductState.NewAdd.getValue());
            productDao.updateProductState(product);
        }

    }

    @Override
    public void removeTakeOffingProduct(Integer id) {
        Product product = productDao.selectById(id);

        if (product.getState() == ProductState.TakeOffing.getValue()) {
            String key = id + "takeOff";
            SchedulerTasks schedulerTasks = SchedulerFactory.getSchedulerTasks("takeOffProduct");
            try {
                schedulerTasks.getTimerTask(key).getTimerTask().cancel();
                schedulerTasks.removeTimerTask(key);
                schedulerTasks.clear();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }


            product.setState(ProductState.TakeOn.getValue());
            productDao.updateProductState(product);
        }

    }

}
