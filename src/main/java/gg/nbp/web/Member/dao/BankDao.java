package gg.nbp.web.Member.dao;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.Member.entity.Bank;

public interface BankDao extends CoreDao<Bank, Integer> {

    int insert(Bank bank);

    int deleteById(Integer id);

    int update(Bank bank);

    Bank selectById(Integer id);

    Bank selectByBankNumber(String bank_number);

}
