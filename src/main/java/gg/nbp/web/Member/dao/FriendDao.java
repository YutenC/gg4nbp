package gg.nbp.web.Member.dao;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.Member.entity.Friend;
import gg.nbp.web.Member.entity.Member;

public interface FriendDao extends CoreDao<Friend, Integer> {

    int insert(Friend friend);

    int deleteById(Integer id);

    int update(Friend friend);

    Friend selectById(Integer id);

    Friend selectByNick(Member member);
}
