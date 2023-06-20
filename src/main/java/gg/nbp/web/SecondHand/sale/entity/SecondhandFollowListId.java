package gg.nbp.web.SecondHand.sale.entity;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.Hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class SecondhandFollowListId implements Serializable {
    private static final long serialVersionUID = -6176588184455600133L;
    @Column(name = "Product_id", nullable = false)
    private Integer productId;

    @Column(name = "Member_id", nullable = false)
    private Integer memberId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SecondhandFollowListId entity = (SecondhandFollowListId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.memberId, entity.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, memberId);
    }

}