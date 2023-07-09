package gg.nbp.web.power_of_manager.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import gg.nbp.web.power_of_manager.dao.Power_of_ManagerDAO;
import gg.nbp.web.power_of_manager.entity.Power_of_Manager;
import jakarta.persistence.PersistenceContext;

@Repository
public class Power_of_ManagerDAOImpl implements Power_of_ManagerDAO{
	
	@PersistenceContext
	private Session session;
	
    @SuppressWarnings("deprecation")
	@Override
    public int insert(Power_of_Manager powerOfManager) {
        session.save(powerOfManager);
        return 1; // 返回插入后的主键值，如果有需要的话
    }

    @Override
    public int deleteById(Power_of_Manager.PK id) {
        Power_of_Manager powerOfManager = session.get(Power_of_Manager.class, id);
        if (powerOfManager != null) {
            session.remove(powerOfManager);
            return 1; // 删除成功
        }
        return 0; 
    }

    @SuppressWarnings("deprecation")
	@Override
    public int update(Power_of_Manager powerOfManager) {
        session.saveOrUpdate(powerOfManager);
        return 1; // 更新成功
    }

    @Override
    public Power_of_Manager selectById(Power_of_Manager.PK id) {
        return session.get(Power_of_Manager.class, id);
    }

    @Override
    public List<Power_of_Manager> selectAll() {
        return session.createQuery("FROM Power_of_Manager", Power_of_Manager.class).getResultList();
    }
    
    // 可以添加其他
}
