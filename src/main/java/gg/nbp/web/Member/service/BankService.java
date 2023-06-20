package gg.nbp.web.Member.service;

import java.util.List;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.Member.entity.Bank;

public interface BankService extends CoreService {

    Bank edit(Bank bank);       //  編輯銀行帳戶

    Boolean remove(Integer id);     // 刪除銀行帳戶

    List<Bank> findAll();   // 查詢所有銀行帳戶

    Bank add(Bank bank);    // 新增銀行帳戶
}
