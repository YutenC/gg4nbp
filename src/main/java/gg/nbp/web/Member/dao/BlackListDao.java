package gg.nbp.web.Member.dao;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.Member.entity.Black_list;

public interface BlackListDao extends CoreDao<Black_list, Integer> {

    int insert(Black_list blackList);

    int deleteById(Integer id);

    int update(Black_list blackList);

    Black_list selectById(Integer id);
}
