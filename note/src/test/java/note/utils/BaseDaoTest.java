package note.utils;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

public class BaseDaoTest {
	@Test
	public void testBaseDao(){
		SqlSessionFactory sessionFac = BaseDao.getSessionFactory();
		System.out.println("This is sessionFactory\n"+sessionFac);
		System.out.println("-------------------------------------");
	}
}
