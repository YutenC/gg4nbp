package gg.nbp.web.Act.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Act", schema = "five")
public class Act {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Act_id", nullable = false)
    private Integer Id;

    @Column(name = "Act_name", nullable = false, length = 30)
    private String actName;

    @Column(name = "Act_time", nullable = false)
    private Date actTime;

    @Column(name = "Act_location", length = 100)
    private String actLocation;

    @Column(name = "Act_quota", columnDefinition = "int UNSIGNED")
    private Long actQuota;

    @Column(name = "Act_price", columnDefinition = "int UNSIGNED")
    private Long actPrice;

    @Lob
    @Column(name = "Act_value", nullable = false)
    private String actValue;

    @Column(name = "Invite_Target", nullable = false)
    private Byte inviteTarget;

    @Column(name = "Organizer_bank_num", length = 14)
    private String organizerBankNum;

    @Column(name = "Del_from")
    private Integer delFrom;

    @Column(name = "Start_time", nullable = false)
    private Date startTime;

    @Column(name = "End_time", nullable = false)
    private Date endTime;

    @Column(name = "Act_Image", length = 2048)
    private String actImage;

    @Column(name = "Organizer_id")
    private  Integer OrganizerId;


}
