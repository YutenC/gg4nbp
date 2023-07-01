package gg.nbp.web.Member.entity;


import java.io.Serial;
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
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "login_record", schema = "five")
public class Login_record extends Core {
    @Serial
    private static final long serialVersionUID = 2613430298482970208L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer login_id;
    @Column
    private Integer member_id;
    @Column
    private String login_device;
    @Column
    private String host_name;
    @Column
    private String login_city;
    @Column
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date login_time;
}
