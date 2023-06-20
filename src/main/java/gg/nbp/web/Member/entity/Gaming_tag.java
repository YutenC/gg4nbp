package gg.nbp.web.Member.entity;

import gg.nbp.core.pojo.Core;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "gaming_tag", schema = "five")
public class Gaming_tag extends Core {
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private Integer member_id;
    @Id
    @Column
    private Integer tag_id;
}
