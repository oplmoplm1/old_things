package note.dao;

import note.entity.User;

public interface UserMapper {
	User selectUserById(String userId);
	User selectUserByName(String userName);
	Boolean insertUser(User user);
	Boolean updateUser(User user);
	Boolean deleteUser(String userId);
}
