package gg.nbp.web.Member.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import gg.nbp.web.Member.dao.BlackListDao;
import gg.nbp.web.Member.entity.Black_list;
import jakarta.persistence.PersistenceContext;


@Repository
public class BlackListDaoImpl implements BlackListDao {
	@PersistenceContext
	private Session session;
	
    @Override
    public List<Black_list> selectAll() {
        final String sql = "FROM Black_list ORDER BY member_id";
        return session.createNativeQuery(sql, Black_list.class).getResultList();
    }

    @Override
    public int insert(Black_list blackList) {
        session.persist(blackList);
        return blackList.getBlack_id();
    }

    @Override
    public int deleteById(Integer id) {
        Black_list blackList = session.get(Black_list.class, id);
        session.remove(blackList);
        blackList.setMessage("刪除好友成功");
        return blackList.getBlack_id();
    }

    @Override
    public int update(Black_list blackList) {
        return 0;
    }

    @Override
    public Black_list selectById(Integer id) {
        return session.get(Black_list.class, id);
    }
}

