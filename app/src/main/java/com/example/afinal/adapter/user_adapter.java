package com.example.afinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.afinal.MessagingActivity;
import com.example.afinal.Model.Chat;
import com.example.afinal.Model.User;
import com.example.afinal.Model.group;
import com.example.afinal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

public class user_adapter extends RecyclerView.Adapter<user_adapter.ViewHolder> {

    private Context mContext;
    private List<User> mUser;
    /*private List<group> mGroups;*/
    private boolean ischat;
    String theLastMessage;
    public user_adapter(Context mContext,List<User> mUser,boolean ischat)
    {
        this.mContext=mContext;
        this.mUser=mUser;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public user_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view=LayoutInflater.from(mContext).inflate(R.layout.user_item, viewGroup,false);
        ViewHolder vh=new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position)
    {
        final User user=mUser.get(position);
        viewHolder.user_name.setText(user.getUser());
        Glide.with(mContext).load(user.getPhoto()).into(viewHolder.profile_image);
        viewHolder.img_off.setVisibility(View.GONE);
        viewHolder.img_on.setVisibility(View.GONE);
        if (ischat){
            lastMessage(user.getId(), viewHolder.last_msg);
        } else {
            viewHolder.last_msg.setVisibility(View.GONE);
        }
        if (ischat){
            if (user.getStatus().equals("online")){
                viewHolder.img_on.setVisibility(View.VISIBLE);
                viewHolder.img_off.setVisibility(View.GONE);
            } else {
                viewHolder.img_on.setVisibility(View.GONE);
                viewHolder.img_off.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.img_on.setVisibility(View.GONE);
            viewHolder.img_off.setVisibility(View.GONE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(mContext,MessagingActivity.class);
                a.putExtra("userid",user.getId());
                mContext.startActivity(a);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView user_name;
        public ImageView profile_image;
        private ImageView img_on;
        private ImageView img_off;
        private TextView last_msg;
        private ImageView tickyes;
        private ImageView tickno;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
            user_name=(TextView) itemView.findViewById(R.id.username);
            profile_image=(ImageView) itemView.findViewById(R.id.profileimage);
            last_msg = itemView.findViewById(R.id.last_msg);
           /* tickyes=itemView.findViewById(R.id.tick_yes);
            tickno=itemView.findViewById(R.id.tick_no);*/
        }
    }
    private void lastMessage(final String userid, final TextView last_msg){
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (firebaseUser != null && chat != null) {
                        if (chat.getReciver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                                chat.getReciver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
                            theLastMessage = chat.getMessage();
                        }
                    }
                }
                switch (theLastMessage){
                    case  "default":
                        last_msg.setText("No Message");
                        break;

                    default:
                        last_msg.setText(theLastMessage);
                        break;
                }

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

