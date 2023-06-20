package gg.nbp.web.Member.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import gg.nbp.web.Member.dao.BankDao;
import gg.nbp.web.Member.entity.Bank;
import jakarta.persistence.PersistenceContext;


@Repository
public class BankDaoImpl implements BankDao {
	@PersistenceContext
	private Session session;

    @Override
    public int deleteById(Integer id) {
        Bank bank = session.get(Bank.class, id);
        session.remove(bank);
//        getSession().getTransaction().commit();
        bank.setMessage("刪除成功");
        return bank.getBank_id();
    }

    public List<Bank> selectAll() {
        final String hql = "FROM Bank ORDER BY member_id";
        return session.createQuery(hql, Bank.class).getResultList();
    }

    @Override
    public int insert(Bank bank) {
    	session.persist(bank);
        return bank.getBank_id();
    }

    @Override
    public int update(Bank bank) {
        final StringBuilder hql = new StringBuilder()
                .append("UPDATE Bank SET ");
        final String bankNumber = bank.getBank_number();
        if(bankNumber != null && !bankNumber.isEmpty()){
            hql.append("bank_number = :obank_number, ");
        }
        hql.append("WHERE bank_number = :bank_number");

         Query<?> query = session.createQuery(hql.toString());
        if(bankNumber != null && !bankNumber.isEmpty()){
            query.setParameter("bank_number", bankNumber);
        }
        return query
                .setParameter("bank_number", bank.getBank_number())
                .executeUpdate();
    }

    @Override
    public Bank selectById(Integer id) {
        return session.get(Bank.class, id);
    }

    @Override
    public Bank selectByBankNumber(String bank_number){
        final String sql = "FROM Bank where bank_number = :bank_number";
        return session
                .createNativeQuery(sql, Bank.class)
                .setParameter("bank_number", bank_number)
                .uniqueResult();
    }
}


