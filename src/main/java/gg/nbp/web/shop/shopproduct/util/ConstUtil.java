package gg.nbp.web.shop.shopproduct.util;

public class ConstUtil {

    public static String PATH = "";
    private static String CSVPATH = "\\src\\main\\resources\\static\\img\\gameSoftware\\test\\origin\\假資料2.csv";
    private static String SRCIMGPATH = "\\src\\main\\resources\\static\\img\\gameSoftware\\test\\origin\\";
    private static String DESIMGPATH = "\\src\\main\\resources\\static\\img\\gameSoftware\\test\\";
    private static String REL_DESIMGPATH = "../img/gameSoftware/test/";
    private static String DEFAULTIMG = "\\src\\main\\resources\\static\\img\\gameSoftware\\test\\DefaultProduct.PNG";


    private static String getPATH() {
        return PATH;
    }

    public static String getCSVPATH() {
        return PATH + CSVPATH;
    }

    public static String getSRCIMGPATH() {
        return PATH + SRCIMGPATH;
    }

    public static String getDESIMGPATH() {
        return PATH + DESIMGPATH;
    }

    public static String getREL_DESIMGPATH() {
        return REL_DESIMGPATH;
    }

    public static String getDEFAULTIMG() {
        return PATH + DEFAULTIMG;
    }
}
