package gg.nbp.web.SecondHand.buy.VO;

import java.sql.Timestamp;
import java.util.List;

import gg.nbp.core.pojo.Core;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecondhandBuylist extends Core {
	private static final long serialVersionUID = -1589649673055389103L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "buylist_id")
	private Integer buylistId	;
	
	@Column(name = "member_id")
	private Integer memberId;
	
	@Column(name = "manager_id")
	private Integer managerId;
	
	@Column(name = "product_name")
	private String productName;
	
	
	private String type;
	
	private String content;
	
	private Integer estimate;
	
	private Integer price;
	
	@Column(name = "confirm_time")
	private Timestamp confirmTime;
	
	@Column(name = "pay_state")
	private Integer payState;
	
	@Column(name = "approval_state")
	private String approvalState;
	
	@Column(name = "apply_time" , insertable = false)
	private Timestamp applyTime;
	
	@Column(name = "applicant_bank_number")
	private String applicantBankNumber;

	@Transient
	@OneToMany
	@JoinColumn(name = "buylist_id",referencedColumnName = "buylist_id")
	private List<SecondhandBuyPicture> image ;

	@Override
	public String toString() {
		return "SecondHandBuylist [buylistId=" + buylistId + ", memberId=" + memberId + ", managerId=" + managerId
				+ ", productName=" + productName + ", type=" + type + ", content=" + content + ", estimate=" + estimate
				+ ", price=" + price + ", confirmTime=" + confirmTime + ", payState=" + payState + ", approvalState="
				+ approvalState + ", applyTime=" + applyTime + ", applicantBankNumber=" + applicantBankNumber
				+ ", image=" + image + "]";
	}
		
	
		
}
