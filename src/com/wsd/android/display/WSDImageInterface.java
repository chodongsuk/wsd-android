package com.wsd.android.display;

import android.graphics.drawable.Drawable;

public interface WSDImageInterface {
    public void setImage(String url);
    public void setImage(String url, int placeholderDrawableId);
    public void setImage(String url, Drawable placeholderDrawable);
}
