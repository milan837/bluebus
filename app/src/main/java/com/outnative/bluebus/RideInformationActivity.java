package com.outnative.bluebus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RideInformationActivity extends AppCompatActivity {
    String rideId,selectedDate,sourceLocation,destinationLocation;
    TextView rideName,rideDate,rideBoardingTime,rideArrivalTime,ridePrice,rideRoute,availableSeat,selectedSeat;
    ProgressBar progressBar;
    private static final int REQUEST_CODE=3;
    String seatGetFromActivity;
    Typeface type;
    LinearLayout selectedSeatDislayLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_information);

        rideId=getIntent().getStringExtra("rideId");
        selectedDate=getIntent().getStringExtra("selectedDate");
        sourceLocation=getIntent().getStringExtra("sourceLocation");
        destinationLocation=getIntent().getStringExtra("destinationLocation");

        type=Typeface.createFromAsset(getAssets(),"font/elegant.otf");

        selectedSeatDislayLayout=(LinearLayout)findViewById(R.id.selected_seat_linear_layout);
        FrameLayout backLayout=(FrameLayout)findViewById(R.id.back_icon_layout);
         rideName=(TextView)findViewById(R.id.ride_name);
         rideDate=(TextView)findViewById(R.id.ride_date);
         rideBoardingTime=(TextView)findViewById(R.id.ride_boarding_time);
         rideArrivalTime=(TextView)findViewById(R.id.ride_arrival_time);
         ridePrice=(TextView)findViewById(R.id.ride_price);
         rideRoute=(TextView)findViewById(R.id.ride_route);
         availableSeat=(TextView)findViewById(R.id.available_seat);

         selectedSeat=(TextView)findViewById(R.id.ride_selected_seat);
        Button selectSeatButton=(Button)findViewById(R.id.select_seat_button);
        Button proccedButton=(Button)findViewById(R.id.proccedButton);

        progressBar=(ProgressBar)findViewById(R.id.ride_details_progressBar);

        rideName.setTypeface(type);
        rideDate.setTypeface(type);
        rideBoardingTime.setTypeface(type);
        rideArrivalTime.setTypeface(type);
        selectedSeat.setTypeface(type);
        rideRoute.setTypeface(type);
        rideRoute.setTypeface(type);
        ridePrice.setTypeface(type);
        selectSeatButton.setTypeface(type);
        availableSeat.setTypeface(type);
        proccedButton.setTypeface(type);

        final Intent intent=new Intent(RideInformationActivity.this,SelectSeatActivity.class);
        selectSeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("rideId",rideId);
                intent.putExtra("selectedDate",selectedDate);
                startActivityForResult(intent, REQUEST_CODE);
                selectedSeatDislayLayout.removeAllViews();
            }
        });

        //for exit back botton
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RideInformationTask task=new RideInformationTask();
        task.execute();

       final Intent conformIntent=new Intent(RideInformationActivity.this,ConformBookingActivity.class);
        proccedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatGetFromActivity==null){
                    Toast.makeText(getApplicationContext(),"please select the seat",Toast.LENGTH_LONG).show();
                }else if(rideId==null){
                    Log.i("error_bus","rideID");
                }else if(selectedDate==null){
                    Log.i("error_bus", "selectedDate");
                }else if(sourceLocation==null){
                    Log.i("error_bus", "sourceLocation");
                }else if(destinationLocation==null){
                    Log.i("error_bus", "destinationLocation");
                }else{
                    conformIntent.putExtra("selectedSeats",seatGetFromActivity);
                    conformIntent.putExtra("rideId",rideId);
                    conformIntent.putExtra("selectedDate",selectedDate);
                    conformIntent.putExtra("sourceLocation",sourceLocation);
                    conformIntent.putExtra("destinationLocation",destinationLocation);
                    startActivity(conformIntent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            seatGetFromActivity=data.getStringExtra("selectedSeatNumber");
            selectedSeat.setText(seatGetFromActivity);
            //selectedSeat.setVisibility(View.VISIBLE);

            String seatNumber[]=seatGetFromActivity.split(" ");
            for(int i=0;i<seatNumber.length;i++){
                RelativeLayout relativeLayout=new RelativeLayout(this);
                LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                relativeParams.setMargins(5, 5, 10, 5);
                relativeLayout.setLayoutParams(relativeParams);
                selectedSeatDislayLayout.addView(relativeLayout);



                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(90, 100);
               // lparams.setMargins(5, 5, 5, 5);
                ImageView imageView=new ImageView(this);
                imageView.setLayoutParams(lparams);
                imageView.setImageResource(R.drawable.seat_selected);
                relativeLayout.addView(imageView);


                RelativeLayout.LayoutParams seatParams = new RelativeLayout.LayoutParams(50,50);
                seatParams.setMargins(35,15,0,0);
                TextView textView=new TextView(this);
                textView.setLayoutParams(seatParams);
                textView.setTextColor(Color.WHITE);
                textView.setText(seatNumber[i]);
                textView.setTypeface(type);
                textView.setTextSize(16);
                relativeLayout.addView(textView);
            }
        }else{
            Toast.makeText(getApplicationContext(),"please select the seat",Toast.LENGTH_LONG).show();
        }
    }

    class RideInformationTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String link="https://mewmew.000webhostapp.com/bluebus/app_files/rideDetails.php";
            try {
                URL url=new URL(link);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data= URLEncoder.encode("rideId","UTF-8")+"="+URLEncoder.encode(rideId,"UTF-8")+"&"+
                        URLEncoder.encode("selectedDate","UTF-8")+"="+URLEncoder.encode(selectedDate,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                StringBuilder stringBuilder=new StringBuilder();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s==null){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"someting went wrong",Toast.LENGTH_LONG).show();
            }else{
                String json_data=s;

                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(json_data);
                    JSONArray jsonArray=jsonObject.optJSONArray("response_data");
                    JSONObject object=jsonArray.optJSONObject(0);

                    rideName.setText(object.getString("ride_name"));
                    rideDate.setText("Date: "+selectedDate);
                    rideBoardingTime.setText("Boarding Time: "+object.getString("ride_time"));
                    rideArrivalTime.setText("Arrival Time: "+object.getString("ride_time"));
                    ridePrice.setText("Price Per Seat: Rs "+object.getString("ride_price"));
                    availableSeat.setText("Available Seat: "+object.getString("total_seat"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
