package com.example.opener;

import com.example.opener.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*This is a helper class to represent or draw each list item on the HomePage page.*/
public class HomePageImageAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] imageId;

    public HomePageImageAdapter(Context c, String[] web, int[] imageId) {
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
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
              grid = new View(mContext);
              grid = inflater.inflate(R.layout.product_list_single_layout, null);
              
              
              TextView textView = (TextView) grid.findViewById(R.id.grid_text);
              ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            //display the required text in the View--User Profile, History etc
              textView.setText(web[position]);
              //display the image associated with the text in the View
              imageView.setImageResource(imageId[position]);
        } else {
              grid = (View) convertView;
        }
        return grid;

    }

}
