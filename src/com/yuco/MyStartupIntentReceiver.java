package com.yuco;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyStartupIntentReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
/*		Intent i = new Intent("com.yuco.mApplication");
		i.setClass(context, mApplication.class);
		context.startService(i);*/
		Intent i = new Intent(context, LanguageSelectionView.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
	}

}
