package com.wsd.android.list;

import android.content.Context;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class WSDList extends WSDBaseAdapter {
	private ListView mList;
	private ProgressBar mProgressBar;
	private RelativeLayout mErrorView; 
	private RelativeLayout mView; 
	private WSDListImplementation mImplementation;
	
	public WSDList(WSDListImplementation listImplementation, RelativeLayout view) {
		super(listImplementation);
		
		mView = view;
	}
	
	public void setProgressBar(ProgressBar progressBar) {
		mProgressBar = progressBar;
	}
	
	public void setListView(ListView list) {
		mList = list;
	}
	
	public void setErrorView(RelativeLayout errorView) {
		mErrorView = errorView;
	}
	
	private Context getContext() {
		return mImplementation.getContext();
	}
	
	public void setup() {
		if (mList == null) setupListView();
		if (mProgressBar == null) setupProgressBar();
		if (mErrorView == null) setupErrorView();
	}
	
	private void setupListView() {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		
		mList = new ListView(getContext());
		mList.setLayoutParams(params);
		
		mView.addView(mList);
	}
	
	private void setupProgressBar() {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		mList = new ListView(getContext());
		mList.setLayoutParams(params);
		
		mView.addView(mList);
	}

	private void setupErrorView() {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		
		mList = new ListView(getContext());
		mList.setLayoutParams(params);
		
		mView.addView(mList);
	}
}
