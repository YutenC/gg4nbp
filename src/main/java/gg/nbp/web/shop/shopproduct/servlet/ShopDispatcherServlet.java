package gg.nbp.web.shop.shopproduct.servlet;

import com.google.gson.Gson;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shopproduct.controller.*;
import gg.nbp.web.shop.shopproduct.pojo.ProductPojo;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@Component
@WebServlet("/shopDispatcher/*")
//@MultipartConfig
public class ShopDispatcherServlet extends HttpServlet {


    @Autowired
    BackgroundMessageController backgroundMessageController;

    @Autowired
    ProductController productController;

    @Autowired
    CouponManagerController couponManagerController;

    @Autowired
    ProductManagerController productManagerController;

    @Autowired
    testMemberController testMemberController;

    public ShopDispatcherServlet() {
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        req.setCharacterEncoding("utf-8");
        process(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        req.setCharacterEncoding("utf-8");

        process(req, res);

    }

    private void process(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String path = req.getPathInfo();
        System.out.println(path);
        HttpSession session = req.getSession();
        String strOut = "";
        switch (path) {
//            case  "/exPay":
//                StringBuilder requestData_ = new StringBuilder();
//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        requestData_.append(line);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                String payloadData_ = requestData_.toString();
//                System.out.println(payloadData_);
//
//                Member member_=testMemberController.getDefaultMember();
//                Gson gson__ = new Gson();
//                strOut = gson__.toJson(member_);
//
//
//
//                break;


            case  "/ecPay":
                StringBuilder requestData_ = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        requestData_.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String payloadData_ = requestData_.toString();
                System.out.println(payloadData_);

                Member member_=testMemberController.getDefaultMember();
                Gson gson__ = new Gson();
                strOut = gson__.toJson(member_);
                
                break;
            case "/addProduct":
                StringBuilder requestData = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        requestData.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String payloadData = requestData.toString();
                System.out.println(payloadData);

                Gson gson_ = new Gson();
                ProductPojo productPojo = gson_.fromJson(payloadData, ProductPojo.class);

                productManagerController.addProduct(productPojo);
//                List<ProductImage> productImages= productPojo.getNewProduct().getProductImages();
//                for (int i=0;i<productImages.size();i++) {
//                    ProductImage productImage= productImages.get(i);
//                    productImage.setImage(productImage.getImage().split(",")[1]);
//                }


                break;
//            case "/uploadProduct":
//                /* Receive file uploaded to the Servlet from the HTML5 form */
//                Part filePart = null;
//                try {
//                    filePart = req.getPart("file");
//                    String fileName = filePart.getSubmittedFileName();
//                    for (Part part : req.getParts()) {
//                        part.write("C:\\upload\\" + fileName);
//                    }
//
//                    strOut = "The file uploaded sucessfully.";
//
//                } catch (ServletException e) {
//                    throw new RuntimeException(e);
//                }
//
//
//                break;

            case "/getProductByType":
                String type = req.getParameter("type");
                strOut = productController.getProductByType(Integer.valueOf(type));
                break;
            case "/getProductByBuyTimes":
                String type_ = req.getParameter("type");
                strOut = productController.getProductByBuyTimes(Integer.valueOf(type_));
                break;


            case "/searchProducts":
                String search = req.getParameter("search");
                strOut = productController.searchProducts(search);
                break;

            case "/getSomeProduct":

                break;

            case "/addFollow":
                String id_str_ = req.getParameter("id");
                Integer productId__=Integer.valueOf(id_str_);

                Object isLogin__=  session.getAttribute("isLogin");
                if(isLogin__!=null){
                    Boolean isLogin=(Boolean)isLogin__;
                    if(isLogin){
                        Member member= (Member)session.getAttribute("member");
                        productController.addFollow(productId__,member.getMember_id());
                    }
                    else{
//                        res.sendRedirect("/Five_NBP_gg/member_login.html");
                    }

                }
                else
                {
//                    res.sendRedirect("/Five_NBP_gg/member_login.html");
                }


                break;



            case "/addCart":
                String id_str = req.getParameter("id");
                Integer productId_=Integer.valueOf(id_str);

                Object isLogin_=  session.getAttribute("isLogin");
                if(isLogin_!=null){
                    Boolean isLogin=(Boolean)isLogin_;
                    if(isLogin){
                        Member member= (Member)session.getAttribute("member");
                        productController.addCart(productId_,member.getMember_id());
                    }
                    else{
//                        res.sendRedirect("/Five_NBP_gg/member_login.html");
                    }

                }
                else
                {
//                    res.sendRedirect("/Five_NBP_gg/member_login.html");
                }

                break;
            case "/getCartNum":

                break;

            case "/createProductFromcsv":
                productManagerController.createProductFromcsv();
                break;
            case "/getAllProduct":
                strOut = productController.getAllProduct();
                break;
            case "/getProductDetail":
                String productId_json = req.getParameter("id");
                Gson gson = new Gson();
                Integer productId = gson.fromJson(productId_json, Integer.class);
                strOut = productController.getProductDetail(productId);
                break;

            case "/getProductHistory":
                strOut = productController.getProductHistory();
                break;

            case "/takeOnProduct":
                productManagerController.takeOnProduct(Integer.valueOf(req.getParameter("id")));
                break;

            case "/cancelTakeOnProduct":
                productManagerController.cancelTakeOnProduct(Integer.valueOf(req.getParameter("id")));
                break;

            case "/takeOffProduct":
                productManagerController.takeOffProduct(Integer.valueOf(req.getParameter("id")));
                break;

            case "/autoGenerateCouponActivity":
                couponManagerController.autoGenerateCouponActivity();
                break;

            case "/getAllCouponActivity":
                strOut = couponManagerController.getAllCouponActivity(session);
                break;
            case "/addCouponActivity":
                String newCouponActivity = req.getParameter("newCouponActivity");
                System.out.println("newCouponActivity: " + newCouponActivity);
                couponManagerController.addCouponActivity(session, newCouponActivity);
                break;

            case "/updateCouponActivity":
                String json_newCouponActivity = req.getParameter("newCouponActivity");

                strOut = couponManagerController.updateCouponActivity(json_newCouponActivity);
                break;


            case "/addCoupon":
                String json_newCoupon = req.getParameter("json_newCoupon");
                couponManagerController.addCouponActivity(session, json_newCoupon);
                break;
            case "/deleteCoupon":
                Integer couponId = Integer.parseInt(req.getParameter("couponId"));
                couponManagerController.deleteCoupon(couponId);
                break;


            case "/longTimeProcess":
                strOut = productManagerController.longTimeProcess();
                break;
            case "/getBackgroundMessage":
                String taskName = req.getParameter("taskName");
                strOut = backgroundMessageController.getBackgroundMessage(taskName);
                break;

            case "/login":
                Member member=testMemberController.getDefaultMember();
                session.setAttribute("isLogin", true);
                session.setAttribute("member", member);


                break;
        }

        res.setContentType("application/json;charset=utf-8");
        PrintWriter out = res.getWriter();
        out.println(strOut);

    }
}
