package com.outnative.bluebus;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class SelectDateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        Typeface type=Typeface.createFromAsset(getAssets(),"font/elegant.otf");

        final DatePicker datePicker=(DatePicker)findViewById(R.id.datePicker);
        Button selectDateButton=(Button)findViewById(R.id.select_ride_date_button);
        FrameLayout backFrameLayout=(FrameLayout)findViewById(R.id.back_icon_layout);
        TextView toolbarTitle=(TextView)findViewById(R.id.select_date_toolbar_title);
        toolbarTitle.setTypeface(type);

        backFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Intent intent=new Intent();

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDate= String.valueOf(datePicker.getDayOfMonth());
                String selectedMonth= String.valueOf(datePicker.getMonth()+1);
                String selectedYear= String.valueOf(datePicker.getYear());
                intent.putExtra("date",selectedDate);
                intent.putExtra("month",selectedMonth);
                intent.putExtra("year",selectedYear);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
