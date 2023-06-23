package gg.nbp.web.Member.service.impl;

import gg.nbp.web.Member.dao.LoginRecordDao;
import gg.nbp.web.Member.dao.impl.LoginRecordDaoImpl;
import gg.nbp.web.Member.entity.Login_record;
import gg.nbp.web.Member.service.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class LoginRecordServiceImpl implements LoginRecordService {

    @Autowired
    private LoginRecordDao dao;
    public LoginRecordServiceImpl(){dao = new LoginRecordDaoImpl();}
    @Override
    public List<Login_record> findAll(Login_record loginRecord) {
        return dao.selectByMemberId(loginRecord);
    }

    @Override
    public Login_record record(Login_record loginRecord) {
        if(loginRecord.getHost_name() == null){
            loginRecord.setMessage("使用者未提供IP");
            loginRecord.setSuccessful(false);
            return loginRecord;
        }
        if(loginRecord.getLogin_device() == null){
            loginRecord.setMessage("使用者未提供裝置名稱");
            loginRecord.setSuccessful(false);
            return loginRecord;
        }
        if(loginRecord.getLogin_city() == null){
            loginRecord.setMessage("使用者未同意開啟定位服務");
            loginRecord.setSuccessful(false);
            return loginRecord;
        }
        try{
            final int result = dao.insert(loginRecord);
            if(result < 1){
                loginRecord.setSuccessful(false);
                return loginRecord;
            }
            loginRecord.setSuccessful(true);
            return loginRecord;
        }catch (Exception e){
            e.printStackTrace();
            loginRecord.setSuccessful(false);
            return loginRecord;
        }
    }
    @Override
    public Boolean removeAll(Login_record loginRecord) {
        return dao.delectByMemberId(loginRecord);
    }
}
