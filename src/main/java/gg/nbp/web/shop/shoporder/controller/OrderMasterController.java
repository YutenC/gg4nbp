package gg.nbp.web.shop.shoporder.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.entity.Notice;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.Member.service.NoticeService;
import gg.nbp.web.shop.shoporder.entity.OrderMaster;
import gg.nbp.web.shop.shoporder.service.OrderDetailService;
import gg.nbp.web.shop.shoporder.service.OrderMasterService;
import gg.nbp.web.shop.shoporder.util.ManageOrder;
import gg.nbp.web.shop.shoporder.util.MemberViewOrder;
import gg.nbp.web.shop.shoporder.util.OrderSelection;
import gg.nbp.web.shop.shoporder.util.ResOrderMaster;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;
import gg.nbp.web.shop.shopproduct.service.CouponService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderMaster")
public class OrderMasterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Gson gson;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private OrderMasterService orderMasterService;
	
	@Autowired
	private NoticeService noticeService;
	
	public OrderMasterController() {
        super();
        this.gson = new Gson();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
    	
    	Gson gson = new Gson();
    	res.setCharacterEncoding("UTF-8");
    	res.setContentType("application/json");
    	PrintWriter pw = res.getWriter();
		
		HttpSession httpSession = req.getSession();
		
		if (httpSession.getAttribute("member") == null) {
			Member failLogin = new Member();
			failLogin.setSuccessful(false);
			pw.println(gson.toJson(failLogin));
			return;
		}
		
		Member member = (Member)httpSession.getAttribute("member");
		Integer memberId = member.getMember_id();
    	
    	if (req.getParameter("manageAll") != null) {
    		int limit = 10;
    		Integer offset = Integer.valueOf(req.getParameter("manageAll")) * limit ;
    		
    		Integer sortBy = Integer.valueOf(req.getParameter("sortBy"));
    		
    		Map<String, String> orderBy = new HashMap<>();
    		switch (sortBy) {
			case 1:
				orderBy.put("orderBy", "orderId");
				break;
			case 2:
				orderBy.put("orderBy", "memberId");
				break;
			case 3:
				orderBy.put("orderBy", "commitDate");
				break;
			case 4:
				orderBy.put("orderBy", "totalPrice");
				break;
			case 5:
				orderBy.put("orderBy", "orderStatus");
				break;
			case 6:
				orderBy.put("orderBy", "payStatus");
				break;
			case 7:
				orderBy.put("orderBy", "deliverState");
				break;
			}

    		Integer sortWay = Integer.valueOf(req.getParameter("sortWay"));
    		switch (sortWay) {
			case 0:
				orderBy.put("orderWay", "ASC");
				break;
			case 1:
				orderBy.put("orderWay", "DESC");
				break;
			}
    		
    		List<ManageOrder> mgOrderList = null;
    		switch (sortBy) {
			case 0:
				mgOrderList = orderMasterService.showAllMgOrderList(limit, offset);
				break;
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				Map<String, Integer> limitAndOffset = new HashMap<>();
				limitAndOffset.put("limit", 10);
				limitAndOffset.put("offset", offset);
				mgOrderList = orderMasterService.showMgOrderListSortedWithLimitOffset(orderBy, limitAndOffset);
				break;
			}
    		pw.println(gson.toJson(mgOrderList));
    		return;
    	}
    	
    	String manageCondition = req.getParameter("manageCondition");
    	if (manageCondition != null) {
    		Integer offset = Integer.valueOf(req.getParameter("offset")) * 10;
    		Integer selection = Integer.valueOf(req.getParameter("selection"));
    		Integer sortWay = Integer.valueOf(req.getParameter("sortWay"));
    		OrderSelection os = OrderSelection.values()[selection - 1];
    		List<ManageOrder> results = orderMasterService.getJedisOrderMasterResults(os, sortWay, offset);
    		pw.println(gson.toJson(results));
    		return;
    	}
    	
    	String fresh = req.getParameter("fresh");
    	if (fresh != null) {
    		pw.println(gson.toJson(orderMasterService.renewOrderMasterResults()));
    		return;
    	}
    	
    	String character = req.getParameter("countListLength");
    	if (character != null) {
    		Map<String, Integer> condition = new HashMap<>();
    		if ("manager".equals(character)) {
    			String criteria = req.getParameter("criteria");
    			if (criteria != null) {
    				Integer criteriaValue = Integer.valueOf(criteria);
    				OrderSelection os = OrderSelection.values()[criteriaValue - 1];
    				switch (os) {
					case ALL:
						break;
					case PAID:
						condition.put("payStatus", 2);
						break;
					case UNPAID:
						condition.put("payStatus", 1);
						break;
					case PAIDONEDELI:
						condition.put("payStatus", 3);
						break;
					case DELIVERD:
						condition.put("deliverState", 1);
						break;
					case UNDELI:
						condition.put("deliverState", 0);
						break;
					case DONE:
						condition.put("orderStatus", 1);
						break;
					case CANCELED:
						condition.put("orderStatus", 2);
						break;
					case APPLYCAN:
						condition.put("orderStatus", 3);
						break;
					case APPLYRETURN:
						condition.put("orderStatus", 4);
						break;
    				}
    			}
    		} else if ("member".equals(character)) {
    			condition.put("memberId", member.getMember_id());
    		}
    		pw.println(gson.toJson(orderMasterService.countDataNum(condition)));
    	}
    	
    	String getOne = req.getParameter("getOne");
    	if (getOne != null) {
    		Integer orderId = Integer.valueOf(getOne);
    		OrderMaster om = orderMasterService.getOne(orderId);
    		pw.println(gson.toJson(om));
    		return;
    	}
    	
    	String searchUser = req.getParameter("searchUser");
    	if (searchUser != null) {
    		Integer offset = Integer.valueOf(req.getParameter("offset")) * 10;
    		Integer sortWay = Integer.valueOf(req.getParameter("sortWay"));
    		Map<String, Integer> limitOffset = new HashMap<>();
    		limitOffset.put("limit", 10);
    		limitOffset.put("offset", offset);
    		
    		pw.println(gson.toJson(orderMasterService.ambiguMemberNameSearch(searchUser, sortWay, limitOffset)));
    		return;
    		
    	}
    	
    	String searchLength = req.getParameter("searchLength");
    	if (searchLength != null) {
    		String keyword = req.getParameter("keyword");
    		pw.println(gson.toJson(orderMasterService.ambiguMemberNameSearchLength(keyword)));
    		return;
    	}
    	
    	String memberAll = req.getParameter("memberAll");
    	if (memberAll != null) {
    		String criteria = req.getParameter("criteria");
    		
    		Integer setNum = 0;
    		setNum = Integer.valueOf(memberAll);
    		
    		Integer criteriaNum = 1;
    		if (criteria != null) {
    			criteriaNum = Integer.valueOf(criteria);
    		}
    		
    		Map<String, Integer> limitOffset = new TreeMap<>();
    		limitOffset.put("LIMIT", 10);
    		limitOffset.put("OFFSET", setNum);
    		
    		Map<String, Integer> whereCondition = new HashMap<>();
    		
    		whereCondition.put("memberId", memberId);
    		
    		switch (criteriaNum) {
			case 1:
				break;
			case 2:
				whereCondition.put("payStatus", 1);
				break;
			case 3:
				whereCondition.put("payStatus", 2);
				break;
			case 4:
				whereCondition.put("deliverState", 1);
				break;
			case 5:
				whereCondition.put("deliverState", 0);
				break;
			}

    		List<MemberViewOrder> mvList = orderMasterService.memberOrderList(whereCondition, limitOffset);
    		pw.println(gson.toJson(mvList));
    		return;
    	}
    	
    	String nowBonus = req.getParameter("nowBonus");
    	if (nowBonus != null) {
    		pw.println(gson.toJson(member.getBonus()));
    		return;
    	}
    	
    	if (req.getParameter("getOneProduct") != null) {
			Integer productId = Integer.valueOf(req.getParameter("getOneProduct"));
			TransOrderProduct trpd = orderMasterService.getOneProduct(productId);
			pw.println(gson.toJson(trpd));
			return;
		}
		
    }
    
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter pw = res.getWriter();
		
		HttpSession httpSession = req.getSession();
		
		if (httpSession.getAttribute("member") == null) {
			Member failLogin = new Member();
			failLogin.setSuccessful(false);
			pw.println(gson.toJson(failLogin));
			return;
		}
		
		Member member = (Member)httpSession.getAttribute("member");
		Integer memberId = member.getMember_id();
		
		String demand = req.getParameter("demand");
		
		if ("checkOut".equals(demand)) {
			// 取得前端回傳購物列表(使用axios params傳參數會遇到[]中括號無法放在query string的異常問題(或許改tomcat設定就可解決?)
			// 因此改放在axios data，用reader讀取request body內部資料
			Reader rd = req.getReader();
			BufferedReader brd = new BufferedReader(rd);
			String reqStr = brd.readLine();
			reqStr = reqStr.substring(1, reqStr.length() - 1);
						
			int cardIndex = reqStr.indexOf("card");
			int addressIndex = reqStr.indexOf("address");
						
			// 取得購物明細傳輸商品類別物件
			String trObjStr = reqStr.substring(0, cardIndex - 2);
			trObjStr = trObjStr.substring(trObjStr.indexOf(":") + 1, trObjStr.length());
			List<TransOrderProduct> trObjList = gson.fromJson(trObjStr, new TypeToken<List<TransOrderProduct>>(){});	// 使用Gson轉為java List<T>
			
			List<TransOrderProduct> purchaseProducts = new ArrayList<>();
			for (TransOrderProduct trOdPd : trObjList) {
				if (trOdPd.isChecked() == true) {
					purchaseProducts.add(trOdPd);
				}
			}
						
			// 取得結帳信用卡資訊Json物件
			String cardStr = reqStr.substring(cardIndex  , addressIndex - 2);
			cardStr = cardStr.substring(cardStr.indexOf(":") + 1, cardStr.length());
			JsonObject cardDetail = JsonParser.parseString(cardStr).getAsJsonObject();
			
			// 取得配送地址資訊Json物件
			String addressStr = reqStr.substring(addressIndex, reqStr.length());
			addressStr = addressStr.substring(addressStr.indexOf(":") + 1, addressStr.length());
			JsonObject addressDetail = JsonParser.parseString(addressStr).getAsJsonObject();
			
			String commitType = req.getParameter("payment"); // 取得付款類別
			
			String pickType = req.getParameter("deliver");  // 取得取貨方式
			
			String discountRadio = req.getParameter("discountRadio");  // 取得所選擇的折購方式
			String couponCode = req.getParameter("couponCode");	// 取得輸入的優惠卷折購代碼
			String bonus = req.getParameter("bonus");	// 取得所使用的紅利
			
			OrderMaster om = orderMasterService.createNewOrderMaster(trObjList, cardDetail, addressDetail, member, commitType,
																				pickType, discountRadio, couponCode, bonus);
			
			orderMasterService.establishNewOrder(om, purchaseProducts, member);
			
			OrderMaster insertOk = orderMasterService.getOne(om.getOrderId());
			
			ResOrderMaster resOM = orderMasterService.getOrderResult(insertOk);
			
			pw.println(gson.toJson(resOM)); // 一律送出資料後由前端submit到ecpay
				
			// 寄送成功下單通知
			Notice notice = new Notice();
			notice.setMember_id(memberId);
			notice.setNotice_value("會員 " + member.getNick() + " 您已於" + om.getCommitDate() + "完成下單，訂單編號" + om.getOrderId());
			
			noticeService.addNotice(notice);
			return;
		}
		
		if ("updateOMfromManager".equals(demand)) {
			Reader rd = req.getReader();
			BufferedReader brd = new BufferedReader(rd);
			String reqStr = brd.readLine();
			
			String omStr = reqStr.substring(reqStr.indexOf(":") + 1, reqStr.length() - 1);
			
			OrderMaster fromManager = gson.fromJson(omStr, OrderMaster.class);
			
			pw.println(orderMasterService.updateFromManager(fromManager));
			
			Notice notice = new Notice();
			notice.setMember_id(memberId);
			Format sfm1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			notice.setNotice_value("會員 " + member.getNick() + " 您已於" + sfm1.format(new Date()) + "完成取消，訂單編號" + fromManager.getOrderId());
			
			noticeService.addNotice(notice);
			return;
		}
		
		if ("updateOMFromMember".equals(demand)) {
			Reader rd = req.getReader();
			BufferedReader brd = new BufferedReader(rd);
			String reqStr = brd.readLine();
			
			String omStr = reqStr.substring(reqStr.indexOf(":") + 1, reqStr.length() - 1);
			
			OrderMaster fromMember = gson.fromJson(omStr, OrderMaster.class);
			
			pw.println(orderMasterService.updateFromMember(fromMember));
			return;
		}

	}

}

