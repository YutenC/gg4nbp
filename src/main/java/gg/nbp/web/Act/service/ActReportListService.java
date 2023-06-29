package gg.nbp.web.Act.service;


import gg.nbp.web.Act.dao.ActReportListRepository;
import gg.nbp.web.Act.model.ActReportList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActReportListService {

    @Autowired
    private ActReportListRepository actReportListRepository;

    // 查詢全部
    public List<ActReportList> getAllActReportLists() {
        return actReportListRepository.findAll();
    }
//
//    // 查詢指定ID
//    public ActReportList getActById(int id) {
//        return actReportListRepository.findById(id).orElse(null);
//    }

    // 新增
    public ActReportList createActReportList(ActReportList actReportList) {
        actReportList.setReviewState((byte) 0);

        return actReportListRepository.save(actReportList);

    }


//    // 更改
//    public ActReportList updateActReportList(int id, ActReportList actReportList) {
//        ActReportList existingActReportList = actReportListRepository.findById(id).orElse(null);
//        if (existingActReportList != null) {
//            existingActReportList.setReportContent(actReportList.getReportContent());
//            existingActReportList.setReportedName(actReportList.getReportedName());
//            existingActReportList.setReportImage(actReportList.getReportImage());
//            existingActReportList.setReviewState(actReportList.getReviewState());
//            existingActReportList.setReportTime(actReportList.getReportTime());
//            return actReportListRepository.save(existingActReportList);
//        }
//        return null;
//    }
//
//    // 刪除
//    public void deleteActReportList(int id) {
//        actReportListRepository.deleteById(id);
//    }
}