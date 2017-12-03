package com.example.opener;

import com.example.opener.R;
import com.example.opener.FMRegistration;
import com.example.opener.UIMain;

import User.SaveSharedPreference;
import User.User;
import User.UserManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * UIMain is a boundary class which represents the main screen, the login screen.  
 *
 */
public class UIMain extends Activity {

	/**
	 * method on create of screen
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// request no title bar for the main view since it makes no sense
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.loginscreen);

		//method on clicking login
		Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				//extract email and password into string

				EditText etEmail = (EditText) findViewById(R.id.idTextBox);
				String email = etEmail.getText().toString();

				EditText etPassword = (EditText) findViewById(R.id.preferredIDTextBox);
				String password = etPassword.getText().toString();


				UserManager userManager = new UserManager(
						UIMain.this);
				userManager.open();

				//use usermanager to validate user
				User user1 = userManager.validateAndGetUser(email, password);

				//provide feedback to user if invalid username/password
				if (user1 == null){
					Toast.makeText(getApplicationContext(), "Invalid user name or password " ,
							Toast.LENGTH_SHORT).show();
					return;
				}

				//if valid and normal user, take user to Product List View
				if (user1.getUserType()==1){
					SaveSharedPreference.setUserId(UIMain.this, Integer.toString(user1.getUserid()));
					SaveSharedPreference.setUserName(UIMain.this, email);
					SaveSharedPreference.setUserType(UIMain.this, Integer.toString(user1.getUserType()));

					Intent selectProductView = new Intent(UIMain.this, ProductListView.class);
					startActivity(selectProductView);

				}
				//if valid and fm user, take user to Add Product View
				else if (user1.getUserType()==2) {

					SaveSharedPreference.setUserId(UIMain.this, Integer.toString(user1.getUserid()));
					SaveSharedPreference.setUserName(UIMain.this, email);
					SaveSharedPreference.setUserType(UIMain.this, Integer.toString(user1.getUserType()));

					Intent fmAddProductView = new Intent(UIMain.this, FMAddProduct.class);
					startActivity(fmAddProductView);

				}else {
					Toast.makeText(getApplicationContext(), "Unable to identify user type. " ,
							Toast.LENGTH_SHORT).show();
					return;
				}

				Toast.makeText(getApplicationContext(), "Welcome " + user1.getName() +"!" ,
						Toast.LENGTH_SHORT).show();

			}
		});

		//method on clicking register food manufacturer
		Button registerFoodManuButton = (Button) findViewById(R.id.registerFoodManufacturerButton);
		registerFoodManuButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent registerFoodManufacturer = new Intent(UIMain.this,FMRegistration.class);//ProductListView

				startActivity(registerFoodManufacturer);
			}
		});

		final Intent registerNormalUser = new Intent(this,
				NormalUserRegistration.class);

		//method on clicking register normal user
		Button registerNormalButton = (Button) findViewById(R.id.registerNormalUserButton);
		registerNormalButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(registerNormalUser);
			}
		});

	}

	/**
	 * Method to create options menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.uimain, menu);
		return true;
	}

	/**
	 * Method once option items have been selected
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
