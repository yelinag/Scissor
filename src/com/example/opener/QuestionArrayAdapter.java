package com.example.opener;

import com.example.opener.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * This class is to set the rating questions in Product Rating page
* It is associated with rating_question_layout.xml
* It also has the RatingBarChangeListener which set the value of stars when the user rate it. */

public class QuestionArrayAdapter extends ArrayAdapter<String> {
	protected ListView mListView;
	private final Context context;
	private final String[] values;
	private int[] ratingRecords;
	
	boolean flag1 = false;
	boolean flag2 = false;
	boolean flag3 = false;
	boolean flag4 = false;
	boolean flag5 = false;
	

	public QuestionArrayAdapter(Context context, String[] values) {
		super(context, R.layout.rating_question_layout, values);
		this.context = context;
		this.values = values;
		ratingRecords = new int[values.length];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater
				.inflate(R.layout.rating_question_layout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.tvQuestion);

		textView.setText(values[position]);
		
	
		// if rating value is changed,
		// display the current rating value in the result (textview)
		// automatically
		final RatingBar rb = (RatingBar)rowView.findViewById(R.id.ratingBar);
		rb.setTag(position);
		rb.setRating(ratingRecords[position]);
		rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				
				Integer myPosition= (Integer)rb.getTag();
				
				ratingRecords[myPosition] = (int)rating;
				rb.setRating(rating);
				
			}

		});
		
		return rowView;
	}
	
	public int[] getRatingRecords(){
		return this.ratingRecords;
	}
	

}



