package gg.nbp.web.Member.entity;

import java.util.Date;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notice", schema = "five")
public class Notice extends Core {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer notice_id;
    @Column
    private String notice_value;
    @Column
    private Integer member_id;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column
    private Date notice_time;
    @Column
    private Integer is_read;
}
