package com.outnative.bluebus.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.outnative.bluebus.GetterAndSetter.BusDetailsRow;
import com.outnative.bluebus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milan on 9/5/2017.
 */
public class RideListAdapter extends ArrayAdapter {
    Context ctx;
    Typeface type;
    List list=new ArrayList<>();
    public RideListAdapter(Context context, int resource) {
        super(context, resource);
        this.ctx=context;
        this.type=Typeface.createFromAsset(ctx.getAssets(),"font/elegant.otf");
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View view=inflater.inflate(R.layout.adapter_ride_list_items, parent, false);
        BusDetailsRow row=(BusDetailsRow)this.getItem(position);

        TextView name=(TextView)view.findViewById(R.id.ride_name_number);
        TextView price=(TextView)view.findViewById(R.id.ride_price);
        TextView seat=(TextView)view.findViewById(R.id.ride_available_seat);
        TextView boardingAndDepatureTime=(TextView)view.findViewById(R.id.ride_time);
        TextView totalHours=(TextView)view.findViewById(R.id.ride_hours);
        TextView rideId=(TextView)view.findViewById(R.id.ride_id);
        TextView offDeals=(TextView)view.findViewById(R.id.ticket_deal_offer_information_textview);
        TextView cancelTime=(TextView)view.findViewById(R.id.ticket_cancelation_time_textView);

        name.setText(row.getRideName());
        price.setText(row.getTicketPrice());
        seat.setText(row.getSeatAvailable());
        boardingAndDepatureTime.setText(row.getBoradingTime()+" - "+row.getDepatureTime());
        totalHours.setText(row.getTravellingHours());
        rideId.setText(row.getRideId());

        offDeals.setTypeface(type);
        cancelTime.setTypeface(type);
        name.setTypeface(type);
        price.setTypeface(type);
        seat.setTypeface(type);
        boardingAndDepatureTime.setTypeface(type);
        totalHours.setTypeface(type);

        return view;
    }
}
