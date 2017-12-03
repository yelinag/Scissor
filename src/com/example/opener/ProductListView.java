package com.example.opener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.example.opener.R;
import com.example.opener.ProductListView;

import Product.Product;
import Product.ProductMgr;
import User.SaveSharedPreference;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;


/*This class is the boundary class for displaying all products in the database*/
public class ProductListView extends Activity {
	
	int userId=0;
	int userType = 0;
	
	public final static String PRODUCT_ID ="ProductListView.PRODUCT_ID";
	public final static String PRODUCT_NAME = "ProductListView.PRODUCT_NAME";
	String [] strProductName;
	 ArrayList <Product> pList = new ArrayList <Product> ();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_layout);
        
        //Check on user session (security purpose)      	
    	String userIdString =  SaveSharedPreference.getUserId(this);
    	String userTypeString =  SaveSharedPreference.getUserType(this);
    	
    	if ((userIdString==null) || (userTypeString==null)) {
    		Toast.makeText(getApplicationContext(), "Unable to identify user account. Please login again. " ,
    				Toast.LENGTH_LONG).show();
    		return;
    	}
    	else{
    		userId =  Integer.parseInt(userIdString);
    		userType =  Integer.parseInt(userTypeString);
    	}
       
		
		  final ProductMgr pMgr = new ProductMgr(ProductListView.this);
	        pMgr.open();
	        
	        //add some products during the first time user visit to Product List Screen(just for testing purpose) 
	        Bitmap b1=BitmapFactory.decodeResource(getResources(), R.drawable.chocopie);
	        Bitmap b2=BitmapFactory.decodeResource(getResources(),R.drawable.nescafe);
	        Bitmap b3=BitmapFactory.decodeResource(getResources(),R.drawable.pepsi);
	        Bitmap b4=BitmapFactory.decodeResource(getResources(),R.drawable.pokka_carrot);
	        Bitmap b5=BitmapFactory.decodeResource(getResources(),R.drawable.jacknjill_ridges);
	        Bitmap b6=BitmapFactory.decodeResource(getResources(),R.drawable.paseo_tissue);
	        Bitmap b7=BitmapFactory.decodeResource(getResources(),R.drawable.myojo_tom_yum);
	    	
	        if (pMgr.getAllProduct().size() <= 0){
	        	
	        	ByteArrayOutputStream out1 = new ByteArrayOutputStream();
	        	ByteArrayOutputStream out2 = new ByteArrayOutputStream();
	        	ByteArrayOutputStream out3 = new ByteArrayOutputStream();
	        	ByteArrayOutputStream out4 = new ByteArrayOutputStream();
	        	ByteArrayOutputStream out5 = new ByteArrayOutputStream();
	        	ByteArrayOutputStream out6 = new ByteArrayOutputStream();
	        	ByteArrayOutputStream out7 = new ByteArrayOutputStream();

	            b1.compress(Bitmap.CompressFormat.PNG, 100, out1);
	            byte[] buffer1=out1.toByteArray();
	            Product p1 = new Product("Choco Pie",buffer1,"The ultimate taste.",10);
	            b2.compress(Bitmap.CompressFormat.PNG, 100, out2);
	            byte[] buffer2=out2.toByteArray();
	            Product p2 = new Product("Nescafe",buffer2,"Bring out the best in you.",20);
	            b3.compress(Bitmap.CompressFormat.PNG, 100, out3);
	            byte[] buffer3=out3.toByteArray();
	            Product p3 = new Product("Pepsi",buffer3,"A cool refreshing drink for your pleasure",30);
	            b4.compress(Bitmap.CompressFormat.PNG, 100, out4);
	            byte[] buffer4=out4.toByteArray();
	            Product p4 = new Product("Pokka",buffer4,"Delicious and Refreshing! Enjoy!",10);
	            b5.compress(Bitmap.CompressFormat.PNG, 100, out5);
	            byte[] buffer5=out5.toByteArray();
	            Product p5 = new Product("JacknJill",buffer5,"Opening happiness",20);
	            b6.compress(Bitmap.CompressFormat.PNG, 100, out6);
	            byte[] buffer6=out6.toByteArray();
	            Product p6 = new Product("Paseo",buffer6,"Delicious and Refreshing! Enjoy!",30);
	            b7.compress(Bitmap.CompressFormat.PNG, 100, out7);
	            byte[] buffer7=out7.toByteArray();
	            Product p7 = new Product("Myojo",buffer7,"Best noodle in town.",10);
	         
	        pMgr.addProduct(p1);
	        pMgr.addProduct(p2);
	        pMgr.addProduct(p3);
	        pMgr.addProduct(p4);
	        pMgr.addProduct(p5);
	        pMgr.addProduct(p6);
	        pMgr.addProduct(p7);
	        
	        }
	       
	       
	        //get all products from database
	        for (Product product:pMgr.getAllProduct() ){
	        	pList.add(product);
	        }
	        
	        strProductName = new String [pList.size()];

	        for (int i=0; i<pList.size(); i++){
	        	strProductName[i]=pList.get(i).getProductName();
	        }
	        
	        Bitmap[] bitArray=new Bitmap[strProductName.length];
	        
	        for (int i=0; i<strProductName.length; i++){
	        	bitArray[i] = BitmapFactory.decodeByteArray(pMgr.getProdImage(strProductName[i]), 0, pMgr.getProdImage(strProductName[i]).length);
	        }
	        
	        //set product 
	        final GridView gridview = (GridView) findViewById(R.id.gridview);
	        gridview.setAdapter(new ProductListImageAdapter(this, strProductName, bitArray));
	       

	        gridview.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	       		
	             
	            	if (userType ==1 || userType ==2){ //if the user type is correct (1 = normal user, 2 = Food manufacturer)
		            	
		                Intent intentRatingView = new Intent(ProductListView.this, ProductRatingView.class);
		                intentRatingView.putExtra(PRODUCT_ID, Integer.toString(pList.get(position).getProductId()));
		                intentRatingView.putExtra(PRODUCT_NAME, strProductName[position]);
		                ProductListView.this.startActivity(intentRatingView);
	            	}
	            	else {
	            		Toast.makeText(getApplicationContext(), "Unable to identify user account. Please login again. " ,
	        					Toast.LENGTH_LONG).show();
	            		Intent intentUIMain = new Intent(ProductListView.this, UIMain.class);
	            		ProductListView.this.startActivity(intentUIMain);
	            	}
	            	
	            }
	        });
	    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	//getMenuInflater().inflate(R.menu.main, menu);
        //return true;
    	
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
 
    	switch (item.getItemId()) {
        case R.id.action_home:
            //openHome();
        	Intent intent = new Intent(ProductListView.this, HomePage.class);
        	ProductListView.this.startActivity(intent);
            return true;
        case R.id.action_search:
            //openSearch();
            return true;
        default:
            return super.onOptionsItemSelected(item);
    	}
    	
    }
}
