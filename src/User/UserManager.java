
package User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.opener.*;

/**
 * UserManager class is the control class for Users
 */

public class UserManager {

	SQLiteDatabase database;
	private DBOpenHelper2 dbOpenHelper;
	private Context context;
	private static int[] verifCodeArray = {10160,10260,10360,10460,10560,10660,10760,10860,10960,11060};

	public UserManager(Context c) {
		context = c;
		dbOpenHelper = new DBOpenHelper2(context);
	}

	/**
	 * Open method to open the database for UserManager
	 * @return UserManager
	 * @throws SQLException
	 */
	public UserManager open() throws SQLException {
		database = dbOpenHelper.getWritableDatabase();
		return this;
	}

	/**
	 * Open method to open the database for UserManager
	 */

	public void close() {
		dbOpenHelper.close();
	}

	/**
	 * validateUser method to check if email and password provided by user exists in database	
	 * @param email
	 * @param password
	 * @return boolean
	 */

	public boolean validateUser(String email, String password) {
		// check email and password for login purposes

		String Query = "SELECT * FROM " + GlobalSQL.Table_User + " WHERE "
				+ GlobalSQL.Column_Email + "='" + email + "' AND "
				+ GlobalSQL.Column_Password + "='" + password + "'";
		Cursor cursor = database.rawQuery(Query, null);
		if (cursor.getCount() <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 	validateAndGetUser method to check if email and password provided by User exists 
	 *  in the database and return a User		
	 * @param email
	 * @param password
	 * @return User
	 */
	public User validateAndGetUser(String email, String password) {
		// check email and password for login purposes
		// Return user object if the user is found


		// dbOpenHelper.onUpgrade(database, 1, 1);
		User b = null;
		String Query = "SELECT * FROM " + GlobalSQL.Table_User + " WHERE "
				+ GlobalSQL.Column_Email + "='" + email + "' AND "
				+ GlobalSQL.Column_Password + "='" + password + "'";
		Cursor cursor = database.rawQuery(Query, null);
		if (cursor.getCount()>0 && cursor != null){
			cursor.moveToFirst();
			b = new User(cursor);
		}

		cursor.close();
		return b;

	}

	/**
	 * addFMUser method to add food manufacturer into the user database
	 * @param u
	 * @return boolean 
	 */
	// To add Food Manufacturer User
	public boolean addFMUser(User u) {

		ContentValues contentValue = new ContentValues();
		contentValue.put(GlobalSQL.Column_Name, u.getName());
		contentValue.put(GlobalSQL.Column_Email, u.getEmail());
		contentValue.put(GlobalSQL.Column_Password, u.getPassword());
		contentValue.put(GlobalSQL.Column_UserType, 0);
		long result = 0;
		result = database.insert(GlobalSQL.Table_User, null, contentValue);
		if (result != -1)
			return true;
		else
			return false;
	}
	/**
	 * addUser method to add normal user into the user database 
	 * @param u
	 * @return boolean 
	 */
	// To add normal user
	public boolean addUser(User u) {

		ContentValues contentValue = new ContentValues();
		contentValue.put(GlobalSQL.Column_Name, u.getName());
		contentValue.put(GlobalSQL.Column_Email, u.getEmail());
		contentValue.put(GlobalSQL.Column_Password, u.getPassword());
		contentValue.put(GlobalSQL.Column_UserType, u.getUserType());
		long result = 0;
		result = database.insert(GlobalSQL.Table_User, null, contentValue);
		if (result != -1)
			return true;
		else
			return false;
	}

	/**
	 * checkUserExist method to see if user with email provided exists in database
	 * @param email
	 * @return boolean
	 */
	public boolean checkUserExist(String email) {

		String Query = "SELECT * FROM " + GlobalSQL.Table_User + " WHERE "
				+ GlobalSQL.Column_Email + "='" + email + "'";
		Cursor cursor = database.rawQuery(Query, null);
		if (cursor.getCount() <= 0) {
			return false;
		}
		return true;

	}

	/**
	 * checkFMUserValid to see if the code provided by the FMUser is valid 
	 * @param verifCode
	 * @return boolean 
	 */

	public boolean checkFMUserValid(int verifCode) {

		for (int i = 0; i < verifCodeArray.length; i++) {
			if (verifCode == verifCodeArray[i] && verifCode != 0) {
				return true;
			}
		}

		return false;

	}

	/**
	 * updatePassword method to change user's password in the database		
	 * @param email
	 * @param oldPassword
	 * @param newPassword
	 * @return boolean 
	 */

	//to update password
	public boolean updatePassword(String email, String oldPassword, String newPassword){
		boolean result=false;
		int uid=-1;
		//ContentValues values=new ContentValues();
		String Query = "SELECT * FROM " + GlobalSQL.Table_User + " WHERE "
				+ GlobalSQL.Column_Email + "='" + email + "'";
		Cursor cursor = database.rawQuery(Query, null);
		if (cursor.getCount() <= 0) {

			result=false;
		}
		User b;

		if (cursor.getCount()>0 && cursor != null){

			cursor.moveToFirst();

			b = new User(cursor);
			uid=b.getUserid();
		}



		ContentValues contentValue = new ContentValues();

		contentValue.put(GlobalSQL.Column_Password, newPassword);

		int rowUpdated=database.update(GlobalSQL.Table_User, contentValue, GlobalSQL.Column_userId+"="+uid, null);


		if (rowUpdated!=0){ 
			result=true;}


		return result;

	}

	/**
	 * getNameFromEmail method to get username on the basis of his email
	 * @param email
	 * @return String(name)
	 */
	public String getNameFromEmail(String email) {
		String returnString;
		String Query = "SELECT * FROM " + GlobalSQL.Table_User + " WHERE "
				+ GlobalSQL.Column_Email + "='" + email + "'";
		Cursor cursor = database.rawQuery(Query, null);
		if (cursor.getCount() <= 0) {
			returnString= "Invalid email";
		}
		User b;
		returnString="Invalid name";
		if (cursor.getCount()>0 && cursor != null){
			cursor.moveToFirst();
			b = new User(cursor);
			returnString=b.getName();


		}
		return returnString;
	}

	/**
	 * method getUidFromEmail to get a user's userid from email
	 * @param email
	 * @return int (userid)
	 */
	public int getUidFromEmail(String email) {
		int returnInt;
		String Query = "SELECT * FROM " + GlobalSQL.Table_User + " WHERE "
				+ GlobalSQL.Column_Email + "='" + email + "'";
		Cursor cursor = database.rawQuery(Query, null);
		if (cursor.getCount() <= 0) {
			returnInt= -1;
		}
		User b;
		returnInt=-1;
		if (cursor.getCount()>0 && cursor != null){
			cursor.moveToFirst();
			b = new User(cursor);
			returnInt=b.getUserid();


		}
		return returnInt;
	}

	/**
	 * method updateEmailandName to change user's email from old to new
	 * @param oldEmail
	 * @param newEmail
	 * @param name
	 * @return boolean 
	 */

	public boolean updateEmailAndName(String oldEmail, String newEmail, String name){

		boolean result=false;
		ContentValues values=new ContentValues();
		String Query = "SELECT * FROM " + GlobalSQL.Table_User + " WHERE "
				+ GlobalSQL.Column_Email + "='" + oldEmail + "'";
		Cursor cursor = database.rawQuery(Query, null);
		if (cursor.getCount() <= 0) {

			result=false;
		}
		User b;

		if (cursor.getCount()>0 && cursor != null){

			cursor.moveToFirst();

			b = new User(cursor);
			int uid=b.getUserid();

			values.put(GlobalSQL.Column_Email, newEmail);
			values.put(GlobalSQL.Column_Name, name);
			int id=database.update(GlobalSQL.Table_User, values, GlobalSQL.Column_userId+"="+uid, null);
			if (id==0){

				result=false;
			}
			else{

				result=true;
			}
		}	return result;


	}
}
