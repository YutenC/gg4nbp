package gg.nbp.web.SecondHand.buy.dto;

import java.sql.Timestamp;
import java.util.List;

import gg.nbp.core.pojo.Core;
import gg.nbp.web.Member.dao.MemberDao;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuyPicture;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.dao.SecondHandBuylistDao;
import gg.nbp.web.SecondHand.buy.util.Toolbox;
import lombok.Getter;


@Getter
public class BuyEvent extends Core {
	private static final long serialVersionUID = -7518578406780806592L;
	private Integer eventId;
	private Integer memberId;
	private String memberName;
	private String productName;
	private String type;
	private String content;
	private Integer estimate;
	private Integer price;
	private Timestamp confirmTime;
	private String confirmDate;
	private String payState;
	private String approvalState;
	private Timestamp applyTime;
	private String applyDate;
	private String applicantBankNumber;
	private List<SecondhandBuyPicture> image;

	/*****************************************************
	 * 用來回應給會員前端頁面的物件 直接把SecondhandBuylist放到建構子裡面就行了
	 ***************************************************/
	public BuyEvent(SecondhandBuylist bs,MemberDao dao) {

		try {
			eventId = bs.getBuylistId();
			memberId = bs.getMemberId();
			memberName = Toolbox.memberId2Name(bs.getMemberId(),dao);
			productName = bs.getProductName();
			type = getTypeValue(bs.getType());
			content = bs.getContent();
			estimate = bs.getEstimate();
			price = bs.getPrice();
			confirmTime = bs.getConfirmTime();
			confirmDate = confirmTime == null ? null : Toolbox.dateformat(confirmTime);
			payState = getPayState(bs.getPayState());
			approvalState = getApprovalState(Integer.parseInt(bs.getApprovalState()));
			applyTime = bs.getApplyTime();
			applyDate = applyTime == null ? null :Toolbox.dateformat(applyTime);
			applicantBankNumber = bs.getApplicantBankNumber();
			image = bs.getImage();
			this.setSuccessful(true);
			this.setMessage("成功");

		} catch (Exception e) {
			e.printStackTrace();
			this.setSuccessful(false);
			this.setMessage("查詢失敗");
		}
	}
	
	
	
	
	

	public BuyEvent() {

	}

	private static String getPayState(Integer i) {
		switch (i) {
		case 0:
			return "待審核";
		case 1:
			return "待付款";
		case 2:
			return "已付款";
		default:
			return "錯誤";
		}
	}

	private static String getApprovalState(Integer i) {
		switch (i) {
		case 0:
			return "待審核";
		case 1:
			return "查驗中";
		case 2:
			return "議價中";
		case 3:
			return "非收購品項";
		case 4:
			return "不合格品";
		case 5:
			return "已退回";
		case 6:
			return "已完成";
		default:
			return "錯誤";
		}
	}

	private static String getTypeValue(String type) {
		String f = type.substring(0, 1);
		String b = type.substring(1, 2);
		String result ;
		switch (f) {
		case "1":
			result = "任天堂";
			break;
		case "2":
			result = "Sony";
			break;
		case "3":
			result = "XBOX";
			break;
		default:
			result = "其他";
			break;
		}
		result += " / ";
		switch (b) {
		case "0":
			result += "主機";
			break;
		case "1":
			result += "手把";
			break;
		case "2":
			result += "光碟、卡帶";
			break;
		default:
			result += "周邊";
			break;
		}
		
		return result;
		
	}

	@Override
	public String toString() {
		return "BuyEvent [eventId=" + eventId + ", memberName=" + memberName + ", productName=" + productName
				+ ", type=" + type + ", content=" + content + ", estimate=" + estimate + ", price=" + price
				+ ", confirmTime=" + confirmTime + ", payState=" + payState + ", approvalState=" + approvalState
				+ ", applyTime=" + applyTime + ", applicantBankNumber=" + applicantBankNumber + ", image=" + image
				+ "]";
	}

	public static SecondhandBuylist trans4Mana(BuyEvent be ,SecondHandBuylistDao dao ) {
		SecondhandBuylist sl = dao.selectById(be.eventId);
		sl.setPrice(be.price < 0  ? null : be.price);
		sl.setConfirmTime(be.confirmTime);
		for(int i = 0 ; i < 3 ; i++) {
			if(getPayState(i).equals(be.payState)) {
				sl.setPayState(i);
			} 
		}
		
		for(int i = 0 ; i < 7 ; i++) {
			if(getApprovalState(i).equals(be.approvalState)) {
				sl.setApprovalState(i+"");
			} 
		}
		
		return sl ;
		
		
	}

	
	public static SecondhandBuylist trans4Mem(BuyEvent be ,SecondHandBuylistDao dao ) {
		SecondhandBuylist sl = dao.selectById(be.eventId);
		sl.setProductName(be.productName);
		sl.setContent(be.content);
		String[] types = {"0","1","2","3"};
		for (int i = 0; i < types.length; i++) 
			for (int j = 0; j < types.length; j++) 
				if(getTypeValue(types[i]+types[j]).equals(be.type)) 
					sl.setType(types[i]+types[j]);
		sl.setEstimate(be.estimate < 0 ? null : be.estimate);
		sl.setApplicantBankNumber(be.applicantBankNumber);
		return sl ;
	}
	
}
