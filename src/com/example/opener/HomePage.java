package com.example.opener;

import com.example.opener.R;

import User.SaveSharedPreference;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/*This class is the boundary class for displaying the Home Page of the User */
public class HomePage extends Activity {

	//array of Strings for different actions available to the user
	String[] web = {
			"Search Product",
			"User Profile",
			"View History",
			"About Us",
			"Settings",
			"Log Out"

	} ;
	//array of ids of drawable images to be displayed on the Home Page
	int[] mThumbIds = {
			R.drawable.ic_action_search, R.drawable.user_profile_button,
			R.drawable.history_button, R.drawable.about_us_button,
			R.drawable.setting_button, R.drawable.log_out_big_button
	};



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page_layout);

		GridView gridview = (GridView) findViewById(R.id.homeButtons);
		gridview.setAdapter(new HomePageImageAdapter(this, web, mThumbIds));

		

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				
				//set the intent to the page linking the icon on the Home Page
				Intent intent;
				switch(position){
				case 0:
					intent = new Intent(HomePage.this, ProductListView.class);
					break;
				case 1:
					intent = new Intent(HomePage.this, UserProfileView.class);
					break;
				case 2:
					intent = new Intent(HomePage.this, HistoryView.class);
					break;
				case 3:
					intent = new Intent(HomePage.this, HomePage.class);
					break;
				case 4:
					intent = new Intent(HomePage.this, HomePage.class);
					break;
				case 5:
					intent = new Intent(HomePage.this, UIMain.class);
					SaveSharedPreference.setUserId(HomePage.this, null);
					SaveSharedPreference.setUserName(HomePage.this, null);
					SaveSharedPreference.setUserType(HomePage.this, null);
					break;
				default:
					intent = new Intent(HomePage.this, HomePage.class);
					break;
				}
				//open the selected page
				HomePage.this.startActivity(intent);

			}
		});
	}

	@Override
	/*
	 * onCreateOptionsMenu() is a method to inflate the menu, and add items to the action bar if present
	 * @param Menu menu
	 * @return boolean: returns true if function executes successfully, else false
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	/*
	 * onOptionsItemSelected() is a method to handle action bar item clicks here. The action bar will 
	 *	automatically handle clicks on the Home/Up button, so long
	 * as you specify a parent activity in AndroidManifest.xml.
	 * @param MenuItem item
	 * @return boolean: returns true if function executes successfully, else false
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		

		switch (item.getItemId()) {
		case R.id.action_home:
			//openHome();
			Intent intent = new Intent(HomePage.this, HomePage.class);
			HomePage.this.startActivity(intent);
			return true;
		case R.id.action_search:
			//openSearch();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
