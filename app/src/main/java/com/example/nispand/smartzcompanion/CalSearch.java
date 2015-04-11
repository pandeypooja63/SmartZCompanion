package com.example.nispand.smartzcompanion;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Date;


public class CalSearch extends ActionBarActivity {
    CalendarView calendar;
    CalendarContract Calendars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_search);
        Bundle b = getIntent().getExtras();
        int day = b.getInt("day");
        int month = b.getInt("month");
        int year = b.getInt("year");
        Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
        long date1 = b.getLong("date1");
        Date d2 = new Date(date1);

        String[] projection =
                {
                        CalendarContract.Reminders._ID,
                        CalendarContract.Reminders.TITLE,
                        CalendarContract.Reminders.DTSTART,
                        CalendarContract.Events.EXDATE,
                };
        // Get a Cursor over the Events Provider.
        Cursor cursor = getContentResolver().query(
                CalendarContract.Events.CONTENT_URI, projection, null, null,
                null);
        // Get the index of the columns.
        int nameIdx = cursor
                .getColumnIndexOrThrow(CalendarContract.Reminders.TITLE);
        int idIdx = cursor.getColumnIndexOrThrow(CalendarContract.Reminders._ID);
        int D1 = cursor.getColumnIndexOrThrow(CalendarContract.Reminders.DTSTART);

        // Initialize the result set.
        String[] result = new String[cursor.getCount()];
        // Iterate over the result Cursor.
        while (cursor.moveToNext()) {
            Date d1 = new Date(cursor.getLong(D1));
            // Extract the name.
            String name = cursor.getString(nameIdx);
            // Extract the unique ID.
            String id = cursor.getString(idIdx);
            result[cursor.getPosition()] = name + "(" + id + ")";
            if (d1.compareTo(d2) == 0) {
                Toast.makeText(getApplicationContext(), name + "(" + id + ")" + "(" + d1 + ")", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
