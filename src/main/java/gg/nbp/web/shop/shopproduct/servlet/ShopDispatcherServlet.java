package gg.nbp.web.shop.shopproduct.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shopproduct.controller.*;
import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.pojo.*;
import gg.nbp.web.shop.shopproduct.util.ConvertJson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URLDecoder;
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
        HttpSession session = req.getSession();
        String strOut = "";
        DaoConditionSelect daoConditionSelect = null;
        Object objectMember = session.getAttribute("member");
        Member member = null;
        if (objectMember != null) {
            member = (Member) objectMember;
        } else {
            member = new Member();
            member.setMember_id(-1);
        }


        switch (path) {
            case "/checkLogin":
                Object isLogin = session.getAttribute("isLogin");
                ResponseMsg responseMsg = null;
                if (isLogin == null) {
                    responseMsg = new ResponseMsg.Builder().setState("ok").setMsg("nologin").build();
                } else {
                    if (member == null || member.getMember_id() == -1) {
                        responseMsg = new ResponseMsg.Builder().setState("ok").setMsg("nologin").build();
                    } else {
                        responseMsg = new ResponseMsg.Builder().setState("ok").setMsg("login").build();
                    }
                }

                strOut = ConvertJson.toJson(responseMsg);
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

                Gson gson_ = new Gson();
                ProductPojo productPojo = gson_.fromJson(payloadData, ProductPojo.class);
                productManagerController.addProduct(productPojo);
                break;

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
                JsonElement jsonElement = (JsonElement) ConvertJson.getJsonDataMap(getPostData(req)).get("product");
                Product p = new Gson().fromJson(jsonElement, Product.class);
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
                String obj = req.getParameter("limit");
                Integer limit = -1;
                if (obj != null) {
                    limit = Integer.valueOf(obj);
                }

                strOut = productController.getAllProduct(member.getMember_id(), limit);
                break;
            case "/getAllProductByCondition":
                String params = req.getParameter("params");
                ProductSelect productSelect = null;
                if (params != null) {
                    String params_encode = URLDecoder.decode(params, StandardCharsets.UTF_8);

                    productSelect = new Gson().fromJson(params_encode, ProductSelect.class);
                }
                strOut = productController.getAllProductByCondition(member.getMember_id(), productSelect);
                break;

            case "/getAllProductWithIndexImg":
                strOut = productController.getAllProductWithIndexImg(member.getMember_id());
                break;

            case "/getProductsWithRequired":
                String obj_ = req.getParameter("limit");
                Integer limit_ = -1;
                if (obj_ != null) {
                    limit_ = Integer.valueOf(obj_);
                }

                strOut = productController.getAllProduct(member.getMember_id(), limit_);
                break;

            case "/getProductDetail":
                String productId_json = req.getParameter("id");
                Gson gson = new Gson();
                Integer productId = gson.fromJson(productId_json, Integer.class);
                strOut = productController.getProductDetail(member.getMember_id(), productId);
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
                daoConditionSelect = getDaoConditionSelect(req);
                if (daoConditionSelect != null) {
                    strOut = couponManagerController.getCouponActivityByCondition(daoConditionSelect);
                }
                break;
            case "/getCouponByDiscountCode":
                String discountCode = req.getParameter("discountCode");
                strOut = couponController.getCouponByDiscountCode(discountCode);
                break;

            case "/addCouponActivity":
                String newCouponActivity = req.getParameter("newCouponActivity");
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
            case "/generateDiscountCode":
                strOut = couponManagerController.generateDiscountCode();
                break;
            case "/deleteCoupon":
                Integer couponId = Integer.parseInt(req.getParameter("couponId"));
                couponManagerController.deleteCoupon(couponId);
                break;

            case "/publishCouponActivity":
                String object = req.getParameter("couponId");
                if (object != null) {
                    Integer couponId_ = Integer.parseInt(object);
                    strOut = couponManagerController.publishCouponActivity(couponId_);
                }

                break;

            case "/getCouponMemberInfo":
                strOut = couponManagerController.getCouponMemberInfo();
                break;

            case "/sendEmail":
                String params__ = getParameter(req, "params");
                String params_ = URLDecoder.decode(params__, StandardCharsets.UTF_8);
                if (params_ != null) {
                    Map<String, JsonElement> map_ = ConvertJson.getJsonDataMap(params_);
                    int action = map_.get("action").getAsInt();
                    List<CouponMember> couponMembers = ConvertJson.getFromArray(map_.get("couponMembers"), CouponMember.class);

                    strOut = couponManagerController.sendEmail(action, couponMembers);
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

            case "/login"://測試用
//                member = testMemberController.getDefaultMember();
//                session.setAttribute("isLogin", true);
//                session.setAttribute("member", member);
                break;
        }

        res.setContentType("application/json;charset=utf-8");
        PrintWriter out = res.getWriter();
        out.println(strOut);

    }


    private String getParameter(HttpServletRequest req, String parm) {
        return req.getParameter(parm);

    }

    private String getPostData(HttpServletRequest req) {
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


        return payloadData;
    }

    private DaoConditionSelect getDaoConditionSelect(HttpServletRequest req) {
        String params = req.getParameter("params");
        DaoConditionSelect daoConditionSelect = null;
        if (params != null) {
            String params_encode = URLDecoder.decode(params, StandardCharsets.UTF_8);
            daoConditionSelect = new Gson().fromJson(params_encode, DaoConditionSelect.class);
        }
        return daoConditionSelect;
    }

}
