package gg.nbp.web.Member.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import gg.nbp.web.Member.dao.CreditDao;
import gg.nbp.web.Member.entity.Member_credit;
import jakarta.persistence.PersistenceContext;


@Repository
public class CreditDapImpl implements CreditDao {
	@PersistenceContext
	private Session session;
    @Override
    public List<Member_credit> selectAll() {
        final String hql = "FROM Member_credit ORDER BY member_id";
        return session.createQuery(hql, Member_credit.class).getResultList();
    }

    @Override
    public int insert(Member_credit member_credit) {
        session.persist(member_credit);
        return member_credit.getCredit_id();
    }

    @Override
    public int deleteById(Integer id) {
        Member_credit member_credit = session.get(Member_credit.class, id);
        session.remove(member_credit);
//        session.getTransaction().commit();
        member_credit.setMessage("刪除成功");
        return member_credit.getCredit_id();
    }

    @Override
    public int update(Member_credit member_credit) {
        final StringBuilder hql = new StringBuilder()
                .append("UPDATE Member_credit SET ");
        final String creditNumber = member_credit.getCred_number();
        if(creditNumber != null && !creditNumber.isEmpty()){
            hql.append("cred_number = :ocred_number, ");
        }
        hql.append("WHERE cred_number = :cred_number");

        @SuppressWarnings("deprecation")
		Query<?> query = session.createQuery(hql.toString());
        if(creditNumber != null && !creditNumber.isEmpty()){
            query.setParameter("cred_number", creditNumber);
        }
        return query
                .setParameter("cred_number", member_credit.getCred_number())
                .executeUpdate();
    }

    @Override
    public Member_credit selectById(Integer id) {
        return session.get(Member_credit.class, id);
    }

    @Override
    public Member_credit selectByBankNumber(String cred_number) {
        final String sql = "FROM Member_credit where cred_number = :cred_number";
        return session
                .createNativeQuery(sql, Member_credit.class)
                .setParameter("cred_number", cred_number)
                .uniqueResult();
    }
}
