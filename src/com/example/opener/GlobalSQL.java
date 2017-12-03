package com.example.opener;

/*this class is a collection of all string declarations used in the program*/


public class GlobalSQL {

	public static final String DATABASE_NAME = "Packing.db";
	public static final int DATABASE_VERSION = 1;

	public static final String Column_FMId = "FMId";


	// ============ User table =============//
	public static final String Table_User = "UserTable";
	public static final String Column_userId = "UserId";
	public static final String Column_Email = "Email";
	public static final String Column_Password = "Password";
	public static final String Column_Name = "UserName";
	public static final String Column_UserType = "UserType";

	public static final String[] userTableColumns = { Column_userId,
		Column_Email,Column_Password, Column_Name, Column_UserType };

	public static final String CREATE_UserTable = "create table " + Table_User
			+ "(" + Column_userId + " integer primary key autoincrement, "
			+ Column_Email + " text, "
			+ Column_Password + " text not null, "
			+ Column_Name + " text, " 
			+ Column_UserType + " int );";


	// ============ Product table =============//
	public static final String Table_Product = "ProductTable";
	public static final String Column_ProductId = "ProductId";
	public static final String Column_ProductName = "ProductName";
	public static final String Column_ProductImage = "ProductImage";
	public static final String Column_ProductDesc = "ProductDesc";

	public static final String Column_FMUserId = "FMUserId";

	public static final String[] productTableColumns = { Column_ProductId,
		Column_ProductName,Column_ProductImage,
		Column_ProductDesc, Column_FMUserId};

	public static final String CREATE_ProductTable = "create table " + Table_Product
			+ "(" + Column_ProductId + " integer primary key autoincrement, "
			+ Column_ProductName + " text not null, " 
			+ Column_ProductImage + " blob, "
			+ Column_ProductDesc + " text, " 
			+ Column_FMUserId + " int "
			+ " );";

	// ============ Rating table =============//
	public static final String Table_Rating = "RatingTable";

	public static final String Column_Uid = "UserId";
	public static final String Column_Pid = "ProductId";
	public static final String Column_Comment = "Comment";

	public static final String Column_Rval1 = "RatingVal1";
	public static final String Column_Rval2 = "RatingVal2";
	public static final String Column_Rval3 = "RatingVal3";
	public static final String Column_Rval4 = "RatingVal4";
	public static final String Column_Rval5 = "RatingVal5";

	public static final String[] RatingTableColumns = { Column_Uid,
		Column_Pid,Column_Comment,Column_Rval1,Column_Rval2,Column_Rval3,Column_Rval4,Column_Rval5 };

	public static final String CREATE_RatingTable = "create table " + Table_Rating
			+ "(" + Column_Uid + " int, "
			+ Column_Pid + " int, " 
			+ Column_Comment + " text, "
			+ Column_Rval1 + " int, " 
			+ Column_Rval2 + " int, " 
			+ Column_Rval3 + " int, " 
			+ Column_Rval4 + " int, " 
			+ Column_Rval5 + " int " 
			//+ " primary key " + "( " + Column_Uid + ", "+ Column_Pid + ")"
			+ ");";

}
