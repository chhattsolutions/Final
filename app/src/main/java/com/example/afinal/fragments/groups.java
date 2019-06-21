package com.example.afinal.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.example.afinal.Model.User;
import com.example.afinal.Model.group;
import com.example.afinal.Notification.Token;
import com.example.afinal.R;
import com.example.afinal.adapter.ShowGroupAdpter;
import com.example.afinal.adapter.user_adapter;
import com.example.afinal.multi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class groups extends Fragment {
    private RecyclerView recyclerView;
    DatabaseReference reference;
    private List<group> mGroups;
    private ShowGroupAdpter showGroupAdapter;
    FirebaseUser fuser;
    TextView group;
    FloatingActionButton floatingActionButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_groups, container, false);
        recyclerView =view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        floatingActionButton=(FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),multi.class);
                startActivity(i);
            }
        });
        mGroups =new ArrayList<>();
        readGroups();
        return view;
    }
    private void readGroups()
    {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Groups");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                mGroups.clear();
                for(final DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren())
                    {

                        GenericTypeIndicator<HashMap<String, Object>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        Map<String, Object> objectHashMap = dataSnapshot1.getValue(objectsGTypeInd);
                        List<Object> SaveData = new ArrayList<Object>(objectHashMap.values());
                        String group= dataSnapshot1.child("Group_name").getValue().toString();
                        String admin = dataSnapshot1.child("Member0").getValue().toString();
                        group Groups = new group(SaveData,group,admin);

                        for(int i=0; i<Groups.Members.size(); i++)
                        {
                            try {
                                if(Groups.Members.get(i).equals(fuser.getUid()))
                                {
                                  /*  Toast.makeText(getContext(), Groups.Members.get(i).toString(), Toast.LENGTH_SHORT).show();*/
                                    mGroups.add(Groups);
                                }
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    /*Toast.makeText(getContext(), "hdsabd", Toast.LENGTH_LONG).show();*/

                    showGroupAdapter = new ShowGroupAdpter(getContext(),mGroups);
                    recyclerView.setAdapter(showGroupAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
