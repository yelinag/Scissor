package com.example.opener;

import com.example.opener.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

/*This class is to set the product name and image in Product List View page
It is associated with product_list_layout.xml which contains product_list_single_layout.xml as a sub UI*/

public class ProductListImageAdapter extends BaseAdapter {
	private Context mContext;
	private final String[] web;
	private final Bitmap[] imageId;

	public ProductListImageAdapter(Context c, String[] web, Bitmap[] imageId) {
		mContext = c;
		this.web = web;
		this.imageId = imageId;
	}

	public int getCount() {
		return web.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {

		View grid;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			grid = new View(mContext);
			grid = inflater.inflate(R.layout.product_list_single_layout, null);

			TextView textView = (TextView) grid.findViewById(R.id.grid_text);
			ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);
			imageView.setScaleType(ScaleType.FIT_XY);
			textView.setText(web[position]);
			imageView.setImageBitmap(imageId[position]);
		} else {
			grid = (View) convertView;
		}
		return grid;

	}

}
