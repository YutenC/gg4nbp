package gg.nbp.web.SecondHand.buy.dto;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

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
	private String confirmDate;
	private String payState;
	private String approvalState;
	private String applyDate;
	private String applicantBankNumber;
	private List<SecondhandBuyPicture> image;
	private Integer progress;
	private Boolean agree;

	/*****************************************************
	 * 用來回應給會員前端頁面的物件 直接把SecondhandBuylist放到建構子裡面就行了
	 ***************************************************/
	public BuyEvent(SecondhandBuylist bs, MemberDao dao) {

		try {
			this.setEventId(bs.getBuylistId()).setMemberId(bs.getMemberId())
					.setMemberName(Toolbox.memberId2Name(memberId, dao)).setProductName(bs.getProductName())
					.setType(getTypeValue(bs.getType())).setContent(bs.getContent()).setEstimate(bs.getEstimate())
					.setPrice(bs.getPrice())
					.setConfirmDate(bs.getConfirmTime() == null ? null : Toolbox.dateformat(bs.getConfirmTime()))
					.setApprovalState(getApprovalState(Integer.parseInt(bs.getApprovalState())))
					.setPayState(getPayState(bs.getPayState()))
					.setApplyDate(bs.getApplyTime() == null ? null : Toolbox.dateformat(bs.getApplyTime()))
					.setApplicantBankNumber(bs.getApplicantBankNumber()).setImage(bs.getImage());
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
			return "未完成";
		}
	}

	private static String getTypeValue(String type) {
		String f = type.substring(0, 1);
		String b = type.substring(1, 2);
		String result;
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

	public static SecondhandBuylist trans4Mana(BuyEvent be, SecondHandBuylistDao dao) {
		SecondhandBuylist sl = dao.selectById(be.eventId);
		be.setApprovalState(getApprovalState(Integer.parseInt(sl.getApprovalState())))
				.setPayState(getPayState(sl.getPayState()));
		sl.setPrice(be.price < 0 ? null : be.price);
		sl.setConfirmTime(Toolbox.getNow());
		
		List<SecondhandBuyPicture> addPic = sl.getImage().stream()
					 									 .map(sp -> {
					 										 SecondhandBuyPicture ss = new SecondhandBuyPicture();
					 										 ss.setImage(sp.getImage());
					 										 return ss ;
					 									 })
					 									 .collect(Collectors.toList());
		sl.setImage(addPic);
		
		switch (be.progress) {
		case 0:
			if(be.agree) 
				sl.setApprovalState("1");
			else
				sl.setApprovalState("3");
			break;
		case 1:
			if(be.agree) {
				sl.setApprovalState("2");
				if(sl.getPrice() != null) 
					sl.setMessage("二手收購管理員以提供收購價，立即去看看價格滿不滿意吧˙");
			} 
			else
				sl.setApprovalState("4");
			break;
		case 2:
			if(sl.getPrice() != null) 
				sl.setMessage("二手收購管理員以提供收購價，立即去看看價格滿不滿意吧˙");
			
			break;
		case 3:
			if(be.agree) {
				sl.setPayState(2);
				sl.setMessage("您申請的案件已經付款囉，趕快去看看吧˙");				
			}
			break;
		case 4:
			
			break;

		default:
			break;
		}

		return sl;

	}

	public static SecondhandBuylist trans4Mem(BuyEvent be, SecondHandBuylistDao dao) throws SQLException,NullPointerException {

		SecondhandBuylist sl = dao.selectById(be.eventId);
		if(sl.getApprovalState().equals("7")) 
			sl.setApprovalState("0");
		
		
		
		
		be.setApprovalState(getApprovalState(Integer.parseInt(sl.getApprovalState())))
		  .setPayState(getPayState(sl.getPayState()));

		/* 如果訂單已經完成，不接受再執行更新 */
		if (sl.getPayState() > 0)
			throw new SQLException();
		

		sl.setProductName(be.productName);
		sl.setContent(be.content);
		String[] types = { "0", "1", "2", "3" };
		for (int i = 0; i < types.length; i++)
			for (int j = 0; j < types.length; j++)
				if (getTypeValue(types[i] + types[j]).equals(be.type))
					sl.setType(types[i] + types[j]);
		System.out.println(be.type);
		sl.setEstimate(be.estimate < 0 ? null : be.estimate);
		sl.setApplicantBankNumber(be.applicantBankNumber);
		sl.setImage(be.getImage());	
		
		if(sl.getPrice() == null && be.progress == 2)
			throw new NullPointerException();

		/* 如果賣家同意收購價，則將付款狀態改為待付款 */
		if (be.progress == 2 && be.agree)
			sl.setPayState(1);
		else
			sl.setPrice(null);

		return sl;
	}

	private BuyEvent setEventId(Integer eventId) {
		this.eventId = eventId;
		return this;
	}

	private BuyEvent setMemberId(Integer memberId) {
		this.memberId = memberId;
		return this;
	}

	private BuyEvent setMemberName(String memberName) {
		this.memberName = memberName;
		return this;
	}

	private BuyEvent setProductName(String productName) {
		this.productName = productName;
		return this;
	}

	private BuyEvent setType(String type) {
		this.type = type;
		return this;
	}

	private BuyEvent setContent(String content) {
		this.content = content;
		return this;
	}

	private BuyEvent setEstimate(Integer estimate) {
		this.estimate = estimate;
		return this;
	}

	private BuyEvent setPrice(Integer price) {
		this.price = price;
		return this;
	}

	private BuyEvent setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
		return this;
	}

	private BuyEvent setPayState(String payState) {
		this.payState = payState;
		switch (payState) {
		case "待付款":
			setProgress(3);
			break;
		case "已付款":
			setProgress(4);
			setApprovalState("已完成");
			break;
		default:
			break;
		}
		return this;
	}

	private BuyEvent setApprovalState(String approvalState) {
		this.approvalState = approvalState;
		switch (approvalState) {
		case "待審核":
			setProgress(0);
			break;
		case "查驗中":
			setProgress(1);
			break;
		case "議價中":
			setProgress(2);
			break;
		case "非收購品項":
			setProgress(500);
			break;
		case "不合格":
			setProgress(404);
			break;
		default:
			break;
		}
		return this;
	}

	private BuyEvent setApplyDate(String applyDate) {
		this.applyDate = applyDate;
		return this;
	}

	private BuyEvent setApplicantBankNumber(String applicantBankNumber) {
		this.applicantBankNumber = applicantBankNumber;
		return this;
	}

	private BuyEvent setImage(List<SecondhandBuyPicture> image) {
		this.image = image;
		return this;
	}

	private BuyEvent setProgress(Integer progress) {
		this.progress = progress;
		return this;
	}
	
	public static int getProgress(SecondhandBuylist sl) {
		return new BuyEvent()
					.setApprovalState(getApprovalState(Integer.parseInt(sl.getApprovalState())))
					.setPayState(getPayState(sl.getPayState()))
					.getProgress();
	}

}
