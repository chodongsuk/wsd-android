package com.wsd.android.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class WSDDialog {
	public static void showAlert(Context context, String title, String message, String ok, String cancel, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (title != null) builder.setTitle(title);
		if (message != null) builder.setMessage(message);
		if (ok != null) builder.setPositiveButton(ok, okListener);
		if (cancel != null) builder.setNegativeButton(cancel, cancelListener);
		builder.setCancelable(false);
		builder.show();
	}
	
	public static ProgressDialog showProgressDialog(Context context, String title, String message) {
		ProgressDialog progress = new ProgressDialog(context);
		progress.setTitle(title);
		progress.setMessage(message);
		progress.setCancelable(false);
		progress.setIndeterminate(true);
		progress.show();
		
		return progress;
	}
}
