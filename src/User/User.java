package User;

import android.database.Cursor;

/**
 * 
 * User class contain the relevant attributes of a user such as email, password, name, userType
 *
 */
public class User {

	private int userid;
	private String email;
	private String password;
	private String name;
	private int UserType;

	/**
	 * Constructor 1
	 * @param email
	 * @param password
	 * @param name
	 * @param userType
	 */
	public User(String email, String password, String name, int userType) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		UserType = userType;
	}
	/**
	 * Constructor 2	
	 * @param cursor
	 */
	public User(Cursor cursor) {
		this.setUserid(cursor.getInt(0));
		this.setEmail(cursor.getString(1));
		this.setPassword(cursor.getString(2));
		this.setName(cursor.getString(3));
		this.setUserType(cursor.getInt(4));
	}

	/**
	 * Method to get the  userid
	 * @return int(userid)
	 */
	public int getUserid() {
		return userid;
	}

	/**
	 * Method to set the userid
	 * @param userid
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}

	/**
	 * Method to get name 
	 * @return String(name)
	 */
	public String getName(){
		return name;
	}

	/**
	 * Method to set name of user
	 * @param newName
	 */
	public void setName(String newName){
		this.name=newName;
	}

	/**
	 * Method to get email of user
	 * @return String(email)
	 */
	public String getEmail(){
		return email;
	}

	/**
	 * Method to set email of user
	 * @param newEmail
	 */
	public void setEmail(String newEmail){
		this.email=newEmail;
	}

	/**
	 * Method to get password of user
	 * @return String(password)
	 */
	public String getPassword(){
		return password;
	}

	/**
	 * Method to set password of user
	 * @param newPassword
	 */
	public void setPassword(String newPassword){
		this.password=newPassword;
	}

	/**
	 * Method to get usertype: FM or normal user 
	 * @return int (usertype)
	 */
	public int getUserType() {
		return UserType;
	}
	/**
	 * Method to set usertype
	 * @param userType
	 */
	public void setUserType(int userType) {
		UserType = userType;
	}
}
