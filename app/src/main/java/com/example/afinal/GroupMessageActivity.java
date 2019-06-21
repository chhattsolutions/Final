package com.example.afinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.Model.GroupChat;
import com.example.afinal.Model.User;
import com.example.afinal.adapter.GroupMessageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupMessageActivity extends AppCompatActivity {
    CircleImageView profileimage;
    TextView username;
    FirebaseUser fuser;
    DatabaseReference reference;
    Intent intent;
    String userid;
    String name;
    ImageButton btn_send;
    EditText txt_send;
    Bundle args;
    GroupMessageAdapter groupMessageAdapter;
    List<GroupChat> mGChat;
    RecyclerView recyclerView;
    String GroupName;

    List<Object> members;
    List<String> receiver;
    List<User> mUser;

    String Flag = "0";
    String Flag1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_message);


        members = new ArrayList<>();
        receiver = new ArrayList<String>();
        mUser = new ArrayList<>();
        mGChat=new ArrayList<>();

       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GroupMessageActivity.this, GroupInfo.class);
               /* args = new Bundle();*/
                args.putSerializable("members",(Serializable)members);
                intent1.putExtra("BUNDLE" , args);
                intent1.putExtra("Admin",intent.getStringExtra("Admin"));
                intent1.putExtra("Group_name", GroupName);
                startActivity(intent1);
            }
        });
        recyclerView =(RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        profileimage = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        txt_send = findViewById(R.id.txt_send);

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        intent = getIntent();
        //userid = intent.getStringExtra("userid");
        username.setText(intent.getStringExtra("GroupName" ));
        Flag1 = intent.getStringExtra("Flag");
       args = intent.getBundleExtra("BUNDLE");
        members = (ArrayList<Object>) args.getSerializable("members");
        GroupName = intent.getStringExtra("GroupName");


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = txt_send.getText().toString();
                if (!message.equals("")){
                    sendMessage(fuser.getUid(),members, message,intent.getStringExtra("GroupName" ));
                }
                else
                {
                    Toast.makeText(GroupMessageActivity.this, "You Can't Send Empty Message", Toast.LENGTH_SHORT).show();
                }

                txt_send.setText("");

            }
        });


        for(int iteration = 0; iteration<members.size(); iteration ++)
        {
            if(!members.get(iteration).equals(fuser.getUid()) && !members.get(iteration).equals(GroupName))
            {
                //Toast.makeText(GroupMessageActivity.this, members.get(iteration).toString(), Toast.LENGTH_SHORT).show();
                reference = FirebaseDatabase.getInstance().getReference("Users").child(members.get(iteration).toString());
                reference.keepSynced(true);
                final int i = iteration;
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.getValue(User.class);
                        readMessage(fuser.getUid(),members.get(i).toString(), user.getPhoto() ,i);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        }


    }



    private void sendMessage(String sender, List<Object> reciver, String message ,String groupname)
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        for (int i= 0; i<reciver.size(); i++)
        {
            if(!reciver.get(i).equals(fuser.getUid()) && !reciver.get(i).equals(GroupName))
            {
                hashMap.put("reciver"+i,reciver.get(i).toString());
            }
        }

        hashMap.put("message",message);
        hashMap.put("Group_Name",groupname);
        reference.child("Group_Chats").push().setValue(hashMap);



    }


    private void readMessage(final String myid, final String userid, final String imageurl ,final int i)
    {

        reference=FirebaseDatabase.getInstance().getReference("Group_Chats");
        reference.keepSynced(true);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mGChat.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    GenericTypeIndicator<HashMap<String, Object>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    Map<String, Object> objectHashMap = snapshot.getValue(objectsGTypeInd);
                    List<Object> SaveData = new ArrayList<Object>(objectHashMap.values());
                    for(int i=1; i<SaveData.size(); i++)
                    {
                        if(snapshot.child("reciver"+i).exists())
                        {
                            receiver.add(snapshot.child("reciver"+(i)).getValue().toString());
                        }
                    }

                    String sender = snapshot.child("sender").getValue().toString();
                    String group = snapshot.child("Group_Name").getValue().toString();
                    String message = snapshot.child("message").getValue().toString();

                    GroupChat groupChat = new GroupChat(sender , message , group, receiver);
                    for(int i=0; i<groupChat.getReciver().size(); i++)
                    {
                        if ((groupChat.getReciver().get(i).equals(myid)/*&&groupChat.getSender().equals(userid)*/ ||
                                /*groupChat.getReciver().get(i).equals(userid) &&*/ groupChat.getSender().equals(myid))
                                && groupChat.getGroup_name().equals(GroupName) )
                        {
                            //Toast.makeText(GroupMessageActivity.this, groupChat.getMessage(), Toast.LENGTH_SHORT).show();
                            mGChat.add(groupChat);
                            break;
                        }
                    }

                    groupMessageAdapter = new GroupMessageAdapter(GroupMessageActivity.this, mGChat, imageurl);
                    recyclerView.setAdapter(groupMessageAdapter);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
