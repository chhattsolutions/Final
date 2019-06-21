package com.example.afinal.float_button;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.afinal.MapActivity;
import com.example.afinal.R;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;


public class Addtwo extends AppCompatActivity
    {

    private Button nextButton;
    public static Spinner cities,category_spinner;
    private EditText landmark;
    private Button location , voice;
    private String list1,propertytype,propertyfor;

    private TextView output;
    private final int REQ_CODE_SPEECH_OUTPUT = 143;

    private LocationManager locationManager;

        private GoogleApiClient googleApiClient;
        //private LocationRequest locationRequest;
        private Location lastLocation;
        //private Marker UserCurrentLocation;
        private static final int Request_User_Location_Code = 99;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_addtwo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chhatt (چھت)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intent = getIntent();
        list1 = intent.getStringExtra("list1");
        propertytype = intent.getStringExtra("propertytype");
        propertyfor = intent.getStringExtra("propertyfor");
        nextButton = (Button) findViewById(R.id.next_button);
        landmark = (EditText) findViewById(R.id.landmark_editText);
        cities = (Spinner) findViewById(R.id.cities_spinner);
        category_spinner = (Spinner) findViewById(R.id.category_spinner);
        //  location = (EditText) findViewById(R.id.location_editText);
        location=(Button) findViewById(R.id.location_editText);
        /*category_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list1=="Inventrory"&&)
            }Residential
        });*/
        if (intent.getStringExtra("list1").equals("Inventrory")&&
                intent.getStringExtra("propertytype").equals("Residential")&&
                intent.getStringExtra("propertyfor").equals("Sale")) {
            String[] categoryList = new String[]{"Select Category", "Plot", "Bungalow/House", "Lower Portion", "Upper Portion", "Apartment/Flat",
                    "Pent House", "Agriculture Land/Farm House"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
           // Listing list = new Listing();
        } // final
         if (intent.getStringExtra("list1").equals("Inventrory")&&
                 intent.getStringExtra("propertytype").equals("Residential")&&
                 intent.getStringExtra("propertyfor").equals("Rent")) {
            String[] categoryList = new String[]{"Select Category","Bungalow/House", "Lower Portion", "Upper Portion", "Apartment/Flat",
                    "Pent House","Guest House/Rooms", "Agriculture Land/Farm House"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        } // final
        if (intent.getStringExtra("list1").equals("Inventrory")&&
                intent.getStringExtra("propertytype").equals("Residential")&&
                intent.getStringExtra("propertyfor").equals("Booking")) {
            String[] categoryList = new String[]{"Select Category","Plot","Bungalow/House","Lower Portion", "Upper Portion", "Apartment/Flat",
                    "Pent House","Studio", "Agriculture Land/Farm House"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        } // final
        //ins/res end
        if (intent.getStringExtra("list1").equals("Inventrory")&&intent.getStringExtra("propertytype").equals("Industrial")&&intent.getStringExtra("propertyfor").equals("Sale")) {
            String[] categoryList = new String[]{"Select Category", "Plot", "Factory", "Warehouse"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        } // final
        if (intent.getStringExtra("list1").equals("Inventrory")&&intent.getStringExtra("propertytype").equals("Industrial")&&intent.getStringExtra("propertyfor").equals("Rent")) {
            String[] categoryList = new String[]{"Select Category","Plot", "Factory", "Warehouse"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        } // final
        if (intent.getStringExtra("list1").equals("Inventrory")&&intent.getStringExtra("propertytype").equals("Industrial")&&intent.getStringExtra("propertyfor").equals("Booking")) {
            String[] categoryList = new String[]{"Select Category","Plot","Factory","Warehouse"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        } // final
        //ins/inds end
        if (intent.getStringExtra("list1").equals("Inventrory")&&intent.getStringExtra("propertytype").equals("Commercial")&&intent.getStringExtra("propertyfor").equals("Sale")) {
            String[] categoryList = new String[]{"Select Category", "Plot", "Building", "Basement", "Shop/Showroom", "Office Floor/Space",
                    "Mezzanine", "School/Hospital/Hotel"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        }  // final
        if (intent.getStringExtra("list1").equals("Inventrory")&&intent.getStringExtra("propertytype").equals("Commercial")&&intent.getStringExtra("propertyfor").equals("Rent")) {
            String[] categoryList = new String[]{"Select Category","Building", "Basement", "Shop/Showroom", "Office Floor/Space",
                    "Mezzanine","Marriage Hall"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        } // final
        if (intent.getStringExtra("list1").equals("Inventrory")&&intent.getStringExtra("propertytype").equals("Commercial")&&intent.getStringExtra("propertyfor").equals("Booking")) {
            String[] categoryList = new String[]{"Select Category","Plot","Building","Shop/Showroom", "Basement", "Office Floor/Space",
                    "Mezzanine Floor"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        } // final
        //inv / com end
        if (intent.getStringExtra("list1").equals("Requirement")&&intent.getStringExtra("propertytype").equals("Residential")&&intent.getStringExtra("propertyfor").equals("Sale")) {
            String[] categoryList = new String[]{"Select Category", "Plot", "Bungalow/House", "Lower Portion", "Upper Portion", "Apartment/Flat",
                    "Pent House", "Agriculture Land/Farm House"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        }
        if (intent.getStringExtra("list1").equals("Requirement")&&intent.getStringExtra("propertytype").equals("Residential")&&intent.getStringExtra("propertyfor").equals("Rent")) {
            String[] categoryList = new String[]{"Select Category","Bungalow/House", "Lower Portion", "Upper Portion", "Apartment/Flat",
                    "Pent House","Guest House/Rooms", "Agriculture Land/Farm House"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        }
        if (intent.getStringExtra("list1").equals("Requirement")&&intent.getStringExtra("propertytype").equals("Residential")&&intent.getStringExtra("propertyfor").equals("Booking")) {
            String[] categoryList = new String[]{"Select Category","Plot","Bungalow/House","Lower Portion", "Upper Portion", "Apartment/Flat",
                    "Pent House","Studio", "Agriculture Land/Farm House"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        }
        //re /res end
        if (intent.getStringExtra("list1").equals("Requirement")&&intent.getStringExtra("propertytype").equals("Commercial")&&intent.getStringExtra("propertyfor").equals("Sale")) {
            String[] categoryList = new String[]{"Select Category", "Plot", "Building", "Basement", "Shop/Showroom", "Office Floor/Space",
                    "Mezzanine", "School/Hospital/Hotel"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        }
        if (intent.getStringExtra("list1").equals("Requirement")&&intent.getStringExtra("propertytype").equals("Commercial")&&intent.getStringExtra("propertyfor").equals("Rent")) {
            String[] categoryList = new String[]{"Select Category","Building", "Basement", "Shop/Showroom", "Office Floor/Space",
                    "Mezzanine","Marriage Hall"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        }
        if (intent.getStringExtra("list1").equals("Requirement")&&intent.getStringExtra("propertytype").equals("Commercial")&&intent.getStringExtra("propertyfor").equals("Booking")) {
            String[] categoryList = new String[]{"Select Category","Plot","Building","Shop/Showroom", "Basement", "Office Floor/Space",
                    "Mezzanine Floor"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        }
        //re/com end
        if (intent.getStringExtra("list1").equals("Requirement")&&intent.getStringExtra("propertytype").equals("Industrial")&&intent.getStringExtra("propertyfor").equals("Sale")) {
            String[] categoryList = new String[]{"Select Category", "Plot", "Factory", "WareHouse"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        }
        if (intent.getStringExtra("list1").equals("Requirement")&&intent.getStringExtra("propertytype").equals("Industrial")&&intent.getStringExtra("propertyfor").equals("Rent")) {
            String[] categoryList = new String[]{"Select Category","Plot", "Factory", "Warehouse"};
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        }
        if (intent.getStringExtra("list1").equals("Requirement")&&intent.getStringExtra("propertytype").equals("Industrial")&&intent.getStringExtra("propertyfor").equals("Booking")) {
            String[] categoryList = new String[]{"Select Category","Plot","Factory","WareHouse"   };
            ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, categoryList);
            categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(categoryArray);
            // Listing list = new Listing();
        }
        //res/inds/end
        String[] cityList = new String[]{"Select City", "Karachi", "Lahore", "Islamabad", "Hyderabad", "Bagh", "Bhimber", "khuiratta", "Kotli", "Mangla", "Mirpur",
                "Muzaffarabad", "Plandri", "Rawalakot", "Punch"
                , "Amir Chah", "Bazdar", "Bela", "Bellpat", "Bagh", "Burj", "Chagai"
                , "Chah Sandan",
                "Chakku",
                "Chaman",
                "Chhatr",
                "Dalbandin", "Mirpur Khas", "Sheikhupura", "Jhang Sadr", "Gujrat", "Bardar", "Dera Ghazi Khan", "Masiwala", "Nawabshah", "Okara", "Gilgit", "Chiniot", "Sadiqabad", "Turbat", "Dera Ismail Khan", "Chaman", "Zhob", "Mehra", "Parachinar", "Gwadar", "Kundian", "Shahdad Kot", "Haripur", "Matiari", "Dera Allahyar", "Lodhran", "Batgram", "Thatta", "Bagh", "Badin", "Mansehra", "Ziarat", "Muzaffargarh", "Tando Allahyar", "Dera Murad Jamali", "Karak", "Mardan", "Uthal", "Nankana Sahib", "Barkhan", "Hafizabad", "Kotli", "Loralai", "Dera Bugti", "Jhang City", "Sahiwal", "Sanghar", "Pakpattan", "Chakwal", "Khushab", "Ghotki", "Kohlu", "Khuzdar", "Awaran", "Nowshera", "Charsadda", "Qila Abdullah", "Bahawalnagar", "Dadu", "Alīabad", "Lakki Marwat", "Chilas", "Pishin", "Tank", "Chitral", "Qila Saifullah", "Shikarpur"};
        ArrayAdapter<String> cityArray = new ArrayAdapter<String>(Addtwo.this, android.R.layout.simple_spinner_item, cityList);
        cityArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cities.setAdapter(cityArray);
        //Listing list = new Listing();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String selectedLandmark = landmark.getText().toString();*/
               String selectedCity=cities.getSelectedItem().toString();
                String selectedCategory = category_spinner.getSelectedItem().toString();
                Intent addListingThree = new Intent(Addtwo.this, Addthree.class);
                // addListingThree.putExtra("category", selectedCategory);
                addListingThree.putExtra("cites",selectedCity);
                addListingThree.putExtra("list1",list1);
                addListingThree.putExtra("propertytype",propertytype);
                addListingThree.putExtra("propertyfor",propertyfor);
               /* addListingThree.putExtra("landmark", selectedLandmark);*/
                addListingThree.putExtra("category", selectedCategory);
                //addListingThree.putExtra("list", list);
               /* addListingThree.putExtra("list1", list1);
                addListingThree.putExtra("home",txt1);
                addListingThree.putExtra("plot",txt2);
                addListingThree.putExtra("commerical",txt3);
                addListingThree.putExtra("sale",txt4);
                addListingThree.putExtra("lease",txt5);
                addListingThree.putExtra("booking",txt6);*/
                  /*  addListingThree.putExtra("list", list);
                    addListingThree.putExtra("propertyType", property);
                    addListingThree.putExtra("propertyFor", propertyFor);

                    addListingThree.putExtra("city", selectedCity);
                    addListingThree.putExtra("location", selectedLocation);
                    ;*/

                startActivity(addListingThree);
            }
        });


     location.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String selectedCity=cities.getSelectedItem().toString();
             Intent intent = new Intent(Addtwo.this, MapActivity.class);
             intent.putExtra("location",selectedCity);
             startActivity(intent);
         }
     });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQ_CODE_SPEECH_OUTPUT:
            {
                if (requestCode == RESULT_OK && null != data)
                {
                    ArrayList<String> VoiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    output.setText(VoiceInText.get(0));
                }
                break;
            }
        }
    }
    }
