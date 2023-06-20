package gg.nbp.web.Member.dao;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.Member.entity.Member;

public interface MemberDao extends CoreDao<Member, Integer> {

    int insert(Member member);

    Member selectByAccount(String account);        // 找使用者的名稱

    Member selectForLogin(String account, String password);          // 找登入的會員

    Member selectByEmail(String email);     // 利用會員信箱驗證

    public Member selectByAccountNEmail(String account, String email);
}
