package com.outnative.bluebus.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.outnative.bluebus.GetterAndSetter.DroppingLocationRows;
import com.outnative.bluebus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milan on 9/11/2017.
 */
    public class DroppingLocationAdapter extends ArrayAdapter {
        List list=new ArrayList<>();
        Context ctx;
        Typeface type;
        public DroppingLocationAdapter(Context context, int resource) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater= LayoutInflater.from(getContext());
            View customView=inflater.inflate(R.layout.adapter_dropping_location, parent, false);
            DroppingLocationRows row=(DroppingLocationRows)this.getItem(position);

            TextView checkBox=(TextView)customView.findViewById(R.id.dropping_checkbox);
            TextView checkBoxId=(TextView)customView.findViewById(R.id.dropping_check_box_id);

            checkBoxId.setText(row.getId());
            checkBox.setText(row.getStation());
            checkBox.setTypeface(type);
            return customView;
        }
}
