package com.example.opener;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/*This class extends SQLiteOpenHelper Class*/
public  class DBOpenHelper2 extends SQLiteOpenHelper {

	public DBOpenHelper2(Context context) {
		super(context, GlobalSQL.DATABASE_NAME, null,
				GlobalSQL.DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * onCreate() method to create required tables in the SQLite database
	 * @param SQLiteDatabase db
	 * @return: void
	 */
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(GlobalSQL.CREATE_UserTable);
			db.execSQL(GlobalSQL.CREATE_ProductTable);
			db.execSQL(GlobalSQL.CREATE_RatingTable);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		;

	}

	@Override
	/**
	 * onCreate() method to create required tables in the SQLite database
	 * @param SQLiteDatabase db
	 * @int oldVersion
	 * @inte newVersion
	 * @return: void
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //in cases of upgrade, drop tables
		//			for (String table : GlobalSQL.ALL_TABLES) {
		//				db.execSQL("DROP TABLE IF EXISTS " + table);
		//			}
		db.execSQL("DROP TABLE IF EXISTS " + GlobalSQL.Table_Product);
		db.execSQL("DROP TABLE IF EXISTS " + GlobalSQL.Table_User);
		db.execSQL("DROP TABLE IF EXISTS " + GlobalSQL.Table_Rating);
		onCreate(db);

	}

}
