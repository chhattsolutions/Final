package com.example.afinal.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.afinal.Model.User;
import com.example.afinal.Model.group;
import com.example.afinal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class multi_adpter extends RecyclerView.Adapter<multi_adpter.ViewHolder> {
    private android.content.Context mContext;
    private List<User> mUser;
    public List<String> mGroup_member_Id =new ArrayList<>();
    int flag = 0;


    public multi_adpter(Context mContext, List<User> mUser) {
        this.mContext = mContext;
        this.mUser = mUser;
    }
    @NonNull
    @Override
    public multi_adpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= LayoutInflater.from(mContext).inflate(R.layout.groupuser, viewGroup,false);
        ViewHolder vh=new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final User user = mUser.get(i);
        viewHolder.user_name.setText(user.getUser());
        Glide.with(mContext).load(user.getPhoto()).into(viewHolder.profile_image);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0)
                {
                    viewHolder.checkBox.setVisibility(View.VISIBLE);
                    flag = 1;
                    mGroup_member_Id.add(user.getId());
                }
                else
                {
                    viewHolder.checkBox.setVisibility(View.GONE);
                    flag = 0;
                    for(int i =0 ; i<mGroup_member_Id.size(); i++)
                    {
                        if (user.getId() == mGroup_member_Id.get(i))
                        {
                            mGroup_member_Id.remove(i);
                            break;
                        }
                    }

                }
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
        public ImageView checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name=(TextView) itemView.findViewById(R.id.username);
            profile_image=(ImageView) itemView.findViewById(R.id.profileimage);
            checkBox = itemView.findViewById(R.id.checkBox);
            /*next=(Button) itemView.findViewById(R.id.next);*/
        }
    }

}

