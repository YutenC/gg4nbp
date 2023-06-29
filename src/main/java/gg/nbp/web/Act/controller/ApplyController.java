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
    public Boolean getApply(@RequestBody ApplyData applyData){
        String message = applyData.toString() + "\n\n有事聯絡工作人員：這個男人叫做小帥";
        emailSenderService.sendEmail(applyData.getEmail(), "活動表單", message);
        emailSenderService.sendEmail("max875e6@gmail.com", "主辦方表單", message);

        return true;
    }

}

