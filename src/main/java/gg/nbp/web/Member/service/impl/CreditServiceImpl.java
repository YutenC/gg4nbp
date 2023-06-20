package gg.nbp.web.Member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gg.nbp.web.Member.dao.CreditDao;
import gg.nbp.web.Member.entity.Member_credit;
import gg.nbp.web.Member.service.CreditService;

@Transactional
@Service
public class CreditServiceImpl implements CreditService {
	
	@Autowired
    private CreditDao dao;

  

    @Override
    public Member_credit edit(Member_credit member_credit) {
        try {
            final Member_credit oMemberCredit = dao.selectByBankNumber(member_credit.getCred_number());
            if (member_credit.getBank_name() == null) {
                member_credit.setBank_name(oMemberCredit.getBank_name());
            } else {
                oMemberCredit.setBank_name(member_credit.getBank_name());
            }
            if (member_credit.getCred_number() == null) {
                member_credit.setCred_number(oMemberCredit.getCred_number());
            } else {
                oMemberCredit.setCred_number(member_credit.getCred_number());
            }
            oMemberCredit.setBank_name(member_credit.getBank_name());
            oMemberCredit.setCred_number(member_credit.getCred_number());
            final int resultCount = dao.update(member_credit);
            member_credit.setSuccessful(resultCount > 0);
            member_credit.setMessage(resultCount > 0 ? "修改成功" : "修改失敗");
            return member_credit;
        } catch (Exception e) {
            e.printStackTrace();
            member_credit.setMessage("修改失敗");
            return member_credit;
        }
    }

    @Override
    public Boolean remove(Integer id) {
        return dao.deleteById(id) > 0;
    }

    @Override
    public List<Member_credit> findAll() {
        return dao.selectAll();
    }

    @Override
    public Member_credit add(Member_credit member_credit) {
        return null;
    }
}
