package gg.nbp.web.shop.shopproduct.core;


import java.util.List;

public interface CoreDao<P, I> {

    I insert(P pojo);

    int deleteById(I id);

    int update(P pojo);

    P selectById(I id);

    List<P> selectAll();

//    default Session getSession() {
//        return HibernateUtil.getSessionFactory().getCurrentSession();
////        return HibernateUtil.getSessionFactory().openSession();
//    }

}

