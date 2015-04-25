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
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;



public class WifiReceiver extends BroadcastReceiver {

    private NotificationManager mNotificationManager;
    private int notificationID = 100;
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        SQLiteDatabase db = context.openOrCreateDatabase("SCDB",context.MODE_PRIVATE,null);
        if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            //Get the Reminder note from database
            String sSelectQ="Select * from SCDB where ReminderSet = 1;";
            Cursor c= db.rawQuery(sSelectQ,null);
            String events;
            if(c!=null && c.getCount() > 0)
            {

                c.moveToFirst();
                events =c.getString(c.getColumnIndex("Eventnote"))+"\n";
                while(c.moveToNext())
                {
                    events+=c.getString(c.getColumnIndex("Eventnote"))+"\n";
                }
                Toast.makeText(context,events, Toast.LENGTH_LONG).show();

                NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);
                nBuilder.setContentTitle("You have a WiFi reminder");
                nBuilder.setContentText("Click here for detail");
                nBuilder.setTicker("Alert!!");
                nBuilder.setSmallIcon(R.mipmap.ic_launcher);
                nBuilder.setSound(Uri.parse("android.resource://com.example.nispand.smartzcompanion/" + R.raw.alert));

                Intent resultIntent =new Intent(context,NotificationView.class);
                resultIntent.putExtra("reminderNote",events);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(NotificationView.class);

         /* Adds the Intent that starts the Activity to the top of the stack */
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

                nBuilder.setContentIntent(resultPendingIntent);

                mNotificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

      /* Update the existing notification using same notification ID */
                mNotificationManager.notify(notificationID, nBuilder.build());
            }
            db.close();
        }
        else {
            Toast.makeText(context, "Wifi Not Connected!", Toast.LENGTH_SHORT).show();

        }


    }
}
