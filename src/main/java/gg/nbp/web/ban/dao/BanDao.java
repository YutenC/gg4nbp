package gg.nbp.web.ban.dao;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.ban.entity.Ban;

public interface BanDao extends CoreDao<Ban, Integer>{

	Ban selectByMember(Integer member_id);
	
	Ban selectByManager(Integer manager_id);
	
}
