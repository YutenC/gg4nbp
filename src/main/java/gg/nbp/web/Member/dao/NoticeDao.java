package gg.nbp.web.Member.dao;

import java.util.List;

import gg.nbp.web.Member.entity.Notice;

public interface NoticeDao {
    int insert(Notice notice);
    int deleteById(Integer id);
    int update(Notice notice);
    Notice selectById(Integer id);
    List<Notice> selectAll(Notice notice);
    int delectByMemberId(Notice notice);
    int updateAll(Notice notice);
}
