package com.example.afinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.GroupMessageActivity;
import com.example.afinal.Model.group;
import com.example.afinal.R;

import java.io.Serializable;
import java.util.List;

public class ShowGroupAdpter extends RecyclerView.Adapter<ShowGroupAdpter.ViewHolder> {
    private Context mContext;
    private List<group> mGroups;
    public ShowGroupAdpter(Context mContext , List<group> mGroups)
    {
        this.mContext=mContext;
        this.mGroups=mGroups;
    }

    @NonNull
    @Override
    public ShowGroupAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.groupuser, viewGroup, false);
        return new ShowGroupAdpter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowGroupAdpter.ViewHolder holder, int position) {
       final group groups = mGroups.get(position);

        //Toast.makeText(mContext, groups.getGroup_name(), Toast.LENGTH_SHORT).show();

        holder.username.setText(groups.getGroup_name());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(mContext, GroupMessageActivity.class);
                //a.putExtra("username" ,groups.getGroup_name());
                Bundle args = new Bundle();
                args.putSerializable("members",(Serializable)groups.getMembers());
                a.putExtra("BUNDLE" , args);
                a.putExtra("GroupName", groups.getGroup_name());
                a.putExtra("Flag" , "1");
                a.putExtra("Admin", groups.getAdmin());
                mContext.startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
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
