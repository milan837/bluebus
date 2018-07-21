package com.outnative.bluebus.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.outnative.bluebus.GetterAndSetter.BoardingLocationRows;
import com.outnative.bluebus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milan on 9/11/2017.
 */
public class BoardingLocationAdapter extends ArrayAdapter {
    List list=new ArrayList<>();
    Context ctx;
    Typeface type;
    public String selected_item_id=null;
    public BoardingLocationAdapter(Context context, int resource) {
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
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(getContext());
        View customView=inflater.inflate(R.layout.adapter_boarding_location, parent, false);
        final BoardingLocationRows row=(BoardingLocationRows)this.getItem(position);

        final TextView boardingLocationName=(TextView)customView.findViewById(R.id.checkBox);
        TextView boardingLocationId=(TextView)customView.findViewById(R.id.check_box_id);

        boardingLocationId.setText(row.getId());
        boardingLocationName.setText(row.getStation());
        boardingLocationName.setTypeface(type);

        return customView;
    }

}
