package gg.nbp.web.Member.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gg.nbp.web.Member.dao.BankDao;
import gg.nbp.web.Member.dao.impl.BankDaoImpl;
import gg.nbp.web.Member.entity.Bank;
import gg.nbp.web.Member.service.BankService;

@Transactional
@Service
public class BankServiceImpl implements BankService {

    private BankDao dao;

    public BankServiceImpl() {
        dao = new BankDaoImpl();
    }

    @Override
    public Bank edit(Bank bank) {
        try {
            final Bank oBank = dao.selectByBankNumber(bank.getBank_number());
            if (bank.getBank_name() == null) {
                bank.setBank_name(oBank.getBank_name());
            } else {
                oBank.setBank_name(bank.getBank_name());
            }
            if (bank.getBank_number() == null) {
                bank.setBank_number(oBank.getBank_number());
            } else {
                oBank.setBank_number(bank.getBank_number());
            }
            oBank.setBank_name(bank.getBank_name());
            oBank.setBank_number(bank.getBank_number());
            final int resultCount = dao.update(bank);
            bank.setSuccessful(resultCount > 0);
            bank.setMessage(resultCount > 0 ? "修改成功" : "修改失敗");
            return bank;
        } catch (Exception e) {
            e.printStackTrace();
            bank.setMessage("修改失敗");
            return bank;
        }
    }

    @Override
    public Boolean remove(Integer id) {
        return dao.deleteById(id) > 0;
    }

    @Override
    public List<Bank> findAll() {
        return dao.selectAll();
    }

    @Override
    public Bank add(Bank bank) {
        if (bank.getBank_number() == null) {
            bank.setMessage("銀行號碼未輸入");
            bank.setSuccessful(false);
            return bank;
        }

        if (bank.getBank_name() == null) {
            bank.setMessage("銀行名稱未輸入");
            bank.setSuccessful(false);
            return bank;
        }

        try {
            if (dao.selectByBankNumber(bank.getBank_number()) != null) {
                bank.setMessage("銀行帳號重複");
                bank.setSuccessful(false);
                return bank;
            }
            final int resulCount = dao.insert(bank);
            if (resulCount < 1) {
                bank.setMessage("新增失敗，請聯繫管理員");
                bank.setSuccessful(false);
                return bank;
            }
            bank.setMessage("新增成功");
            return bank;
        } catch (Exception e) {
            e.printStackTrace();
            bank.setMessage("新增失敗");
            return bank;
        }
    }
}
