package com.wsd.android.display;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class WSDImageCapture {
	private static final int CAMERA_PIC_REQUEST = 1044;
	private static final int GALLERY_PIC_REQUEST = 1033;
	private Context mContext;
	private long captureTime;
	private Uri tmp_uri;
	private String imageFilePath;
	
	public WSDImageCapture(Context context) {
		mContext = context;
	}
	
	public void showChooser(final WSDImageCaptureImplementation activity) {
		final String[] items = {"C‡mara", "Librer’a"};
		
		ListAdapter adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items) {
			ViewHolder holder;
			
			class ViewHolder {
				TextView title;
			}
			
			public View getView(int position, View convertView,
					ViewGroup parent) {
				final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				if (convertView == null) {
					convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
					
					holder = new ViewHolder();
					holder.title = (TextView) convertView.findViewById(android.R.id.text1);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				
				holder.title.setText(items[position]);
				
				return convertView;
			}
		};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("Subir foto desde");
		builder.setAdapter(adapter, 
				new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int item) {
					switch (item) {
					case 1:
						startGallery(activity);
						break;

					default:
						startCamera(activity);
						break;
					}
					
					dialog.dismiss();
				}
			});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void startCamera(WSDImageCaptureImplementation activity) {
    	Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    	
    	captureTime = System.currentTimeMillis();
    	String filename = captureTime + "";
    	ContentValues values = new ContentValues();
    	values.put(MediaStore.Images.Media.TITLE, filename);
    	
    	tmp_uri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    	
    	intent.putExtra(MediaStore.EXTRA_OUTPUT, tmp_uri);
    	
    	activity.startImageCaptureForResult(intent, CAMERA_PIC_REQUEST);
    }
    
    private void startGallery(WSDImageCaptureImplementation activity) {
    	Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    	intent.setType("image/*");
    	activity.startImageCaptureForResult(intent, GALLERY_PIC_REQUEST);
    }
	
	public Bitmap onActivityResult(int requestCode, int resultCode, Intent data, int imageWidth) {
    	Uri uri = null;
    	Bitmap bitmap = null;
    	
    	if (requestCode == CAMERA_PIC_REQUEST) {
    		if (resultCode == Activity.RESULT_OK) {
    			if (data != null) {
    				uri = data.getData();
    			} else {
    				uri = tmp_uri;
    			}
    		}
    	} else if (requestCode == GALLERY_PIC_REQUEST) {
    		if (resultCode == Activity.RESULT_OK) {
    			uri = data.getData();
    		}
    	}
    	
    	if (uri != null) {
    		imageFilePath = Image.getPath(mContext, uri);
    		
    		if (Image.exists(imageFilePath)) {
    			if (imageWidth == 0) {
    				bitmap = Image.getFromPath(imageFilePath);
    			} else {
    				bitmap = Image.resize(imageFilePath, imageWidth);
    			}
				int degrees = (int) Image.getRotation(imageFilePath);
				bitmap = Image.rotate(bitmap, degrees);
			}
    	}
    	
    	return bitmap;
    }
}
