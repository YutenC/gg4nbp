package gg.nbp.web.shop.shopproduct.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DaoConditionSelect {
    String msg;
    List<ProductSelect.Condition> conditions;
    List<ProductSelect.Condition> sqlConditions;
    List<String> required;
    ProductSelect.Condition sort;

    @Getter
    @Setter
    public class Condition{
        String action;
        String key;
        Object value;
    }

}
