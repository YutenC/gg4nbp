package gg.nbp.web.shop.shopproduct.core;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.PersistenceContext;

public abstract class CoreDaoImpl<P, I> implements CoreDao <P, I>{
    private Class<?> entityClass;
    private String entityName;

    @PersistenceContext
    protected Session session;
    public CoreDaoImpl(){
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        try {
            entityClass = Class.forName(types[0].getTypeName());
            entityName=types[0].getTypeName();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
	@Override
    public I insert(P pojo) {
//        Session session = getSession();
//        Transaction transaction= session.beginTransaction();
//        session.persist(pojo);


//        transaction.commit();
        return (I)session.save(pojo);
    }

    @Override
    public int deleteById(I id) {
//        Session session = getSession();
        Object coupon = session.get(entityClass, (Serializable) id);
        session.remove(coupon);
        return 0;
    }

    @Override
    public int update(P pojo) {
        return 0;
    }

    @Override
    public P selectById(I id) {
//        Session session = getSession();
        @SuppressWarnings("unchecked")
		P coupon = (P) session.get(entityClass, (Serializable)id);
        return coupon;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<P> selectAll() {
        String hql = "from "+entityName;

        return (List<P> )session.createQuery(hql,entityClass).getResultList();
    }
}
