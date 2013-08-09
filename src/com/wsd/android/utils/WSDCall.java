package com.wsd.android.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class WSDCall {
	public static void call(Activity activity, String phone) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
		activity.startActivity(intent);
	}
	
	public static void call(final Activity activity, final String[] phone) {
		ListAdapter adapter = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_list_item_1, phone) {
			ViewHolder holder;
			Drawable tile;
			
			class ViewHolder {
				TextView title;
			}
			
			public View getView(int position, View convertView,
					ViewGroup parent) {
				final LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				if (convertView == null) {
					convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
					
					holder = new ViewHolder();
					holder.title = (TextView) convertView.findViewById(android.R.id.title);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				
				tile = activity.getResources().getDrawable(android.R.drawable.ic_menu_call);
				
				holder.title.setText(phone[position]);
				holder.title.setCompoundDrawables(tile, null, null, null);
				
				return convertView;
			}
		};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("Elija un número de teléfono");
		builder.setAdapter(adapter, 
			new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int item) {
					call(activity, phone[item]);
					dialog.dismiss();
				}
			});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
