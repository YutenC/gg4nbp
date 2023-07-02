package gg.nbp.web.shop.shopproduct.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.Primitives;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shopproduct.controller.*;
import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.pojo.CouponMember;
import gg.nbp.web.shop.shopproduct.pojo.DaoConditionSelect;
import gg.nbp.web.shop.shopproduct.pojo.ProductPojo;
import gg.nbp.web.shop.shopproduct.pojo.ProductSelect;
import gg.nbp.web.shop.shopproduct.util.ConvertJson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    FollowController followController;

    @Autowired
    CouponController couponController;

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
        DaoConditionSelect daoConditionSelect=null;
        Member member = (Member) session.getAttribute("member");
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


            case "/ecPay":
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

                Member member_ = testMemberController.getDefaultMember();
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
                strOut = productController.getProductByType(member.getMember_id(), Integer.valueOf(type));
                break;
            case "/getProductByBuyTimes":
                String type_ = req.getParameter("type");
                String amount_ = req.getParameter("amount");
                if (type_ == null || "".equals(type_)) {
                    type_ = "0";
                }

                if (amount_ == null) {
                    amount_ = "-1";
                }

                Map<String, Object> map = new HashMap<>();
                map.put("memId", member.getMember_id());
                map.put("limit", Integer.valueOf(amount_));

                strOut = productController.getProductByBuyTimes(map, Integer.valueOf(type_));

                break;


            case "/searchProducts":
                String search = req.getParameter("search");
                strOut = productController.searchProducts(member.getMember_id(), search);
                break;

            case "/getProductById":
                strOut = productController.getProductById(Integer.valueOf(req.getParameter("id")));
                break;


            case "/getSomeProduct":

                break;

            case "/updateProductInfo":
                ;
//                getParameter(req,"product",Product.class);


                JsonElement ss=(JsonElement) ConvertJson.getJsonDataMap(getPostData(req)).get("product");

                Product p=new Gson().fromJson(ss,Product.class);
                strOut = productController.updateProductInfo(p);
                break;

            case "/addCart":
                String id_str = req.getParameter("id");
                Integer productId_ = Integer.valueOf(id_str);
                productController.addCart(productId_, member.getMember_id());


                break;
            case "/getCartNum":

                break;

            case "/createProductFromcsv":
                productManagerController.createProductFromcsv();
                break;
            case "/getAllProduct":
                String obj=req.getParameter("limit");
                Integer limit=-1;
                if(obj!=null){
                    limit= Integer.valueOf(obj) ;
                }

                strOut = productController.getAllProduct(member.getMember_id(),limit);
                break;
            case "/getAllProductByCondition":
                String params = req.getParameter("params");
                ProductSelect productSelect=null;
                if(params!=null){
                    String params_encode = URLDecoder.decode(params, StandardCharsets.UTF_8);
                    System.out.println(params_encode);
                    productSelect=  new Gson().fromJson(params_encode, ProductSelect.class);
                }
                strOut = productController.getAllProductByCondition(member.getMember_id(),productSelect);
                break;

            case "/getAllProductWithIndexImg":


                strOut = productController.getAllProductWithIndexImg(member.getMember_id());
                break;

            case "/getProductsWithRequired":


//                ConvertJson.getJsonDataUnFixed();

                String obj_=req.getParameter("limit");
                Integer limit_=-1;
                if(obj_!=null){
                    limit_= Integer.valueOf(obj_) ;
                }

                strOut = productController.getAllProduct(member.getMember_id(), limit_);
                break;

            case "/getProductDetail":
                String productId_json = req.getParameter("id");
                Gson gson = new Gson();
                Integer productId = gson.fromJson(productId_json, Integer.class);
                strOut = productController.getProductDetail(member.getMember_id(), productId);

//                strOut = productController.getProductDetail(member.getMember_id(),(Integer)getParameter(req,"id"));
                break;

            case "/getProductHistory":
                strOut = productController.getProductHistory();
                break;

            case "/takeOnProduct":
                productManagerController.takeOnProduct(Integer.valueOf(req.getParameter("id")));
                break;

            case "/removeTakeOningProduct":
                productManagerController.removeTakeOningProduct(Integer.valueOf(req.getParameter("id")));
                break;

            case "/cancelTakeOnProduct":
                productManagerController.cancelTakeOnProduct(Integer.valueOf(req.getParameter("id")));
                break;

            case "/takeOffProduct":
                productManagerController.takeOffProduct(Integer.valueOf(req.getParameter("id")));
                break;

            case "/removeTakeOffingProduct":
                productManagerController.removeTakeOffingProduct(Integer.valueOf(req.getParameter("id")));
                break;

            case "/autoGenerateCouponActivity":
                couponManagerController.autoGenerateCouponActivity();
                break;

            case "/getAllCouponActivity":
                strOut = couponManagerController.getAllCouponActivity(session);
                break;

            case "/getCouponActivityByCondition":
                daoConditionSelect= getDaoConditionSelect(req);
                if(daoConditionSelect!=null){
                    strOut = couponManagerController.getCouponActivityByCondition(daoConditionSelect);
                }
                break;
            case "/getCouponByDiscountCode":
                String discountCode = req.getParameter("discountCode");
                strOut = couponController.getCouponByDiscountCode(discountCode);
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

            case "/publishCouponActivity":
                String object= req.getParameter("couponId");
                if(object!=null){
                    Integer couponId_ = Integer.parseInt(object);
                    strOut=  couponManagerController.publishCouponActivity(couponId_);
                }

                break;

            case "/getCouponMemberInfo":
                strOut= couponManagerController.getCouponMemberInfo();
                break;

            case "/sendEmail":
//                Map<String,JsonElement> params_= ConvertJson.getJsonDataMap("params");
                String params__ =getParameter(req,"params");

                String params_= URLDecoder.decode(params__,StandardCharsets.UTF_8);
                if(params_!=null){
                    Map<String,JsonElement> map_= ConvertJson.getJsonDataMap(params_);
                    int action= map_.get("action").getAsInt();
//                    CouponMember couponMember= new Gson().fromJson(map_.get("couponMembers"), CouponMember.class);
                    List<CouponMember> couponMembers=ConvertJson.getFromArray(map_.get("couponMembers"), CouponMember.class);

                    strOut= couponManagerController.sendEmail(action,couponMembers);
                }


                break;


            case "/longTimeProcess":
                strOut = productManagerController.longTimeProcess();
                break;
            case "/getBackgroundMessage":
                String taskName = req.getParameter("taskName");
                strOut = backgroundMessageController.getBackgroundMessage(taskName);
                break;

            case "/getFollowByMember":
                strOut = followController.getFollowByMember(member);

                break;
            case "/getFollowByMemberId":
                strOut = followController.getFollowByMemberId(member.getMember_id());

                break;
            case "/addFollow":
                String id_str_ = req.getParameter("id");
                Integer productId__ = Integer.valueOf(id_str_);
                strOut = followController.addFollow(productId__, member.getMember_id()).toString();

                break;

            case "/login":
                member = testMemberController.getDefaultMember();
                session.setAttribute("isLogin", true);
                session.setAttribute("member", member);

                break;
        }

        res.setContentType("application/json;charset=utf-8");
        PrintWriter out = res.getWriter();
        out.println(strOut);

    }

    private <T> T getObject(HttpServletRequest req, String parm, Class<T> classOfT) {
        String result = req.getParameter(parm);
        Gson gson = new Gson();
        T obj = gson.fromJson(result, classOfT);
        return Primitives.wrap(classOfT).cast(obj);
    }

    private String getParameter(HttpServletRequest req, String parm) {
        return req.getParameter(parm);

    }

    private <T> T getParameter(HttpServletRequest req, String parm, T c) {
        String result = req.getParameter(parm);
        return (T) result;
    }

    private String getPostData(HttpServletRequest req){
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

        return payloadData;
    }

    private DaoConditionSelect getDaoConditionSelect(HttpServletRequest req){
        String params = req.getParameter("params");
        DaoConditionSelect daoConditionSelect=null;
        if(params!=null){
            String params_encode = URLDecoder.decode(params, StandardCharsets.UTF_8);
            System.out.println(params_encode);
            daoConditionSelect=  new Gson().fromJson(params_encode, DaoConditionSelect.class);
        }
        return daoConditionSelect;
    }

}
