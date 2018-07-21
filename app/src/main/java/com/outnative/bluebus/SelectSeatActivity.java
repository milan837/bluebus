package com.outnative.bluebus;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.outnative.bluebus.Adapter.BusSeatAdapter;
import com.outnative.bluebus.BackgroundTask.BusSeatBackgroundTask;
import com.outnative.bluebus.GetterAndSetter.BusSeatRow;

import java.util.ArrayList;

public class SelectSeatActivity extends AppCompatActivity{
    FrameLayout[] seatLayout;
    String selectedDate,rideId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);
        Typeface type = Typeface.createFromAsset(getAssets(), "font/elegant.otf");

        rideId=getIntent().getStringExtra("rideId");
        selectedDate=getIntent().getStringExtra("selectedDate");
        Toast.makeText(getApplicationContext(),selectedDate,Toast.LENGTH_LONG).show();

        FrameLayout layout = (FrameLayout) findViewById(R.id.back_icon_layout);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_route_title);
        TextView selectedTextView = (TextView) findViewById(R.id.selected_text_view);
        TextView avalibleTextView = (TextView) findViewById(R.id.available_seat_text_view);
        TextView bookedTextView = (TextView) findViewById(R.id.booked_text_view);
        ProgressBar progressBar=(ProgressBar)findViewById(R.id.loadSeatProgressBar);


        toolbarTitle.setTypeface(type);
        selectedTextView.setTypeface(type);
        avalibleTextView.setTypeface(type);
        bookedTextView.setTypeface(type);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final GridView gridView=(GridView)findViewById(R.id.bus_seat_grid_view);
        FrameLayout okButton=(FrameLayout)findViewById(R.id.right_icon_layout);
        FrameLayout backButton=(FrameLayout)findViewById(R.id.back_icon_layout);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final BusSeatAdapter busSeatAdapter=new BusSeatAdapter(this,R.layout.adapter_bus_seat_layout);
        gridView.setAdapter(busSeatAdapter);

        BusSeatBackgroundTask busSeatBackgroundTask=new BusSeatBackgroundTask(this,busSeatAdapter,progressBar);
        busSeatBackgroundTask.execute(rideId, selectedDate);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView number = (TextView) view.findViewById(R.id.bus_seat_number_text_view);
                TextView booking = (TextView) view.findViewById(R.id.bus_booked_seat_number);
                RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.bus_seat_number_relative_layout);
                BusSeatRow object = (BusSeatRow) busSeatAdapter.getItem(position);

                String seatNumber = number.getText().toString().trim();
                String bookingStatus = booking.getText().toString().trim();
                if (bookingStatus.equals("1")) {
                    //for already booked seat
                    Toast.makeText(getApplicationContext(), "Seat " + seatNumber + " is already booked ", Toast.LENGTH_LONG).show();
                } else if (bookingStatus.equals("0")) {
                    //for selected seat
                    layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.seat_selected));
                    booking.setText("2");
                    object.setBookedNumber("2");
                } else if (bookingStatus.equals("2")) {
                    //for dis select seat
                    layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.seat_avilable));
                    booking.setText("0");
                    object.setBookedNumber("0");
                    // busSeatAdapter.notifyDataSetChanged();
                }

            }
        });

        final Intent intent=new Intent();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> numberList=new ArrayList<String>();
                int count=0;
                for(int i=0;i<busSeatAdapter.getCount();i++){
                    BusSeatRow row=(BusSeatRow)busSeatAdapter.getItem(i);
                    if(row.getBookedNumber().equals("2")){
                        numberList.add(row.getSeatNumber());
                        count++;
                    }
                }

                //changing list of seatnumber into string
                StringBuilder stringBuilder=new StringBuilder();
                for(int i=0;i<numberList.size();i++){
                    stringBuilder.append(numberList.get(i)+" ");
                }
                String selectedSeatNumber=stringBuilder.toString().trim();

                if(count<=4) {
                    intent.putExtra("selectedSeatNumber", selectedSeatNumber);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"you can select on 4 seat at a time",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}
