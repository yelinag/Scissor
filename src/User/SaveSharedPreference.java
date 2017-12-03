package User;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * SaveSharedPreference is a class used to track information about the logged in 
 * user across screens. It is also cleared when the user logs out. 
 * 
 *
 */

public class SaveSharedPreference 
{
	static final String PREF_USER_NAME= "username";
	static final String PREF_USER_ID= "userId";
	static final String PREF_USER_TYPE = "userType";

	/**
	 * Method to get SharedPreferences
	 * @param ctx
	 * @return SharedPreferences
	 */
	static SharedPreferences getSharedPreferences(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	/**
	 * Method setUserName is used to get name of user
	 * @param ctx
	 * @param userName
	 */
	public static void setUserName(Context ctx, String userName) 
	{
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_USER_NAME, userName);
		editor.commit();
	}

	/**
	 * Method getUserName to get name based on context
	 * @param ctx
	 * @return String
	 */
	public static String getUserName(Context ctx)
	{
		return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
	}

	/**
	 * Method to set user's id based on context 
	 * @param ctx
	 * @param userId
	 */
	public static void setUserId(Context ctx, String userId) 
	{
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_USER_ID, userId);
		editor.commit();
	}

	/**
	 * Method to getUserId from the context
	 * @param ctx
	 * @return String-userid
	 */
	public static String getUserId(Context ctx)
	{
		return getSharedPreferences(ctx).getString(PREF_USER_ID, "");
	}

	/**
	 * Method to set UserType
	 * @param ctx
	 * @param String-userType
	 */
	public static void setUserType(Context ctx, String userType) 
	{
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_USER_TYPE, userType);
		editor.commit();
	}

	/**
	 * Method to get usertype
	 * @param ctx
	 * @return String 
	 */

	public static String getUserType(Context ctx)
	{
		return getSharedPreferences(ctx).getString(PREF_USER_TYPE, "");
	}
}