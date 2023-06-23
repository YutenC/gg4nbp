package gg.nbp.web.SecondHand.buy.util;

import gg.nbp.web.Member.dao.MemberDao;


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
}
