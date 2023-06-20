package gg.nbp.web.power.dao;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.power.entity.Power;

public interface PowerDao extends CoreDao<Power, Integer>{
	
	Power selectByPowerName(String power_name);        // 找管理員的帳號

}
