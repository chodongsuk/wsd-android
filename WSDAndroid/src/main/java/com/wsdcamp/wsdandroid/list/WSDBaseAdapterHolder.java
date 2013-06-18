package com.wsdcamp.wsdandroid.list;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by normanpaniagua on 6/18/13.
 */
public class WSDBaseAdapterHolder {
    private Map<String, TextView> mTextView;
    private ImageView mImageView;
    private WSDListItem mItem;

    public void setTextView(String field, TextView textView) {
        if (this.mTextView == null) this.mTextView = new HashMap<String, TextView>();
        if (textView == null) return;

        this.mTextView.put(field, textView);
    }

    public TextView getTextView(String field) {
        if (!this.mTextView.containsKey(field)) return null;
        return this.mTextView.get(field);
    }

    public void setImageView(ImageView imageView) {
        this.mImageView = imageView;
    }

    public ImageView getImageView() {
        return this.mImageView;
    }

    public void setData(WSDListItem item) {
        this.mItem = item;
        item.loadImage(getImageView());

        for (String field : this.mTextView.keySet()) {
            getTextView(field).setText(item.get(field).toString());
        }
    }

    public WSDListItem getData() {
        return this.mItem;
    }
}
