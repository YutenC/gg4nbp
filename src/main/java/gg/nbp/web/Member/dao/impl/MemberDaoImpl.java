package gg.nbp.web.Member.dao.impl;

import java.sql.Date;

//import static core.util.CommonUtil.getConnection;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import gg.nbp.web.Member.dao.MemberDao;
import gg.nbp.web.Member.entity.Member;
import jakarta.persistence.PersistenceContext;

@Repository
public class MemberDaoImpl implements MemberDao {
	
	
	@PersistenceContext
	private Session session;

//    Session session = session;

    @Override
    public int insert(Member member) {

//        Transaction transaction = session.beginTransaction();   // 從session來，開始交易
//        // 基本上上面3行是固定
        session.persist(member);    // persist(填入要insert的物件)
//        transaction.commit();   //
        return member.getMember_id();
    }

    @Override
    public int deleteById(Integer id) {
//        session.beginTransaction();
        // 不能直接用session.remove(id)，因為括弧內要放的想要刪除的物件
        // 所以要把刪除整筆資料時需要把整個想要刪除的Member物件放進來
        // 要"先查詢要刪除的會員id"，"再用該id指定給session，刪除該會員"
        Member member = session.get(Member.class, id);
        session.remove(member);
//        session.getTransaction().commit();
        member.setMessage("刪除成功");
        return member.getMember_id();
    }


    @Override
    public int update(Member member) {  // 修改會員資料

        final StringBuilder hql = new StringBuilder()
                .append("UPDATE Member SET ");

        final String password = member.getPassword();
        
        final Date suspend_deadline = member.getSuspend_deadline();
        
        if (password != null && !password.isEmpty()) {
            hql.append("password = :password, ");
        }
        
//        if (suspend_deadline != null) {
            hql.append("suspend_deadline = :suspend_deadline, ");
//        }
        
        hql.append("email = :email, ")
                .append("phone = :phone, ")
                .append("address = :address, ")
                .append("bonus = :bonus, ")
                .append("member_ver_state = :member_ver_state, ")
                .append("headshot = :headshot, ")
                .append("violation = :violation ")
                .append("where account = :account");

        Query<?> query = session.createQuery(hql.toString());
        if (password != null && !password.isEmpty()) {
            query.setParameter("password", password);
        }
        
//        if (suspend_deadline != null) {
            query.setParameter("suspend_deadline", suspend_deadline);
//        }
        
        return query
                .setParameter("email", member.getEmail())
                .setParameter("phone", member.getPhone())
                .setParameter("address", member.getAddress())
                .setParameter("bonus", member.getBonus())
                .setParameter("member_ver_state", member.getMember_ver_state())
                .setParameter("headshot", member.getHeadshot())
                .setParameter("violation", member.getViolation())
                .setParameter("account", member.getAccount())
                .executeUpdate();
    }

    @Override
    public Member selectById(Integer id) {
        return session.get(Member.class, id);
    }

    @Override
    public List<Member> selectAll() {
        // 老師寫法
        final String hql = "FROM Member ORDER BY member_id";
        return session.createQuery(hql, Member.class).getResultList();
    }

    @Override
    public Member selectByAccount(String account) {
//          老師的寫法
//        Session session = session;
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
//        Root<Member> root = criteriaQuery.from(Member.class);
//        criteriaQuery.where(criteriaBuilder.equal(root.get("account"),account));
//        return session.createQuery(criteriaQuery).uniqueResult();

        final String sql = "SELECT * FROM member WHERE account = :account";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("account", account)
                .uniqueResult();
    }

    @Override
    public Member selectForLogin(String account, String password) {
        // 使用 Native SQL
        final String sql = "SELECT * FROM member WHERE account = :account and password = :password";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("account", account)
                .setParameter("password", password)
                .uniqueResult();
    }

    @Override
    public Member selectByEmail(String email) {
        final String sql = "SELECT * FROM member WHERE email = :email";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public Member selectByAccountNEmail(String account, String email){
        final String sql = "SELECT * FROM member WHERE account = :account and email = :email";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("account", account)
                .setParameter("email", email)
                .uniqueResult();
    }
}
