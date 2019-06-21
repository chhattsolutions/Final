package com.example.afinal.fragments;


import android.app.ProgressDialog;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.afinal.API.API;
import com.example.afinal.MessagingActivity;
import com.example.afinal.Model.Listing;
import com.example.afinal.Model.User;
import com.example.afinal.R;
import com.example.afinal.adapter.listingadpter;
import com.example.afinal.adapter.user_adapter;
import com.example.afinal.float_button.Addone;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class status extends Fragment {
    private listingadpter listing_adpter;
    private List<Listing> Listing;
    private RecyclerView recyclerView;
    private listingadpter listingadpter;
    private List<User> mUser;
    FirebaseUser fuser;
    DatabaseReference reference;
    TextView group;
    private FloatingActionButton floatButton;
    public static ProgressDialog progressDialog;

    public status() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_status, container, false);
        floatButton=view.findViewById(R.id.fab);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),Addone.class);
                startActivity(i);
            }
        });
        recyclerView =view.findViewById(R.id.recyclerStatus);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(llm);
        Listing =new ArrayList<>();
        readUser();
        fuser=FirebaseAuth.getInstance().getCurrentUser();
                return view;
    }
    private void readUser() {

        progressDialog = new ProgressDialog(getContext());
        this.progressDialog.setTitle("Loading...");
        this.progressDialog.setMessage("Plz Wait");
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.show();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Listing");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Listing.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    final Listing listing = snapshot.getValue(Listing.class);
                    Listing.add(listing);
                    listing_adpter = new listingadpter(getContext(),Listing);
                    recyclerView.setAdapter(listing_adpter);
                    listing_adpter.notifyDataSetChanged();
                }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
