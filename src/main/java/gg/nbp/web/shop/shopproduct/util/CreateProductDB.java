package gg.nbp.web.shop.shopproduct.util;

import gg.nbp.web.shop.shopproduct.dao.ProductDao;
import gg.nbp.web.shop.shopproduct.dao.ProductImageDao;
import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.entity.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class CreateProductDB<T, P> {

    @Autowired
    ProductDao productDao;

    @Autowired
    ProductImageDao productImageDao;
    private Class entityClass;
    private Class entityClass_P;


    public CreateProductDB(Class c, Class p) {
        entityClass = c;
        entityClass_P = p;
    }

    public List<T> readCSV() throws NoSuchFieldException, IllegalAccessException {
        String csvFile = ConstUtil.getCSVPATH();
        String line;
        String csvDelimiter = ",";

        List<String> meta = new ArrayList<>();
        List<T> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            if ((line = br.readLine()) != null) {
                String[] data = line.split(csvDelimiter);
                for (String value : data) {
                    meta.add(value);
                }
            }

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvDelimiter);

                if (data.length > 1) {
                    T entity = null;
                    entity = (T) entityClass.getDeclaredConstructor().newInstance();

                    // Process the data from the CSV file
                    for (int i = 0; i < data.length; i++) {
                        setValue(entity, meta.get(i), data[i]);
                    }

                    products.add(entity);
                } else {
                }

                System.out.println();
            }

            for (T value : products) {
                System.out.println(value + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    public List<P> createImgEntity() throws NoSuchFieldException, IllegalAccessException {
        List<String> fileNames = getListFile(ConstUtil.getSRCIMGPATH());


        String newFileName = null;
        File file;
        String pathName;
        List<T> products = (List<T>) productDao.selectAll();

        for (int i = 0; i < products.size(); i++) {
            T product = products.get(i);

            Field field = product.getClass().getDeclaredField("productName");
            field.setAccessible(true);
            String fileName = (String) field.get(product);

            field = product.getClass().getDeclaredField("id");
            field.setAccessible(true);
            Integer p_id = (Integer) field.get(product);


            for (int j = 0; j < 3; j++) {
                if (j != 0) {
                    pathName = ConstUtil.getSRCIMGPATH() + fileName + "_" + j + ".PNG";
                    newFileName = p_id + "_" + j + ".PNG";
                } else {
                    pathName = ConstUtil.getSRCIMGPATH() + fileName + ".PNG";
                    newFileName = p_id + "_index" + ".PNG";
                }

                file = new File(pathName);
                if (!file.exists()) {
                    continue;
                }

                // Check if the file exists
                if (file.exists()) {

                    File file2 = new File(ConstUtil.getDESIMGPATH() + newFileName);
                    copyFile(file, file2);

                } else {
                    System.out.println("File does not exist.");
                }

                ProductImage productImage = new ProductImage((Product) product, "../img/gameSoftware/test/" + newFileName);
                productImageDao.insert(productImage);
            }

        }

        return null;
    }

    public void copyFile(File file1, File file2) {

        try {

            FileInputStream fileInputStream = new FileInputStream(file1);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);

            BufferedInputStream BufferInputStream = new BufferedInputStream(fileInputStream);
            BufferedOutputStream BufferOutputStream = new BufferedOutputStream(fileOutputStream);

            byte[] buffer2 = new byte[1024];

            while (BufferInputStream.read(buffer2) != -1) {
                BufferOutputStream.write(buffer2);
            }

            BufferOutputStream.close();
            BufferInputStream.close();
            fileInputStream.close();
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<String> getListFile(String dirPath) {
        List<String> fileNames = new ArrayList<>();
        File directory = new File(dirPath); // 目錄物件
//        System.out.println(("File Name: " + directory.getName()));
//        System.out.println(("File Path: " + directory.getPath()));
//        System.out.println(("File getAbsolutePath: " + directory.getAbsolutePath()));
        // Check if the specified path is a directory
        if (directory.isDirectory()) {
            // Get all files in the directory
            File[] files = directory.listFiles();

            // Iterate over the files and print their names
            if (files != null) {
                for (File file : files) {
//                    System.out.println(file.getName());
                    fileNames.add(file.getName());
                }
            }
        }
        return fileNames;

    }

    private void setValue(T object, String property, Object propertyValue) {
        Class objectClass = object.getClass();

        try {
            Field field = objectClass.getDeclaredField(property);
            if (("java.util.Date").equals(field.getType().getName())) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                propertyValue = simpleDateFormat.parse((String) propertyValue);
            } else if (("java.lang.Integer").equals(field.getType().getName())) {
                if (!"type".equals(property)) {
                    propertyValue = Integer.valueOf((String) propertyValue);
                }

            }

            if ("type".equals(property)) {
                String type_ = (String) propertyValue;
                type_ = type_.trim();
                switch (type_) {
                    case "PS5 game"://22
                        propertyValue = ProductType.PSGame.getValue();//22
                        break;
                    case "Switch game"://2
                        propertyValue = ProductType.NSGame.getValue();//2
                        break;
                    case "XBOX Game"://12
                        propertyValue = ProductType.XBOXGame.getValue();//12
                        break;
                }
            }

            if (field != null) {
                field.setAccessible(true);
                field.set(object, propertyValue);
            }

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }


}
