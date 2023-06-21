package gg.nbp.web.SecondHand.buy.util;

public class Toolbox {
	
	public static boolean isEmpty4Strings(String... args) {
		for(int i = 0 ; i<args.length ; i++) {
			if(args[i].trim().isEmpty()) {
				return true ;
			}
		}
		return false ;
	}
	
	
}
