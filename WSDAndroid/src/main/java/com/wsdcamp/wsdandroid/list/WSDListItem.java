package com.wsdcamp.wsdandroid.list;

import android.content.Context;
import android.widget.ImageView;

import com.wsdcamp.wsdandroid.display.Image;

import java.util.Map;

/**
 * Created by normanpaniagua on 6/13/13.
 */
public class WSDListItem {
    public String mId;
    private Map<String, String> mData;
    private String mImageField;
    private Image mImage;

    public WSDListItem(Context context, String id, Map<String, String> data) {
        this.mId = id;
        this.mData = data;
        this.mImage = new Image(context);
    }

    public void setImageField(String imageField) {
        this.mImageField = imageField;
    }

    public Object get(String field) {
        return this.mData.get(field);
    }

    public void loadImage(ImageView imageView) {
        if (imageView == null || get(this.mImageField) == null) return;
        this.mImage.loadFromUrl(get(this.mImageField).toString(), imageView);
    }
}
