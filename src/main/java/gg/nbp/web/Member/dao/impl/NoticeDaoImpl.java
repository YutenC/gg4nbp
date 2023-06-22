package gg.nbp.web.Member.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import gg.nbp.web.Member.dao.NoticeDao;
import gg.nbp.web.Member.entity.Notice;
import jakarta.persistence.PersistenceContext;


@Repository
public class NoticeDaoImpl implements NoticeDao {
	@PersistenceContext
	private Session session;

    @Override
    public int update(Notice notice) {
        final String sql = "UPDATE Notice SET is_read = :is_read WHERE notice_id = :notice_id";
        /*  原始寫法  */
//        NativeQuery<?> nativeQuery = session.createNativeQuery(sql)
//                .setParameter("isRead", notice.getIs_read())
//                .setParameter("notice", notice.getNotice_id());
//        int result = nativeQuery.executeUpdate();
//        return result;

        /*  簡化後  */
        return session.createNativeQuery(sql)
                .setParameter("is_read", 0)
                .setParameter("notice_id", notice.getNotice_id())
                .executeUpdate();
    }

    @Override
    public Notice selectById(Integer id) {
        return session.get(Notice.class, id);
    }

    @Override
    public List<Notice> selectAll(Notice notice) {
        final String hql = "FROM Notice ORDER BY member_id";
        return session.createQuery(hql, Notice.class).getResultList();
    }

    @Override
    public int insert(Notice notice) {

        final String sql = "INSERT INTO Notice (is_read, member_id, notice_value) VALUE (:is_read, :member_id, :notice_value)";
        return session.createNativeQuery(sql)
                .setParameter("is_read", notice.getIs_read())
                .setParameter("member_id", notice.getMember_id())
                .setParameter("notice_value", notice.getNotice_value())
                .executeUpdate();
    }

    @Override
    public int deleteById(Integer id) {
        Notice notice = session.get(Notice.class, id);
        session.remove(notice);
        notice.setMessage("刪除成功");
        return notice.getNotice_id();
    }

    @Override
    public int delectByMemberId(Notice notice) {
        final  String sql = "DELETE FROM Notice WHERE member_id = :member_id";
        /*  原始寫法  */
//        NativeQuery<?> nativeQuery = session.createNativeQuery(sql)
//                .setParameter("member_id", notice.getMember_id());
//        int result = nativeQuery.executeUpdate();
//        return result;
        /* 簡化寫法 */
        return session.createNativeQuery(sql)
                .setParameter("member_id", notice.getMember_id())
                .executeUpdate();
    }

    @Override
    public int updateAll(Notice notice) {
        final String sql = "UPDATE Notice SET is_read = :is_read WHERE member_id = :member_id";

        return session.createNativeQuery(sql)
                .setParameter("is_read", 0)
                .setParameter("member_id", notice.getMember_id())
                .executeUpdate();
    }
}
