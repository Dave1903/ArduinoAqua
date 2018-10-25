package com.thedevlopershome.arduinoaqua;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyAsyncTaskAqua extends AsyncTask<Void,Void,Integer> {

    public List<DetailsData> uidata;
    private WeakReference<Context> contextRef;
    private int type;

    public interface AsyncResponse {
        void processFinish(DetailsData output);

    }

    public MyAsyncTaskAqua.AsyncResponse delegate = null;


    public MyAsyncTaskAqua (Context context, MyAsyncTaskAqua.AsyncResponse delegate, int type){
        this.delegate=delegate;
        contextRef=new WeakReference<Context>(context);
        this.type=type;
    }



    ProgressDialog progressDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog((AquaMainActivity)contextRef.get());
        progressDialog.setTitle("LOADING...");
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.show();
    }

    private StringBuilder gaveStringData(){  //to read json data from url
        StringBuilder content = new StringBuilder();
        try {
            String getUrl="http://gogreennitrr.org/arduinoAqua/showData.php";
            URL url = new URL(getUrl);
            HttpURLConnection urlConnection =
                    (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // gets the server json data
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream()));
            String next;
            while ((next = bufferedReader.readLine()) != null)
            {
                content.append(next);
            }
            bufferedReader.close();
            urlConnection.disconnect();
        }
        catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return content;
    }

    @Override
    protected Integer doInBackground(Void... voids) {




        uidata=new ArrayList<>();
        String data=gaveStringData().toString();
        try {
            JSONObject reader = new JSONObject(data);
            JSONArray jArray = reader.getJSONArray("posts");
            for(int i=0;i<jArray.length();i++){
                JSONObject nestObject=jArray.getJSONObject(i);
                JSONObject mainObject=nestObject.getJSONObject("post");
                DetailsData bookData ;


                bookData = new DetailsData(mainObject.getString("time"),Float.parseFloat(mainObject.getString("waterLevel")));

                uidata.add(bookData);

            }




        }
        catch(JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }




    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        progressDialog.dismiss();
        DetailsData output=uidata.get(uidata.size()-1);
        delegate.processFinish(output);

    }


}
