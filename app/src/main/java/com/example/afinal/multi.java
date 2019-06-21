package com.example.afinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.Model.User;
import com.example.afinal.adapter.multi_adpter;
import com.example.afinal.adapter.user_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class multi extends AppCompatActivity {
private RecyclerView recyclerView;
private multi_adpter multi_adapter;
private List<User> mUsers;
private EditText groupName;
private List<String> mGroup;
String text;
DatabaseReference reference;
FirebaseUser fUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);
       // RequestNewGroup();
        fUser=FirebaseAuth.getInstance().getCurrentUser();
        multi_adapter = new multi_adpter(getApplicationContext(), mUsers);
        recyclerView =findViewById(R.id.recycler_view);
        groupName=(EditText) findViewById(R.id.txt);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        mUsers =new ArrayList<>();
        ImageView next=(ImageView) findViewById(R.id.next);
       /* next.setImageResource(R.drawable.chattlogo1);*/
        readUser();
        Toast.makeText(multi.this,"Done",Toast.LENGTH_SHORT).show();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroup = multi_adapter.mGroup_member_Id;


                if(TextUtils.isEmpty(groupName.getText()) || mGroup.size() < 2)
                {
                    Toast.makeText(getApplicationContext(), "Please Write Group name OR Check length of Group Members" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                        final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users");
                        reference = FirebaseDatabase.getInstance().getReference("Groups").child(fUser.getUid()).push();
                        final HashMap<Object , String > hashMap = new HashMap<>();
                        final HashMap<Object , Object> hashMap1 = new HashMap<>();

                    /*hashMap1.put("group_name" , groupName.getText().toString());
                    reference.setValue(hashMap1);*/

                        //reference = reference.child(groupName.getText().toString());
                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mUsers.clear();
                                for(int i=0; i<mGroup.size();i++)
                                {
                                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                                    {
                                        User user = snapshot.getValue(User.class);
                                        if(user.getId().equals(mGroup.get(i))){
                                            mUsers.add(user);
                                        }
                                    }

                                }
                                hashMap.put("Group_name", groupName.getText().toString());
                                hashMap.put("Member0", fUser.getUid());
                                for(int i=1; i<=mGroup.size(); i++)
                                {
                                    hashMap.put("Member"+i, mGroup.get(i-1));
                                }

                                reference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                       /* Toast.makeText(multi.this, "Successfully Created", Toast.LENGTH_SHORT).show();*/
                                      /*  Intent i=new Intent(multi.this,MainActivity.class);
                                        startActivity(i);*/
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




                }

            }
        });
    }
    private void readUser() {
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    final User user=snapshot.getValue(User.class);
                    if(!user.getId().equals(firebaseUser.getUid()))
                    {
                        mUsers.add(user);
                    }
                }
                multi_adapter = new multi_adpter(multi.this,mUsers);
                recyclerView.setAdapter(multi_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
