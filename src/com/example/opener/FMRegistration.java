package com.example.opener;

import com.example.opener.R;
import com.example.opener.FMRegistration;

import User.SaveSharedPreference;
import User.User;
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

/*This is the boundary class for registration of a new FM User*/
public class FMRegistration extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_food_manufacturer);

		// please check validation here
		// update to database

		addButtonsClickOnListener();
	}

	//code for "Add" button in FM User's registration page 
	private void addButtonsClickOnListener() {
		Button btnFMRegistration = (Button) findViewById(R.id.registerFMButton);
		btnFMRegistration.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//get all the strings entered by the User
				EditText etName = (EditText) findViewById(R.id.nameTextBox);
				String name = etName.getText().toString();

				EditText eToken = (EditText) findViewById(R.id.preferredIDTextBox);
				String token = eToken.getText().toString();

				EditText etPassword = (EditText) findViewById(R.id.passwordTextBox);
				String password = etPassword.getText().toString();

				EditText etRetypedPassword = (EditText) findViewById(R.id.confirmPasswordTextBox);
				String retypedPassword = etRetypedPassword.getText().toString();

				EditText etEmail = (EditText) findViewById(R.id.emailTextBox);
				String email = etEmail.getText().toString();

				User user = new User(email, password,name,  2);

				UserManager userManager = new UserManager(FMRegistration.this);
				userManager.open();


				boolean result = false;
				//check if all fields have been entered
				if ((email.equals("")) || (email == null) || (password.equals("")) || (password==null) || (retypedPassword.equals("")) || (retypedPassword==null) || (name.equals("")) || (name == null) ||  (token.equals("")) || (token==null)){
					Toast.makeText(
							getApplicationContext(),
							"Please fill in all the fields.", Toast.LENGTH_SHORT).show();
					return;
				}

				//check no non numeric characters in verification code
				if (!token.matches("[0-9]+") ) {

					Toast.makeText(
							getApplicationContext(),
							"Please enter a valid numeric id "
									+ result, Toast.LENGTH_SHORT).show();
				}

				else{
					//check the verification code entered is valid
					result = userManager.checkFMUserValid(Integer.parseInt(token));
					if (result == false) {
						Toast.makeText(
								getApplicationContext(),
								"Token not correct. Please obtain valid token "
										+ result, Toast.LENGTH_SHORT).show();
						return;
					} else {
						//check if same user exists
						result = userManager.checkUserExist(email);
						if (result == true) {
							Toast.makeText(getApplicationContext(),
									"Same user exists. " + result,
									Toast.LENGTH_SHORT).show();
							return;
						}
					}

					//add new FM user to the User Table
					result = userManager.addUser(user);
					if (result)
						Toast.makeText(getApplicationContext(), "Successfully Registerd",
								Toast.LENGTH_SHORT).show();
					else

						Toast.makeText(getApplicationContext(), "Registration Failed" ,
								Toast.LENGTH_SHORT).show();

					Intent intent = new Intent(FMRegistration.this, UIMain.class);
					
					//store userId, UserName and UserType in SaveSharedPreference to keep the user logged in
					SaveSharedPreference.setUserId(FMRegistration.this, Integer.toString(user.getUserid()));
					SaveSharedPreference.setUserName(FMRegistration.this, user.getEmail());
					SaveSharedPreference.setUserType(FMRegistration.this, Integer.toString(user.getUserType()));
					//open UI Main 
					startActivity(intent);
				}
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
		
		getMenuInflater().inflate(R.menu.fmregistration, menu);
		return true;
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
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);

	}
}
