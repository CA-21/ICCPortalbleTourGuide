package com.yuco;
 
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
 
public class ScreenReceiver extends BroadcastReceiver {
	String TAG = "ScreenReceiver";
    public static boolean screenOff;
 
    @Override
    public void onReceive(Context context, Intent intent) {
 
    	Log.d(TAG,"onReceive ");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            screenOff = true;
            Log.d(TAG,"SCREEN TURNED OFF on BroadcastReceiver");
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            screenOff = false;
            Log.d(TAG,"SCREEN TURNED ON on BroadcastReceiver");
        }
        Intent i = new Intent(context, com.yuco.UpdateService.class);
        i.putExtra("screen_state", screenOff);
        context.startService(i);
    }
 
}