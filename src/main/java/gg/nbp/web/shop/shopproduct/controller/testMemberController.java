package gg.nbp.web.shop.shopproduct.controller;


import gg.nbp.web.Member.dao.MemberDao;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shopproduct.service.ProductManagerService;
import gg.nbp.web.shop.shopproduct.util.ObjectInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class testMemberController {

    @Autowired
    MemberDao memberDao;

    public testMemberController() {
    }

    public Member getDefaultMember() {
        Member member = memberDao.selectByAccount("Black");

        return member;
    }
}
