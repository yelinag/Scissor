package Product;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.database.Cursor;

public class Product {

	/* Attributes */
	private int productId;
	private String productName;
	private byte[] productImage;
	private String productDesc;
	private int fmUserId;
	

	// private Date createdDate = new Date();// datetime format
	// private Date lastUpdatedDate = new Date();// datetime format
	// private Date deletedDate = new Date();// datetime format
	// private String createdBy;
	// private String lastUpdatedBy;
	// private String deletedBy;

	/* Methods */
	


	
	public Product( String productName,  byte[] productImage,String productDesc,
			int fmUserId) {
		super();
		this.productDesc=productDesc;
		this.productName = productName;
		setProductImage(productImage);
		this.fmUserId = fmUserId;
	}


	public Product(Cursor cursor) {

		this.setProductId(cursor.getInt(0));
		this.setProductName(cursor.getString(1));
		this.setProductImage(cursor.getBlob(2));
		this.setProductDesc(cursor.getString(3));
		this.setFMUserId(cursor.getInt(4));

	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public byte[] getProductImage() {
		return productImage;
	}

	public void setProductImage(byte[] oldproductImage) {
		this.productImage=new byte[oldproductImage.length];
		for(int i=0;i<oldproductImage.length;i++){
			
			this.productImage[i]=oldproductImage[i];	
		}
		
	}



	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		if (productDesc == "" || productDesc == null) {
			this.productDesc = "";
		} else
			this.productDesc = productDesc;
	}

	
	public int getFMUserId() {
		return fmUserId;
	}

	public void setFMUserId(int fmUserId) {
		this.fmUserId = fmUserId;
	}

	public boolean assignBrand() {
		return true;
	}

	public boolean unassignBrand() {
		return true;
	}



}
