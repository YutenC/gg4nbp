package gg.nbp.web.Member.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import gg.nbp.core.pojo.Core;
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
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member", schema = "five")
public class Member extends Core {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer member_id;
    // 會員編號

    @Column
    private String account;
    // 會員帳號

    @Column
    private String password;
    // 會員密碼

    @Column
    private String nick;
    // 會員暱稱

    @Column
    private String email;
    // 會員Email

    @Column
    private String phone;
    // 會員手機號碼

    @Column
    private java.sql.Date birth;
    // 會員生日

    @Column
    private String id_number;
    // 會員身證證字號

    @Column
    private String address;
    // 會員地址

    @Column
//            (nullable = true, columnDefinition = "double default 0.00")
    private Double bonus;
    // 累積紅利

    @Column
//            (columnDefinition = "int default 0")
    private Integer member_ver_state;
    // 會員驗證狀態

    @Column
//            (nullable = true)
    private Date suspend_deadline;
    // 會員停權期限

    @Column
//            (nullable = true)
    private String headshot;
    // 大頭照

    @Column
//            (nullable = true)
    private Date ver_deadline;
    // 驗證信到期時間  可不在這做，可以存到redis就好
    // sql.Date 只存時間到日；util.Date 會存時間精準到毫秒

    @Column
//            (columnDefinition = "int default 0")
    private Integer violation;
    // 被檢舉次數
}
