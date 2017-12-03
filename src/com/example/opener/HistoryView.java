package com.example.opener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.opener.R;

import Product.RatingMgr;
import User.SaveSharedPreference;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/*This is the boundary class for users to view their own comment history (journal)*/
public class HistoryView extends Activity {


	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_history_layout);

		final ListView listview = (ListView) findViewById(R.id.vhlListview);


		ArrayList<String> list = new ArrayList<String>();

		RatingMgr rMgr = new RatingMgr(HistoryView.this);
		rMgr.open();

		//get the userID of the current user from SaveSharedPreference
		//retrieve the comment list from the RatingTable for the given userID 
		list = rMgr.getCommentByUserId(Integer.parseInt(SaveSharedPreference.getUserId(HistoryView.this)));

		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

	@Override
	/*
	 * onCreateOptionsMenu() is a method to inflate the menu, and add items to the action bar if present
	 * @param Menu menu
	 * @return boolean: returns true if function executes successfully, else false
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

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
			// openHome();
			Intent intent = new Intent(HistoryView.this, HomePage.class);
			HistoryView.this.startActivity(intent);
			return true;
		case R.id.action_search:
			// openSearch();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}