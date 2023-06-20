package gg.nbp.web.Member.entity;


import java.util.Date;

import gg.nbp.core.pojo.Core;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "login_record", schema = "five")
public class Login_record extends Core {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer login_id;
    @Id
    @Column
    private Integer member_id;
    @Column
    private String login_device;
    @Column
    private String host_name;
    @Column
    private String login_city;
    @Column
    private Date login_time;
}
