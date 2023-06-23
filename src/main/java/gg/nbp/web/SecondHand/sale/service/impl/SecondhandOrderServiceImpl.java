package gg.nbp.web.SecondHand.sale.service.impl;

import gg.nbp.web.Member.dao.BankDao;
import gg.nbp.web.Member.dao.CreditDao;
import gg.nbp.web.Member.dao.MemberDao;
import gg.nbp.web.Member.entity.Bank;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.entity.Member_credit;
import gg.nbp.web.SecondHand.sale.dao.SecondhandOrderDao;
import gg.nbp.web.SecondHand.sale.dao.SecondhandProductDao;
import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.service.SecondhandOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SecondhandOrderServiceImpl implements SecondhandOrderService {

    @Autowired
    private MemberDao memdao;

    @Autowired
    private BankDao bankdao;

    @Autowired
    private CreditDao credao;

    @Autowired
    private SecondhandProductDao shpdao;

    @Autowired
    private SecondhandOrderDao oddao;



    @Override
    public SecondhandOrder addOd(SecondhandOrder od, Member mem, Bank bank, Member_credit credit) {
        if ((mem.getPhone().trim().isEmpty())) {
            od.setMessage("請輸入電話");
            od.setSuccessful(false);
            return od;
        }

        if ((mem.getEmail().trim().isEmpty())) {
            od.setMessage("請輸入電子郵件");
            od.setSuccessful(false);
            return od;
        }


        if (od.getDeliverLocation().trim().isEmpty()) {
            od.setMessage("請填入寄送地點");
            od.setSuccessful(false);
            return od;
        }


//        if (bankdao.selectById(mem.getMember_id()).getBank_number().trim().isEmpty() || credao.selectById(mem.getMember_id()).getCred_number().trim().isEmpty()) {
//            od.setMessage("請填入付款方式");
//            od.setSuccessful(false);
//            return od;
//        }

//        if (bankdao.selectById(mem.getMember_id()).getBank_number().trim().isEmpty() || credao.selectById(mem.getMember_id()).getCred_number().trim().isEmpty()) {
//            od.setMessage("請填入付款方式");
//            od.setSuccessful(false);
//            return od;
//        }

        final int resultCount1 = oddao.insert(od);
        final int resultCount2 = memdao.insert(mem);
        final int resultCount3 = bankdao.insert(bank);
        final int resultCount4 = credao.insert(credit);


        if (resultCount1 < 1 && resultCount2 < 1 && resultCount3 < 1 && resultCount4 < 1) {
            od.setMessage("商品上架失敗");
            od.setSuccessful(false);
            return od;
        } else {
            od.setMessage("商品上架成功");
            od.setSuccessful(true);
            return od;
        }


    }

    @Override
    public boolean delOd(Integer orderId) {
        oddao.deleteById(orderId);
        return true;
    }

    @Override
    public SecondhandOrder editOd(SecondhandOrder od, Member mem, Bank bank, Member_credit credit) {

        final SecondhandOrder ood = oddao.selectById(od.getOrderId());
        final Member om = memdao.selectByAccount(mem.getAccount());
        final Bank obank = bankdao.selectById(bank.getBank_id());
        final Member_credit ocredit = credao.selectById(credit.getCredit_id());


        if (mem.getPhone() != null) {
            om.setPhone(mem.getPhone());
        }
        if (mem.getEmail() != null) {
            om.setEmail(mem.getEmail());
        }
        if (od.getDeliverLocation() != null) {
            ood.setDeliverLocation(od.getDeliverLocation());
        }
        if (bank.getBank_number() != null || credit.getCred_number() != null) {
            obank.setBank_number(bank.getBank_number());
            ocredit.setCred_number(credit.getCred_number());
        }


        final int resultCount1 = oddao.update(ood);
        final int resultCount2 = memdao.update(om);
        final int resultCount3 = bankdao.update(obank);
        final int resultCount4 = credao.update(ocredit);

        ood.setSuccessful(resultCount1 > 0 && resultCount2 > 0 && resultCount3 > 0 && resultCount4 > 0);
        ood.setMessage(resultCount1 > 0 && resultCount2 > 0 && resultCount3 > 0 && resultCount4 > 0 ? "修改成功" : "修改失敗");
        return ood;

    }

    @Override
    public SecondhandOrder selectOne(Integer orderId) {
        return oddao.selectById(orderId);
    }

    @Override
    public List<SecondhandOrder> selectAll() {
        return oddao.selectAll();
    }

    @Override
    public List<SecondhandOrder> selectMem(Integer memId) {
        return oddao.selectByMemId(memId);
    }
}
