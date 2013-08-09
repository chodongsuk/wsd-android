package com.wsd.android.list;

import android.graphics.drawable.Drawable;

public class WSDListField {
	public static int TYPE_TEXT = 0;
	public static int TYPE_IMAGE = 1;
	public static int TYPE_RATING = 2;
	
	private String mKey;
	private int mValue;
	private int mType;
	private Drawable mPlaceHolder = null;
	
	public WSDListField(String key, int value, int type) {
		mKey = key;
		mValue = value;
		mType = type;
	}
	
	public WSDListField(String key, int value, Drawable placeHolder) {
		mKey = key;
		mValue = value;
		mType = TYPE_IMAGE;
		mPlaceHolder = placeHolder;
	}
	
	public String getKey() {
		return mKey;
	}
	
	public int getValue() {
		return mValue;
	}
	
	public int getType() {
		return mType;
	}
	
	public Drawable getPlaceHolder() {
		return mPlaceHolder;
	}
}
