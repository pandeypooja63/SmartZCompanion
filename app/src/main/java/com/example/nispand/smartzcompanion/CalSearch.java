package com.example.nispand.smartzcompanion;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.widget.CalendarView;
import android.widget.TextView;


public class CalSearch extends ActionBarActivity {
    CalendarView calendar;
    CalendarContract Calendars;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_search);
        Bundle b = getIntent().getExtras();
        String Name1 = null;
        if (b != null) {
            Name1 = b.getString("Name");
        }
        TextView t1 = (TextView) findViewById(R.id.textView3);
        t1.setText(Name1);
    }
    }
