package gg.nbp.web.SecondHand.sale.test;


import gg.nbp.web.Member.dao.BankDao;
import gg.nbp.web.Member.dao.CreditDao;
import gg.nbp.web.Member.dao.MemberDao;
import gg.nbp.web.Member.dao.impl.BankDaoImpl;
import gg.nbp.web.Member.dao.impl.CreditDapImpl;
import gg.nbp.web.Member.dao.impl.MemberDaoImpl;
import gg.nbp.web.Member.entity.Bank;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.entity.Member_credit;
import gg.nbp.web.SecondHand.sale.dao.SecondhandOrderDao;
import gg.nbp.web.SecondHand.sale.dao.impl.SecondhandOrderDaoImpl;
import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;
import gg.nbp.web.SecondHand.sale.service.SecondhandOrderService;
import gg.nbp.web.SecondHand.sale.service.impl.SecondhandOrderServiceImpl;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;

public class TestApp_Order {

    @PersistenceContext
    private Session session;

    public static void main(String[] args) {

//        MemberDao memdao = new MemberDaoImpl();
//        BankDao bankdao = new BankDaoImpl();
//        CreditDao creditdao = new CreditDapImpl();
//        SecondhandOrderDao oddao = new SecondhandOrderDaoImpl();
//        SecondhandOrderService orderService = new SecondhandOrderServiceImpl();

//        Bank bank = new Bank();
//        Member_credit credit = new Member_credit();
//        SecondhandOrder od = new SecondhandOrder();
//
//        Integer memberId = 105;
//        String bankNumber = "1111111111";
//        String creditNumber = "2222222222";
//        String location = "台中市";

//        Member memFocus = memdao.selectById(memberId);
//        memFocus.setEmail("xxx@gmail.com");
//        memFocus.setPhone("0222222222");
//
//        bank.setBank_number("1111111111");
//        credit.setCred_number("2222222222");
//        od.setDeliverLocation("台中市");
//
//
//        orderService.addOd(od);
//
//        System.out.println("addod");


    }

}
