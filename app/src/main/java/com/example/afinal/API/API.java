package com.example.afinal.API;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sajid on 6/15/2019.
 */

public class API extends AsyncTask<Void,Void,Void> {
    String  Url ="http://chhatt.com/API/propertymain.php";
    public static List<String> id =new ArrayList<>(), price = new ArrayList<>() ,price_prefix = new ArrayList<>(), description = new ArrayList<>(), city = new ArrayList<>() , state = new ArrayList<>(),bathroom  = new ArrayList<>() ,bedroom = new ArrayList<>(),garage = new ArrayList<>(),size = new ArrayList<>(),size_postfix = new ArrayList<>(), image = new ArrayList<>(),
    date = new ArrayList<>();
    RequestQueue rq  ;
    private Context mContext;
    int id1 = 0;
    String data = "";
    private ProgressDialog progressDialog;


    public API(Context mContext) {
        this.mContext=mContext;
        rq = Volley.newRequestQueue(mContext);
        //progressDialog = new ProgressDialog(mContext);
    }

    @Override
    public void onPreExecute() {
        progressDialog = new ProgressDialog(mContext);
        this.progressDialog.setTitle("Loading...");
        this.progressDialog.setMessage("Plz Wait");
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {

            URL url = new URL("https://chhatt.com/API/propertymain.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while (line != null)
            {
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONObject jObj = new JSONObject(data);
            for (int i = 0; i < data.length() ; i++) {
                JSONObject jsonObject =jObj.getJSONObject(String.valueOf(i));
                //JSONObject jsonObject = data.getJSONObject(String.valueOf(i));
                //JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                if(jsonObject.has("_thumbnail_id")) {
                    id.add((jsonObject.getString("_thumbnail_id")));
                }
                else {
                    id.add("NA");
                }
                if(jsonObject.has("ft_property_price")) {
                    price.add(jsonObject.getString("ft_property_price"));
                }
                else {
                    price.add("NA");
                }
                if (jsonObject.has("ft_property_price_prefix")) {
                    price_prefix.add(jsonObject.getString("ft_property_price_prefix"));
                }
                else {
                    price_prefix.add("NA");
                }
                if (jsonObject.has("name")) {
                    description.add(jsonObject.getString("name"));
                }
                else {
                    description.add("NULL");
                }
                if (jsonObject.has("ft_property_address_state")) {
                    state .add(jsonObject.getString("ft_property_address_state"));
                }
                else {
                    state .add("NA");
                }
                if (jsonObject.has("ft_property_address_city")) {
                    city.add (jsonObject.getString("ft_property_address_city"));
                }
                else {
                    city .add("NA");
                }
                if (jsonObject.has("ft_property_bedrooms")) {
                    bedroom .add(jsonObject.getString("ft_property_bedrooms"));
                }
                else {
                    bedroom .add("NA");
                }
                if (jsonObject.has("ft_property_bathrooms")) {
                    bathroom .add(jsonObject.getString("ft_property_bathrooms"));
                }
                else {
                    bathroom.add("NA");
                }
                if (jsonObject.has("ft_property_garage")) {
                    garage.add(jsonObject.getString("ft_property_garage"));
                }
                else {
                    garage.add("NA");
                }
                if (jsonObject.has("ft_property_size")) {
                    size .add(jsonObject.getString("ft_property_size"));
                }
                else {
                    size.add("NA");
                }
                if (jsonObject.has("ft_property_size_postfix")) {
                    size_postfix.add(jsonObject.getString("ft_property_size_postfix"));
                }
                else {
                    size_postfix.add("NA");
                }
                if (jsonObject.has("img"))
                {
                    image.add(jsonObject.getString("img"));
                }
                else {
                    image.add("NA");
                }
                if(jsonObject.has("date")){
                    date.add(jsonObject.getString("date"));
                }
                else {
                    date.add("NA");
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
