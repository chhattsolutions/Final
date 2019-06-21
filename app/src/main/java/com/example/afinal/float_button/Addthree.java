package com.example.afinal.float_button;

import android.content.Intent;
import android.icu.text.IDNA;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.MainActivity;
import com.example.afinal.Model.Listing;
import com.example.afinal.Model.User;
import com.example.afinal.More;
import com.example.afinal.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Addthree extends AppCompatActivity {
    private Intent intent;
    private ImageView propertyImage;
    private String  selectedCategory,list1,propertytype,cites,propertyfor,Us,ph;
    private EditText price , areaPerUnit,size_plot,size_plot2;
    private Button submitButton,moredetails;
    private Spinner bd,bth,sq;
    private TextView size,size2,bed1,bth1;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    FirebaseUser fuser;
    private Uri imageUri;
    private static final int Pick_image = 1;

    private User myAccount;

    private final static String LISTING = "listing";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addthree);
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
        cites=intent.getStringExtra("cites");
        selectedCategory = intent.getStringExtra("category");
        propertytype = intent.getStringExtra("propertytype");
        propertyfor = intent.getStringExtra("propertyfor");
        list1 = intent.getStringExtra("list1");
        bd=(Spinner) findViewById(R.id.bed);
        propertyImage=findViewById(R.id.property_img);
        sq=(Spinner) findViewById(R.id.sq);
        bth=(Spinner) findViewById(R.id.bath);
        bed1=(TextView) findViewById(R.id.bd);
        bth1=(TextView) findViewById(R.id.bth);
        moredetails=(Button) findViewById(R.id.more_details);
        price = (EditText) findViewById(R.id.price_editText);
        areaPerUnit = (EditText) findViewById(R.id.area_editText);
        size_plot = (EditText) findViewById(R.id.size_plot);
        size_plot2 = (EditText) findViewById(R.id.size2_plot);
        size=(TextView) findViewById(R.id.size);
        size2=(TextView) findViewById(R.id.size2);
        submitButton = (Button) findViewById(R.id.submit_button);
        fuser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
       /* databaseReference = firebaseDatabase.getReference().child(LISTING);*/

        String[] bd_count = new String[]{"1" , "2" , "3" , "4","5","6","7","8","9","10"};
        ArrayAdapter<String> bdArray = new ArrayAdapter<String>(Addthree.this, android.R.layout.simple_spinner_item, bd_count);
        bdArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bd.setAdapter(bdArray);
        String[] bth_count = new String[]{"1" , "2" , "3" , "4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21"
                ,"22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44"
                ,"45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62","63"};
        ArrayAdapter<String> bthArray = new ArrayAdapter<String>(Addthree.this, android.R.layout.simple_spinner_item, bth_count);
        bdArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bth.setAdapter(bthArray);
        /*final String plot=size_plot.getText().toString();
        final double plotdouble=Double.parseDouble(plot);
        String selectedPrice = price.getText().toString();
        final double priceDouble = Double.parseDouble(selectedPrice);
        final double rate=priceDouble/plotdouble;
        areaPerUnit.setText(String.valueOf(rate));*/
        //final
    if(intent.getStringExtra("category").equals("Plot")||intent.getStringExtra("category").equals("Agriculture Land/Farm House")||
            intent.getStringExtra("category").equals("School/Hospital/Hotel"))
    {
        String[] sq_count = new String[]{"Sq ft" , "Sq yd"  , "Marla","Kanal", "Acre"};
        ArrayAdapter<String> sqArray = new ArrayAdapter<String>(Addthree.this, android.R.layout.simple_spinner_item, sq_count);
        bdArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sq.setAdapter(sqArray);
        size.setText("Plot Area");
        size2.setVisibility(View.GONE);
        size_plot2.setVisibility(View.GONE);
        bd.setVisibility(View.GONE);
        bth.setVisibility(View.GONE);
        bed1.setVisibility(View.GONE);
        bth1.setVisibility(View.GONE);
    }
    if(intent.getStringExtra("category").equals("Bungalow/House")||
            intent.getStringExtra("category").equals("Lower Portion") ||
                intent.getStringExtra("category").equals("Upper Portion"))
        {
            String[] sq_count = new String[]{"Sq ft" , "Sq yd"  , "Marla","Kanal", "Acre"};
            ArrayAdapter<String> sqArray = new ArrayAdapter<String>(Addthree.this, android.R.layout.simple_spinner_item, sq_count);
            bdArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sq.setAdapter(sqArray);
            size.setText("Plot Area");
            size2.setText("Floor Area");
            size_plot2.setVisibility(View.VISIBLE);
            bd.setVisibility(View.VISIBLE);
            bth.setVisibility(View.GONE);
            bed1.setVisibility(View.VISIBLE);
            bth1.setVisibility(View.GONE);
        }
        if(intent.getStringExtra("category").equals("Apartment/Flat")
                ||intent.getStringExtra("category").equals("Pent House")
                ||intent.getStringExtra("category").equals("Studio"))
        {
            String[] sq_count = new String[]{"Sq ft" , "Sq yd" , "Marla","Kanal" , "Acre"};
            ArrayAdapter<String> sqArray = new ArrayAdapter<String>(Addthree.this, android.R.layout.simple_spinner_item, sq_count);
            bdArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sq.setAdapter(sqArray);
            size.setText("Floor Area");
            size2.setVisibility(View.GONE);
            size_plot2.setVisibility(View.GONE);
            bd.setVisibility(View.VISIBLE);
            bth.setVisibility(View.VISIBLE);
            bed1.setVisibility(View.VISIBLE);
            bth1.setVisibility(View.VISIBLE);
        }
        if(intent.getStringExtra("category").equals("Factory")
                ||intent.getStringExtra("category").equals("Warehouse"))
        {
            String[] sq_count = new String[]{"Sq ft" , "Sq yd" , "Marla","Kanal", "Acre"};
            ArrayAdapter<String> sqArray = new ArrayAdapter<String>(Addthree.this, android.R.layout.simple_spinner_item, sq_count);
            bdArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sq.setAdapter(sqArray);
            size.setText("Plot Area");
            size2.setText("Floor Area");
            bd.setVisibility(View.GONE);
            bth.setVisibility(View.VISIBLE);
            bed1.setVisibility(View.GONE);
            bth1.setVisibility(View.VISIBLE);
        } if(intent.getStringExtra("category").equals("Basement"))
        {
            String[] sq_count = new String[]{"Sq ft" , "Sq yd"  , "Marla","Kanal", "Acre"};
            ArrayAdapter<String> sqArray = new ArrayAdapter<String>(Addthree.this, android.R.layout.simple_spinner_item, sq_count);
            bdArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sq.setAdapter(sqArray);
            size.setText("Floor Area");
            size_plot.setVisibility(View.VISIBLE);
            bd.setVisibility(View.GONE);
            bth.setVisibility(View.GONE);
            bed1.setVisibility(View.GONE);
            bth1.setVisibility(View.GONE);
        }
        if(intent.getStringExtra("category").equals("Marriage Hall"))
        {
            String[] sq_count = new String[]{"Unit","Sq ft" , "Sq yd" , "Marla","Kanal", "Acre"};
            ArrayAdapter<String> sqArray = new ArrayAdapter<String>(Addthree.this, android.R.layout.simple_spinner_item, sq_count);
            bdArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sq.setAdapter(sqArray);
            size.setText("FLoor Area");
            size2.setText("Capacity");
            bd.setVisibility(View.GONE);
            bth.setVisibility(View.GONE);
            bed1.setVisibility(View.GONE);
            bth1.setVisibility(View.GONE);
        }
        if(intent.getStringExtra("category").equals("Building"))
        {
            String[] sq_count = new String[]{"Sq ft" , "Sq yd" , "Marla","Kanal", "Acre"};
            ArrayAdapter<String> sqArray = new ArrayAdapter<String>(Addthree.this, android.R.layout.simple_spinner_item, sq_count);
            bdArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sq.setAdapter(sqArray);
            size.setText("Plot Area");
            size2.setText("Floor Area");
            bd.setVisibility(View.GONE);
            bth.setVisibility(View.GONE);
            bed1.setVisibility(View.GONE);
            bth1.setVisibility(View.GONE);
        }
        if(intent.getStringExtra("category").equals("Office Floor/Space")||intent.getStringExtra("category").equals("Mezzanine"))
        {
            String[] sq_count = new String[]{"Sq ft" , "Sq yd" , "Marla","Kanal", "Acre"};
            ArrayAdapter<String> sqArray = new ArrayAdapter<String>(Addthree.this, android.R.layout.simple_spinner_item, sq_count);
            bdArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sq.setAdapter(sqArray);
            size.setText("Floor Area");
            size2.setVisibility(View.GONE);
            size_plot2.setVisibility(View.GONE);
            bd.setVisibility(View.GONE);
            bth.setVisibility(View.GONE);
            bed1.setVisibility(View.GONE);
            bth1.setVisibility(View.GONE);
        }if(intent.getStringExtra("category").equals("Guest House/Rooms"))
        {
            String[] sq_count = new String[]{"Sq ft" , "Sq yd" , "Marla","Kanal", "Acre"};
            ArrayAdapter<String> sqArray = new ArrayAdapter<String>(Addthree.this, android.R.layout.simple_spinner_item, sq_count);
            bdArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sq.setAdapter(sqArray);
            size.setText("Room Area");
            size2.setVisibility(View.GONE);
            size_plot2.setVisibility(View.GONE);
            bd.setVisibility(View.VISIBLE);
            bth.setVisibility(View.VISIBLE);
            bed1.setVisibility(View.VISIBLE);
            bth1.setText("Rooms");
        }
        if(intent.getStringExtra("category").equals("Shop/Showroom"))
        {
            String[] sq_count = new String[]{"Sq ft" , "Sq yd" , "Marla","Kanal", "Acre"};
            ArrayAdapter<String> sqArray = new ArrayAdapter<String>(Addthree.this, android.R.layout.simple_spinner_item, sq_count);
            bdArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sq.setAdapter(sqArray);
            size.setText("Floor Area");
            size2.setVisibility(View.GONE);
            size_plot2.setVisibility(View.GONE);
            bd.setVisibility(View.GONE);
            bth.setVisibility(View.GONE);
            bed1.setVisibility(View.GONE);
            bth1.setText("Rooms");
        }
        moredetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Addthree.this,More.class);
                startActivity(i);
            }
        });
        propertyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery(v);

            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String bed=bd.getSelectedItem().toString();
                final String bath=bth.getSelectedItem().toString();
                final String prie=price.getText().toString();
                final String squ=sq.getSelectedItem().toString();
                final String apu=areaPerUnit.getText().toString();
                /*final String Company=price.getText().toString();*/
                final String plt=size_plot.getText().toString();
                final String plt2=size_plot2.getText().toString();

                fuser= FirebaseAuth.getInstance().getCurrentUser();
                reference=FirebaseDatabase.getInstance().getReference("Users");
                reference=FirebaseDatabase.getInstance().getReference("");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren())
                        {
                            User user=snapshot.getValue(User.class);
                            if (fuser.getUid().equals(user.getId())) {
                                Us = user.getUser();
                                ph = user.getPhoto();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                 reference=FirebaseDatabase.getInstance().getReference("Listing").push();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("uid",fuser.getUid());
                        hashMap.put("bed",bed);
                        hashMap.put("bath",bath);
                        hashMap.put("prie",prie);
                        hashMap.put("squ",squ);
                        hashMap.put("plt",plt);
                        hashMap.put("cites",cites);
                        hashMap.put("apu",apu);
                        hashMap.put("propertyimage",propertyImage);
                        hashMap.put("selectedCategory",selectedCategory);
                        hashMap.put("propertytype",propertytype);
                        hashMap.put("list1",list1);
                        hashMap.put("propertyfor",propertyfor);
                        hashMap.put("ph",ph);
                        hashMap.put("Us",Us);
                        reference.setValue(hashMap);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });


    }
    //sufyan
    public void opengallery(View view) {

        Intent gallery = new Intent();
        gallery.setAction(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(Intent.createChooser(gallery, "Select"), Pick_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                imageUri = data.getData();
                propertyImage.setImageURI(imageUri);
            }
        }
    }
    }

