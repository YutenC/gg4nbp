package gg.nbp.web.Act.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gg.nbp.web.Act.model.Act;
import gg.nbp.web.Act.service.ActService;

@RestController
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

    // 新增
    @PostMapping("/act")
    public Act processAct(@RequestBody Act act) {
        return actService.createAct(act);
    }

    @DeleteMapping("/act/{id}")
    public void deleteAct(@PathVariable("id") int id) {
        actService.updateActMessage(id, (byte) 1);
    }


    @RestController
@RequestMapping("/img/acts")
public class ActImageController {

        public static String IMG_URL = "http://localhost:8000/img/act/";

        private final ActService actService;

        @Autowired
        public ActImageController(ActService actService) {
            this.actService = actService;
        }

        // 用于指定 {imgUrl} 只能由数字组成。 [0-9] 表示匹配一个数字字符，+ 表示匹配前面的表达式一次或多次。
        @GetMapping(value = "/{imgUrl:[0-9]+}", produces = MediaType.IMAGE_GIF_VALUE)
        public byte[] getPhoto(@PathVariable("imgUrl") final int id) {
            return actService.findActImage(id);
        }


    }

}
// 更改
//    @PutMapping("/act/{id}")
//    public Act updateAct(@PathVariable("id") int id, @RequestBody Act act) {
//        return actService.updateAct(id, act);
//    }

//    // 删除



