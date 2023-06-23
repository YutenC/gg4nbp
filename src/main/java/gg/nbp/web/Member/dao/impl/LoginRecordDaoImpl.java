package gg.nbp.web.Member.dao.impl;

import gg.nbp.web.Member.dao.LoginRecordDao;
import gg.nbp.web.Member.entity.Login_record;
import jakarta.persistence.PersistenceContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class LoginRecordDaoImpl implements LoginRecordDao {
    @PersistenceContext
    private Session session;
    @Override
    public Integer insert(Login_record loginRecord) {
        try{
            session.persist(loginRecord);
            return loginRecord.getLogin_id();
        }catch (HibernateException e){
            e.printStackTrace();
        }
        return null;
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
        try{
            Login_record login_record = session.getReference(Login_record.class, loginRecord.getMember_id());
            session.remove(login_record);
            return true;
        }catch (HibernateException e){
            e.printStackTrace();
        }
        return false;
    }
}
