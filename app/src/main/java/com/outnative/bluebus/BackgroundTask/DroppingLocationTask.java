package com.outnative.bluebus.BackgroundTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.outnative.bluebus.Adapter.DroppingLocationAdapter;
import com.outnative.bluebus.GetterAndSetter.DroppingLocationRows;

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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by milan on 9/11/2017.
 */
public class DroppingLocationTask extends AsyncTask<String,Void,String> {
    Context ctx;
    DroppingLocationAdapter adapter;

    public DroppingLocationTask(Context context,DroppingLocationAdapter droppingLocationAdapter){
        this.ctx=context;
        this.adapter=droppingLocationAdapter;
    }

    @Override
    protected String doInBackground(String... params) {
        String link="https://mewmew.000webhostapp.com/bluebus/app_files/droppingLocationData.php";
        String rideId,selectedDate,sourceLocation,destinationLocation;
        rideId=params[0];
        selectedDate=params[1];
        sourceLocation=params[2];
        destinationLocation=params[3];

        try {
            URL url=new URL(link);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data= URLEncoder.encode("ride_id","UTF-8")+"="+URLEncoder.encode(rideId,"UTF-8")+"&"+
                    URLEncoder.encode("selected_date","UTF-8")+"="+URLEncoder.encode(selectedDate,"UTF-8")+"&"+
                    URLEncoder.encode("source_location","UTF-8")+"="+URLEncoder.encode(sourceLocation,"UTF-8")+"&"+
                    URLEncoder.encode("destination_location","UTF-8")+"="+URLEncoder.encode(destinationLocation,"UTF-8");
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
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s==null){
            Toast.makeText(this.ctx,"somthing went worng",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this.ctx,s,Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.optJSONArray("dropping_data");
                String id,station;
                int count=0;
                while(count<jsonArray.length()){
                    JSONObject object=jsonArray.optJSONObject(count);
                    id=object.getString("id");
                    station=object.getString("station");
                    DroppingLocationRows row=new DroppingLocationRows(id,station);
                    adapter.add(row);

                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
