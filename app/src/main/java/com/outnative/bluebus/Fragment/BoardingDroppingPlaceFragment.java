package com.outnative.bluebus.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.outnative.bluebus.Adapter.BoardingLocationAdapter;
import com.outnative.bluebus.Adapter.DroppingLocationAdapter;
import com.outnative.bluebus.BackgroundTask.BoardingLocationTask;
import com.outnative.bluebus.BackgroundTask.DroppingLocationTask;
import com.outnative.bluebus.GetterAndSetter.BoardingLocationRows;
import com.outnative.bluebus.GetterAndSetter.DroppingLocationRows;
import com.outnative.bluebus.R;

/**
 * Created by milan on 9/11/2017.
 */
public class BoardingDroppingPlaceFragment extends Fragment {
    String rideId,selectedDate,sourceLocation,destinationLocation,selectedSeat;
    String boardingLocationId=null,droppingLocationId=null;
    BoardingLocationAdapter adapter;
    DroppingLocationAdapter droppingLocationAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_boarding_dropping,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle=getArguments();
        selectedSeat=bundle.getString("selectedSeats");
        rideId=bundle.getString("rideId");
        selectedDate=bundle.getString("selectedDate");
        sourceLocation=bundle.getString("sourceLocation");
        destinationLocation=bundle.getString("destinationLocation");


        Typeface type=Typeface.createFromAsset(getActivity().getAssets(),"font/elegant.otf");

        FrameLayout backLayout=(FrameLayout)getActivity().findViewById(R.id.back_icon_layout);
        TextView textView=(TextView)getActivity().findViewById(R.id.toolbar_name);
        RelativeLayout nextButtonLayout=(RelativeLayout)getActivity().findViewById(R.id.next_button_relative_layout);
        TextView nextTextView=(TextView)getActivity().findViewById(R.id.next_button_textview);

        nextTextView.setTypeface(type);
        textView.setTypeface(type);
        textView.setText(sourceLocation + " to " + destinationLocation);

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        TextView sourcePickUpTextView=(TextView)getActivity().findViewById(R.id.source_pick_up_textview);
        TextView destinationDroopTextView=(TextView)getActivity().findViewById(R.id.destination_droop_textview);
        //Button nextButton=(Button)getActivity().findViewById(R.id.bording_dropping_next_button);

        sourcePickUpTextView.setTypeface(type);
        destinationDroopTextView.setTypeface(type);
        //nextButton.setTypeface(type);

        //button tabs for layout
        final RelativeLayout boardingButton=(RelativeLayout)getActivity().findViewById(R.id.source_location_layout_tab);
        final RelativeLayout droppingButton=(RelativeLayout)getActivity().findViewById(R.id.destination_location_layout_tab);
        //layout for tab
        final RelativeLayout boardingLayout=(RelativeLayout)getActivity().findViewById(R.id.boarding_relative_layout);
        final RelativeLayout droppingLayout=(RelativeLayout)getActivity().findViewById(R.id.dropping_relative_layout);
        ProgressBar progressBar=(ProgressBar)getActivity().findViewById(R.id.loadBoardingProgressBar);


        //for bottom text display layout
        final LinearLayout bottomLayout=(LinearLayout)getActivity().findViewById(R.id.bording_dropping_textview_bottom);
        final TextView selectedBoardingTextview=(TextView)getActivity().findViewById(R.id.boarding_location_name_display);
        final TextView selectedDroppingTextview=(TextView)getActivity().findViewById(R.id.dropping_location_name_display);
        selectedBoardingTextview.setTypeface(type);
        selectedDroppingTextview.setTypeface(type);


        //for boarding location
        final ListView boardingListView=(ListView)getActivity().findViewById(R.id.boarding_listview);
        adapter=new BoardingLocationAdapter(getActivity(),R.layout.adapter_boarding_location);
        boardingListView.setAdapter(adapter);
        BoardingLocationTask task=new BoardingLocationTask(getActivity(),adapter,progressBar);
        task.execute(rideId, selectedDate, sourceLocation, destinationLocation);


        //for dropping location
        ListView droppingListView=(ListView)getActivity().findViewById(R.id.dropping_listview);
        droppingLocationAdapter=new DroppingLocationAdapter(getActivity(),R.layout.adapter_dropping_location);
        droppingListView.setAdapter(droppingLocationAdapter);
        DroppingLocationTask droppingLocationTask=new DroppingLocationTask(getActivity(),droppingLocationAdapter);
        droppingLocationTask.execute(rideId, selectedDate, sourceLocation, destinationLocation);

        //setting event while touching the boarding location name
        boardingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //losing the boarding  loaction listview layout
                boardingLayout.setVisibility(View.GONE);
                droppingLayout.setVisibility(View.VISIBLE);
                boardingButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                droppingButton.setBackgroundColor(getResources().getColor(R.color.button));

                BoardingLocationRows row = (BoardingLocationRows) adapter.getItem(position);
                boardingLocationId = row.getId();
                Toast.makeText(getActivity(), row.getId(), Toast.LENGTH_LONG).show();

                //code for bottom display text
                bottomLayout.setVisibility(View.VISIBLE);
                selectedBoardingTextview.setVisibility(View.VISIBLE);
                selectedBoardingTextview.setText("Boarding  : "+row.getStation());
            }
        });

        //dropping location adapter
        droppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DroppingLocationRows row=(DroppingLocationRows)droppingLocationAdapter.getItem(position);
                droppingLocationId=row.getId();

                //code for bottom display text
                bottomLayout.setVisibility(View.VISIBLE);
                selectedDroppingTextview.setVisibility(View.VISIBLE);
                selectedDroppingTextview.setText("Dropping : "+row.getStation());
            }
        });



//for tab host type button boarding
        boardingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boardingLocationId = null;
                droppingLayout.setVisibility(View.GONE);
                boardingLayout.setVisibility(View.VISIBLE);
                boardingButton.setBackgroundColor(getResources().getColor(R.color.button));
                droppingButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });

//for tab host type button dropping
        droppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                droppingLocationId = null;
                boardingLayout.setVisibility(View.GONE);
                droppingLayout.setVisibility(View.VISIBLE);
                boardingButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                droppingButton.setBackgroundColor(getResources().getColor(R.color.button));
            }
        });

     /*   nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //putting values to new fragment
                Bundle bundle=new Bundle();
                bundle.putString("rideId",rideId);
                bundle.putString("selectedDate",selectedDate);
                bundle.putString("selectedSeat",selectedSeat);
             //   bundle.putString("boardingLocation",);
             //   bundle.putString("droppingLocation",);


             //navigation to next fragment
            /*    ConformBooklingDetails conformBooklingDetails=new ConformBooklingDetails();
                FragmentManager manager=getFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.conform_booking_fragment_display_layout,conformBooklingDetails,"boardingDroppingFragment");
                transaction.addToBackStack("boardingDroppingFragment");
                transaction.commit();

            }
        });
        */

        nextButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(boardingLocationId==null){
                    Toast.makeText(getActivity(),"select your boarding location",Toast.LENGTH_LONG).show();
                }else if(droppingLocationId==null){
                    Toast.makeText(getActivity(),"select your dropping location",Toast.LENGTH_LONG).show();
                }else {

                    //putting value for next fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("rideId", rideId);
                    bundle.putString("selectedDate", selectedDate);
                    bundle.putString("selectedSeat", selectedSeat);
                    bundle.putString("boardingLocationId", boardingLocationId);
                    bundle.putString("droppingLocationId", droppingLocationId);

                    //navigation to next fragment
                    ConformBooklingDetails conformBooklingDetails = new ConformBooklingDetails();
                    conformBooklingDetails.setArguments(bundle);
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.conform_booking_fragment_display_layout, conformBooklingDetails, "boardingDroppingFragment");
                    transaction.addToBackStack("boardingDroppingFragment");
                    transaction.commit();
                }

            }
        });


    }
}
