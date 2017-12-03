package com.example.opener;

import java.util.Timer;

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
import android.widget.Toast;

/*This class is the boundary class for changing the password of the user */
public class PasswordChangeView extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_change_layout);


		addButtonOnClickListener();

	}
	private void addButtonOnClickListener(){
		Button btnConfirm = (Button)findViewById(R.id.passConfrim);

		btnConfirm.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				//retrieve the email of the user logged in using SaveSharedPreference
				String strEmail = SaveSharedPreference.getUserName(PasswordChangeView.this);

				EditText etOldPass = (EditText)findViewById(R.id.profileOldPass);
				String strOldPass = etOldPass.getText().toString();
				UserManager um=new UserManager(PasswordChangeView.this);
				um.open();


				//verify the old password is correct. check with the database
				boolean userIsValid=um.validateUser(strEmail, strOldPass);


				if(userIsValid){
					//get the new password typed in by the User
					EditText etNewPass = (EditText)findViewById(R.id.profileNewPass);
					String strNewPass = etNewPass.getText().toString();
					EditText etRePass = (EditText)findViewById(R.id.profileRePass);
					String strReNewPass = etRePass.getText().toString();
					
					
					//check if both passwords match
					if(strNewPass.equals(strReNewPass)){


						//update Password in the database
						boolean updateSuccess=um.updatePassword(strEmail, strOldPass, strNewPass);

						if(updateSuccess){
							Toast.makeText(getApplicationContext(), "Update successful!",
									Toast.LENGTH_SHORT).show();
							Intent myIntent = new Intent(PasswordChangeView.this, UserProfileView.class);
							PasswordChangeView.this.startActivity(myIntent);
						}
						else{
							Toast.makeText(getApplicationContext(), "Update unsuccessful. Please try again",
									Toast.LENGTH_SHORT).show();

						}

					}else{
						Toast.makeText(getApplicationContext(), "New password and retyped password do not match",
								Toast.LENGTH_SHORT).show();

					}
				}
				else{
					Toast.makeText(getApplicationContext(), "Invalid old password",
							Toast.LENGTH_SHORT).show();
				}

			}});
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
			Intent intent = new Intent(PasswordChangeView.this, HomePage.class);
			PasswordChangeView.this.startActivity(intent);
			return true;
		case R.id.action_search:
			//openSearch();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
