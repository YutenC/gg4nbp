package gg.nbp.web.Member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gg.nbp.web.Member.dao.MemberDao;
import gg.nbp.web.Member.dao.impl.MemberDaoImpl;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;

@Transactional
@Service
public class MemberServiceimpl implements MemberService {

	@Autowired
    private MemberDao dao;

    public MemberServiceimpl() {
        dao = new MemberDaoImpl();
    }

    @Override
    public Member register(Member member) {     //註冊驗證
        if (member.getAccount() == null) {
            member.setMessage("帳號未輸入");
            member.setSuccessful(false);
            System.out.println("帳號未輸入");
            return member;
        }

        if (member.getPassword() == null) {
            member.setMessage("密碼未輸入");
            member.setSuccessful(false);
            System.out.println("密碼未輸入");
            return member;
        }

        if (member.getNick() == null) {
            member.setMessage("會員暱稱未輸入");
            member.setSuccessful(false);
            System.out.println("會員暱稱未輸入");
            return member;
        }

        if (member.getEmail() == null) {
            member.setMessage("會員信箱未輸入");
            member.setSuccessful(false);
            System.out.println("會員信箱未輸入");
            return member;
        }

        if (member.getPhone() == null) {
            member.setMessage("連絡電話未輸入");
            member.setSuccessful(false);
            System.out.println("連絡電話未輸入");
            return member;
        }

        if (member.getBirth() == null) {
            member.setMessage("生日未輸入");
            member.setSuccessful(false);
            System.out.println("生日未輸入");
            return member;
        }
        try {
//            beginTransaction();
            if (dao.selectByAccount(member.getAccount()) != null) {
                member.setMessage("帳號重複");
                member.setSuccessful(false);
                System.out.println("帳號重複");
//                rollback();
                return member;
            }

            if (dao.selectByEmail(member.getEmail()) != null) {
                member.setMessage("信箱重複");
                member.setSuccessful(false);
                System.out.println("信箱重複");
//                rollback();
                return member;
            }

            final int resultCount = dao.insert(member);
            if (resultCount < 1) {
                member.setMessage("註冊錯誤，請聯絡管理員!");
                member.setSuccessful(false);
                System.out.println("註冊錯誤，請聯絡管理員!");
//                rollback();
                return member;
            }
//            commit();
            member.setMessage("註冊成功");
            member.setSuccessful(true);
            System.out.println("註冊成功");
            return member;
        } catch (Exception e) {
//            rollback();
            e.printStackTrace();
            member.setMessage("註冊失敗");
            member.setSuccessful(false);
            System.out.println("註冊失敗");
            return member;          // 不確定retrun什麼
        }
    }

    @Override
    public Member login(Member member) {
        final String account = member.getAccount();
        final String password = member.getPassword();

        if (account == null) {
            member.setMessage("帳號未輸入");
            member.setSuccessful(false);
            return member;
        }

        if (password == null) {
            member.setMessage("密碼未輸入");
            member.setSuccessful(false);
            return member;
        }
//        --因為查詢登入是需要交易，所以開始交易寫在這--
//        beginTransaction();
        member = dao.selectForLogin(account, password);
        if (member == null) {
            member = new Member();
            member.setMessage("帳號或密碼錯誤");
            member.setSuccessful(false);
            return member;
        }
        //        --以上為驗證機制--
//        commit();
        member.setMessage("登入成功");
        member.setSuccessful(true);
        return member;
    }


    @Override
    public Member edit(Member member) {     // 編輯會員資料(email、電話、地址、大頭照、認證狀態、被檢舉次數、累積紅利)
        try {
//            beginTransaction();
            final Member oMember = dao.selectByAccount(member.getAccount());
            // oMember 為資料庫原始資料     member 為會員輸入的資料
            if (member.getEmail() == null) {
                member.setEmail(oMember.getEmail());
            } else {
                oMember.setEmail(member.getEmail());
            }
            if (member.getPhone() == null) {
                member.setPhone(oMember.getPhone());
            } else {
                oMember.setPhone(oMember.getPhone());
            }
            if (member.getAddress() == null) {
                member.setAddress(oMember.getAddress());
            } else {
                oMember.setAddress(oMember.getAddress());
            }
            if (member.getBonus() == null) {
                member.setBonus(oMember.getBonus());
            } else {
                oMember.setBonus(oMember.getBonus());
            }
            if (member.getMember_ver_state() == null) {
                member.setMember_ver_state(oMember.getMember_ver_state());
            } else {
                oMember.setMember_ver_state(oMember.getMember_ver_state());
            }
            if (member.getHeadshot() == null) {
                member.setHeadshot(oMember.getHeadshot());
            } else {
                oMember.setHeadshot(oMember.getHeadshot());
            }
            if (member.getViolation() == null) {
                member.setViolation(oMember.getViolation());
            } else {
                 oMember.setViolation(oMember.getViolation());
            }
            oMember.setAccount(member.getAccount());
            oMember.setPassword(member.getPassword());
            final int resultCount = dao.update(member);
//            commit();
            member.setSuccessful(resultCount > 0);
            member.setMessage(resultCount > 0 ? "修改成功" : "修改失敗");
            return member;
        } catch (Exception e) {
//            rollback();
            e.printStackTrace();
            member.setMessage("修改失敗");
            return member;
        }
    }

    @Override
    public List<Member> findAll() {
//        beginTransaction();
        List<Member> memberList = dao.selectAll();
//        commit();
        return memberList;
    }

    @Override
    public boolean remove(Integer id) {
        // 原始寫法
//        return dao.deleteById(id) > 0;
//        try{
//        建立HibernateFilter後交易機制交給他處理，beginTransaction, commit, rollback都可以註解掉
//        回傳dao.deleteById(id) > 0 即可(回傳 >0原因 如下)
//            beginTransaction();
//            final int resultCount = dao.deleteById(id);
//            commit();
//            return resultCount > 0;
//        }catch (Exception e){
//            rollback();
//            e.printStackTrace();
//            return false;
//        }
        return dao.deleteById(id) > 0;
    }

    @Override
    public Member forgetPassword(Member member) {
        String account = member.getAccount();
        String email = member.getEmail();
        Member fMember = dao.selectByAccountNEmail(account, email);
        if (fMember == null) {
            member = new Member();
            member.setMessage("查無會員或信箱");
            member.setSuccessful(false);
            return member;
        }
        member.setMessage("密碼已重置");
        member.setSuccessful(true);
        return member;
    }
}
