package gg.nbp.web.Member.service;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.Member.entity.Login_record;

import java.util.List;

public interface LoginRecordService extends CoreService {
    List<Login_record> findAll(Login_record loginRecord);
    Login_record record(Login_record loginRecord);
    Boolean removeAll(Login_record loginRecord);
}
