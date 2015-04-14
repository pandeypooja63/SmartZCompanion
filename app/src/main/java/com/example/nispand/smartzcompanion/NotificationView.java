package com.example.nispand.smartzcompanion;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class NotificationView extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);
        mTextView = (TextView) findViewById(R.id.text);
        mTextView.setText(getIntent().getExtras().getString("reminderNote"));
        Button bSwithcOff= (Button) findViewById(R.id.switchOff);
        bSwithcOff.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = openOrCreateDatabase("SCDB",MODE_PRIVATE,null);
                db.execSQL("Update SCDB SET ReminderSet =0 Where ReminderSet=1; ");
                db.close();
                Toast.makeText(NotificationView.this, "Reminder deactivated!", Toast.LENGTH_SHORT).show();
                finish();
                System.exit(0);
            }
        });
    }


}