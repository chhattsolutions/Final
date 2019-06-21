package com.example.afinal;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.afinal.Model.User;
import com.example.afinal.Model.verification;
import com.google.android.gms.common.api.Response;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class profile extends AppCompatActivity {
    EditText username,Compnayname,phone,email,agent;
    ImageView profile;
    FirebaseUser fuser;
    DatabaseReference reference,ref;
    HttpURLConnection urlConnection;
    URL url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username=(EditText) findViewById(R.id.view_spinner);
        username.setEnabled(false);
        profile=(ImageView) findViewById(R.id.profileimage);
        email=(EditText)findViewById(R.id.corner_spinner);
        email.setEnabled(false);
        phone=(EditText)findViewById(R.id.floor_spinner);
        phone.setEnabled(false);
        agent=(EditText)findViewById(R.id.agent);
        agent.setEnabled(false);
        Compnayname=(EditText)findViewById(R.id.facing_spinner) ;
        Compnayname.setEnabled(false);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chhatt (چھت)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

       /* try {

            url = new URL("http://chhatt.com/API/propertymain.php");
            Toast.makeText(this, url.toString(), Toast.LENGTH_LONG).show();
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000); // time in milliseconds
            urlConnection.setConnectTimeout(15000); // time in milliseconds
            urlConnection.setRequestMethod("GET"); // request method GET OR POST
            urlConnection.setDoInput(true);
            urlConnection.connect();

            InputStream in = urlConnection.getInputStream();
           BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            //InputStreamReader isw = new InputStreamReader(in);
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String JsonResponse= buffer.toString();
            JSONObject jsonobj = new JSONObject(JsonResponse);
            JSONArray jarray = jsonobj.getJSONArray(String.valueOf(url));

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);


                phone.setText(object.getString( "PropertyId"));


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                username.setText(fuser.getDisplayName());
                email.setText(fuser.getEmail());
               Glide.with(getApplicationContext()).load(fuser.getPhotoUrl().toString()).into(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref=FirebaseDatabase.getInstance().getReference("Info").child(fuser.getUid()) ;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                verification vef=dataSnapshot.getValue(verification.class);
                if(vef.getInfoid().equals(fuser.getUid())) {
                    phone.setText(vef.getPhone());
                    Compnayname.setText(vef.getCompany());
                    agent.setText(vef.getAgent());
                }
                else
                    {
                        Toast.makeText(profile.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
