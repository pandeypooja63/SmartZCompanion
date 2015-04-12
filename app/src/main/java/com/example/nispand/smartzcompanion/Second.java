package com.example.nispand.smartzcompanion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import static android.widget.Toast.LENGTH_LONG;


public class Second extends ActionBarActivity {

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final TextView mTV=(TextView) findViewById(R.id.editText2);
         Button b= (Button) findViewById(R.id.btnSet);
          Button bDel = (Button) findViewById(R.id.delRem);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  TextView tV= (TextView) findViewById(R.id.txtSet);
//                tV.setText(mTV.getText());

                SQLiteDatabase db = openOrCreateDatabase("SCDB",MODE_PRIVATE,null);
                //db.execSQL("Drop TABLE SCDB");
                db.execSQL("CREATE TABLE IF NOT EXISTS SCDB (EventID varchar(60),Eventnote varchar(600),ReminderSet BOOLEAN);");
              //  db.execSQL("Delete from SCDB;");
                SimpleDateFormat fdate = new SimpleDateFormat("HHmmssSSS");
                java.util.Date date= new java.util.Date();
                String sEventID = fdate.format(date);
                String sEventtxt = mTV.getText().toString();
                String sIquery = "Insert Into SCDB VALUES('"+sEventID+"', '"+sEventtxt+"',1);";
                db.execSQL(sIquery);

//                String sSelectQ="Select * from SCDB where EventID = '"+sEventID+"';";
//                Cursor c= db.rawQuery(sSelectQ,null);
//            String events ="";
//            if(c!=null)
//            {
//
//                c.moveToFirst();
//                events =c.getString(c.getColumnIndex("Eventnote"));
////                while(c.moveToNext())
////                {
////                    events+=c.getString(c.getColumnIndex("Eventnote"));
////                }
//
//            }

                db.close();
                setDisplay("REMINDER IS SET!!");
            }

        });

          bDel.setOnClickListener(new View.OnClickListener() {

              public void onClick(View v) {
                  SQLiteDatabase db = openOrCreateDatabase("SCDB",MODE_PRIVATE,null);
                  String sSelectQ="Select * from SCDB;";
                  Cursor c= db.rawQuery(sSelectQ,null);
                  if(c!=null)
                  {
                     if(c.getCount() > 0)
                     {
                        int cnt =c.getCount();
                         db.execSQL("Delete from SCDB;");
                         setDisplay("Deleted "+cnt+"WiFi reminders.");
                     }
                     else
                     {
                         setDisplay("No reminders to be deleted!!");
                     }
                  }

              }
          });


          }

    public void setDisplay(String toastNotif)
    {
        Toast.makeText(Second.this,toastNotif, LENGTH_LONG).show();
        Intent intent = new Intent(Second.this,MainActivity.class);
        startActivity(intent);
    }


}
