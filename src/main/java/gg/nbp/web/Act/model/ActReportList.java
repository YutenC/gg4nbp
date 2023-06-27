package gg.nbp.web.Act.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Act_report_list", schema = "five2")
public class ActReportList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Report_id", nullable = false)
    private Integer id;

    @Column(name = "Reported_Name", length = 30)
    private String reportedName;

    @Lob
    @Column(name = "Report_content", nullable = false)
    private String reportContent;

    @Column(name = "Report_time")
    private Date reportTime;

    @Column(name = "Report_Image", length = 2048)
    private String reportImage;

    @Column(name = "Review_state", nullable = false)
    private Byte reviewState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Act_id", referencedColumnName = "Act_id")
    private Act act;

}