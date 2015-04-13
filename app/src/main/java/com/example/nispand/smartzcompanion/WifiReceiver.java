package com.example.nispand.smartzcompanion;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;


public class WifiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        SQLiteDatabase db = context.openOrCreateDatabase("SCDB",context.MODE_PRIVATE,null);
        if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            //Get the Reminder note from database
            String sSelectQ="Select * from SCDB where ReminderSet = 1;";
            Cursor c= db.rawQuery(sSelectQ,null);
            String events ="";
            if(c!=null && c.getCount() > 0)
            {

                c.moveToFirst();
                events =c.getString(c.getColumnIndex("Eventnote"))+"\n";
                while(c.moveToNext())
                {
                    events+=c.getString(c.getColumnIndex("Eventnote"))+"\n";
                }
                Toast.makeText(context,events, Toast.LENGTH_LONG).show();
                NotificationManager nM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify = new Notification(android.R.drawable.stat_notify_more,"WiFi Reminder",System.currentTimeMillis());
                Intent intnt = new Intent(context,WifiReceiver.class);
                PendingIntent pending = PendingIntent.getActivity(context,0,intnt,0);
                notify.setLatestEventInfo(context,"WifiReminder below",events,pending);
                notify.sound = Uri.parse("android.resource://com.mavs.atul.firstapplication/"+R.raw.alert);
                nM.notify(0,notify);


            }
            db.close();
        }
        else {
            Toast.makeText(context, "Wifi Not Connected!", Toast.LENGTH_SHORT).show();

        }


    }
}
