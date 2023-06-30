package gg.nbp.web.shop.shopproduct.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductSelect {

    String msg;
    List<Condition> conditions;
    List<Condition> sqlConditions;

    @Getter
    @Setter
    public class Condition{
        String key;
        Object value;
    }



}
