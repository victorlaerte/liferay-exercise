package com.example.luisafarias.myapplication.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Victor Oliveira
 */
public class AndroidUtil {

	public static boolean isOnline(Context context) {
		ConnectivityManager manager =
			(ConnectivityManager) context.getSystemService(
				Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
