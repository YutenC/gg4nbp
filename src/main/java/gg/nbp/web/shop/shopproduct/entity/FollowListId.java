package gg.nbp.web.shop.shopproduct.entity;

import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class FollowListId implements Serializable {
    private static final long serialVersionUID = -1510792738718142346L;
    @Column(name = "Member_id", nullable = false)
    private Integer memberId;

    @Column(name = "Product_id", nullable = false)
    private Integer productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FollowListId entity = (FollowListId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.memberId, entity.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, memberId);
    }

}