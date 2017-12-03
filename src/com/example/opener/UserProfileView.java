package com.example.opener;

import com.example.opener.R;

import User.SaveSharedPreference;
import User.UserManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

/**
 * UserProfileView is the boundary class for the user's interaction with his profile
 * to change details etc 
 *
 */

public class UserProfileView extends Activity {

	private ImageView mImageView;

	/**
	 * onCreate method
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile_layout);
		UserManager um=new UserManager(this);
		um.open();
		
		//display user profile pic image
		
		mImageView = (ImageView) findViewById(R.id.ivProfilePic);
		mImageView.setImageResource(R.drawable.user_profile_button);
		
		//extract the email 
		String strEmail = SaveSharedPreference.getUserName(this);
		EditText etEmail = (EditText)findViewById(R.id.profileEmail);
		
		//extract the name 
		etEmail.setText(strEmail);
		String strName=um.getNameFromEmail(strEmail);
		
		EditText etName = (EditText)findViewById(R.id.profileName);
		etName.setText(strName);
		
		addButtonsOnClickListener();
		
	}
	
	/**
	 * addButtonsOnClickListener method
	 */
	private void addButtonsOnClickListener(){
		Button btnConfrim = (Button)findViewById(R.id.profileConfirm);
		Button btnPassword = (Button)findViewById(R.id.changePassword);
		Button btnCancel =(Button)findViewById(R.id.profileCancel);
		
	//Confirm button 
		btnConfrim.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				//update on database
				
				UserManager um=new UserManager(UserProfileView.this);
				um.open();
				
				//extract the email
				String oldEmail = SaveSharedPreference.getUserName(UserProfileView.this);
				EditText etEmail = (EditText)findViewById(R.id.profileEmail);
				String newEmail = etEmail.getText().toString();
				
				//extract the name 
				EditText etName = (EditText)findViewById(R.id.profileName);
				String newName = etName.getText().toString();
				
				//change name and email to new onces
				boolean successNameEmail=um.updateEmailAndName(oldEmail,newEmail, newName);
				
				//if successful
				if(successNameEmail==true){
					Toast.makeText(getApplicationContext(), "Successfully updated!",
							Toast.LENGTH_SHORT).show();
					SaveSharedPreference.setUserName(UserProfileView.this, newEmail);
					Intent myIntent = new Intent(UserProfileView.this, HomePage.class);
					UserProfileView.this.startActivity(myIntent);
					
					
				}
				else{
					Toast.makeText(getApplicationContext(), "UnSuccessful update! Please try again." ,
							Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
		
		//cancel button
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	
				Intent myIntent = new Intent(UserProfileView.this, HomePage.class);
				UserProfileView.this.startActivity(myIntent);
			}
		});
		
		//change password button
		btnPassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent myIntent = new Intent(UserProfileView.this, PasswordChangeView.class);
			
				UserProfileView.this.startActivity(myIntent);
			}
		});
	}

	/**
	 * Method to create options menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Method once option items have been selected
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
    	switch (item.getItemId()) {
        case R.id.action_home:
          
        	Intent intent = new Intent(UserProfileView.this, HomePage.class);
        	UserProfileView.this.startActivity(intent);
            return true;
        case R.id.action_search:
            
            return true;
        default:
            return super.onOptionsItemSelected(item);
    	}
	}
}
