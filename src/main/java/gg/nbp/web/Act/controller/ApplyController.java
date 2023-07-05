package gg.nbp.web.Act.controller;

import gg.nbp.web.Act.model.ApplyData;
import gg.nbp.web.Act.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ApplyController {

    private final EmailSenderService emailSenderService;

    @Autowired
    public ApplyController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/apply")
    public Boolean getApply(@RequestBody ApplyData applyData) {
        String message = applyData.toString() + "<br><br>聯絡工作人員🙋🏻‍:這個男人叫做小帥<br>電話📞：0975654320<br><br>謝謝您報名我們的活動祝您活動愉快";

        emailSenderService.sendEmail(applyData.getEmail(), "★★★★★★★★★★★★★★★NBP.gg活動確認單★★★★★★★★★★★★★★★", message);
        emailSenderService.sendEmail("410614220@ndhu.edu.tw", "平台方確認表單", message);

        return true;
    }



        // Here you could deserialize the requestBody into an ApplyData object
        // For example (assuming you are using the Jackson library)
        // ObjectMapper objectMapper = new ObjectMapper();
        // ApplyData applyData = objectMapper.readValue(requestBody, ApplyData.class);

        // Continue your implementation...


    }
