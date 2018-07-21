package com.outnative.bluebus.BackgroundTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.outnative.bluebus.Adapter.RideListAdapter;
import com.outnative.bluebus.GetterAndSetter.BusDetailsRow;

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
 * Created by milan on 9/5/2017.
 */
public class BusListBackgroundTask extends AsyncTask<String,Void,String>{
    Context ctx;
    RideListAdapter adapter;
    ProgressBar progressBar;
    TextView showErrorMsg;

    public BusListBackgroundTask(Context context,RideListAdapter adapter,ProgressBar progressBar,TextView showMsg){
        this.adapter=adapter;
        this.ctx=context;
        this.progressBar=progressBar;
        this.showErrorMsg=showMsg;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {
        String sourceLocation=params[0];
        String destinationLocation=params[1];
        String selectDate=params[2];
        String link="https://mewmew.000webhostapp.com/bluebus/app_files/rideListData.php";

        if(!sourceLocation.isEmpty() && !destinationLocation.isEmpty() && !selectDate.isEmpty()) {
            try {
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("sourceLocation","UTF-8")+"="+URLEncoder.encode(sourceLocation,"UTF-8")+"&"+
                        URLEncoder.encode("destinationLocation","UTF-8")+"="+URLEncoder.encode(destinationLocation,"UTF-8")+"&"+
                        URLEncoder.encode("selectDate","UTF-8")+"="+URLEncoder.encode(selectDate,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.close();
                outputStream.close();
                outputStream.flush();

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
        }else{
            Toast.makeText(ctx,"Asdasd",Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s==null){
            this.progressBar.setVisibility(View.GONE);
            Toast.makeText(ctx,"Someting went wrong",Toast.LENGTH_LONG).show();
        }else if(s.equals("0")){
            this.progressBar.setVisibility(View.GONE);
            showErrorMsg.setVisibility(View.VISIBLE);
        }else{
            this.progressBar.setVisibility(View.GONE);
            String result=s;
            //Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
            //Log.i("jkl", result);
            try {
                JSONObject jsonObject=new JSONObject(result);
                JSONArray jsonArray=jsonObject.optJSONArray("response_data");
                int count=0;
                String name,price,id,seat,hours,stime,dtime;
                while(count < jsonArray.length()){
                    JSONObject object=jsonArray.optJSONObject(count);
                    name=object.getString("ride_name");
                    price=object.getString("price");
                    id=object.getString("ride_id");
                    seat=object.getString("seat");
                    hours=object.getString("hours");
                    stime=object.getString("boarding_time");
                    dtime=object.getString("depature_time");

                    BusDetailsRow row=new BusDetailsRow(name,id,price,stime,dtime,hours,seat);
                    adapter.add(row);

                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
