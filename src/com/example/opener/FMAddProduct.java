package com.example.opener;

import java.io.FileNotFoundException;
import java.io.InputStream;

import com.example.opener.R;

import Product.Product;
import Product.ProductMgr;
import User.SaveSharedPreference;
import User.UserManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/*This class is the boundary class for adding a new product by the FM User*/
public class FMAddProduct extends Activity {

	private static final int SELECT_PHOTO = 100;
	private boolean isClicked = false;
	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fm_homepage_layout);

		addButtonsClickOnListener();

	}

	// code for "Add" button in Food Manufacturer's Add Product page
	private void addButtonsClickOnListener() {
		Button btnAdd = (Button) findViewById(R.id.addConfirm);
		btnAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// ====================== Get the values from UI
				// ==========================
				EditText etPName = (EditText) findViewById(R.id.addProductName);
				String pname = etPName.getText().toString();

				EditText etDesc = (EditText) findViewById(R.id.addBrandName);
				String desc = etDesc.getText().toString();

				// ====================== Trying to add the product
				// ==========================
				ProductMgr pManager = new ProductMgr(FMAddProduct.this);
				pManager.open();

				UserManager um = new UserManager(FMAddProduct.this);
				um.open();

				boolean productExists = pManager.checkProdNameExists(pname);

				if (productExists) {
					Toast.makeText(FMAddProduct.this,
							"Product Name exists.Try again", Toast.LENGTH_LONG)
							.show();
				} else { // if the same product name doesn't exist
					String fmEmail = SaveSharedPreference
							.getUserName(FMAddProduct.this);

					int uid = um.getUidFromEmail(fmEmail);

					if (uid == -1) {
						Toast.makeText(FMAddProduct.this,
								"You are not a valid user", Toast.LENGTH_LONG)
								.show();
					}

					else {
						//if the user has not uploaded the image 
						if (!isClicked) {
							Toast.makeText(FMAddProduct.this,
									"Please add image first" + isClicked,
									Toast.LENGTH_LONG).show();
						} else {
							if (pManager.getProdImage(pname) == null) {
								Toast.makeText(
										FMAddProduct.this,
										"No image returned from db.Null returned",
										Toast.LENGTH_LONG).show();
							}

							else {
								// add the product to database here
								boolean addSuccess = pManager
										.addProductDetails(pname, desc, uid);
								if (addSuccess) {
									Toast.makeText(FMAddProduct.this,
											"Product Added Successfully",
											Toast.LENGTH_LONG).show();
								} else {
									Toast.makeText(
											FMAddProduct.this,
											"We could not add your product. Please try again",
											Toast.LENGTH_LONG).show();

								}

								Intent intent = new Intent(FMAddProduct.this,
										ProductListView.class);
								startActivity(intent);

							}

						}
					}

				}

			}

		}

				);

		Button btnSkip = (Button) findViewById(R.id.skipToFMPage);
		btnSkip.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FMAddProduct.this, HomePage.class);
				startActivity(intent);
			}
		});

		Button btnAddImage = (Button) findViewById(R.id.addFilePath);
		btnAddImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_PHOTO);
				// TODO Auto-generated method stub

				// need to put code to add image here
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		switch (requestCode) {
		case SELECT_PHOTO:
			if (resultCode == RESULT_OK) {
				try {
					final Uri selectedImage = imageReturnedIntent.getData();
					final InputStream imageStream = getContentResolver()
							.openInputStream(selectedImage);
					final Bitmap userSelectedImage = BitmapFactory
							.decodeStream(imageStream);

					EditText etPName = (EditText) findViewById(R.id.addProductName);

					if (etPName.getText().toString().trim().length() == 0) {
						Toast.makeText(FMAddProduct.this,
								"Please add product name first",
								Toast.LENGTH_LONG).show();
					}
					else {

						String pname = etPName.getText().toString();

						ProductMgr pManager = new ProductMgr(FMAddProduct.this);
						pManager.open();

						boolean productExists = pManager
								.checkProdNameExists(pname);

						if (productExists) {
							Toast.makeText(FMAddProduct.this,
									"Product Name exists.Try again",
									Toast.LENGTH_LONG).show();
						}

						else {
							//add product image to the database first before adding Product name and other info
							isClicked = pManager.addProdImageandName(
									userSelectedImage, pname);

							if (isClicked == false) {
								Toast.makeText(
										FMAddProduct.this,
										"Image not added successfully. Please try again",
										Toast.LENGTH_LONG).show();
							} else {
								Bitmap bit = BitmapFactory.decodeByteArray(
										pManager.getProdImage(pname), 0,
										pManager.getProdImage(pname).length);
								mImageView = (ImageView) findViewById(R.id.productPic);
								mImageView.setImageBitmap(bit);
							}
							// Intent intent = new Intent(FMAddProduct.this,
							// FMAddProduct.class);
							// startActivity(intent);

						}
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			}
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
		getMenuInflater().inflate(R.menu.fmhome_page, menu);
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
