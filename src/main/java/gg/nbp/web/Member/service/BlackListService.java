package gg.nbp.web.Member.service;

import java.util.List;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.Member.entity.Black_list;

public interface BlackListService extends CoreService {

    Boolean remove(Integer id);

    List<Black_list> finAll();

    Black_list add(Black_list blackList);

    Black_list findOne(Black_list blackList);
}
