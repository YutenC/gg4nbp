package gg.nbp.web.shop.shopproduct.core;

import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

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

    @Override
    public I insert(P pojo) {
        return (I)session.save(pojo);
    }

    @Override
    public int deleteById(I id) {
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
        P coupon = (P) session.get(entityClass, (Serializable)id);
        return coupon;
    }

    @Override
    public List<P> selectAll() {
        String hql = "from "+entityName;
        return (List<P> )session.createQuery(hql,entityClass).getResultList();
    }
}
