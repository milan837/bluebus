package com.outnative.bluebus.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.outnative.bluebus.GetterAndSetter.BusSeatRow;
import com.outnative.bluebus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milan on 9/4/2017.
 */
public class BusSeatAdapter extends ArrayAdapter {
    List list=new ArrayList();
    Typeface type;

    public BusSeatAdapter(Context context, int resource) {
        super(context, resource);
        this.type=Typeface.createFromAsset(context.getAssets(),"font/elegant.otf");
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        final View customView=inflater.inflate(R.layout.adapter_bus_seat_layout, parent, false);
        final BusSeatRow row=(BusSeatRow)this.getItem(position);

        final TextView seatNumber=(TextView)customView.findViewById(R.id.bus_seat_number_text_view);
        TextView bookedNumber=(TextView)customView.findViewById(R.id.bus_booked_seat_number);
        final RelativeLayout layout=(RelativeLayout)customView.findViewById(R.id.bus_seat_number_relative_layout);
        if(row.getSeatNumber().equals("0")){
            layout.setBackgroundColor(Color.WHITE);
            seatNumber.setVisibility(View.GONE);
            bookedNumber.setVisibility(View.GONE);
            //layout.setClickable(false);
        }else{
            if(row.getBookedNumber().equals("1")){
                layout.setBackgroundDrawable(customView.getResources().getDrawable(R.drawable.seat_booked));
                //this.notifyDataSetChanged();
            }

            seatNumber.setText(row.getSeatNumber());
            bookedNumber.setText(row.getBookedNumber());

            seatNumber.setTypeface(type);
            bookedNumber.setTypeface(type);


        }

        return customView;
    }
}
