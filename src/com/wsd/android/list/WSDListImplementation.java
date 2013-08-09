package com.wsd.android.list;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface WSDListImplementation {
	public int getItemLayout();
	public Context getContext();
	public void onClick(WSDListItem item, View view);
	public void getData(WSDBaseAdapter adapter);
	public List<WSDListField> getFields();
	public View getView(int i, View view, ViewGroup viewGroup, WSDListItem item);
}
