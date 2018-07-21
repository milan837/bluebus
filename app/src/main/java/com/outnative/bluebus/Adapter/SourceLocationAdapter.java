package com.outnative.bluebus.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.outnative.bluebus.GetterAndSetter.LocationRows;
import com.outnative.bluebus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milan on 8/22/2017.
 */

public class SourceLocationAdapter extends ArrayAdapter {
    List list=new ArrayList();
    Typeface type;
    Context ctx;
    public SourceLocationAdapter(Context context, int resource) {
        super(context, resource);
        this.ctx=context;
        this.type=Typeface.createFromAsset(this.ctx.getAssets(),"font/elegant.otf");
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View customView=inflater.inflate(R.layout.adapter_source_location_layout, parent, false);
        LocationRows row=(LocationRows)this.getItem(position);

        TextView itemName=(TextView)customView.findViewById(R.id.source_location_name);
        itemName.setText(row.getLocationName());
        itemName.setTypeface(type);

        return customView;
    }
}
