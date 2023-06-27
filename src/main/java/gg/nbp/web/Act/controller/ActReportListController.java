package gg.nbp.web.Act.controller;



import gg.nbp.web.Act.model.ActReportList;
import gg.nbp.web.Act.service.ActReportListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/actReportList")
public class ActReportListController {

    @Autowired
    private ActReportListService actReportListService;

//     查询
    @CrossOrigin
    @GetMapping("/actReportList")
    public List<ActReportList> getActReportList() {

        List<ActReportList> actReportLists = actReportListService.getAllActReportLists();
        return actReportLists;
    }

    // 新增
    @PostMapping("/actReportList")
    @CrossOrigin
    public ActReportList processAct(@RequestBody ActReportList actReportList) {
        return actReportListService.createActReportList(actReportList);
    }

//    // 更改
//    @PutMapping("/actReportList/{id}")
//    public ActReportList updateAct(@PathVariable("id") int id, @RequestBody ActReportList actReportList) {
//        return actReportListService.updateActReportList(id, actReportList);
//    }
//
//    // 删除
//    @DeleteMapping("/actReportList/{id}")
//    public void deleteActReportList(@PathVariable("id") int id) {
//        actReportListService.deleteActReportList(id);
//    }
}
