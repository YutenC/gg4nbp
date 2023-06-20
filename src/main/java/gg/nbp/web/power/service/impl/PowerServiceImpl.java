package gg.nbp.web.power.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gg.nbp.web.power.dao.PowerDao;
import gg.nbp.web.power.dao.impl.PowerDaoImpl;
import gg.nbp.web.power.entity.Power;
import gg.nbp.web.power.service.PowerService;



@Transactional
@Service
public class PowerServiceImpl implements PowerService {
	
	@Autowired
	private PowerDao dao;
	
	public PowerServiceImpl() {
		dao= new PowerDaoImpl();
	}
	
	
	@Override
	public Power selectPowerByPowerName(String power_name) {
		Power power= dao.selectByPowerName(power_name);
		return power;
	}
	
	@Override
	public List<Power> findAll() {
		return dao.selectAll();
	}
}
