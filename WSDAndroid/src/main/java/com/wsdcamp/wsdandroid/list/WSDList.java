package com.wsdcamp.wsdandroid.list;

import android.content.Context;

import java.util.List;

/**
 * Created by normanpaniagua on 6/13/13.
 */
public class WSDList {
    private String TAG = "com.wsdcamp.wsdandroid.list.WSDList";
    private Context mContext;
    private int mResLayoutListID;
    private WSDBaseAdapter mListAdapter;

    public WSDList(Context context)
    {
        this.mContext = context;
    }

    public WSDList setLayoutId(int resLayoutListID) {
        this.mResLayoutListID = resLayoutListID;
        return this;
    }

    public WSDList setBaseAdapter(WSDBaseAdapter listAdapter) {
        this.mListAdapter = listAdapter;
        return this;
    }

    public void setItems(List<WSDListItem> items) {

    }
}
