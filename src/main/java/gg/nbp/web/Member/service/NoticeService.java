package gg.nbp.web.Member.service;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.Member.entity.Notice;

import java.util.List;

public interface NoticeService extends CoreService {
    List<Notice> findAll(Notice notice);    // 顯示該會員全部的通知
    Notice addNotice(Notice notice);    // 系統新增訊息
    boolean remove(Integer id);    // 移除單筆推知
    boolean removeAll(Notice notice);   // 會員移除全部通知
    int read(Notice notice);     // 已讀單筆通知
    int readAll(Notice notice);     // 已讀所有通知

}
