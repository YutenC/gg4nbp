package gg.nbp.web.Manager.dao;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.Manager.entity.Manager;

public interface ManagerDao extends CoreDao<Manager, Integer> {
	
	Manager selectByUserName(String account);        // 找管理員的帳號

    Manager selectForLogin(String account, String password);          // 找登入的管理員

}
