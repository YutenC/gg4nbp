package gg.nbp.web.Manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.Manager.entity.Manager;



@Service
public interface ManagerService extends CoreService{
	Manager register(Manager manager); //註冊

	Manager login(Manager manager); //登入

	Manager selectManager(Integer manager_id); // 用ID找Manager
	
	Manager edit(Manager manager); //更改帳戶資料

	List<Manager> findAll(); //查總表

	boolean remove(Integer manager_id); //根據ID刪除管理員

	boolean save(Manager manager); // 儲存更新(新增或修改)
	
	//新增的service
	Manager editWorkingState(Manager manager); //改工作狀態
}
