package com.wsd.android.list;

import java.util.HashMap;
import java.util.Map;

import com.wsd.android.utils.WSDDate;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by normanpaniagua on 6/18/13.
 */
public class WSDBaseAdapterHolder {
    private Map<String, TextView> mTextView;
    private Map<String, TextView> mDateView;
    private Map<String, RatingBar> mRatingBar;
    private Map<String, ImageView> mImageView;
    private Map<String, Drawable> mPlaceHolder;
    private WSDListItem mItem;

    public WSDBaseAdapterHolder() {
    	mImageView = new HashMap<String, ImageView>();
    	mPlaceHolder = new HashMap<String, Drawable>();
    	mTextView = new HashMap<String, TextView>();
    	mDateView = new HashMap<String, TextView>();
    	mRatingBar = new HashMap<String, RatingBar>();
    }
    
    public void setTextView(String field, TextView textView) {
        if (textView == null) return;

        mTextView.put(field, textView);
    }
    
    public void setDateView(String field, TextView textView) {
        if (textView == null) return;

        mDateView.put(field, textView);
    }

    public RatingBar getRatingBar(String field) {
        if (!mRatingBar.containsKey(field)) return null;
        return mRatingBar.get(field);
    }
    
    public void setRating(String field, float rating) {
    	if (getRatingBar(field) == null) return;
    	getRatingBar(field).setRating(rating);
    }
    
    public void setRatingBar(String field, RatingBar ratingBar) {
        if (ratingBar == null) return;

        mRatingBar.put(field, ratingBar);
    }

    public TextView getTextView(String field) {
        if (!mTextView.containsKey(field)) return null;
        return mTextView.get(field);
    }
    
    public TextView getDateView(String field) {
        if (!mDateView.containsKey(field)) return null;
        return mDateView.get(field);
    }
    
    public void setText(String field, String text) {
    	if (getTextView(field) == null) return;
    	getTextView(field).setText(text);
    }
    
    public void setDate(String field, String text) {
    	if (getDateView(field) == null) return;
    	getDateView(field).setText(WSDDate.humanize(text));
    }

    public void setImageView(ImageView imageView) {
        mImageView.put("imgThumbnail", imageView);
    }
    
    public void setImageView(String field, ImageView imageView, Drawable placeHolder) {
    	mPlaceHolder.put(field, placeHolder);
        mImageView.put(field, imageView);
    }
    
    public ImageView getImageView() {
        return mImageView.get("imgThumbnail");
    }

    public ImageView getImageView(String field) {
        return mImageView.get(field);
    }

    public void setData(WSDListItem item) {
    	if (item == null) return;
    	
    	mItem = item;
    	
    	for (String field : mImageView.keySet()) {
            mItem.loadImage(field, getImageView(field), mPlaceHolder.get(field));
        }
        
        for (String field : mTextView.keySet()) {
        	if (item.get(field) == null) continue;
        	setText(field, (String)item.get(field));
        }
        
        for (String field : mDateView.keySet()) {
        	if (item.get(field) == null) continue;
        	setDate(field, (String)item.get(field));
        }
        
        for (String field : mRatingBar.keySet()) {
        	if (item.get(field) == null) continue;
            setRating(field, Float.parseFloat(item.get(field).toString()));
        }
    }

    public WSDListItem getData() {
        return mItem;
    }
}
