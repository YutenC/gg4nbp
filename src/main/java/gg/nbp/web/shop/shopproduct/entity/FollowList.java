package gg.nbp.web.shop.shopproduct.entity;


import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "follow_list", schema = "five")
public class FollowList {


    @EmbeddedId
    private FollowListId id;

}