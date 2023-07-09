package gg.nbp.web.Member.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import gg.nbp.web.Member.dao.FriendDao;
import gg.nbp.web.Member.entity.Friend;
import gg.nbp.web.Member.entity.Member;
import jakarta.persistence.PersistenceContext;


@Repository
public class FriendDaoImpl implements FriendDao {
	@PersistenceContext
	private Session session;
    @Override
    public List<Friend> selectAll() {
        final String sql = "FROM Friend ORDER BY member_id";
        return session.createNativeQuery(sql, Friend.class).getResultList();
    }

    @Override
    public int insert(Friend friend) {
        session.persist(friend);
        return friend.getFriend_id();
    }

    @Override
    public int deleteById(Integer id) {
        Friend friend = session.get(Friend.class, id);
        session.remove(friend);
        friend.setMessage("刪除好友成功");
        return friend.getFriend_id();
    }

    @Override
    public int update(Friend friend) {
        return 0;
    }

    @Override
    public Friend selectById(Integer id) {
        return session.get(Friend.class, id);
    }

    @Override
    public Friend selectByNick(Member member){
//        final String sql = "SELECT";
        Friend friend = new Friend();
        return friend;
    }
}
