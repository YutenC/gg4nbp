package gg.nbp.web.Act.controller;


import gg.nbp.web.Act.model.Act;
import gg.nbp.web.Act.service.ActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/")
@CrossOrigin
public class ActController {

    @Autowired
    private ActService actService;

    // 查询
    @GetMapping("/act")
    public List<Act> getAct() {
        List<Act> acts = actService.getAllActs();
        return acts;
    }
    @GetMapping("/act/{id}")
    public Act getActById(@PathVariable("id") int id) {
        return actService.getActById(id);
    }


    // 新增
    @PostMapping("/act")
    public Act processAct(@RequestBody Act act) {
        return actService.createAct(act);
    }
//
//    @DeleteMapping("/act/{id}")
//    public void deleteAct(@PathVariable("id") int id) {

//    }

    @PutMapping("/act/{id}")
    public Act updateAct(@PathVariable("id") int id) {
        actService.updateActMessage(id, (byte) 1);
        return null;
    }

//    @PutMapping("/act/{id}")
//    public Act updateAct(@PathVariable("id") int id, @RequestBody Act act) {
//        actService.updateActMessage(id, (byte) 1);
//        return null;
//    }

//    @RestController
//    @RequestMapping("/img/acts")
//    public class ActImageController {
//
//        public static String IMG_URL = "http://localhost:8000/img/act/";
//
//        private final ActService actService;
//
//        @Autowired
//        public ActImageController(ActService actService) {
//            this.actService = actService;
//        }
//
//        // 用于指定 {imgUrl} 只能由数字组成。 [0-9] 表示匹配一个数字字符，+ 表示匹配前面的表达式一次或多次。
//        @GetMapping(value = "/{imgUrl:[0-9]+}", produces = MediaType.IMAGE_GIF_VALUE)
//        public byte[] getPhoto(@PathVariable("imgUrl") final int id) {
//            return actService.findActImage(id);
//        }
//

}


