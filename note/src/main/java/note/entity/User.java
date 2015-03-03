package note.entity;

public class User {
	private String userId;
	private String userName;
	private String userPassword;
	private String userDetail;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public String getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(String userDetail) {
		this.userDetail = userDetail;
	}
	public User(String userId, String userName, String userPassword,
			String detail) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userDetail = detail;
	}
	public User() {
		super();
	}
	@Override
	public String toString() {
		return "User [userDetail=" + userDetail + ", userId=" + userId
				+ ", userName=" + userName + ", userPassword=" + userPassword
				+ "]";
	}
	
}
