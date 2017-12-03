package com.example.opener;

import java.util.ArrayList;

import Product.Product;
import Product.ProductMgr;
import Product.RatingMgr;
import User.SaveSharedPreference;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/*This class is the boundary class for displaying the Product page with comments and overall rating */
public class ProductView extends Activity {

	int userId=0;
	int userType = 0;
	private ImageView mImageView;
	
	private CommentArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_view_layout);
		
		int productIdtoInt =0;
		final String productId;
		final String productName; 
		Intent intent = getIntent();
		
		 //Check on user session      	
    	String userIdString =  SaveSharedPreference.getUserId(this);
    	String userTypeString =  SaveSharedPreference.getUserType(this);
    	
    	if ((userIdString==null) || (userTypeString==null)) {
    		Toast.makeText(getApplicationContext(), "Unable to identify user account. Please login again. " ,
    				Toast.LENGTH_LONG).show();
    		intent = new Intent(ProductView.this, UIMain.class);
    		ProductView.this.startActivity(intent);
    	}
    	else{
    		userId =  Integer.parseInt(userIdString);
    		userType =  Integer.parseInt(userTypeString);
    	}
		
		
		 productId = intent
				.getStringExtra(com.example.opener.ProductRatingView.PRODUCT_ID);
		 productName = intent
				.getStringExtra(com.example.opener.ProductRatingView.PRODUCT_NAME);
	
		
		if (productId !=null){
			productIdtoInt = Integer.parseInt(productId);
		}
	
		//====================== Getting the values ==========================
		RatingMgr rMgr = new RatingMgr(this);
		rMgr.open();
		
		ProductMgr pMgr = new ProductMgr(this);
		pMgr.open();
		Product p = pMgr.getProductByProdName(productName);
		
		float rating = rMgr.getOverallAvgRating(productIdtoInt);
		
		ArrayList <String[]> userNameAndCommentAryList = rMgr.getUserNameAndCommentByProdId(productIdtoInt);
		
		String[] comments= new String [userNameAndCommentAryList.size()];
		for (int i=0;i<userNameAndCommentAryList.size();i++){
			comments[i] = userNameAndCommentAryList.get(i)[1];
		}	
	
		String[] names= new String [userNameAndCommentAryList.size()];
		for (int i=0;i<userNameAndCommentAryList.size();i++){
			names[i] = userNameAndCommentAryList.get(i)[0];
		}
	
		//====================== Display the values ==========================		
		TextView textProdName = (TextView) findViewById(R.id.pvlProductName);
		textProdName.setText(productName);
		
		Bitmap bit=BitmapFactory.decodeByteArray(pMgr.getProdImage(productName), 0, pMgr.getProdImage(productName).length);
		mImageView = (ImageView) findViewById(R.id.pvlProductPic);
		mImageView.setImageBitmap(bit);	
		
		TextView textProdDesc = (TextView) findViewById(R.id.pvlDescription);
		textProdDesc.setText(p.getProductDesc());
		
		RatingBar ratingBar = (RatingBar) findViewById(R.id.pvlOverallRating);
		ratingBar.setRating(rating);

		final ListView listview = (ListView) findViewById(R.id.pvlCommentList);
	
		adapter = new CommentArrayAdapter(ProductView.this,
				comments,names);
		listview.setAdapter(adapter);
		
		//=======================================================================
		
		listview.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		
		//when the user click "OK" button, redirect him to Product list View
		Button btnOk = (Button)findViewById(R.id.pvlOkay);
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ProductView.this, ProductListView.class);
				ProductView.this.startActivity(intent);
			}
		});
		
		setListViewHeightBasedOnChildren(listview);
	}
	
	/**** Method for Setting the Height of the ListView dynamically.
	 **** Hack to fix the issue of not showing all the items of the ListView
	 **** when placed inside a ScrollView  ****/
	public static void setListViewHeightBasedOnChildren(ListView listView) {
	    ListAdapter listAdapter = listView.getAdapter();
	    if (listAdapter == null)
	        return;

	    int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
	    int totalHeight = 0;
	    View view = null;
	    for (int i = 0; i < listAdapter.getCount(); i++) {
	        view = listAdapter.getView(i, view, listView);
	        if (i == 0)
	            view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));

	        view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
	        totalHeight += view.getMeasuredHeight();
	    }
	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	    listView.setLayoutParams(params);
	    listView.requestLayout();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
        	Intent intent = new Intent(ProductView.this, HomePage.class);
        	ProductView.this.startActivity(intent);
            return true;
        case R.id.action_share:
        	Intent intent_share = new Intent(Intent.ACTION_SEND);
			intent_share.setType("text/plain");
			intent_share.putExtra(Intent.EXTRA_TEXT, "Check this out!!");
			startActivity(Intent.createChooser(intent_share, "Dialog title text"));
            return true;
            
        default:
            return super.onOptionsItemSelected(item);
    	}
	}
}
