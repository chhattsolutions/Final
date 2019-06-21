package com.example.afinal.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.afinal.Model.Listing;
import com.example.afinal.Model.User;
import com.example.afinal.R;
import com.example.afinal.adapter.listingadpter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Inventory extends Fragment {
    private listingadpter listing_adpter;
    private List<com.example.afinal.Model.Listing> Listing;
    private RecyclerView recyclerView;
    private List<User> mUser;
    FirebaseUser fuser;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_inventory, container, false);
        recyclerView =view.findViewById(R.id.recyclerStatus);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        Listing =new ArrayList<>();
        /*   readUser();*/
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user=dataSnapshot.getValue(User.class);
                readUser(user.getUser(), user.getPhoto());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void readUser(final String users, final String photo) {
        final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Listing");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Listing.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    final Listing listing = snapshot.getValue(Listing.class);
                        if (listing.getUid().equals(firebaseUser.getUid())&&listing.getList1().equals("Inventrory")) {
                            Listing.add(listing);
                        }
                }
                listing_adpter = new listingadpter(getContext(), Listing);
                recyclerView.setAdapter(listing_adpter);
                listing_adpter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
