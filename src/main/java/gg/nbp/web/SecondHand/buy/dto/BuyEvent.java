package gg.nbp.web.SecondHand.buy.dto;

import java.util.Date;
import java.util.List;

import gg.nbp.core.pojo.Core;
import gg.nbp.web.Member.dao.MemberDao;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuyPicture;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.util.Toolbox;
import lombok.Getter;


@Getter
public class BuyEvent extends Core {
	private static final long serialVersionUID = -7518578406780806592L;
	private Integer eventId;
	private String memberName;
	private String productName;
	private String type;
	private String content;
	private Integer estimate;
	private Integer price;
	private Date confirmTime;
	private String payState;
	private String approvalState;
	private Date applyTime;
	private String applicantBankNumber;
	private List<SecondhandBuyPicture> image;

	/*****************************************************
	 * 用來回應給會員前端頁面的物件 直接把SecondhandBuylist放到建構子裡面就行了
	 ***************************************************/
	public BuyEvent(SecondhandBuylist bs,MemberDao dao) {

		try {
			eventId = bs.getBuylistId();
			memberName = Toolbox.memberId2Name(bs.getMemberId(),dao);
			productName = bs.getProductName();
			type = getTypeValue(bs.getType());
			content = bs.getContent();
			estimate = bs.getEstimate();
			price = bs.getPrice();
			confirmTime = bs.getConfirmTime();
			payState = getPayState(bs.getPayState());
			approvalState = getApprovalState(bs.getApprovalState());
			applyTime = bs.getApplyTime();
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

	private String getPayState(Integer i) {
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

	private String getApprovalState(String str) {
		switch (str) {
		case "0":
			return "待審核";
		case "1":
			return "接受預估價";
		case "2":
			return "議價中";
		case "3":
			return "非收購品項";
		case "4":
			return "不合格品";
		case "5":
			return "已退回";
		case "6":
			return "已完成";
		default:
			return "錯誤";
		}
	}

	private String getTypeValue(String type) {
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

	

}
