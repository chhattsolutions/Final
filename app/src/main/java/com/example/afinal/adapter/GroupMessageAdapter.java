package com.example.afinal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.afinal.Model.GroupChat;
import com.example.afinal.Model.User;
import com.example.afinal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GroupMessageAdapter extends RecyclerView.Adapter<GroupMessageAdapter.ViewHolder>{
    private Context mContext;
    private List<GroupChat> mGChat;
    public static int MSG_TYPE_LEFT=0;
    public static int MSG_TYPE_RIGHT=1;
    String imageurl;
    FirebaseUser fuser;

    DatabaseReference reference;


    public GroupMessageAdapter(Context mContext, List<GroupChat> mGChat ,String imageurl) {
        this.mContext = mContext;
        this.mGChat = mGChat;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public GroupMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return  new GroupMessageAdapter.ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return  new GroupMessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final GroupMessageAdapter.ViewHolder holder, int position) {
        GroupChat groupChat = mGChat.get(position);
        holder.show_message.setVisibility(View.VISIBLE);
        holder.show_message.setText(groupChat.getMessage());
        reference = FirebaseDatabase.getInstance().getReference("Users").child(groupChat.getSender());
        Glide.with(mContext).load(imageurl).into(holder.profile_image);
    }
    @Override
    public int getItemCount() {
        return mGChat.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if (mGChat.get(position).getSender().equals(fuser.getUid())) {
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }

    }
         public class ViewHolder extends RecyclerView.ViewHolder{

                public TextView show_message;
                public ImageView profile_image;
                public ViewHolder(View itemView) {
                    super(itemView);

                    show_message = itemView.findViewById(R.id.show_message);
                    profile_image = itemView.findViewById(R.id.profile_image);

                }
            }
    }
