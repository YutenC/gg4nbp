package gg.nbp.web.Member.service;

import java.util.List;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.Member.entity.Member_credit;

public interface CreditService extends CoreService {

    Member_credit edit(Member_credit member_credit);

    Boolean remove(Integer id);

    List<Member_credit> findAll();

    Member_credit add(Member_credit member_credit);
}
