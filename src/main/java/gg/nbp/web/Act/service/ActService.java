package gg.nbp.web.Act.service;

import gg.nbp.web.Act.dao.ActMessageRepository;
import gg.nbp.web.Act.dao.ActRepository;
import gg.nbp.web.Act.model.Act;
import gg.nbp.web.Act.model.ActMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class ActService {

    @Autowired
    private ActRepository actRepository;
    @Autowired
    private ActMessageRepository actMessageRepository;


    // 查詢全部
    public List<Act> getAllActs() {

        List<Act> acts = actRepository.findAll();



        return acts;
    }

    public List<Act> getAllActs(Integer memId) {
        List<ActMessage> actMessages = actMessageRepository.findByMemId(0L);
        List<Act> acts = actRepository.findAll();

        List<Act> memAct = new ArrayList<>();
        for (ActMessage actMessage : actMessages) {
            for (Act act : acts) {
                if (actMessage.getActId() == act.getId() && actMessage.getMessageState() == 0) {
                    memAct.add(act);
                    break;
                }
            }

        }

        return memAct;
    }

    // 查詢指定ID
    public Act getActById(int id) {
        return actRepository.findById(id).orElse(null);
    }

    // 新增
    // 新增
    public Act createAct(Act act) {
        act.setOrganizerId(12);
        String img_ = act.getActImage();
        String imgBase64 = img_.split(",")[1];
        System.out.println(imgBase64);

        byte[] imageBytes = Base64.getDecoder().decode(imgBase64);
        String fileName = null;  // 這裡我們替換掉所有非字母和數字的字符
        try {
            fileName = URLEncoder.encode(act.getActName(), "UTF-8")+ ".jpg";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String pathName = "/Users/wujoe/Documents/five-project/src/main/resources/static/img/imgact";
        String pathName2= "../imgact/" + fileName;
        // 確保路徑存在
        File directory = new File(pathName);
        if (! directory.exists()){
            directory.mkdir();
        }
        // Create output file and write the byte array
        Path path = Path.of(pathName+"/"+fileName);
        try {
            Files.write(path, imageBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        act.setActImage(pathName2);

        return actRepository.save(act);
    }

    public byte[] findActImage(int id) {
        Act act = actRepository.findById(id).orElse(null);
        if (act != null && act.getActImage() != null) {
            try {
                // 假設圖片路徑存儲在資料庫中，這裡使用Path類來讀取圖片檔案的二進位資料
                Path imagePath = Path.of(act.getActImage());
                return Files.readAllBytes(imagePath);
            } catch (IOException e) {
                // 處理讀取圖片時的例外錯誤
                e.printStackTrace();
            }
        }
        return null;

    }

//    public void deleteAct(int id) {
//        actRepository.deleteById(id);
//    }

    public void updateActMessage(int id, byte messageState) {
//        actMessageRepository.updateMessageStateById(id, messageState);
        List<ActMessage> actMessages = actMessageRepository.findByMemId(0L);

        Integer actMessageId=0;
        for (ActMessage actMessage : actMessages) {

                if (actMessage.getActId() == id) {
                    actMessageId=actMessage.getId();
                    break;
                }

        }

        actMessageRepository.updateMessageStateById(actMessageId, messageState);

    }
}

// 更改
//    public Act updateAct(int id, Act act) {
//        Act existingAct = actRepository.findById(id).orElse(null);
//        if (existingAct != null) {
//            existingAct.setActName(act.getActName());
//            existingAct.setActTime(act.getActTime());
//            existingAct.setActLocation(act.getActLocation());
//            existingAct.setActQuota(act.getActQuota());
//            existingAct.setActPrice(act.getActPrice());
//            existingAct.setActValue(act.getActValue());
//            existingAct.setInviteTarget(act.getInviteTarget());
//            existingAct.setOrganizerBankNum(act.getOrganizerBankNum());
//            existingAct.setDelFrom(act.getDelFrom());
//            existingAct.setStartTime(act.getStartTime());
//            existingAct.setEndTime(act.getEndTime());
//            existingAct.setActImage(act.getActImage());
//
//            return actRepository.save(existingAct);
//        }
//        return null;
//    }

//    // 刪除

//    }
