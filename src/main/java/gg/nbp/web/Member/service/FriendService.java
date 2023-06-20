package gg.nbp.web.Member.service;

import java.util.List;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.Member.entity.Friend;

public interface FriendService extends CoreService {

    Boolean remove(Integer id);

    List<Friend> findAll();

    Friend add(Friend friend);

    Friend findOne(Friend friend);
}
