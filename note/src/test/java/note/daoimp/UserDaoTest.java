package note.daoimp;

import note.entity.User;
import note.utils.CommonUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserDaoTest {
	String userId ;
	String userName;
	
	@Before
	public void testInsertUser(){
		userId = CommonUtils.generateKey();
		userName = userId+"nam";
		User u =new User(userId, userName, "1234", null);
		Boolean rs = new UserMapperImp().insertUser(u);
		Assert.assertTrue(rs);
	}
	@Test
	public void testSelectById(){
		User u = new UserMapperImp().selectUserById(userId);
		System.out.println(u);
	}
	@Test
	public void testSelectByName(){
		User u = new UserMapperImp().selectUserByName(userName);
		System.out.println(u);
	}
	@Test
	public void testUpdateUser(){
		User u = new User(userId, userName+"n", "1234", null);
		Boolean rs = new UserMapperImp().updateUser(u);
		Assert.assertTrue(rs);
	} 
	@After
	public void testDeleteUser(){
		Boolean rs = new UserMapperImp().deleteUser(userId);
		Assert.assertTrue(rs);
		
	}
	
}
