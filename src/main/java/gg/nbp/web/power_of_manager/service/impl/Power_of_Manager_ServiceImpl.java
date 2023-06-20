package gg.nbp.web.power_of_manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gg.nbp.web.power_of_manager.dao.Power_of_ManagerDAO;
import gg.nbp.web.power_of_manager.dao.impl.Power_of_ManagerDAOImpl;
import gg.nbp.web.power_of_manager.entity.Power_of_Manager;
import gg.nbp.web.power_of_manager.service.Power_of_ManagerService;

@Transactional
@Service
public class Power_of_Manager_ServiceImpl implements Power_of_ManagerService{
	
	@Autowired
    private Power_of_ManagerDAO dao;
    
    public Power_of_Manager_ServiceImpl() {
    	dao= new Power_of_ManagerDAOImpl();
    }
    
    @Override
    public Power_of_Manager selectPower_of_Manager(Power_of_Manager.PK id) {
        return dao.selectById(id);
    }
    
    @Override
    public List<Power_of_Manager> findAll() {
    	
    	return dao.selectAll();
    }
    
    
    @Override
    public void deletePower_of_Manager(Power_of_Manager.PK id) {
        Power_of_Manager power_of_Manager = dao.selectById(id);
        if (power_of_Manager != null) {
            dao.deleteById(id);
        }
    }

    @Override
    public void savePower_of_Manager(Power_of_Manager power_of_Manager) {
    	dao.update(power_of_Manager);
    }
}
