package com.outnative.bluebus;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.outnative.bluebus.Adapter.SourceLocationAdapter;
import com.outnative.bluebus.ExtraClass.CustomTypeface;
import com.outnative.bluebus.ExtraClass.DateClass;
import com.outnative.bluebus.GetterAndSetter.LocationRows;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public final static int REQUEST_CODE=1;
    TextView showDateTextView;
    String selectedRideDate;
    ArrayAdapter adapter,destinationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navItemsView=navigationView.getRootView();

        Typeface type=Typeface.createFromAsset(getAssets(), "font/elegant.otf");
        TextView username=(TextView)navItemsView.findViewById(R.id.username_textview);
        TextView phoneNumber=(TextView)navItemsView.findViewById(R.id.phone_number_textview);

        final EditText sourceLocation=(EditText)findViewById(R.id.source_location);
        final EditText destinationLocation=(EditText)findViewById(R.id.destination_location);
        showDateTextView=(TextView)findViewById(R.id.show_selected_ride_date_textview);
        TextView  selectDateTextView=(TextView)findViewById(R.id.selectDateTextView);
        TextView  toolbarTitleName=(TextView)findViewById(R.id.toolbarTitleName);
        TextView  from=(TextView)findViewById(R.id.from_textview);
        TextView  to=(TextView)findViewById(R.id.to_textview);
        Button searchRideButton=(Button)findViewById(R.id.searchRideButton);
        RelativeLayout selectDateButton=(RelativeLayout)findViewById(R.id.selectDateButtonLayout);
        TextView dateOfJourneyTitle=(TextView)findViewById(R.id.select_date_title_textview);
        TextView todayDateTextView=(TextView)findViewById(R.id.today_day_date_select);

        dateOfJourneyTitle.setTypeface(type);
        todayDateTextView.setTypeface(type);
        sourceLocation.setTypeface(type);
        destinationLocation.setTypeface(type);
        searchRideButton.setTypeface(type);
        from.setTypeface(type);
        to.setTypeface(type);
        showDateTextView.setTypeface(type);
        toolbarTitleName.setTypeface(type);
        selectDateTextView.setTypeface(type);

        final Intent intent=new Intent(MainActivity.this,SelectDateActivity.class);
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        LinearLayout showDateLayout=(LinearLayout)findViewById(R.id.date_show_relative_layout);
        showDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        searchRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String source=sourceLocation.getText().toString().trim();
                String destination=destinationLocation.getText().toString().trim();

                if(source.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Select Source Location",Toast.LENGTH_LONG).show();
                }else if(destination.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Select Destination Location",Toast.LENGTH_LONG).show();
                }else if(selectedRideDate == null){
                    Toast.makeText(getApplicationContext(),"Please Select Date For Ride",Toast.LENGTH_LONG).show();
                }else {
                    Intent searchRideIntent = new Intent(MainActivity.this, RideListDetailsActivity.class);
                    searchRideIntent.putExtra("selected_date", selectedRideDate);
                    searchRideIntent.putExtra("source", source);
                    searchRideIntent.putExtra("destination", destination);
                    startActivity(searchRideIntent);
                }
            }
        });

        String locationsArray[]=getResources().getStringArray(R.array.locations);
      /*  for(int i=0;i<locationsArray.length-1;i++){
            String id= String.valueOf(i);
            String location=locationsArray[i];
            LocationRows row=new LocationRows(id,location);
            adapter.add(row);
        }*/

        //source location list view dropdown llist

        final ListView listView=(ListView)findViewById(R.id.listView);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,locationsArray);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        sourceLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String sourceLocationString = sourceLocation.getText().toString().trim();
                if (sourceLocation.length() > 0) {
                    listView.setVisibility(View.VISIBLE);
                    MainActivity.this.adapter.getFilter().filter(s);
                } else {
                    listView.setVisibility(View.GONE);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String locationName= String.valueOf(listView.getItemAtPosition(position));
                sourceLocation.setText(locationName);
                listView.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),locationName,Toast.LENGTH_LONG).show();
            }
        });

        //listview drop down list for destination location
        final ListView destinationListView=(ListView)findViewById(R.id.destination_location_listview);
        destinationAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,locationsArray);
        destinationListView.setAdapter(destinationAdapter);

        destinationListView.setTextFilterEnabled(true);
        destinationLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String destinationText = sourceLocation.getText().toString().trim();
                if (destinationLocation.length() > 0) {
                    destinationListView.setVisibility(View.VISIBLE);
                    MainActivity.this.destinationAdapter.getFilter().filter(s);
                } else {
                    destinationListView.setVisibility(View.GONE);
                }
            }
        });

        destinationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String locationName= String.valueOf(destinationListView.getItemAtPosition(position));
                destinationLocation.setText(locationName);
                destinationListView.setVisibility(View.GONE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode==RESULT_OK){
            String getDate=data.getStringExtra("date");
            String getMonth=data.getStringExtra("month");
            String getYear=data.getStringExtra("year");
            DateClass month=new DateClass(Integer.valueOf(getMonth));
            selectedRideDate=getDate + " " + month.monthName() + " " + getYear;
            showDateTextView.setText(getDate + " " + month.monthName() + " " + getYear);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
