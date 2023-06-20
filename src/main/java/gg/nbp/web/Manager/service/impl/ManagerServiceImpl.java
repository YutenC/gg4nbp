package gg.nbp.web.Manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gg.nbp.web.Manager.dao.ManagerDao;
import gg.nbp.web.Manager.dao.impl.ManagerDaoImpl;
import gg.nbp.web.Manager.entity.Manager;
import gg.nbp.web.Manager.service.ManagerService;

@Transactional
@Service
public class ManagerServiceImpl implements ManagerService{
	
	@Autowired
	private ManagerDao dao;

	public ManagerServiceImpl() {
		dao = new ManagerDaoImpl();
	}
	
	@Override
	public Manager register(Manager manager) {
		if (manager.getAccount() == null) {
			manager.setMessage("帳號未輸入");
			manager.setSuccessful(false);
			return manager;
		}

		if (manager.getPassword() == null) {
			manager.setMessage("密碼未輸入");
			manager.setSuccessful(false);
			return manager;
		}

		if (manager.getName() == null) {
			manager.setMessage("名字未輸入");
			manager.setSuccessful(false);
			return manager;
		}

		if (dao.selectByUserName(manager.getAccount()) != null) {
			manager.setMessage("帳號重複");
			manager.setSuccessful(false);
			return manager;
		}

		manager.setIs_working(1);
		
		final int resultCount = dao.insert(manager);
		if (resultCount < 1) {
			manager.setMessage("註冊錯誤，請聯絡管理員!");
			manager.setSuccessful(false);
			return manager;
		}

		manager.setMessage("註冊成功");
		manager.setSuccessful(true);
		return manager;
	}
	
	@Override
	public Manager login(Manager manager) {
		final String account = manager.getAccount();
		final String password = manager.getPassword();

		if (account == null) {
			manager.setMessage("帳號未輸入");
			manager.setSuccessful(false);
			return manager;
		}

		if (password == null) {
			manager.setMessage("密碼未輸入");
			manager.setSuccessful(false);
			return manager;
		}

		manager = dao.selectForLogin(account, password);
		if (manager == null) {
			manager = new Manager();
			manager.setMessage("帳號或密碼錯誤");
			manager.setSuccessful(false);
			return manager;
		}

		manager.setMessage("登入成功");
		manager.setSuccessful(true);
		return manager;
	}
	
	@Override
	public Manager selectManager(Integer manager_id) {
		Manager manager= dao.selectById(manager_id);
		return manager;
	}
	
	@Override
	public Manager edit(Manager manager) {
		final Manager oManager = dao.selectById(manager.getManager_id());
		manager.setIs_working(oManager.getIs_working());
		manager.setManager_id(manager.getManager_id());
		final int resultCount = dao.update(manager);
		manager.setSuccessful(resultCount > 0);
		manager.setMessage(resultCount > 0 ? "修改成功" : "修改失敗");
		return manager;
	}
	
	@Override
	public List<Manager> findAll() {
		return dao.selectAll();
	}
	
	@Override
	public boolean remove(Integer manager_id) {
		return dao.deleteById(manager_id) > 0;
	}
	
	@Override
	public boolean save(Manager manager) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Manager editWorkingState(Manager manager) {
		final Manager oManager = dao.selectById(manager.getManager_id());
		
		int newWorkingState;
		if (oManager.getIs_working()==1) {
			newWorkingState= 0;
		}else {
			newWorkingState= 1;
		}
		
		manager.setIs_working(newWorkingState);
		manager.setManager_id(manager.getManager_id());
		final int resultCount = dao.update(manager);
		manager.setSuccessful(resultCount > 0);
		manager.setMessage(resultCount > 0 ? "修改成功" : "修改失敗");
		return manager;
	}
	
}
