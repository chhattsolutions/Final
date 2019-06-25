package com.example.afinal.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
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
import com.bumptech.glide.request.RequestOptions;
import com.example.afinal.API.API;
import com.example.afinal.MessagingActivity;
import com.example.afinal.Model.Listing;
import com.example.afinal.Model.User;
import com.example.afinal.Notification.Data;
import com.example.afinal.R;
import com.example.afinal.fragments.status;
import com.google.firebase.storage.internal.SleeperImpl;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class listingadpter extends RecyclerView.Adapter<listingadpter.ViewHolder> {
    private Context mContext;
    private List<Listing> mListing;
    public TextView namee;


    RequestQueue rq;
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
    public static List<String> id = new ArrayList<>(), price, price_prefix, description, city, state, bathroom, bedroom, garage, size, size_postfix, image, date, pstatus,
            locationlatlng;

    String Url = "http://chhatt.com/API/propertymain.php";
    ProgressDialog progressDialog;

    public listingadpter(Context mContext, List<Listing> mListing) {
        this.mListing = mListing;
        this.mContext = mContext;
    }

    public listingadpter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public listingadpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        if (Count == 0) {
            String format = "yyyy-MM-dd";
            Date c = Calendar.getInstance().getTime();
            df = new SimpleDateFormat(format);


            api = new API(mContext);
            api.execute();

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
            pstatus = api.pstatus;
            locationlatlng = api.locationlatlng;

            CurrentDate = df.format(c);
            try {
                CDate = df.parse(CurrentDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            while (id.isEmpty() && id.size() != 10) {
            }
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.listingfront, viewGroup, false);
        listingadpter.ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @SuppressLint("ResourceType")
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final listingadpter.ViewHolder viewHolder, final int i) {

        //final Listing list = mListing.get(i);
        status = new status();
        status.progressDialog.dismiss();

        Count++;
        viewHolder.price_budget.setText(price_prefix.get(i) + " " + price.get(i));
        viewHolder.category.setText(description.get(i));
        viewHolder.City.setText(city.get(i) + "," + state.get(i));
        viewHolder.tv_garage.setText(garage.get(i));
        viewHolder.tv_bathroom.setText(bathroom.get(i));
        viewHolder.tv_bed.setText(bedroom.get(i));
        viewHolder.tv_area.setText(size.get(i) + " " + size_postfix.get(i));
        viewHolder.inventory.setText(pstatus.get(i));

        try {
            Date PDate = df.parse(date.get(i));
            Diff = (CDate.getTime() - PDate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.tv_time.setText(Long.toString(Diff) + " DAYS");
        if (!image.isEmpty() && !image.get(i).contains("NA")) {
            Picasso.with(mContext).load(image.get(i)).fit().into(viewHolder.tile_img);
        }

        viewHolder.City.setMovementMethod(LinkMovementMethod.getInstance());
        viewHolder.City.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Plz Wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                String[] location = locationlatlng.get(i).split(",");

                String locat1 = GetExactLocation(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
                String uriBegin = "geo:" + Double.parseDouble(location[0]) + "," + Double.parseDouble(location[1]);
                String query = Double.parseDouble(location[0]) + "," + Double.parseDouble(location[1]) + "(" + locat1 + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                //String locat = "http://maps.google.com/maps?"+Double.parseDouble(location[0])+","+Double.parseDouble(location[1]);


                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                i.setPackage("com.google.android.apps.maps");
                mContext.startActivity(i);
            }
        });
    }

    public String GetExactLocation(double lati, double longi) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lati, longi, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Toast.makeText(mContext, strAdd, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, "Error ", Toast.LENGTH_LONG).show();
                //Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG).show();
            //Log.w("My Current loction address", "Canont get Address!");
        }
        progressDialog.dismiss();
        return strAdd;
    }


    @Override
    public int getItemCount() {

        if (id.isEmpty()) {
            return mListing.size();
        } else {
            return id.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView imageview_Profile, tile_img;
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
            namee = (TextView) itemView.findViewById(R.id.tv_name);
            imageview_Profile = (ImageView) itemView.findViewById(R.id.imageview_Profile);
            //sufyan
            //property_img=(ImageView) itemView.findViewById(R.id.property_image);

            price_budget = (TextView) itemView.findViewById(R.id.price_budget);
            inventory = (TextView) itemView.findViewById(R.id.inventory);
            City = (TextView) itemView.findViewById(R.id.City);
            category = (TextView) itemView.findViewById(R.id.category);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_garage = itemView.findViewById(R.id.tv_garage);
            tv_bathroom = itemView.findViewById(R.id.tv_bathroom);
            tv_bed = itemView.findViewById(R.id.tv_bed);
            tv_area = itemView.findViewById(R.id.tv_area);
            tile_img = itemView.findViewById(R.id.tile_img);
        }
    }
}