package gg.nbp.web.Member.dao.impl;

import gg.nbp.web.Member.dao.LoginRecordDao;
import gg.nbp.web.Member.entity.Login_record;
import jakarta.persistence.PersistenceContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoginRecordDaoImpl implements LoginRecordDao {
    @PersistenceContext
    private Session session;

    @Override
    public int insert(Login_record loginRecord) {
        session.persist(loginRecord);
        return loginRecord.getLogin_id();
    }

    @Override
    public List<Login_record> selectByMemberId(Login_record loginRecord) {
//        return session.createQuery("FROM Login_record ", Login_record.class).getResultList();
        final String sql = "SELECT * FROM Login_record WHERE member_id = :member_id ORDER BY login_time DESC";
        return session.createNativeQuery(sql, Login_record.class)
                .setParameter("member_id", loginRecord.getMember_id())
                .getResultList();
    }

    @Override
    public Boolean delectByMemberId(Login_record loginRecord) {
//        try {
//            Login_record login_record = session.getReference(Login_record.class, loginRecord.getMember_id());
//            session.remove(login_record);
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        return false;
//      上述語法有錯是因為 使用hibernate的getReferance必須要對應到主鍵值才能進行刪除，像member_id不是主鍵值，就必須用下面的寫法
        try {
            String hql = "DELETE FROM Login_record WHERE member_id = :member_id";
//            Query query = session.createQuery(hql);
//            query.setParameter("member_id", loginRecord.getMember_id());

//            // 先查詢member_id後再執行刪除

//            int resultList = query.executeUpdate();
//            return resultList > 0;

            // 可簡化成
            return session.createQuery(hql)
                    .setParameter("member_id", loginRecord.getMember_id())
                    .executeUpdate() > 0;

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }
}
