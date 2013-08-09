package com.wsd.android.display;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;

/**
 * Created by normanpaniagua on 6/18/13.
 */
public class Image {

    public static Bitmap resize(String pathName, int targetW) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		
		Bitmap bmp = BitmapFactory.decodeFile(pathName, opts);
		
		int photoW = opts.outWidth;
		int scaleFactor = Math.round(photoW/targetW);
		
		opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = scaleFactor;
		opts.inPurgeable = true;
		
		bmp = BitmapFactory.decodeFile(pathName, opts);
		
		photoW = bmp.getWidth();
		
		return bmp;
	}
	
	public static Bitmap rotate(String pathName, int degrees) {
		Matrix matrix = new Matrix();
		matrix.setRotate(degrees);
		
		Bitmap src = BitmapFactory.decodeFile(pathName);
		
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
	}
	
	public static Bitmap rotate(Bitmap src, int degrees) {
		Matrix matrix = new Matrix();
		matrix.setRotate(degrees);
		
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
	}
	
	public static float getRotation(Context context, Uri uri) {
		if (uri.getScheme().equals("content")) {
			String[] projection = {Images.ImageColumns.ORIENTATION};
			Cursor c = context.getContentResolver().query(uri, projection, null, null, null);
			if (c.moveToFirst()) {
				return c.getInt(0);
			}
		} else if (uri.getScheme().equals("file")) {
			try {
				ExifInterface exif = new ExifInterface(uri.getPath());
				int rotation = (int) exifOrientationToDegrees(
						Integer.parseInt(exif.getAttribute(ExifInterface.TAG_ORIENTATION)));
				return rotation;
			} catch (IOException e) {
				Log.e("wsd.display.Image", "Error checking exif", e);
			}
		}
		
		return 0;
	}
	
	public static float getRotation(String imageFilePath) {
		try {
			ExifInterface exif = new ExifInterface(imageFilePath);
			int rotation = (int) exifOrientationToDegrees(
					Integer.parseInt(exif.getAttribute(ExifInterface.TAG_ORIENTATION)));
			return rotation;
		} catch (IOException e) {
			Log.e("wsd.display.Image", "Error checking exif", e);
		}
		
		return 0;
	}
	
	private static float exifOrientationToDegrees(int exifOrientation) {
		switch (exifOrientation) {
		case ExifInterface.ORIENTATION_ROTATE_270:
			return 270;
		case ExifInterface.ORIENTATION_ROTATE_180:
			return 180;
		case ExifInterface.ORIENTATION_ROTATE_90:
			return 90;
		}
		
		return 0;
	}
	
	public static Bitmap getFromPath(String pathName) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		
		Bitmap bmp = BitmapFactory.decodeFile(pathName, opts);
		
		return bmp;
	}
	
	public static String getPath(Context context, Uri uri) {
		String[] projection = {MediaStore.Images.Media.DATA};
		Cursor c = context.getContentResolver().query(uri, projection, null, null, null);
		if (c.moveToFirst()) {
			return c.getString(0);
		}
		
		return "";
	}
	
	public static Boolean exists(String path) {
		File file = new File(path);
		if (file.exists()) {
			return true;
		}
		
		return false;
	}
}