package gg.nbp.web.Forum.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "article" ,schema = "five")
public class Article implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7628276938414018860L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Article_id")
	private Integer articeId; //文章編號
	@Column(name = "Member_id")
	private Integer memberId; //發文會員
	@Column(name = "Article_class")
	private String articeClass; 
	//文章類別(最新消息/心得分享/問題交流/新聞專區)
	@Column(name = "Article_title")
	private String articeTitle;//文章標題
	@Column(name = "Article_value")
	private String articeValue;//文章內容
	@Column(name = "Post_Time")
	private Timestamp postTime;
	@Column(name = "Change_Time")
	private Timestamp changeTime;
	@Column(name = "State")
	private Integer state;
	@Column(name = "Manager_id")
	private Integer delFrom;
	@Column(name = "Theme_id")//主題(01PS/02Xbox/03NS)
	private Integer themeId;

}
