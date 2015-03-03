package note.utils;

import java.util.Date;

import org.junit.Test;

public class CommonUtilTest {
	@Test
	public void testGenerateKey(){
		String key = CommonUtils.generateKey();
		System.out.println(key);
		System.out.println("--------------------------------");
	}
	@Test
	public void testDateString(){
		String date = CommonUtils.getTimeString(new Date());
		System.out.println(date);
		System.out.println("----------------------------------");
	}
	private CommonUtilTest(){
		
	}
}
