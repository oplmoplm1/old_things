package note.utils;

import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BaseDao {
	private static final SqlSessionFactory SQLMAPPER;
	private static final Logger LOGGER=Logger.getAnonymousLogger();
	static{
		String path = "config/SqlMapper-Configuration.xml";
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(path);
		} catch (IOException e) {
			LOGGER.log(Level.FINE,"BASEDAO CAN'T BE BUILD",e);
		}
		 SQLMAPPER = new SqlSessionFactoryBuilder().build(reader);
	}
	public static SqlSessionFactory getSessionFactory(){
		return SQLMAPPER;
	}
	protected BaseDao(){
		super();
	}
}
