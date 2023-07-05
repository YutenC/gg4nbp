package gg.nbp.web.SecondHand.buy.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import gg.nbp.web.Member.dao.MemberDao;
import gg.nbp.web.Member.entity.Notice;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;

public class Toolbox {
	
	
	
	public static boolean isEmpty4Strings(String... args) {
		for(int i = 0 ; i<args.length ; i++) {
			if(args[i].trim().isEmpty()) {
				return true ;
			}
		}
		return false ;
	}
	
	
	public static String memberId2Name(Integer id ,MemberDao memberDao) {
		return memberDao.selectById(id).getNick();
	}
	
	
	
	public static String dateformat(Timestamp timeStamp) {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return sdFormat.format(timeStamp);
	}
	
	public static Timestamp getNow() {
		return new Timestamp(new Date().getTime());
	}
	
	
	public static Notice sendNotice(SecondhandBuylist sl) {
		Notice nt = new Notice();
		nt.setMember_id(sl.getMemberId());
		nt.setNotice_value(sl.getMessage());
		return nt ;
	}
	
	
	
}
