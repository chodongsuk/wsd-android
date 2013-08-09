package com.wsd.android.net;

import java.io.IOException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

// usually, subclasses of AsyncTask are declared inside the activity class.
// that way, you can easily modify the UI thread from here
public class DownloadFile extends AsyncTask<String, Void, Bitmap> {
	private DownloadFileCallback mCallback;
	
	public DownloadFile(DownloadFileCallback callback) {
		mCallback = callback;
	}

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(String... params) {
        String stringUrl = String.valueOf(params[0]);
        Bitmap bitmap = null;

        try {
        	URL url = new URL(stringUrl);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    protected void onPostExecute(Bitmap bitmap) {
        if (mCallback == null) return;
        
        mCallback.onComplete(bitmap);
    }
}
