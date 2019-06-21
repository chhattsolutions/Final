package com.example.afinal.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.afinal.Model.Chatlist;
import com.example.afinal.Model.User;
import com.example.afinal.Model.group;
import com.example.afinal.Notification.Token;
import com.example.afinal.R;
import com.example.afinal.adapter.ShowGroupAdpter;
import com.example.afinal.adapter.user_adapter;
import com.example.afinal.float_button.users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class friends extends Fragment {
    private RecyclerView recyclerView;
    private user_adapter userAdapter;
    private ShowGroupAdpter showGroupAdapter;
    private List<User> mUser;
    FirebaseUser fuser;
    FloatingActionButton floatingActionButton;
    DatabaseReference reference;
   private List<group> mGroups;
    List<Chatlist> usersList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_friends, container, false);
        recyclerView =view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        fuser=FirebaseAuth.getInstance().getCurrentUser();
        usersList= new ArrayList<>();
        floatingActionButton=view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), users.class);
                startActivity(i);
            }
        });
        reference=FirebaseDatabase.getInstance().getReference("Chatlist").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Chatlist chatlist=snapshot.getValue(Chatlist.class);
                   usersList.add(chatlist);
                }
                chatList();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        updateToken(FirebaseInstanceId.getInstance().getToken());
        return view;

    }
    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fuser.getUid()).setValue(token1);
    }
    private void chatList() {
    mUser=new ArrayList<>();
    reference=FirebaseDatabase.getInstance().getReference("Users");
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           mUser.clear();
            for(DataSnapshot snapshot:dataSnapshot.getChildren())
            {
                User user=snapshot.getValue(User.class);
                for(Chatlist chatlist:usersList)
                {
                    if (user.getId().equals(chatlist.getUserid())) {
                                mUser.add(user);
                            }
                        }
            }
            userAdapter =new user_adapter(getContext(),mUser,true);
            recyclerView.setAdapter(userAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }
}
