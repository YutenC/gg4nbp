package gg.nbp.web.Member.dao;

import gg.nbp.web.Member.entity.Login_record;

import java.util.List;

public interface LoginRecordDao {
    Integer insert (Login_record loginRecord);
    List<Login_record> selectByMemberId(Login_record loginRecord);
    Boolean delectByMemberId(Login_record loginRecord);
}
