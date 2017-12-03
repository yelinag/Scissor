package Product;

//import Brand.BrandMgr;
import java.util.ArrayList;

import User.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.opener.DBOpenHelper2;
import com.example.opener.GlobalSQL;

//each qn has a rating mgr
public class RatingMgr {
	private Context context;
	SQLiteDatabase database;
	private DBOpenHelper2 dbOpenHelper;
	
public RatingMgr(Context c ){
	context = c;
	dbOpenHelper = new DBOpenHelper2(context);
	}

public RatingMgr open() throws SQLException {
	database = dbOpenHelper.getWritableDatabase();
	return this;

}

public void close() {
	dbOpenHelper.close();
}

/*
 * add rating--add it to db
 */

public boolean addRating(RatingClass r) {
	ContentValues contentValue = new ContentValues();
	contentValue.put(GlobalSQL.Column_Uid, r.getUid());
	contentValue.put(GlobalSQL.Column_Pid, r.getPid());
	contentValue.put(GlobalSQL.Column_Comment, r.getComment());
	contentValue.put(GlobalSQL.Column_Rval1, r.getRatingVal1());
	contentValue.put(GlobalSQL.Column_Rval2, r.getRatingVal2());
	contentValue.put(GlobalSQL.Column_Rval3, r.getRatingVal3());
	contentValue.put(GlobalSQL.Column_Rval4, r.getRatingVal4());
	contentValue.put(GlobalSQL.Column_Rval5, r.getRatingVal5());
	
	long result = 0;
	result = database.insert(GlobalSQL.Table_Rating, null, contentValue);
	if (result != -1){
		return true;}
	else{
		return false;}
	
}




//retrieve rating--query db
public double getQnAvgRating(int pid, int qid){
	String Query = "SELECT AVG(RatingVal"+qid+ ") FROM " + GlobalSQL.Table_Rating + " WHERE "
			+ GlobalSQL.Column_Pid + " = "+pid;
	Cursor cursor = database.rawQuery(Query, null);
	
	if (cursor.getCount()>0 && cursor != null){
		cursor.moveToFirst();
		return cursor.getInt(0);
	}
	
	cursor.close();
	return -1;
	
	
}

public float getOverallAvgRating(int pid){
	String Query = "SELECT AVG(RatingVal1),AVG(RatingVal2),AVG(RatingVal3) ,AVG(RatingVal4) ,AVG(RatingVal5) FROM " + GlobalSQL.Table_Rating + " WHERE "
			+ GlobalSQL.Column_Pid + " = "+pid;
	Cursor cursor = database.rawQuery(Query, null);
	if (cursor.getCount()>0 && cursor != null){
		cursor.moveToFirst();
		return (float) ((cursor.getInt(0)+cursor.getInt(1)+cursor.getInt(2)+cursor.getInt(3)+cursor.getInt(4))/5.0);
	}
	
	cursor.close();
	return 0;

}

public boolean hasUserRated(int pid, int uid){

	String Query = "SELECT * FROM " + GlobalSQL.Table_Rating + " WHERE "
			+ GlobalSQL.Column_Pid + "=" + pid + " AND "
			+ GlobalSQL.Column_Uid + "=" + uid ;
	Cursor cursor = database.rawQuery(Query, null);
	if (cursor.getCount() <= 0) {
		return false;
	}
	return true;

}

public ArrayList <String[]>  getUserNameAndCommentByProdId(int prodId){
	ArrayList <String[]> userNameAndCommentAryList =new ArrayList<String[]> ();
	String query = "SELECT b."+GlobalSQL.Column_Name + ",a." + GlobalSQL.Column_Comment 
			+ " FROM "+GlobalSQL.Table_Rating 
			+ " a INNER JOIN "+ GlobalSQL.Table_User+" b ON " 
			+ "a." + GlobalSQL.Column_Uid + "= b."+ GlobalSQL.Column_userId 
			+ " WHERE a."+GlobalSQL.Column_Pid +"=" +  prodId;
	Cursor cursor = database.rawQuery(query, null);
	cursor.moveToFirst();
	while (!cursor.isAfterLast()) {
		String[] s1 = {cursor.getString(0),cursor.getString(1)};
		userNameAndCommentAryList.add(s1);
		cursor.moveToNext();
	}
	cursor.close();
	return userNameAndCommentAryList;
}

public ArrayList <String>  getCommentByUserId(int userId){
	ArrayList <String> commentAryList =new ArrayList<String> ();
	String query = "SELECT " + GlobalSQL.Column_Comment 
			+ " FROM "+GlobalSQL.Table_Rating 
			+ " WHERE "+GlobalSQL.Column_Uid +"=" +  userId;
	Cursor cursor = database.rawQuery(query, null);
	cursor.moveToFirst();
	while (!cursor.isAfterLast()) {
		String s1 = cursor.getString(0);
		commentAryList.add(s1);
		cursor.moveToNext();
	}
	cursor.close();
	return commentAryList;
}
}
