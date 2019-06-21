package com.example.afinal.adapter;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.afinal.API.API;
import com.example.afinal.Model.Listing;
import com.example.afinal.Model.User;
import com.example.afinal.Notification.Data;
import com.example.afinal.R;
import com.example.afinal.fragments.status;
import com.google.firebase.storage.internal.SleeperImpl;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class listingadpter extends RecyclerView.Adapter<listingadpter.ViewHolder>{
    private Context mContext;
    private List<Listing> mListing;
    public TextView namee;


    RequestQueue rq  ;
    Button btn;
    TextView textView;
    String id1 = "1";
    int Count = 0;
//    ProgressDialog  progressDialog = new ProgressDialog(mContext);;
    API api;
    URL url;
    String CurrentDate;
    SimpleDateFormat df;
    Date CDate;
    public static long Diff = 0;

    status status;
    //String id,price,price_prefix,description,city,state,bathroom,bedroom,garage,size,size_postfix;
    //String image;
    static List<String> id = new ArrayList<>() , price ,price_prefix , description , city  , state ,bathroom   ,bedroom ,garage ,size ,size_postfix , image, date;

    String  Url ="http://chhatt.com/API/propertymain.php";
   /* private List<User> mUser;
    private String username,photo;*/
    public listingadpter(Context mContext, List<Listing> mListing)
    {
        this.mListing=mListing;
        this.mContext=mContext;
    }
    @NonNull
    @Override
    public listingadpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        if(Count == 0) {
            String format = "yyyy-MM-dd";
            Date c = Calendar.getInstance().getTime();
            df = new SimpleDateFormat(format);



            api = new API(mContext);
            api.execute();
           /* try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            id = api.id;
            price = api.price;
            price_prefix = api.price_prefix;
            description = api.description;
            city = api.city;
            state = api.state;
            bathroom = api.bathroom;
            bedroom = api.bedroom;
            garage = api.garage;
            size = api.size;
            size_postfix = api.size_postfix;
            image = api.image;
            date = api.date;

            CurrentDate = df.format(c);
            try {
                CDate = df.parse(CurrentDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            while (id.isEmpty()) {

                //progressDialog.show();
                /*try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.listingfront, viewGroup,false);
        listingadpter.ViewHolder vh=new ViewHolder(view);
        return vh;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final listingadpter.ViewHolder viewHolder, final int i) {

        //Toast.makeText(mContext, CurrentDate.toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(mContext, date.get(i), Toast.LENGTH_SHORT).show();

        //final Listing list = mListing.get(i);
        status =new status();
        status.progressDialog.dismiss();

            Count++;
            //api.End_Progress();
            //progressDialog.dismiss();

            viewHolder.price_budget.setText(price_prefix.get(i) + " " + price.get(i));
            viewHolder.category.setText(description.get(i));
            viewHolder.City.setText(city.get(i) + "," + state.get(i));
            viewHolder.tv_garage.setText(garage.get(i));
            viewHolder.tv_bathroom.setText(bathroom.get(i));
            viewHolder.tv_bed.setText(bedroom.get(i));
            viewHolder.tv_area.setText(size.get(i) + " " + size_postfix.get(i));

        try {
            Date PDate = df.parse(date.get(i));
            Diff = (CDate.getTime() - PDate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.tv_time.setText(Long.toString(Diff)+" DAYS");

        if (!image.isEmpty() && !image.get(i).contains("NA") )
            {
                Glide.with(mContext).load(image.get(i)).into(viewHolder.tile_img);
            }

        //Glide.with(mContext).load(mListing.get(i).getPropertyphoto()).into(viewHolder.property_img);
    }

    @Override
    public int getItemCount() {

        if(id.isEmpty())
        {
            return mListing.size();
        }
        else {
            return id.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {



         public ImageView imageview_Profile,tile_img;
         public TextView price_budget;
         public TextView inventory;
         public TextView City;
         public TextView category;
         public TextView tv_time;
         public TextView tv_garage;
         public TextView tv_bathroom;
         public TextView tv_bed;
         public TextView tv_area;
         public ImageView property_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rq = Volley.newRequestQueue(mContext);
            namee=(TextView) itemView.findViewById(R.id.tv_name);
            imageview_Profile=(ImageView) itemView.findViewById(R.id.imageview_Profile);
           //sufyan
            //property_img=(ImageView) itemView.findViewById(R.id.property_image);

            price_budget=(TextView) itemView.findViewById(R.id.price_budget);
            inventory=(TextView) itemView.findViewById(R.id.inventory);
            City=(TextView) itemView.findViewById(R.id.City);
            category=(TextView) itemView.findViewById(R.id.category);
            tv_time=(TextView) itemView.findViewById(R.id.tv_time);
            tv_garage = itemView.findViewById(R.id.tv_garage);
            tv_bathroom = itemView.findViewById(R.id.tv_bathroom);
            tv_bed = itemView.findViewById(R.id.tv_bed);
            tv_area = itemView.findViewById(R.id.tv_area);
            tile_img = itemView.findViewById(R.id.tile_img);
        }
    }


   /* public void sendjsonrequest(final String j)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                   for (int i = 0; i < mListing.size(); i++) {
                        JSONObject jsonObject = response.getJSONObject(String.valueOf(0));
                        if(jsonObject.has("_thumbnail_id")) {
                            id = (jsonObject.getString("_thumbnail_id"));
                        }
                        else {
                            id = ("NULL");
                        }
                        if(jsonObject.has("ft_property_price")) {
                            price = (jsonObject.getString("ft_property_price"));
                        }
                        else {
                            price = ("NULL");
                        }
                        if (jsonObject.has("ft_property_price_prefix")) {
                            price_prefix = (jsonObject.getString("ft_property_price_prefix"));
                        }
                        else {
                            price_prefix = ("NULL");
                        }
                        if (jsonObject.has("name")) {
                            description = (jsonObject.getString("name"));
                        }
                        else {
                            description = ("NULL");
                        }
                        if (jsonObject.has("ft_property_address_state")) {
                            state = (jsonObject.getString("ft_property_address_state"));
                        }
                        else {
                            state = ("NULL");
                        }
                        if (jsonObject.has("ft_property_address_city")) {
                            city = (jsonObject.getString("ft_property_address_city"));
                        }
                        else {
                            city = ("NULL");
                        }
                        if (jsonObject.has("ft_property_bedrooms")) {
                            bedroom = (jsonObject.getString("ft_property_bedrooms"));
                        }
                        else {
                            bedroom = ("NULL");
                        }
                        if (jsonObject.has("ft_property_bathrooms")) {
                            bathroom = (jsonObject.getString("ft_property_bathrooms"));
                        }
                        else {
                            bathroom = ("NULL");
                        }
                        if (jsonObject.has("ft_property_garage")) {
                            garage = (jsonObject.getString("ft_property_garage"));
                        }
                        else {
                            garage = ("NULL");
                        }
                        if (jsonObject.has("ft_property_size")) {
                            size = (jsonObject.getString("ft_property_size"));
                        }
                        else {
                            size = ("NULL");
                        }
                        if (jsonObject.has("ft_property_size_postfix")) {
                            size_postfix = (jsonObject.getString("ft_property_size_postfix"));
                        }
                        else {
                            size_postfix = ("NULL");
                        }
                        if(jsonObject.has("img"))
                        {
                            image = jsonObject.getString("img");
                        }
                        id1 = "0";
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        rq.add(jsonObjectRequest);

    }*/
}

//    private void loadtextviwdata() {
//
//        final ProgressDialog progressBar = new ProgressDialog(mContext);
//        progressBar.setCancelable(true);//you can cancel it by pressing back button
//        progressBar.setMessage("File downloading ...");
//        progressBar.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //Toast.makeText(MainActivity.this, "ss", Toast.LENGTH_SHORT).show();
//                progressBar.dismiss();
//                try {
//                    JSONObject jsonObject= new JSONObject(response);
//                    id=jsonObject.getString("id");
//                    title=jsonObject.getString("title");
//
//                    //descrip=jsonObject.getString("description");
//                    //tv_description.setText(descrip);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressBar.dismiss();
//                Toast.makeText(mContext,error.getMessage(), LENGTH_LONG).show();
//
//             }
//
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
//        requestQueue.add(stringRequest);
//
//    }
//}
