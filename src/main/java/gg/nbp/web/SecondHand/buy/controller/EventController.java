package gg.nbp.web.SecondHand.buy.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gg.nbp.core.pojo.OneString;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.SecondHand.buy.service.SecondHandBuyService;

@RestController("/secondhand")
@CrossOrigin
public class EventController {
	@Autowired
	private SecondHandBuyService service ;

	
	@GetMapping("/delete/{id}")
    public OneString delete(@PathVariable("id") int EventId, @RequestParam("member") Member member) {
		try {
			service.delete(member.getMember_id(), EventId);
			return new OneString("刪除完成");
		} catch (SQLException e) {
			e.printStackTrace();
			return new OneString("刪除失敗");
		}
    }
	
	
	
	
	
	
}
