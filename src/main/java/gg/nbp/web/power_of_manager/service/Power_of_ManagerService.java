package gg.nbp.web.power_of_manager.service;

import java.util.List;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.power_of_manager.entity.Power_of_Manager;

public interface Power_of_ManagerService extends CoreService{
	
	Power_of_Manager selectPower_of_Manager(Power_of_Manager.PK id);

	List<Power_of_Manager> findAll();
	
	void deletePower_of_Manager(Power_of_Manager.PK id);

    void savePower_of_Manager(Power_of_Manager power_of_Manager);
	
	
}
