package com.example.afinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.afinal.Model.User;
import com.example.afinal.float_button.Addtwo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Verification extends AppCompatActivity {

     Spinner Agent;
     EditText Company_name,Ph_no;
     Button btn_next;
     DatabaseReference reference;
     FirebaseUser fuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chhatt (چھت)");
        Agent=(Spinner) findViewById(R.id.corner_spinner);
        Company_name=(EditText) findViewById(R.id.facing_spinner);
        Ph_no=(EditText) findViewById(R.id.view_spinner);
        btn_next=(Button) findViewById(R.id.next_button);
        String[] AgentList = new String[]{"Select Number Of Agent", "1", "2", "3", "4", "5",
                "6", "7"};
        ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Verification.this, android.R.layout.simple_spinner_item, AgentList);
        categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Agent.setAdapter(categoryArray);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Companyname=Company_name.getText().toString();
                final String agentno=Agent.getSelectedItem().toString();
                final String phone=Ph_no.getText().toString();
                fuser= FirebaseAuth.getInstance().getCurrentUser();
                reference=FirebaseDatabase.getInstance().getReference("Info").child(fuser.getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("infoid",fuser.getUid());
                        hashMap.put("Agent",agentno);
                        hashMap.put("Company",Companyname);
                        hashMap.put("Phone",phone);
                        reference.setValue(hashMap);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent a = new Intent(getApplicationContext(), MainActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(a);
                finish();
            }
        });
    }
}
