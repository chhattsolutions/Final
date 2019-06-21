package com.example.afinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.afinal.Model.User;
import com.example.afinal.adapter.GroupInfoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupInfo extends AppCompatActivity {

    TextView admin_name;
    RecyclerView recyclerView;
    CircleImageView circleImageView;
    Intent intent1;
    List<Object> members;
    DatabaseReference reference;
    String group_name;

    static List<User> mUser = null;
    GroupInfoAdapter groupInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        members = new ArrayList<>();
        admin_name = findViewById(R.id.admin_name);
        recyclerView = findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(GroupInfo.this));
        circleImageView = findViewById(R.id.profile_image_admin);
        intent1 = getIntent();
        Bundle args = intent1.getBundleExtra("BUNDLE");
        members = (ArrayList<Object>) args.getSerializable("members");
        group_name = intent1.getStringExtra("Group_name");

        mUser = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(intent1.getStringExtra("Admin"));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                admin_name.setText(user.getUser());

                if(user.getPhoto().equals("default"))
                {
                    circleImageView.setImageResource(R.mipmap.ic_launcher_round);
                }
                else
                {
                    Glide.with(GroupInfo.this).load(user.getPhoto()).into(circleImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        for (int i= 0; i<members.size(); i++)
        {
            if(!members.get(i).toString().equals(intent1.getStringExtra("Admin")) && !members.get(i).toString().equals(group_name))
            {
                reference = FirebaseDatabase.getInstance().getReference("Users").child(members.get(i).toString());


                final int finalI = i;
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.getValue(User.class);
                        mUser.add(user);
                        if(finalI == (members.size()-1))
                        {
                            groupInfoAdapter = new GroupInfoAdapter(GroupInfo.this, mUser);
                            recyclerView.setAdapter(groupInfoAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

        }


    }
}
