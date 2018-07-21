package com.outnative.bluebus;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.outnative.bluebus.Adapter.RideListAdapter;
import com.outnative.bluebus.BackgroundTask.BusListBackgroundTask;

import java.util.ArrayList;

public class RideListDetailsActivity extends AppCompatActivity {
    String selectedDate,sourceLocation,destinationLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_list_details);

        Typeface type=Typeface.createFromAsset(getAssets(), "font/elegant.otf");

        selectedDate=getIntent().getStringExtra("selected_date");
        sourceLocation=getIntent().getStringExtra("source");
        destinationLocation=getIntent().getStringExtra("destination");

        FrameLayout layout=(FrameLayout)findViewById(R.id.back_icon_layout);
        TextView showDateOnToolbarTextView=(TextView)findViewById(R.id.show_ride_date_toolbar_textView);
        TextView toolbarTitle=(TextView)findViewById(R.id.toolbar_route_title);
        TextView showMsg=(TextView)findViewById(R.id.ride_not_found_text_view);


        showDateOnToolbarTextView.setTypeface(type);
        toolbarTitle.setTypeface(type);
        showMsg.setTypeface(type);

        toolbarTitle.setText(sourceLocation+" To "+destinationLocation);
        showDateOnToolbarTextView.setText("Date " + selectedDate);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //listview
        ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressBar);
        ListView listview=(ListView)findViewById(R.id.ride_list_list_view);
        RideListAdapter adapter=new RideListAdapter(this,R.layout.adapter_ride_list_items);
        listview.setAdapter(adapter);

        BusListBackgroundTask task=new BusListBackgroundTask(this, adapter,progressBar,showMsg);
        task.execute(sourceLocation, destinationLocation, selectedDate);

        final Intent intent=new Intent(RideListDetailsActivity.this,RideInformationActivity.class);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView rideId=(TextView)view.findViewById(R.id.ride_id);
                String busId=rideId.getText().toString().trim();
                intent.putExtra("rideId",busId);
                intent.putExtra("selectedDate",selectedDate);
                intent.putExtra("sourceLocation",sourceLocation);
                intent.putExtra("destinationLocation",destinationLocation);
                startActivity(intent);
            }
        });

    }


}
