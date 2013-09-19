package com.wsd.android.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by normanpaniagua on 6/13/13.
 */
public class WSDBaseAdapter extends BaseAdapter implements View.OnClickListener {
//	private String TAG = "com.wsd.android.list.WSDBaseAdapter";
    public List<WSDListItem> mList;
    public List<WSDListItem> mListAll;
    public WSDListImplementation mListImplementation;
    public View mProgressBar;

    public WSDBaseAdapter(WSDListImplementation listImplementation, View progressBar, boolean loadDataOnCreate) {
    	init(listImplementation, progressBar, loadDataOnCreate);
    }
    
    public WSDBaseAdapter(WSDListImplementation listImplementation, View progressBar) {
    	init(listImplementation, progressBar, true);
    }
    
    public WSDBaseAdapter(WSDListImplementation listImplementation) {
    	init(listImplementation, null, true);
    }
    
    private void init(WSDListImplementation listImplementation, View progressBar, boolean loadDataOnCreate) {
    	mListImplementation = listImplementation;
        mList = new ArrayList<WSDListItem>();
        mListAll = new ArrayList<WSDListItem>();
        mProgressBar = progressBar;
        
        if (loadDataOnCreate) reload();
    }
    
    private void showLoading()
    {
    	if (mProgressBar == null) return;
    	mProgressBar.setVisibility(View.VISIBLE);
    }
    
    private void hideLoading()
    {
    	if (mProgressBar == null) return;
    	mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public void setItems(List<WSDListItem> list) {
    	hideLoading();
        mList = list;
        mListAll = list;
        notifyDataSetChanged();
    }
    
    public void filter(String filter, String field) {
    	if (filter == null) {
    		mList = mListAll;
    		notifyDataSetChanged();
    		return;
    	} else if (filter.equals("")) {
    		mList = mListAll;
    		notifyDataSetChanged();
    		return;
    	}
    	
    	mList = new ArrayList<WSDListItem>();
    	
    	String regex = "(\\w*" + filter.trim().toLowerCase(Locale.getDefault()).replace("a", "[a‡]")
				.replace("e", "[eŽ]")
				.replace("i", "[i’]")
				.replace("o", "[o—]")
				.replace("u", "[uœ]")
				.replace("n", "[n–]")+ "+\\w*)";
		
		for (WSDListItem row : mListAll) {
			if (filter != null) {
				String name = row.get(field).toString().toLowerCase(Locale.getDefault());
				boolean matches = name.matches(regex.toLowerCase(Locale.getDefault()));
				boolean indexof = name.indexOf(filter) > -1;
				if (!matches && !indexof) continue;
			}
			
			mList.add(row);
		}
		
		notifyDataSetChanged();
    }
    
    public void reload() {
    	showLoading();
    	mListImplementation.getData(this);
    }
    
    public void backgroundReload() {
    	mListImplementation.getData(this);
    }

    @Override
    public WSDListItem getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public String getObjectId(int i) {
        return mList.get(i).mId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
    	View viewImplementation = mListImplementation.getView(i, view, viewGroup, getItem(i));
    	if (viewImplementation != null) {
    		return viewImplementation;
    	}
    	
        WSDBaseAdapterHolder holder;
        
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)mListImplementation.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mListImplementation.getItemLayout(), null);

            holder = new WSDBaseAdapterHolder();
            
            for (WSDListField field : mListImplementation.getFields()) {
            	if (field.getType() == WSDListField.TYPE_IMAGE) {
            		holder.setImageView(field.getKey(), (ImageView)view.findViewById(field.getValue()), field.getPlaceHolder());
            	} else if (field.getType() == WSDListField.TYPE_RATING) {
            		holder.setRatingBar(field.getKey(), (RatingBar)view.findViewById(field.getValue()));
            	} else if (field.getType() == WSDListField.TYPE_TEXT) {
            		holder.setTextView(field.getKey(), (TextView)view.findViewById(field.getValue()));
            	}
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
        WSDBaseAdapterHolder holder = (WSDBaseAdapterHolder) view.getTag();

        mListImplementation.onClick(holder.getData(), view);
    }
}
