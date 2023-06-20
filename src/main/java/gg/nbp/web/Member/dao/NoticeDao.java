package gg.nbp.web.Member.dao;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.Member.entity.Notice;

public interface NoticeDao extends CoreDao<Notice, Integer> {

    int insert(Notice notice);

    int deleteById(Integer id);     // 刪除該筆通知
    int delectByMember(Integer id);  // 刪除會員id的所有通知
}
