package com.wsd.android.list;

import android.graphics.drawable.Drawable;

public class WSDListField {
	public static enum TYPE {
		TEXT,
		IMAGE,
		RATING,
		DATE,
	}
	
	private String mKey;
	private int mValue;
	private TYPE mType;
	private Drawable mPlaceHolder = null;
	
	public WSDListField(String key, int value, TYPE type) {
		mKey = key;
		mValue = value;
		mType = type;
	}
	
	public WSDListField(String key, int value, Drawable placeHolder) {
		mKey = key;
		mValue = value;
		mType = TYPE.IMAGE;
		mPlaceHolder = placeHolder;
	}
	
	public String getKey() {
		return mKey;
	}
	
	public int getValue() {
		return mValue;
	}
	
	public TYPE getType() {
		return mType;
	}
	
	public Drawable getPlaceHolder() {
		return mPlaceHolder;
	}
}
