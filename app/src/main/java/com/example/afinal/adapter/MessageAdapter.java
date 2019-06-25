package com.example.afinal.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.afinal.MessagingActivity;
import com.example.afinal.Model.Chat;
import com.example.afinal.Model.User;
import com.example.afinal.R;
import com.example.afinal.ViewImage;
import com.example.afinal.videoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static int MSG_TYPE_LEFT=0;
    public static int MSG_TYPE_RIGHT=1;
    private Context mContext;
     List<Chat> mChat;
    SeekBar mSeekBar;
    MediaPlayer mMediaPlayer;
    FirebaseUser fuser;
    String photo;
    TextView mTvAudioLength;
    public MessageAdapter(Context mContext,List<Chat> mChat,String photo)
    {
        this.mContext=mContext;
        this.mChat=mChat;
        this.photo=photo;
    }

    public MessageAdapter()
    {

    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType==MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, viewGroup, false);
            MessageAdapter.ViewHolder vh = new MessageAdapter.ViewHolder(view);
            return vh;
        }
        else
            {
                View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, viewGroup, false);
                MessageAdapter.ViewHolder vh = new MessageAdapter.ViewHolder(view);
                return vh;
            }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
      final Chat  chat = mChat.get(i);
        final MediaPlayer mediaPlayer = new MediaPlayer();
        if (!chat.getMessage().equals("default")) {



            viewHolder.message.setVisibility(View.VISIBLE);
            viewHolder.show_message.setVisibility(View.VISIBLE);
            viewHolder.show_message.setText(chat.getMessage());
            viewHolder.show_video.setVisibility(View.GONE);
            viewHolder.voicecrd.setVisibility(View.GONE);
            viewHolder.image.setVisibility(View.GONE);
            /*viewHolder.btn_video.setVisibility(View.GONE);*/
            viewHolder.txt_time.setText(chat.getTimestamp());
            if (chat.isIsseen()) {
                viewHolder.txt_seen.setText("Seen");
            } else {
                viewHolder.txt_seen.setText("Delivered");
            }

            if (chat.getMessage().contains(" Type = Location"))
            {
                viewHolder.message.setVisibility(View.GONE);
                viewHolder.show_message.setVisibility(View.GONE);
                viewHolder.Map_icon.setVisibility(View.VISIBLE);
                viewHolder.Map_icon.setImageResource(R.drawable.googlemapicon);
                final String[] Message = chat.getMessage().split(",");
                final String StrLocation = GetExactLocation(Double.parseDouble(Message[0]),Double.parseDouble(Message[1]));
                viewHolder.Map_link.setText(StrLocation);
                viewHolder.txt_time2.setText(chat.getTimestamp());
                if (chat.isIsseen()) {
                    viewHolder.txt_seen2.setText("Seen");
                } else {
                    viewHolder.txt_seen2.setText("Delivered");
                }
                viewHolder.Map_link_card.setVisibility(View.VISIBLE);
                viewHolder.Map_link_card.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        String uriBegin = "geo:"+Double.parseDouble(Message[0])+","+Double.parseDouble(Message[1]);
                        String query = Double.parseDouble(Message[0])+","+Double.parseDouble(Message[1]) + "(" + StrLocation + ")";
                        String encodedQuery = Uri.encode(query);
                        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                        Uri uri = Uri.parse(uriString);
                        Intent i = new Intent(Intent.ACTION_VIEW, uri);
                        i.setPackage("com.google.android.apps.maps");
                        mContext.startActivity(i);
                    }
                });
            }
        } else if (!chat.getImage().equals("default")) {

            Glide.with(mContext).load(chat.getImage()).into(viewHolder.show_Image);
           /* viewHolder.btn_video.setVisibility(View.GONE);*/
            viewHolder.show_Image.setVisibility(View.VISIBLE);
            viewHolder.show_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, ViewImage.class);
                    i.putExtra("imageview", chat.getImage());
                    mContext.startActivity(i);
                }
            });
            viewHolder.voicecrd.setVisibility(View.GONE);
            viewHolder.show_video.setVisibility(View.GONE);
            viewHolder.message.setVisibility(View.GONE);
            viewHolder.image.setVisibility(View.VISIBLE);
            viewHolder.txt_time1.setText(chat.getTimestamp());
            if (chat.isIsseen()) {
                viewHolder.txt_seen1.setText("Seen");
            } else {
                viewHolder.txt_seen1.setText("Delivered");
            }
        } else if (!chat.getVideo().equals("default")) {
           /* viewHolder.show_video.setVideoPath(chat.getVideo());*/
            viewHolder.show_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, videoView.class);
                    i.putExtra("video", chat.getVideo());
                    mContext.startActivity(i);
                }
            });
            viewHolder.show_video.setVisibility(View.VISIBLE);
           /* viewHolder.btn_video.setVisibility(View.VISIBLE);*/
            viewHolder.show_message.setVisibility(View.GONE);
            viewHolder.voicecrd.setVisibility(View.GONE);
            viewHolder.message.setVisibility(View.GONE);
            viewHolder.image.setVisibility(View.GONE);
        } else if (!chat.getVoice().equals("default")) {

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            viewHolder.btn_play.setText(">");
            try {
                mediaPlayer.setDataSource(chat.getVoice());
                mediaPlayer.prepare();// might take long for buffering.
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMediaPlayer = mediaPlayer;
            mTvAudioLength = viewHolder.tvAudioLength;
            mSeekBar = viewHolder.seekBar;
            viewHolder.seekBar.setMax(mediaPlayer.getDuration());
            run.run();
            viewHolder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (mediaPlayer != null && fromUser) {
                        mediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });viewHolder.tvAudioLength.setText(calculateDuration(mediaPlayer.getDuration()));
            viewHolder.btn_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                        viewHolder.btn_play.setText("||");
                    } else {
                        mediaPlayer.pause();
                        viewHolder.btn_play.setText(">");
                    }
                }
            });
           /* viewHolder.btn_video.setVisibility(View.GONE);*/
            viewHolder.voicecrd.setVisibility(View.VISIBLE);
            viewHolder.message.setVisibility(View.GONE);
            viewHolder.image.setVisibility(View.GONE);
        } else {
            viewHolder.show_message.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext).load(photo).into(viewHolder.profile_image);

    }
    Runnable run = new Runnable() {
        @Override
        public void run() {
            // Updateing SeekBar every 100 miliseconds
            Handler seekHandler = new Handler();
            mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
            seekHandler.postDelayed(run, 100);
            //For Showing time of audio(inside runnable)
            int miliSeconds = mMediaPlayer.getCurrentPosition();
            if(miliSeconds!=0) {
                //if audio is playing, showing current time;
                long minutes = TimeUnit.MILLISECONDS.toMinutes(miliSeconds);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(miliSeconds);
                if (minutes == 0) {
                    mTvAudioLength.setText("0:" + seconds);
                } else {
                    if (seconds >= 60) {
                        long sec = seconds - (minutes * 60);
                        mTvAudioLength.setText(minutes + ":" + sec);
                    }
                }
            }else{
                //Displaying total time if audio not playing
                int totalTime=mMediaPlayer.getDuration();
                long minutes = TimeUnit.MILLISECONDS.toMinutes(totalTime);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(totalTime);
                if (minutes == 0) {
                    mTvAudioLength.setText("0:" + seconds);
                } else {
                    if (seconds >= 60) {
                        long sec = seconds - (minutes * 60);
                        mTvAudioLength.setText(minutes + ":" + sec);
                    }
                }
            }
        }

    };
    private String calculateDuration(int duration) {
        String finalDuration = "";
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        if (minutes == 0) {
            finalDuration = "0:" + seconds;
        } else {
            if (seconds >= 60) {
                long sec = seconds - (minutes * 60);
                finalDuration = minutes + ":" + sec;
            }
        }
        return finalDuration;
    }


    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //private static final Object URL =  ;
        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen, txt_seen1, txt_seen2;
        public TextView duration;
        public ImageView btn_video;
        public ImageView show_Image;
        public ImageView show_video;
        public TextView txt_time, txt_time1 , txt_time2;
        public CardView message;
        public CardView image, Map_link_card;
        public CardView voicecrd;
        Button btn_play;
        TextView tvAudioLength;
        SeekBar seekBar;
        public ImageView Map_icon;
        TextView Map_link;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = (TextView) itemView.findViewById(R.id.show_message);
            txt_seen = (TextView) itemView.findViewById(R.id.txt_seen);
            txt_seen1 = (TextView) itemView.findViewById(R.id.txt_seen1);
            profile_image = (ImageView) itemView.findViewById(R.id.profile_image);
            duration = (TextView) itemView.findViewById(R.id.duration);
            show_Image = (ImageView) itemView.findViewById(R.id.show_Image);
            show_video = (ImageView) itemView.findViewById(R.id.show_video);
            txt_time = (TextView) itemView.findViewById(R.id.text_time);
            message = (CardView) itemView.findViewById(R.id.lyt_thread);
            image = (CardView) itemView.findViewById(R.id.lyt_thread1);
            Map_link_card = itemView.findViewById(R.id.Map_link_card);
            txt_time1 = (TextView) itemView.findViewById(R.id.text_time1);
            txt_time2 = itemView.findViewById(R.id.text_time2);
            btn_play = (Button) itemView.findViewById(R.id.stop);
            tvAudioLength = (TextView) itemView.findViewById(R.id.duration);
            seekBar = (SeekBar) itemView.findViewById(R.id.seekBar);
            voicecrd=(CardView) itemView.findViewById(R.id.voic_card);
            Map_icon = (ImageView) itemView.findViewById(R.id.Map_icon);
            Map_link = (TextView) itemView.findViewById(R.id.Map_link);
            txt_seen2 = (TextView) itemView.findViewById(R.id.txt_seen2);
        }

        @Override
        public void onClick(View v) {

        }
    }




    @Override
    public int getItemViewType(int position)
    {
        fuser=FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid()))
        {
            return MSG_TYPE_RIGHT;
        }
      else {
            return MSG_TYPE_LEFT;
        }

    }
    public String GetExactLocation(double lati, double longi)
    {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lati, longi, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Toast.makeText(mContext,"http://maps.google.com/maps?"+strAdd, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, "Error ", Toast.LENGTH_LONG).show();
                //Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG).show();
            //Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

}

