package note.daoimp;

import org.apache.ibatis.session.SqlSession;

import note.dao.UserMapper;
import note.entity.User;
import note.utils.BaseDao;

public class UserMapperImp extends BaseDao implements UserMapper {

	public User selectUserById(String userId) {
		SqlSession session = getSessionFactory().openSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		return mapper.selectUserById(userId);		
	}

	public User selectUserByName(String userName) {
		SqlSession session = getSessionFactory().openSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		return mapper.selectUserByName(userName);
	}

	public Boolean insertUser(User user) {
		SqlSession session = getSessionFactory().openSession();
		try{
		UserMapper mapper = session.getMapper(UserMapper.class);
		return mapper.insertUser(user);
		}finally{
			session.commit();
			session.close();
		}
	}

	public Boolean updateUser(User user) {
		SqlSession session = getSessionFactory().openSession();
		try{
		UserMapper mapper = session.getMapper(UserMapper.class);
		return mapper.updateUser(user);
		}finally{
			session.commit();
			session.close();
		}
	}

	public Boolean deleteUser(String userId) {
		SqlSession session = getSessionFactory().openSession();
		try{
		UserMapper mapper = session.getMapper(UserMapper.class);
		return mapper.deleteUser(userId);
		}finally{
			session.commit();
			session.close();
		}	
	}

}
