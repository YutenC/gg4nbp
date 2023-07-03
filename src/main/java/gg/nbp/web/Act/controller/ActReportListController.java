package gg.nbp.web.Act.controller;



import gg.nbp.web.Act.model.ActReportList;
import gg.nbp.web.Act.service.ActReportListService;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public ActReportList processAct(@RequestBody ActReportList actReportList, HttpServletRequest request) {
        Member member = MemberCommonUitl.getMemberSession(request ,"member");
        if(member!=null){
            System.out.println(member.getMember_id());
            System.out.println(actReportList);
            actReportList.setReportPerson(member.getMember_id());
        }


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
