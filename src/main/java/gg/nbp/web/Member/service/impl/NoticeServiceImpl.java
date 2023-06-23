package gg.nbp.web.Member.service.impl;

import gg.nbp.web.Member.dao.NoticeDao;
import gg.nbp.web.Member.dao.impl.NoticeDaoImpl;
import gg.nbp.web.Member.entity.Notice;
import gg.nbp.web.Member.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDao dao;
    public NoticeServiceImpl(){
        dao = new NoticeDaoImpl();
    }
    @Override
    public List<Notice> findAll(Notice notice) {
        return dao.selectAll(notice);
    }

    @Override
    public Notice addNotice(Notice notice) {
        if(notice.getNotice_value() == null){
            notice.setMessage("未輸入訊息");
            notice.setSuccessful(false);
            return notice;
        }
        try{
            final int result = dao.insert(notice);
            if(result < 1 ){
                notice.setSuccessful(false);
                return notice;
            }
            notice.setSuccessful(true);
            return notice;
        }catch (Exception e){
            e.printStackTrace();
            notice.setSuccessful(false);
            return notice;
        }
    }

    @Override
    public boolean remove(Integer id) {
        return dao.deleteById(id) > 0;
    }

    @Override
    public boolean removeAll(Notice notice) {
        return dao.delectByMemberId(notice) > 0;
    }

    @Override
    public int read(Notice notice) {
        return dao.update(notice);
    }

    @Override
    public int readAll(Notice notice) {
        return dao.updateAll(notice);
    }
}
