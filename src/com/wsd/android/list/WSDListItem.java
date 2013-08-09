package com.wsd.android.list;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.applidium.shutterbug.FetchableImageView;

/**
 * Created by normanpaniagua on 6/13/13.
 */
public class WSDListItem {
    public String mId;
    private Map<String, Object> mData;
    private boolean mIsSection = false;
    
    public WSDListItem() {
    	if (mData == null) mData = new HashMap<String, Object>();
    }

    public WSDListItem(Context context, String id, Map<String, Object> data) {
        init(context, id, data);
    }
    
    public WSDListItem init(Context context, String id, Map<String, Object> data) {
    	mId = id;
        mData = data != null ? data : new HashMap<String, Object>();
        
        return this;
    }
    
    public void put(String key, Object value) {
    	if (mData == null) mData = new HashMap<String, Object>();
    	mData.put(key, value);
    }

    public Object get(String key) {
    	if (mData == null) mData = new HashMap<String, Object>();
        return mData.get(key);
    }
    
    public void setIsSection() {
    	mIsSection = true;
    }
    
    public boolean isSection() {
    	return mIsSection;
    }

    public void loadImage(String key, ImageView imageView, Drawable placeHolder) {
        if (placeHolder != null && imageView != null) {
        	imageView.setImageDrawable(placeHolder);
        }
        
        if (imageView == null|| get(key) == null) return;
        
        if (get(key).getClass().toString().equals(BitmapDrawable.class.toString())) {
        	imageView.setImageBitmap(((BitmapDrawable)get(key)).getBitmap());
        } else if (imageView.getClass().toString().equals(FetchableImageView.class.toString())) {
        	((FetchableImageView)imageView).setImage(get(key).toString(), placeHolder);
        }
    }
    
    public Map<String, Object> getData() {
    	return mData;
    }
}
