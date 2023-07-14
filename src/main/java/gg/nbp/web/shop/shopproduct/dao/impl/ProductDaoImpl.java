package gg.nbp.web.shop.shopproduct.dao.impl;

import gg.nbp.web.shop.shopproduct.core.CoreDaoImpl;
import gg.nbp.web.shop.shopproduct.dao.ProductDao;
import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.pojo.ProductSelect;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ProductDaoImpl extends CoreDaoImpl<Product, Integer> implements ProductDao {

    @PersistenceContext
    private Session session;

    @Override
    public int update(Product product) {
        final StringBuilder hql = new StringBuilder()
                .append("UPDATE Product SET ");

        final String productName = product.getProductName();

        hql.append("productName = :productName,")
                .append("type = :type,")
                .append("price = :price,")
                .append("amount = :amount,")
                .append("buyTimes = :buyTimes,")
                .append("brand = :brand,")
                .append("rate = :rate,")
                .append("revieweCount = :revieweCount,")
                .append("content = :content,")
                .append("state = :state, ")
                .append("launchTime = :launchTime ");

        if (product.getTakeoffTime() != null) {
            hql.append(", takeoffTime = :takeoffTime ");
        }


        hql.append("WHERE id = :id");
        Query<?> query = session.createQuery(hql.toString());

        query.setParameter("productName", product.getProductName())
                .setParameter("type", product.getType())
                .setParameter("price", product.getPrice())
                .setParameter("amount", product.getAmount())
                .setParameter("buyTimes", product.getBuyTimes())
                .setParameter("brand", product.getBrand())
                .setParameter("rate", product.getRate())
                .setParameter("revieweCount", product.getRevieweCount())
                .setParameter("content", product.getContent())
                .setParameter("state", product.getState())
                .setParameter("launchTime", product.getLaunchTime())
                .setParameter("id", product.getId());

        if (product.getTakeoffTime() != null) {
            query.setParameter("takeoffTime", product.getTakeoffTime());
        }

        return query.executeUpdate();

    }


    @Override
    public List<Product> selectByCondition(ProductSelect productSelect) {

        String conditionStr = "";
        String conditionStateStr="";
        List<Double> stateArray=new ArrayList<>();
        int stateNum=0;
        boolean other_flag=false;
        if (productSelect.getConditions() != null) {
            for (int i = 0; i < productSelect.getConditions().size(); i++) {
                ProductSelect.Condition condition = productSelect.getConditions().get(i);
                if (i == 0) {
                    conditionStr += " where ";
                }
                else if("state".equals(condition.getKey())){
//                    if(stateNum>=1){
//                        conditionStateStr += " OR ";
//                    }
//                    else{
//                        conditionStateStr += " AND ";
//                    }
                }
                else {
                    if(other_flag){
                        conditionStr += " AND ";
                    }


                }


                if("state".equalsIgnoreCase(condition.getKey())){
                        stateArray.add((Double) condition.getValue());
                        stateNum++;
                }
                else if (condition.getAction() == null || "=".equals(condition.getAction())) {
//                    if("state".equalsIgnoreCase(condition.getKey())){
//                        stateArray.add((Double) condition.getValue());
//                        stateNum++;
////                        conditionStateStr+=" " + condition.getKey() + " = " + condition.getValue() + " ";
//                    }
//                    else{
//
//                    }
                    conditionStr += " " + condition.getKey() + " = " + condition.getValue() + " ";

                    other_flag=true;
                }
                else if ("like".equals(condition.getAction())) {
                    conditionStr += " " + condition.getKey() + " like '%" + condition.getValue() + "%' ";
                    other_flag=true;
                }

            }

            if(stateNum!=0){

                conditionStateStr+="(";

                for (int i=0;i<stateArray.size();i++) {
                    if(i>0){
                        conditionStateStr+=" or ";
                    }
                    Double v=stateArray.get(i);
                    conditionStateStr += " " + "state" + " = " + v + " ";
                }

                conditionStateStr+=")";

                if(other_flag){
                    conditionStr=conditionStr +"  AND "+ conditionStateStr;
                }
                else{
                    conditionStr+= conditionStateStr;
                }

            }
        }

        String hql = "from Product  " + conditionStr;

        Integer limit = -1;
        Integer offset = -1;
        String hql2 = "";
        if (productSelect.getSqlConditions() != null) {
            for (int i = 0; i < productSelect.getSqlConditions().size(); i++) {
                ProductSelect.Condition condition = productSelect.getSqlConditions().get(i);
                if ("limit".equals(condition.getKey())) {
                    Object oValue = condition.getValue();
                    if(oValue!=null){
                        limit = ((Double) oValue).intValue();
                    }
                } else if ("offset".equals(condition.getKey())) {
                    Object oValue = condition.getValue();
                    offset = ((Double) oValue).intValue();
                }
            }
        }


        String hql3 = "";
        if (productSelect.getSort() != null) {
            ProductSelect.Condition condition = productSelect.getSort();

            if ("order".equalsIgnoreCase(condition.getAction())) {
                if (condition.getKey() == null || "".equals(condition.getKey()) || "asc".equalsIgnoreCase(condition.getKey())) {
                    hql3 = "order by " + condition.getValue() + " ";
                } else if ("desc".equalsIgnoreCase(condition.getKey())) {
                    hql3 = "order by " + condition.getValue() + " " + "DESC";
                }
            }

        }

        hql = hql + hql2+hql3;
//        System.out.println(hql);
        Query<Product> query;
        if (limit == -1) {
            query = session.createQuery(hql, Product.class);
        } else {
            query = session.createQuery(hql, Product.class).setMaxResults(limit);
        }

        if (offset != -1) {
            query.setFirstResult(offset);
        }
        return query.getResultList();
    }

    @Override
    public List<Product> selectByType(Integer type) {
        String hql = "from Product  where type = " + type;
//        System.out.println("hql: " + hql);
        return session.createQuery(hql, Product.class).getResultList();
    }

    @Override
    public List<Product> selectByBuyTimes(Integer type) {
        String hql;
        if ("".equals(type) || "0".equals(type)) {
            hql = "from Product " + "order by buyTimes DESC";
        } else {
            hql = "from Product  where type = " + type + "order by buyTimes DESC";
        }

//        System.out.println("hql: "+hql);
        return session.createQuery(hql, Product.class).getResultList();
    }

    @Override
    public List<Product> selectByBuyTimes(Integer limit, Integer type) {
        String hql;
        if (type == -1) {
            hql = "from Product " + "order by buyTimes DESC";
        } else {
            hql = "from Product where type = " + type + "order by buyTimes DESC";
        }

        if (limit == -1) {
            return session.createQuery(hql, Product.class).getResultList();
        } else {
            return session.createQuery(hql, Product.class).setMaxResults(limit).getResultList();
        }

//        System.out.println("hql: "+hql);

    }

    @Override
    public List<Product> searchProducts(String search) {
        String hql = "from Product  where state=1 AND productName like '%" + search + "%'";
//        System.out.println("hql: "+hql);
        return session.createQuery(hql, Product.class).getResultList();
    }


    @Override
    public int updateProductScore(Product product) {
        final StringBuilder hql = new StringBuilder()
                .append("UPDATE Product SET ");

        hql.append("rate = :rate,")
                .append("revieweCount = :revieweCount ")
                .append("WHERE id = :id");

        Query<?> query = session.createQuery(hql.toString());

        return query
                .setParameter("rate", product.getRate())
                .setParameter("revieweCount", product.getRevieweCount())
                .setParameter("id", product.getId())
                .executeUpdate();

    }

    @Override
    public int updateProductAmountBuyTimes(Product product) {
        final StringBuilder hql = new StringBuilder()
                .append("UPDATE Product SET ");


        hql.append("amount = :amount,")
                .append("buyTimes = :buyTimes ")
                .append("WHERE id = :id");


        Query<?> query = session.createQuery(hql.toString());

        return query
                .setParameter("amount", product.getAmount())
                .setParameter("buyTimes", product.getBuyTimes())
                .setParameter("id", product.getId())
                .executeUpdate();
    }

    @Override
    public int updateProductState(Product product) {
        final StringBuilder hql = new StringBuilder()
                .append("UPDATE Product SET ");

        hql.append("state = :state ")
                .append("WHERE id = :id");

        Query<?> query = session.createQuery(hql.toString());

        return query
                .setParameter("state", product.getState())
                .setParameter("id", product.getId())
                .executeUpdate();

    }

}
