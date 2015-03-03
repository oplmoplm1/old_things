package note.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class CommonUtils {
	public static String generateKey(){
		long now = System.currentTimeMillis();
		int ran = new SecureRandom().nextInt(1000);
		return ""+now+ran;
	}
	public static String getTimeString(Date d){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
	}
	
	private CommonUtils(){
		
	}
	
}
