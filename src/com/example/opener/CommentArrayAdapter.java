package com.example.opener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

/*This is a helper class to represent or draw each comment list item on the ProductView page.*/
public class CommentArrayAdapter extends ArrayAdapter<String>{
	protected ListView mListView;
	private final Context context;
	private final String[] comments;
	private final String[] userNames;


	public CommentArrayAdapter(Context context, String[] comments, String [] userNames) {
		super(context, R.layout.single_comment_layout, comments);
		this.context = context;
		this.comments = comments;
		this.userNames = userNames;
		
	}

	@Override
	
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.single_comment_layout,
				parent, false);
		
		//display the comment in the View
		TextView tvComment = (TextView) rowView.findViewById(R.id.sclCommentBox);
		tvComment.setText(comments[position]);
		
		//display the UserName in the View
		TextView tvUserName = (TextView) rowView.findViewById(R.id.sclUser);
		tvUserName.setText(userNames[position]);
		
		
		
		
		return rowView;
	}

}
