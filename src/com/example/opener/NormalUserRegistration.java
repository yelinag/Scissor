package com.example.opener;

import com.example.opener.R;
import com.example.opener.NormalUserRegistration;

import User.SaveSharedPreference;
import User.User;
import User.UserManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*This class is the boundary class for registration of a normal user */
public class NormalUserRegistration extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.register_normal_user);

	

		Button registerUserButton = (Button) findViewById(R.id.registerNormalUserButton);

		registerUserButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//get all the strings entered by the User
				EditText etName = (EditText) findViewById(R.id.nameTextBox);
				String name = etName.getText().toString();

				EditText etPassword = (EditText) findViewById(R.id.passwordTextBox);
				String password = etPassword.getText().toString();

				EditText etRetypedPassword = (EditText) findViewById(R.id.confirmPasswordTextBox);
				String retypedPassword = etRetypedPassword.getText().toString();

				EditText etEmail = (EditText) findViewById(R.id.emailTextBox);
				String email = etEmail.getText().toString();

				User user = new User(email, password,name, 1);

				UserManager userManager = new UserManager(
						NormalUserRegistration.this);
				userManager.open();

				//check if all fields have been entered
				boolean result = false;
				if ((email.equals("")) || (email == null) || (password.equals("")) || (password==null) || (retypedPassword.equals("")) || (retypedPassword==null) || (name.equals("")) || (name == null)){
					Toast.makeText(
							getApplicationContext(),
							"Please fill in all the fields.", Toast.LENGTH_SHORT).show();
					return;
				}

				//check if same user exists
				result = userManager.checkUserExist(email);
				if (result == true) {
					Toast.makeText(getApplicationContext(),
							"Same user exist " + result, Toast.LENGTH_SHORT)
							.show();
					return;
				}

				result = userManager.addUser(user);
				if (result)
					Toast.makeText(getApplicationContext(), "Successfully Registered" ,
							Toast.LENGTH_SHORT).show();
				else

					Toast.makeText(getApplicationContext(), "Registration Failed" ,
							Toast.LENGTH_SHORT).show();


				
				//store UserId, UserName and UserType in SaveSharedPreference to keep the user logged in
				SaveSharedPreference.setUserId(NormalUserRegistration.this, Integer.toString(user.getUserid()));
				SaveSharedPreference.setUserName(NormalUserRegistration.this, user.getEmail());
				SaveSharedPreference.setUserType(NormalUserRegistration.this, Integer.toString(user.getUserType()));
				
				//open UI Main 
				Intent intent = new Intent(NormalUserRegistration.this,
						UIMain.class);
				startActivity(intent);

			}
		});

	}
}


