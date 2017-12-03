package com.example.opener;

import java.util.ArrayList;

import com.example.opener.R;

import Product.ProductMgr;
import Product.RatingClass;
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
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*This class is the boundary class for user to rate a given product*/
public class ProductRatingView extends Activity {

	int userId = 0;
	int userType = 0;
	private ImageView mImageView;

	public final static String PRODUCT_ID = "ProductRatingView.PRODUCT_ID";
	public final static String PRODUCT_NAME = "ProductRatingView.PRODUCT_NAME";

	private String[] values = new String[] {
			"How convenient is the package to carry?",
			"How much do you like the package appearance?",
			"How easy is it to open the package? ",
			"How well does the package protect the food?",
			"How useful are the instructions in opening the package?" };

	private QuestionArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_rating_layout);

		// Check on user session (security purpose)
		String userIdString = SaveSharedPreference.getUserId(this);
		String userTypeString = SaveSharedPreference.getUserType(this);

		if ((userIdString == null) || (userTypeString == null)) {
			Toast.makeText(getApplicationContext(),
					"Unable to identify user account. Please login again. ",
					Toast.LENGTH_LONG).show();
			return;
		} else {
			userId = Integer.parseInt(userIdString);
			userType = Integer.parseInt(userTypeString);
		}

		Intent intent = getIntent();
		final String productId = intent
				.getStringExtra(com.example.opener.ProductListView.PRODUCT_ID);
		final String productName = intent
				.getStringExtra(com.example.opener.ProductListView.PRODUCT_NAME);

		final ProductMgr pMgr = new ProductMgr(ProductRatingView.this);
		pMgr.open();

		// Food Manufacturer will not need to rate the product, therefore, they
		// are redirected to Product Detail View
		// For normal user, if the user has been rated, immediately redirected
		// to Product Detail View
		if (userType == 2
				|| pMgr.hasUserRated(Integer.parseInt(productId), userId)) {
			Intent intentDetailView = new Intent(ProductRatingView.this,
					ProductView.class);
			intentDetailView.putExtra(PRODUCT_ID, productId);
			intentDetailView.putExtra(PRODUCT_NAME, productName);
			ProductRatingView.this.startActivity(intentDetailView);
		}

		// set product name
		TextView textProdName = (TextView) findViewById(R.id.tvProdName);
		textProdName.setText(productName);

		// set product image to the image box
		Bitmap bit = BitmapFactory.decodeByteArray(
				pMgr.getProdImage(productName), 0,
				pMgr.getProdImage(productName).length);
		mImageView = (ImageView) findViewById(R.id.ivProdPic);
		mImageView.setImageBitmap(bit);

		final ListView listview = (ListView) findViewById(R.id.listview);

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}

		// call the QuestionArrayAdapter class to set the questions and rating
		// values in the sub_UI
		adapter = new QuestionArrayAdapter(ProductRatingView.this, values);
		listview.setAdapter(adapter);

		// button click event listener for saving rating
		Button btnRating = (Button) findViewById(R.id.ratingDone);
		btnRating.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// check whether the user has rated all questions
				int[] ratingRecords = adapter.getRatingRecords();
				boolean checkAllRated = true;

				for (int i = 0; i < ratingRecords.length; i++) {
					if (ratingRecords[i] == 0) {
						checkAllRated = false;
						break;
					}
				}

				if (!checkAllRated) { //if the user has not rated some of the questions
					Toast.makeText(ProductRatingView.this,
							"Please enter all ratings.", Toast.LENGTH_LONG).show();
				} else {
					RatingMgr rMgr = new RatingMgr(ProductRatingView.this);
					rMgr.open();

					EditText etComment = (EditText) findViewById(R.id.prlCommentBox);
					// check whether the user has entered comment
					if (etComment.getText().toString().trim().length() == 0) {

						Toast.makeText(ProductRatingView.this,
								"Please enter a comment first.",
								Toast.LENGTH_LONG).show();

					} else {

						String comment = etComment.getText().toString();

						RatingClass r = new RatingClass(userId, Integer
								.parseInt(productId), ratingRecords[0],
								ratingRecords[1], ratingRecords[2],
								ratingRecords[3], ratingRecords[4], comment); 
						
						//save rating and comment to Rating table 
						boolean result = rMgr.addRating(r);
						
						if (result) {
							Toast.makeText(ProductRatingView.this,
									"Thank you for your rating and comment.",
									Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(ProductRatingView.this,
									ProductView.class);
							intent.putExtra(PRODUCT_ID, productId);
							intent.putExtra(PRODUCT_NAME, productName);
							ProductRatingView.this.startActivity(intent);
						} else {
							Toast.makeText(ProductRatingView.this,
									"Some error occurs. Please try again.",
									Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});

		setListViewHeightBasedOnChildren(listview);

	}

	/****
	 * Method for Setting the Height of the ListView dynamically. Hack to fix
	 * the issue of not showing all the items of the ListView when placed inside
	 * a ScrollView
	 ****/
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null)
			return;

		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
				MeasureSpec.UNSPECIFIED);
		int totalHeight = 0;
		int offset = 50;
		View view = null;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, listView);
			if (i == 0)
				view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
						LayoutParams.WRAP_CONTENT));

			view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = offset + totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
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
			// openHome();
			Intent intent = new Intent(ProductRatingView.this, HomePage.class);
			ProductRatingView.this.startActivity(intent);
			return true;
		case R.id.action_search:
			// openSearch();
			return true;
		case R.id.action_share:
			EditText etComment = (EditText) findViewById(R.id.prlCommentBox);
			String comment = etComment.getText().toString();
			String shareBody = comment;
			// Toast.makeText(ProductRatingView.this, "Comment is" + comment,
			// Toast.LENGTH_LONG).show();
			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Packagio");
			sharingIntent
					.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			startActivity(Intent.createChooser(sharingIntent,
					"Send your picture using:"));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
