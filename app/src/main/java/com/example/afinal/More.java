package com.example.afinal;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.afinal.float_button.Addthree;
import com.example.afinal.float_button.Addtwo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class More extends AppCompatActivity {
    Spinner view_spinner;
    Spinner Corner_spinner,facing_spinner,floor_spinner,Bathroom_spiiner;
    Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        view_spinner=(Spinner) findViewById(R.id.view_spinner);
        facing_spinner=(Spinner) findViewById(R.id.facing_spinner);
        Corner_spinner=(Spinner) findViewById(R.id.corner_spinner1);
        floor_spinner=(Spinner) findViewById(R.id.floor_spinner);
        Bathroom_spiiner=(Spinner) findViewById(R.id.Bathroom_spinner);
        getSupportActionBar().setTitle("Chhatt (چھت)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String[] viewList = new String[]{"Select View", "West open","East open","South open","North Open","North_East Open","North_West Open","South_East Open","South_West Open"};
        ArrayAdapter<String> viewArray = new ArrayAdapter<String>(More.this, android.R.layout.simple_spinner_item, viewList);
        viewArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        view_spinner.setAdapter(viewArray);

        String[] CornerList = new String[]{"Select Corner", "2 Side Corner","3 Side Corner"};
        ArrayAdapter<String> CornerArray = new ArrayAdapter<String>(More.this, android.R.layout.simple_spinner_item, CornerList);
        CornerArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Corner_spinner.setAdapter(CornerArray);

        String[] facingList = new String[]{"Select Facing", "Bungalow Facing","Commercial Facing","Sea Facing","Road Facing","Amenity Facing"};
        ArrayAdapter<String> facingArray = new ArrayAdapter<String>(More.this, android.R.layout.simple_spinner_item, facingList);
        CornerArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facing_spinner.setAdapter(facingArray);

        String[] flooringList = new String[]{"Select Flooring", "Marbles","Tiles","Wooden","Cement","Others"};
        ArrayAdapter<String> flooringArray = new ArrayAdapter<String>(More.this, android.R.layout.simple_spinner_item, flooringList);
        CornerArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floor_spinner.setAdapter(flooringArray);

        String[] BathroomList = new String[]{"Select Bathroom", "1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> bathroomArray = new ArrayAdapter<String>(More.this, android.R.layout.simple_spinner_item, BathroomList);
        CornerArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Bathroom_spiiner.setAdapter(bathroomArray);
    }
}
