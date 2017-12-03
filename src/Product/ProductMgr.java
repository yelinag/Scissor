package Product;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.example.opener.DBOpenHelper2;
import com.example.opener.GlobalSQL;

public class ProductMgr {
/* Attributes */
	private ArrayList<Product> productList ;
	private Context context;
	SQLiteDatabase database1;
	private DBOpenHelper2 dbOpenHelper;
	
	public ProductMgr(Context c) {
		context = c;
		dbOpenHelper = new DBOpenHelper2(context);
	}

	public ProductMgr open() throws SQLException {
		database1 = dbOpenHelper.getWritableDatabase();
		return this;

	}

	public void close() {
		dbOpenHelper.close();
	}

	
/* Methods */
	public ArrayList<Product> getAllProduct(){
		productList =new ArrayList<Product>();
		Cursor cursor = database1.query(GlobalSQL.Table_Product,
				GlobalSQL.productTableColumns, null, null, null, null, null);
		if (cursor.getCount()>0 && cursor != null){
			cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Product b = new Product(cursor);
			productList.add(b);
			cursor.moveToNext();
		}
		}
		
		cursor.close();
		return productList;
	}
	public ArrayList<Product> getProductByFMId(int FMId){
		productList =new ArrayList<Product>();
		Cursor cursor = database1.query(GlobalSQL.Table_Product,
				GlobalSQL.productTableColumns, GlobalSQL.Column_FMId + " = " +  FMId,
				null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Product b = new Product(cursor);
			productList.add(b);
			cursor.moveToNext();
		}
		cursor.close();
		return productList;
	}
	public Product getProductByProdName(String prodName){
		
		Cursor cursor = database1.query(GlobalSQL.Table_Product,
				GlobalSQL.productTableColumns, GlobalSQL.Column_ProductName + " = '" +  prodName +"'",
				null, null, null, null);
		if (cursor.getCount() <= 0) {
			return null;
		}
		
		Product b=null;
		if (cursor.getCount()>0 && cursor != null){
		cursor.moveToFirst();
			b = new Product(cursor);
	}
		return b;
	}
	public boolean addProduct(Product b){

		ContentValues contentValue = new ContentValues();
		contentValue.put(GlobalSQL.Column_ProductName, b.getProductName());
		contentValue.put(GlobalSQL.Column_ProductImage, b.getProductImage());
		contentValue.put(GlobalSQL.Column_ProductDesc, b.getProductDesc());
		contentValue.put(GlobalSQL.Column_FMUserId, b.getFMUserId());
		
		long result = 0;
		result = database1.insert(GlobalSQL.Table_Product, null, contentValue);
		if (result != -1)
			return true;
		else
			return false;
	}
	
	public boolean addProdImageandName(Bitmap b,String pname){		
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();

        b.compress(Bitmap.CompressFormat.PNG, 100, out);

        byte[] buffer=out.toByteArray();
        
        ContentValues cv=new ContentValues();
        cv.put(GlobalSQL.Column_ProductImage, buffer);  
        cv.put(GlobalSQL.Column_ProductName, pname);
        long rowId=database1.insert(GlobalSQL.Table_Product, null, cv); 
        
        if (rowId != -1)
			return true;
		else
			return false;
        
        
	}
	
	public boolean addProductDetails(String pname, String desc, int uid){
		 ContentValues cv=new ContentValues();
	        cv.put(GlobalSQL.Column_ProductDesc, desc);  
	        cv.put(GlobalSQL.Column_FMUserId, uid);
		
	        long rowId=database1.update(GlobalSQL.Table_Product, cv,GlobalSQL.Column_ProductName +" = '"+ pname + "'", null);
	        if (rowId != -1)
				return true;
			else
				return false;
		
	}
	
	public byte[] getProdImage(String pname){
		
	
		String Query = "SELECT * FROM " + GlobalSQL.Table_Product + " WHERE "
				+ GlobalSQL.Column_ProductName + "='" + pname + "'";
		Cursor cursor = database1.rawQuery(Query, null);
		
		if (cursor.getCount() <= 0) {
			return null;
		}
		
		Product b;
		 
		if (cursor.getCount()>0 && cursor != null){
		cursor.moveToFirst();
			b = new Product(cursor);
			
		return b.getProductImage();
		

	}
		
		return null;
		
		
		
	}
	
	public boolean checkProdNameExists(String pname){

		String Query = "SELECT * FROM " + GlobalSQL.Table_Product + " WHERE "
				+ GlobalSQL.Column_ProductName + "='" + pname + "'";
		Cursor cursor = database1.rawQuery(Query, null);
		if (cursor.getCount() > 1) {
			return true;
		}
		return false;

		
	}
	
	
	
	/* Methods to delete products */
	public int deleteProduct(int productId){
		return (database1.delete(GlobalSQL.Table_Product, GlobalSQL.Column_ProductId + " = " + productId, null));
	}
	

	/* Methods to update product */
public int updateProduct(int productId, Product b){
		
		ContentValues contentValue = new ContentValues();
		contentValue.put(GlobalSQL.Column_ProductName, b.getProductName());
		contentValue.put(GlobalSQL.Column_ProductImage, b.getProductImage());
		
		contentValue.put(GlobalSQL.Column_ProductDesc, b.getProductDesc());
		contentValue.put(GlobalSQL.Column_FMUserId, b.getFMUserId());
		
		return (database1.update(GlobalSQL.Table_Product,contentValue, GlobalSQL.Column_ProductId + " = " + productId, null));
	}
	public boolean assignFMtoProduct(int productId,int FMId){
		/*This method is to assign a Food Manufacturer to a product.
		 *  This method is needed if we allow a brand or a product to exist
		 *   without a food manufacturer (i.e. without parent/child relation)
		 *   For example, administrator wants to create a new product. 
		 *   But the FM is not registered in the system yet. 
		 */
		return true;
	}
	public boolean unAssignFMtoProduct(int productId){
		/*This method is to unAssign a Food Manufacturer from a product.
		 *  This method is needed if we allow a brand or a product to exist
		 *   without a food manufacturer (i.e. without parent/child relation)
		 *   For example, administrator want to remove a FM from a product 
		 *   because the product is claimed to be owned by another FM.  
		 *   Note that FMId is not needed in parameter assuming a product can only be under a FM.
		 */
		return true;
	}
	
	public boolean hasUserRated(int pid, int uid){

		String Query = "SELECT * FROM " + GlobalSQL.Table_Rating + " WHERE "
				+ GlobalSQL.Column_Pid + "=" + pid + " AND "
				+ GlobalSQL.Column_Uid + "=" + uid ;
		Cursor cursor = database1.rawQuery(Query, null);
		if (cursor.getCount() <= 0) {
			return false;
		}
		return true;

	}
	
}
