package com.outnative.bluebus.Fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.outnative.bluebus.R;
import com.outnative.bluebus.SqlLiteDatabase.PassengerDetailsSql;

/**
 * Created by milan on 9/18/2017.
 */
public class SaveTicketFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_save_ticket,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Typeface type= Typeface.createFromAsset(getActivity().getAssets(), "font/elegant.otf");

        FrameLayout backLayout=(FrameLayout)getActivity().findViewById(R.id.back_icon_layout);
        TextView textView=(TextView)getActivity().findViewById(R.id.toolbar_name);

        textView.setTypeface(type);

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        //textview items
        TextView titleTicketname=(TextView)getActivity().findViewById(R.id.bluebus_m_ticket_textview);
        TextView travellindDestination=(TextView)getActivity().findViewById(R.id.destination_textview);
        TextView boardingTime=(TextView)getActivity().findViewById(R.id.boardingTime);
        TextView busName=(TextView)getActivity().findViewById(R.id.bus_name);
        TextView pnrNumber=(TextView)getActivity().findViewById(R.id.pnr_number);
        TextView email=(TextView)getActivity().findViewById(R.id.ticket_email);
        TextView phoneNumber=(TextView)getActivity().findViewById(R.id.ticket_phone_number);
        TextView ticketNote=(TextView)getActivity().findViewById(R.id.ticket_note_textview);
        TextView totalTicketAmount=(TextView)getActivity().findViewById(R.id.total_ticket_amount);

        titleTicketname.setTypeface(type);
        travellindDestination.setTypeface(type);
        boardingTime.setTypeface(type);
        busName.setTypeface(type);
        pnrNumber.setTypeface(type);
        email.setTypeface(type);
        phoneNumber.setTypeface(type);
        ticketNote.setTypeface(type);
        totalTicketAmount.setTypeface(type);

        TableLayout tableLayout=(TableLayout)getActivity().findViewById(R.id.tableLayout);
        TableRow row=new TableRow(getActivity());
        TableRow.LayoutParams layoutp=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(layoutp);

        TextView seatTitle=new TextView(getActivity());
        TextView nameTextView=new TextView(getActivity());
        TextView genderTextView=new TextView(getActivity());

        seatTitle.setText("Seat");
        nameTextView.setText("Name");
        genderTextView.setText("Gender");

        seatTitle.setTypeface(type);
        nameTextView.setTypeface(type);
        genderTextView.setTypeface(type);

        seatTitle.setTextSize(18);
        nameTextView.setTextSize(18);
        genderTextView.setTextSize(18);

        seatTitle.setTextColor(Color.BLACK);
        nameTextView.setTextColor(Color.BLACK);
        genderTextView.setTextColor(Color.BLACK);

        nameTextView.setMinWidth(250);
        seatTitle.setMinWidth(60);


        row.addView(seatTitle);
        row.addView(nameTextView);
        row.addView(genderTextView);

        tableLayout.addView(row,0);

        PassengerDetailsSql passengerDetailsSql=new PassengerDetailsSql(getActivity());
        SQLiteDatabase db=passengerDetailsSql.getReadableDatabase();
        passengerDetailsSql.onCreate(db);

        Cursor query=db.rawQuery("select * from "+passengerDetailsSql.TABLE_NAME,null);
        query.moveToFirst();
        String name,gender,seatNumber;
        int count=0;
        while(count<query.getCount()){
            seatNumber=query.getString(query.getColumnIndex("seat_number"));
            name=query.getString(query.getColumnIndex("passenger_name"));
            gender=query.getString(query.getColumnIndex("passenger_gender"));

            TableRow tableRow=new TableRow(getActivity());
            TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(layoutParams);

            //creating the row for each items
            TextView seatData=new TextView(getActivity());
            TextView nameData=new TextView(getActivity());
            TextView genderData=new TextView(getActivity());

            seatData.setText(seatNumber);
            nameData.setText(name);
            genderData.setText(gender);

            seatData.setTypeface(type);
            nameData.setTypeface(type);
            genderData.setTypeface(type);

            seatData.setTextSize(18);
            nameData.setTextSize(18);
            genderData.setTextSize(18);

            seatData.setMinWidth(60);
            nameData.setMaxWidth(220);
            nameData.setMinWidth(200);

            tableRow.addView(seatData);
            tableRow.addView(nameData);
            tableRow.addView(genderData);
            tableRow.setPadding(3,3,3,3);

            tableLayout.setPadding(10, 10, 10, 10);
            tableLayout.addView(tableRow,count+1);


            query.moveToNext();
            count++;
        }
    }
}
