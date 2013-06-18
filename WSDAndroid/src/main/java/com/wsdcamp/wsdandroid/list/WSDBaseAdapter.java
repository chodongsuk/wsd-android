package com.wsdcamp.wsdandroid.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by normanpaniagua on 6/13/13.
 */
public class WSDBaseAdapter extends BaseAdapter implements View.OnClickListener {
    private List<WSDListItem> mList;
    private Context mContext;
    private int mResItemLayout;
    private Map<String, Integer> mResTextFieldIds;
    private int mResImageFieldId;
    private WSDBaseAdapterClickListener mClickListener;

    public WSDBaseAdapter(Context context, int resItemLayout, Map<String, Integer> resTextFieldIds, int resImageFieldId) {
        this.mContext = context;
        this.mResItemLayout = resItemLayout;
        this.mResTextFieldIds = resTextFieldIds;
        this.mResImageFieldId = resImageFieldId;
    }

    @Override
    public int getCount() {
        return this.mList.size();
    }

    public void setItems(List<WSDListItem> list) {
        this.mList = list;
    }

    public void setClickListener(WSDBaseAdapterClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    @Override
    public WSDListItem getItem(int i) {
        return this.mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public String getObjectId(int i) {
        return this.mList.get(i).mId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        WSDBaseAdapterHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(this.mResItemLayout, null);

            holder = new WSDBaseAdapterHolder();
            holder.setImageView((ImageView)view.findViewById(mResImageFieldId));

            for (String field : this.mResTextFieldIds.keySet()) {
                holder.setTextView(field, (TextView)view.findViewById(this.mResTextFieldIds.get(field)));
            }

            view.setTag(holder);
        } else {
            holder = (WSDBaseAdapterHolder) view.getTag();
        }

        holder.setData(getItem(i));
        view.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (this.mClickListener == null) return;

        WSDBaseAdapterHolder holder = (WSDBaseAdapterHolder) view.getTag();

        this.mClickListener.onClick(holder.getData(), view);
    }
}
