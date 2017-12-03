package com.example.opener;

//import com.example.gridview.R;

import com.example.opener.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button bGV = (Button)findViewById(R.id.btnGridView);
        bGV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent myIntent = new Intent(MainActivity.this, ProductListView.class);
				MainActivity.this.startActivity(myIntent);
			}
		});
        
        Button bVH = (Button)findViewById(R.id.btnViewHist);
        bVH.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(MainActivity.this, HistoryView.class);
				MainActivity.this.startActivity(myIntent);
			}
		});
        
        Button bPRV = (Button)findViewById(R.id.btnProdRating);
        bPRV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(MainActivity.this, ProductRatingView.class);
				MainActivity.this.startActivity(myIntent);
			}
		});
        
        Button bUP = (Button)findViewById(R.id.btnUserProf);
        bUP.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(MainActivity.this, UserProfileView.class);
				MainActivity.this.startActivity(myIntent);
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
        
    	
    	//getMenuInflater().inflate(R.menu.main, menu);
        //return true;
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
	        case R.id.action_search:
	            //openSearch();
	            return true;
	        case R.id.action_settings:
	            //openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
    	}
    
    }
}
