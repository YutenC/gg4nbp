package gg.nbp.web.shop.shopproduct.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class testCreateProductDB {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar cal = new GregorianCalendar();
        String date = simpleDateFormat.format(cal.getTime());

        System.out.println("date: " + date);


        Date date_ = simpleDateFormat.parse(date);
        System.out.println(date_);

//        GregorianCalendar cal = new GregorianCalendar();
//        System.out.println(cal.getTime());


//        ProductDao productDao=new ProductDaoImpl();
//        CreateProductDB<Product,ProductImage> c=  new CreateProductDB<Product,ProductImage>(Product.class, ProductImage.class);
//         List<Product> products= c.readCSV();
//
////        for (Product value : products) {
////            productDao.insert(value);
////        }
//
//
//        c.createImgEntity();
    }
}
