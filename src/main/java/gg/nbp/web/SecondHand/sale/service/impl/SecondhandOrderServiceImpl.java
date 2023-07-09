package gg.nbp.web.SecondHand.sale.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gg.nbp.web.SecondHand.sale.dao.SecondhandOrderDao;
import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;
import gg.nbp.web.SecondHand.sale.service.SecondhandOrderService;

@Transactional
@Service
public class SecondhandOrderServiceImpl implements SecondhandOrderService {

//    @Autowired
//    private MemberDao memdao;
//
//    @Autowired
//    private BankDao bankdao;
//
//    @Autowired
//    private CreditDao credao;
//
//    @Autowired
//    private SecondhandProductDao shpdao;

    @Autowired
    private SecondhandOrderDao oddao;



    @Override
    public SecondhandOrder addOd(SecondhandOrder od) {


        if (od.getProductId() == null) {
            od.setMessage("請填入寄送地點");
            od.setSuccessful(false);
            return od;
        }

        if (od.getMemberId() == null) {
            od.setMessage("請填入寄送地點");
            od.setSuccessful(false);
            return od;
        }

        if (od.getDeliverName().trim().isEmpty()) {
            od.setMessage("請填入寄送地點");
            od.setSuccessful(false);
            return od;
        }

        if (od.getDeliverLocation().trim().isEmpty()) {
            od.setMessage("請填入寄送地點");
            od.setSuccessful(false);
            return od;
        }


        final int resultCount = oddao.insert(od);

        if (resultCount < 1) {
            od.setMessage("訂單成立");
            od.setSuccessful(false);
            return od;
        } else {
            od.setMessage("訂單成立失敗");
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
    public SecondhandOrder editOd(SecondhandOrder od) {

        final SecondhandOrder ood = oddao.selectById(od.getOrderId());


        if (od.getDeliverLocation() != null) {
            ood.setDeliverLocation(od.getDeliverLocation());
        }

        if (od.getPayState() != null) {
            ood.setPayState(od.getPayState());
        }

        if (od.getDeliverState() != null) {
            ood.setDeliverState(od.getDeliverState());
        }

        if (od.getManagerId() != null) {
            ood.setManagerId(od.getManagerId());
        }


        final int resultCount1 = oddao.update(ood);


        ood.setSuccessful(resultCount1 > 0);
        ood.setMessage(resultCount1 > 0 ? "修改成功" : "修改失敗");
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
