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
import com.example.afinal.Model.User;
import com.example.afinal.R;

import java.util.List;

public class GroupInfoAdapter extends RecyclerView.Adapter<GroupInfoAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUser;

    public GroupInfoAdapter(Context mContext, List<User> mUser) {
        this.mContext = mContext;
        this.mUser = mUser;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.group_info_item, parent, false);
        return new GroupInfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        User user = mUser.get(position);
        holder.username.setText(user.getUser());
        if(user.getPhoto().equals("default"))
        {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher_round);
        }
        else
        {
            Glide.with(mContext).load(user.getPhoto()).into(holder.profile_image);
        }
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;
        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);

        }
    }
}
