package gg.nbp.web.power.service;

import java.util.List;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.power.entity.Power;


public interface PowerService extends CoreService{

	Power selectPowerByPowerName(String power_name); // 用ID找power
	
	List<Power> findAll(); //查總表
}
