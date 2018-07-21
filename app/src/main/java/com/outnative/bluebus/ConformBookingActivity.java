package com.outnative.bluebus;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.outnative.bluebus.Fragment.BoardingDroppingPlaceFragment;

public class ConformBookingActivity extends AppCompatActivity {
    String rideId,selectedDate,sourceLocation,destinationLocation,selectedSeat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform_booking);

        selectedSeat=getIntent().getStringExtra("selectedSeats");
        rideId=getIntent().getStringExtra("rideId");
        selectedDate=getIntent().getStringExtra("selectedDate");
        sourceLocation=getIntent().getStringExtra("sourceLocation");
        destinationLocation=getIntent().getStringExtra("destinationLocation");


        Bundle bundle=new Bundle();
        bundle.putString("selectedSeats",selectedSeat);
        bundle.putString("rideId", rideId);
        bundle.putString("selectedDate", selectedDate);
        bundle.putString("sourceLocation", sourceLocation);
        bundle.putString("destinationLocation", destinationLocation);

        BoardingDroppingPlaceFragment boardingDroppingPlaceFragment=new BoardingDroppingPlaceFragment();
        boardingDroppingPlaceFragment.setArguments(bundle);
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.conform_booking_fragment_display_layout,boardingDroppingPlaceFragment,"boardingDroppingFragment");
        transaction.addToBackStack("boardingDroppingFragment");
        transaction.commit();

    }

}
