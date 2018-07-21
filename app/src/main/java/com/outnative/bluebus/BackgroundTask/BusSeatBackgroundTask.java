package com.outnative.bluebus.BackgroundTask;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.outnative.bluebus.Adapter.BusSeatAdapter;
import com.outnative.bluebus.GetterAndSetter.BusSeatRow;

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

/**
 * Created by milan on 9/4/2017.
 */
public class BusSeatBackgroundTask extends AsyncTask<String,Void,String> {
    BusSeatAdapter busSeatAdapter;
    Context ctx;
    String link;
    ProgressBar progressBar;
    public BusSeatBackgroundTask(Context context,BusSeatAdapter adapter,ProgressBar bar){
        this.busSeatAdapter=adapter;
        this.ctx=context;
        this.progressBar=bar;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        link="https://mewmew.000webhostapp.com/bluebus/app_files/busSeatData.php";
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {
        if(!link.isEmpty()){
            String rideId=params[0];
            String rideScheduleDate=params[1];
            try {
                URL url=new URL(link);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data=URLEncoder.encode("rideId","UTF-8")+"="+URLEncoder.encode(rideId,"UTF-8")+"&"+
                        URLEncoder.encode("rideScheduleDate","UTF-8")+"="+URLEncoder.encode(rideScheduleDate,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                StringBuilder builder=new StringBuilder();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    builder.append(line+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return builder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result==null){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this.ctx,"Something went wrong",Toast.LENGTH_LONG).show();
        }else{
            progressBar.setVisibility(View.GONE);
            String json_data=result;
            JSONObject jsonObject= null;
            try {
                jsonObject = new JSONObject(json_data);
                JSONArray jsonArray=jsonObject.optJSONArray("response_data");
                String seatNumber,selectedSeatNumber;
                int count=0;
                while(count<jsonArray.length()){
                    JSONObject obj=jsonArray.optJSONObject(count);
                    seatNumber=obj.getString("seatNumber");
                    selectedSeatNumber=obj.getString("selectedSeatNumber");
                    BusSeatRow row=new BusSeatRow(seatNumber,selectedSeatNumber);
                    busSeatAdapter.add(row);
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
