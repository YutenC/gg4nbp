package gg.nbp.web.Member.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import gg.nbp.web.Member.dao.NoticeDao;
import gg.nbp.web.Member.entity.Notice;
import jakarta.persistence.PersistenceContext;


@Repository
public class NoticeDaoImpl implements NoticeDao {
	@PersistenceContext
	private Session session;

    @Override
    public int update(Notice member) {
        return 0;
    }

    @Override
    public Notice selectById(Integer id) {
        return session.get(Notice.class, id);
    }

    @Override
    public List<Notice> selectAll() {
        final String hql = "FROM Notice ORDER BY member_id";
        return session.createQuery(hql, Notice.class).getResultList();
    }

    @Override
    public int insert(Notice notice) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public int delectByMember(Integer id) {
        return 0;
    }
}
