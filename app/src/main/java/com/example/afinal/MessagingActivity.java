package com.example.afinal;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.afinal.Model.Chat;
import com.example.afinal.Model.User;
import com.example.afinal.Notification.Client;
import com.example.afinal.Notification.Data;
import com.example.afinal.Notification.MyResponce;
import com.example.afinal.Notification.Sender;
import com.example.afinal.Notification.Token;
import com.example.afinal.adapter.MessageAdapter;
import com.example.afinal.connectionchecker.ConnectionDetector;
import com.example.afinal.fragments.APIService;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagingActivity extends AppCompatActivity {
    CircleImageView profileimage;
    ConnectionDetector cd;
    private View layoutSlideCancel, layoutLock;
    private boolean isLocked = false;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String lattitude,longitude;
    TextView username;
    private boolean isDeleting;
    Button play,pause;
    FirebaseUser fuser;
    public enum UserBehaviour {
        CANCELING,
        LOCKING,
        NONE
    }
    public enum RecordingBehaviour {
        CANCELED,
        LOCKED,
        LOCK_DONE,
        RELEASED
    }
    private float directionOffset, cancelOffset, lockOffset;
    DatabaseReference reference;
    Intent intent;
    private View select_item;
    private View btn_send,btn_voice;

    EditText txt_send;
    private TextView timeText;
    MessageAdapter messageAdapter;
    List<Chat> mchat;
    private int audioTotalTime;
    private TimerTask timerTask;
    private Timer audioTimer;
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("m:ss", Locale.getDefault());
    private static final int RC_VIDEO_PICKER = 1000;
    String userid;
    private View layoutDustin, layoutMessage;
    private View imageViewLockArrow, imageViewLock, imageViewMic, dustin, dustin_cover, imageViewStop;
    private float lastX, lastY;
    private float firstX, firstY;
    RecyclerView recyclerView;
    private String mFileName=null;
    private Animation animBlink, animJump, animJumpFast;
    private MediaRecorder mRecorder = null;
    ValueEventListener seeListener;
    private boolean stopTrackingAction;
    private Handler handler;
    public static final int RECORD_AUDIO = 0;
    private float dp = 0;



    private StorageReference mfirebaseVoice;
    APIService apiService;
    private UserBehaviour userBehaviour = UserBehaviour.NONE;
    private RecordingListener recordingListener;
    boolean notify =false;
    StorageReference storageReference,videoreference;
    private static final int IMAGE_REQUEST = 234;
    SinchClient sinchClient;
    public  com.sinch.android.rtc.calling.Call call;

    Button btn;
    /*private static final int Voice_REQUEST = 1000;*/
    private Uri imageUri,videouri,voiceuri;
    private StorageTask uploadTask,uploadTask1,uploadTask2;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        Toolbar toolbar=findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
cd=new ConnectionDetector(this);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MessagingActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            });
            apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
            profileimage =findViewById(R.id.profileimage);
            username =findViewById(R.id.username);
            btn_send =findViewById(R.id.message_btn_send);
            txt_send =findViewById(R.id.txt_send);
            imageViewLock=findViewById(R.id.imageViewLock);
        imageViewStop = findViewById(R.id.imageViewStop);
        imageViewLockArrow = findViewById(R.id.imageViewLockArrow);
        layoutDustin = findViewById(R.id.layoutDustin);
        layoutMessage = findViewById(R.id.layoutMessage);
        timeText = findViewById(R.id.textViewTime);
        layoutSlideCancel = findViewById(R.id.layoutSlideCancel);
        layoutLock = findViewById(R.id.layoutLock);

        handler = new Handler(Looper.getMainLooper());

        animBlink = AnimationUtils.loadAnimation(MessagingActivity.this,
                R.anim.blink);
        animJump = AnimationUtils.loadAnimation(MessagingActivity.this,
                R.anim.jump);
        animJumpFast = AnimationUtils.loadAnimation(MessagingActivity.this,
                R.anim.jump_fast);
        imageViewMic = findViewById(R.id.imageViewMic);
        dustin = findViewById(R.id.dustin);
        dustin_cover =findViewById(R.id.dustin_cover);

        btn_voice=findViewById(R.id.message_btn_voice);
            select_item=findViewById(R.id.select_item);
        layoutSlideCancel = findViewById(R.id.layoutSlideCancel);
        dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, MessagingActivity.this.getResources().getDisplayMetrics());

        layoutLock = findViewById(R.id.layoutLock);
            //btn=(Button) findViewById(R.id.call);
        fuser=FirebaseAuth.getInstance().getCurrentUser();
            recyclerView =(RecyclerView) findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
        android.content.Context context = this.getApplicationContext();
        final SinchClient sinchClient = Sinch.getSinchClientBuilder().context(context)
                .applicationKey("4d75b0d3-1d14-4b87-a0f4-9b250c9009c2")
                .applicationSecret("uRdORTVeyE+k6spPu7e47w==")
                .environmentHost("clientapi.sinch.com")
                .userId(fuser.getUid())
                .build();
        sinchClient.setSupportCalling(true);
        sinchClient.startListeningOnActiveConnection();
        sinchClient.start();
        //sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
        sinchClient.getCallClient().addCallClientListener(new CallClientListener() {
            @Override
            public void onIncomingCall(final CallClient callClient, final com.sinch.android.rtc.calling.Call incomingcall) {
                AlertDialog alertDialog= new AlertDialog.Builder(MessagingActivity.this).create();
                alertDialog.setTitle("Calling");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Reject", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        incomingcall.hangup();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Pick", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        call=  incomingcall;
                        call.answer();
                        //call.addCallListener(new SinchCallListener());
                        Toast.makeText(getApplicationContext(), "Call is Started", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });

        txt_send.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    if (btn_send.getVisibility() != View.GONE) {
                        btn_send.setVisibility(View.GONE);
                        btn_send.animate().scaleX(0f).scaleY(0f).setDuration(100).setInterpolator(new LinearInterpolator()).start();
                    }
                } else {
                    if (btn_send.getVisibility() != View.VISIBLE && !isLocked) {
                        btn_send.setVisibility(View.VISIBLE);
                        btn_send.animate().scaleX(1f).scaleY(1f).setDuration(100).setInterpolator(new LinearInterpolator()).start();
                    }
                }
            }
        });

        /* sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());*/


//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (call == null) {
//                    call = sinchClient.getCallClient().callUser(userid);
//                   // call.addCallListener(new SinchCallListener());
//                    btn.setText("Hang Up");
//                } else {
//                    call.hangup();
//                }
//            }
//        });
       /* apiServices = Client.getClient("https://fcm.googleapis.com/").create(APIServices.class);*/

        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int unread=0;
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Chat chat=snapshot.getValue(Chat.class);
                    if (chat.getReciver().equals(userid)&&!chat.isIsseen()) {
                        unread++;

                    }
                            /*count_yes.setVisibility(View.GONE);
                            count_no.setVisibility(View.VISIBLE);*/
                }
                /*if (unread==0)
                {
                    count_yes.setVisibility(View.VISIBLE);
                    count_no.setVisibility(View.GONE);
                }
                else
                    {
                        count_yes.setVisibility(View.GONE);
                        count_no.setVisibility(View.VISIBLE);
                    }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            llm.setStackFromEnd(true);
            recyclerView.setLayoutManager(llm);
            storageReference = FirebaseStorage.getInstance().getReference("uploads");
            videoreference = FirebaseStorage.getInstance().getReference("uploadsvideo");
            mfirebaseVoice= FirebaseStorage.getInstance().getReference();
            //mfirebaseVoice= FirebaseStorage.getInstance().getReference();
            mFileName= Environment.getExternalStorageDirectory().getAbsolutePath();
            mFileName+="/recorded_audio.3gp";
        Toast.makeText(MessagingActivity.this, ""+mFileName, Toast.LENGTH_SHORT).show();
            intent =getIntent();
            userid =intent.getStringExtra("userid");

            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notify=true;
                    String msg=txt_send.getText().toString();
                    if (!msg.equals(""))
                    {
                        sendMessage(fuser.getUid(),userid,msg);
                        Toast.makeText(MessagingActivity.this, "Bhaaai", Toast.LENGTH_SHORT).show();

                    }
                    else
                        {Toast.makeText(MessagingActivity.this,"You can't Send empty message",Toast.LENGTH_LONG).show();}
                        txt_send.setText("");}
            });
        select_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu dropDownMenu = new PopupMenu(getApplicationContext(), select_item);
                dropDownMenu.getMenuInflater().inflate(R.menu.media, dropDownMenu.getMenu());
                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id=item.getItemId();
                        if(id==R.id.picture)
                        {
                            openImage();
                        }
                        else if(id==R.id.video)
                        {
                           openVideo();
                        }
                        else if(id==R.id.location)
                        {
                            openLocation();
                        }

                        return true;
                    }
                });
                dropDownMenu.show();
            }
        });
        imageViewStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLocked = false;
                Toast.makeText(MessagingActivity.this, "stop", Toast.LENGTH_SHORT).show();
                stopRecording(RecordingBehaviour.LOCK_DONE);

            }
        });
        btn_voice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (isDeleting) {
                    return true;
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    cancelOffset = (float) (btn_voice.getX() / 2.8);
                    lockOffset = (float) (btn_voice.getX() / 2.5);

                    if (firstX == 0) {
                        firstX = motionEvent.getRawX();
                    }

                    if (firstY == 0) {
                        firstY = motionEvent.getRawY();
                    }

                    if (ActivityCompat.checkSelfPermission(MessagingActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {



                    } else {
                        startRecording();
                    }

                    //startRecording();

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP
                        || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {

                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        stopRecording(RecordingBehaviour.RELEASED);
                    }

                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {

                    if (stopTrackingAction) {
                        return true;
                    }

                    UserBehaviour direction = UserBehaviour.NONE;

                    float motionX = Math.abs(firstX - motionEvent.getRawX());
                    float motionY = Math.abs(firstY - motionEvent.getRawY());

                    if (motionX > directionOffset &&
                            motionX > directionOffset &&
                            lastX < firstX && lastY < firstY) {

                        if (motionX > motionY && lastX < firstX) {
                            direction = UserBehaviour.CANCELING;

                        } else if (motionY > motionX && lastY < firstY) {
                            direction = UserBehaviour.LOCKING;
                        }

                    } else if (motionX > motionY && motionX > directionOffset && lastX < firstX) {
                        direction = UserBehaviour.CANCELING;
                    } else if (motionY > motionX && motionY > directionOffset && lastY < firstY) {
                        direction = UserBehaviour.LOCKING;
                    }

                    if (direction == UserBehaviour.CANCELING) {
                        if (userBehaviour == UserBehaviour.NONE || motionEvent.getRawY() + btn_voice.getWidth() / 2 > firstY) {
                            userBehaviour = UserBehaviour.CANCELING;
                        }

                        if (userBehaviour == UserBehaviour.CANCELING) {
                            translateX(-(firstX - motionEvent.getRawX()));
                        }
                    } else if (direction == UserBehaviour.LOCKING) {
                        if (userBehaviour == UserBehaviour.NONE || motionEvent.getRawX() + btn_voice.getWidth() / 2 > firstX) {
                            userBehaviour = UserBehaviour.LOCKING;
                        }

                        if (userBehaviour == UserBehaviour.LOCKING) {
                            translateY(-(firstY - motionEvent.getRawY()));
                        }
                    }

                    lastX = motionEvent.getRawX();
                    lastY = motionEvent.getRawY();
                }
                view.onTouchEvent(motionEvent);
                return true;
            }
        });
            fuser=FirebaseAuth.getInstance().getCurrentUser();
            reference=FirebaseDatabase.getInstance().getReference("Users").child(userid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User user=dataSnapshot.getValue(User.class);
                            username.setText(user.getUser());
                            Glide.with(MessagingActivity.this).load(user.getPhoto()).into(profileimage);
                            readMessage(fuser.getUid(), userid, user.getPhoto());

                    seenMessage(userid);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }
    private void translateY(float y) {
        if (y < -lockOffset) {
            locked();
            btn_voice.setTranslationY(0);
            return;
        }

        if (layoutLock.getVisibility() != View.VISIBLE) {
            layoutLock.setVisibility(View.VISIBLE);
        }

        btn_voice.setTranslationY(y);
        layoutLock.setTranslationY(y / 2);
        btn_voice.setTranslationX(0);
    }

    private void translateX(float x) {
        if (x < -cancelOffset) {
            canceled();
            btn_voice.setTranslationX(0);
            layoutSlideCancel.setTranslationX(0);
            return;
        }

        btn_voice.setTranslationX(x);
        layoutSlideCancel.setTranslationX(x);
        layoutLock.setTranslationY(0);
        btn_voice.setTranslationY(0);

        if (Math.abs(x) < imageViewMic.getWidth() / 2) {
            if (layoutLock.getVisibility() != View.VISIBLE) {
                layoutLock.setVisibility(View.VISIBLE);
            }
        } else {
            if (layoutLock.getVisibility() != View.GONE) {
                layoutLock.setVisibility(View.GONE);
            }
        }
    }


    private void openLocation() {
        cd=new ConnectionDetector( this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            GetLocation();
        }
        if(cd.isConnected()) {

        }
        else
        {
            Toast.makeText(getApplicationContext(), "error: " + "The Connection could not be found. Please Check your Connection!!", Toast.LENGTH_SHORT).show();

        }

    }
    public void GetLocation()
    {
        if (ActivityCompat.checkSelfPermission(MessagingActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MessagingActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MessagingActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Toast.makeText(this, lattitude +","+ longitude, Toast.LENGTH_SHORT).show();
                String locat1 = GetExactLocation(Double.parseDouble(lattitude), Double.parseDouble(longitude));
                String locat = "http://maps.google.com/maps?"+locat1;
                //String locat = " "+Double.parseDouble(lattitude)+","+Double.parseDouble(longitude);
                sendMessage(fuser.getUid(),userid,locat);


            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Toast.makeText(this, lattitude +","+ longitude, Toast.LENGTH_SHORT).show();
                String locat1 = GetExactLocation(Double.parseDouble(lattitude), Double.parseDouble(longitude));
                String locat = "http://maps.google.com/maps?"+locat1;
                //String locat = "http://maps.google.com/maps?"+Double.parseDouble(lattitude)+","+Double.parseDouble(longitude);
                sendMessage(fuser.getUid(),userid,locat);



            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Toast.makeText(this, lattitude +","+ longitude, Toast.LENGTH_SHORT).show();
                String locat1 = GetExactLocation(Double.parseDouble(lattitude), Double.parseDouble(longitude));
                String locat = "http://maps.google.com/maps?"+locat1;
                //String locat = "http://maps.google.com/maps?"+Double.parseDouble(lattitude)+","+Double.parseDouble(longitude);
                sendMessage(fuser.getUid(),userid,locat);

            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();


            }
        }
    }
    private void locked() {
        stopTrackingAction = true;
        stopRecording(RecordingBehaviour.LOCKED);
        isLocked = true;
//        Animator animator = ViewAnimationUtils.createCircularReveal()
    }

    private void canceled() {
        stopTrackingAction = true;
        stopRecording(RecordingBehaviour.CANCELED);
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public String GetExactLocation(double lati, double longi)
    {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lati, longi, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Toast.makeText(this,"http://maps.google.com/maps?"+strAdd, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Error ", Toast.LENGTH_LONG).show();
                //Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            //Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    public void calluser(String userid)
    {
        Toast.makeText(this, userid, Toast.LENGTH_SHORT).show();
        call =sinchClient.getCallClient().callUser(userid);
        //call.addCallListener(new SinchCallListener());
        openCallerDialog();
    }
    private void openCallerDialog() {
        AlertDialog alertDialog=new AlertDialog.Builder(MessagingActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Calling");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Hang up", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                call.hangup();
            }
        });
        alertDialog.show();
    }
//    private class SinchCallListener implements CallListener {
//        @Override
//        public void onCallProgressing(com.sinch.android.rtc.calling.Call call) {
//
//        }
//
//        @Override
//        public void onCallEstablished(com.sinch.android.rtc.calling.Call call) {
//            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
//        }
//
//        @Override
//        public void onCallEnded(com.sinch.android.rtc.calling.Call endedCall) {
//            call = null;
//            SinchError a = endedCall.getDetails().getError();
//           /* btn.setText("Call");*/
//            /*callState.setText("");*/
//            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
//        }
//
//        @Override
//        public void onShouldSendPushNotification(com.sinch.android.rtc.calling.Call call, List<PushPair> list) {
//
//        }
//    }
//    private class SinchCallClientListener implements CallClientListener {
//
//        @Override
//        public void onIncomingCall(CallClient callClient, com.sinch.android.rtc.calling.Call incomingCall) {
//            call = incomingCall;
//            Toast.makeText(MessagingActivity.this, "incoming call", Toast.LENGTH_SHORT).show();
//            call.answer();
//            call.addCallListener(new SinchCallListener());
//           /* btn.setText("Hang Up");*/
//        }
//    }
    private void seenMessage(final String userid)
    {
        reference=FirebaseDatabase.getInstance().getReference("Chats");

        seeListener=reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Chat chat=snapshot.getValue(Chat.class);
                    if (chat.getReciver().equals(fuser.getUid())&&chat.getSender().equals(userid))
                    {
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("isseen",true);
                        snapshot.getRef().updateChildren(hashMap);

                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = MessagingActivity.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(MessagingActivity.this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null){
            final  StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  task.getException();
                    }

                    return  fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        DateFormat df = new SimpleDateFormat("HH:mm");
                        Calendar calobj = Calendar.getInstance();
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("sender",fuser.getUid());
                        hashMap.put("reciver",userid);
                        hashMap.put("image",mUri);
                        hashMap.put("video","default");
                        hashMap.put("voice","default");
                        hashMap.put("message","default");
                        hashMap.put("isseen",false);
                        hashMap.put("timestamp",df.format(calobj.getTime()));
                        reference.child("Chats").push().setValue(hashMap);

                        pd.dismiss();
                    } else {
                        Toast.makeText(MessagingActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MessagingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(MessagingActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void openVideo() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent1.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(intent1,RC_VIDEO_PICKER);
    }

    private String getFileExtension1(Uri uri){
        ContentResolver contentResolver = MessagingActivity.this.getContentResolver();
        MimeTypeMap mimeTypeMap1 = MimeTypeMap.getSingleton();
        return mimeTypeMap1.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(MessagingActivity. this, "Upload in preogress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
       if (requestCode == RC_VIDEO_PICKER && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            videouri = data.getData();

            if (uploadTask1 != null && uploadTask1.isInProgress()){
                Toast.makeText(MessagingActivity. this, "Upload in preogress", Toast.LENGTH_SHORT).show();
            } else {
                uploadVideo();
            }
        }
    }
    private void uploadVideo(){
        final ProgressDialog pd = new ProgressDialog(MessagingActivity.this);
        pd.setMessage("Uploading");
        pd.show();

        if (videouri != null){
            final  StorageReference fileReference1 = videoreference.child(System.currentTimeMillis()
                    +"."+getFileExtension1(videouri));

            uploadTask1 = fileReference1.putFile(videouri);
            uploadTask1.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  task.getException();
                    }

                    return  fileReference1.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri1 = downloadUri.toString();
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("sender",fuser.getUid());
                        hashMap.put("reciver",userid);
                        hashMap.put("image","default");
                        hashMap.put("message","default");
                        hashMap.put("voice","default");
                        hashMap.put("video",mUri1);
                        hashMap.put("isseen",false);
                        reference.child("Chats").push().setValue(hashMap);

                        pd.dismiss();
                    } else {
                        Toast.makeText(MessagingActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MessagingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(MessagingActivity.this, "No video selected", Toast.LENGTH_SHORT).show();
        }
    }
   private void upload() {
       final ProgressDialog pd = new ProgressDialog(MessagingActivity.this);
       pd.setMessage("Uploading");
       pd.show();
       final StorageReference fileparth = mfirebaseVoice.child("Audio").child("new_audio " + System.currentTimeMillis() + " .3gp");
       voiceuri = Uri.fromFile(new File(mFileName));
       uploadTask2 = fileparth.putFile(voiceuri);
       uploadTask2.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
           @Override
           public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
               if (!task.isSuccessful()) {
                   Toast.makeText(MessagingActivity.this, task.toString(), Toast.LENGTH_SHORT).show();
                   throw task.getException();
               }

               return fileparth.getDownloadUrl();
           }
       }).addOnCompleteListener(
               new OnCompleteListener<Uri>() {
           @Override
           public void onComplete(@NonNull Task<Uri> task) {
               if (task.isSuccessful()) {

                   Uri downloadUri = task.getResult();
                   String mUri2 = downloadUri.toString();
                   DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                   HashMap<String, Object> hashMap = new HashMap<>();
                   hashMap.put("sender", fuser.getUid());
                   hashMap.put("reciver", userid);
                   hashMap.put("image", "default");
                   hashMap.put("message", "default");
                   hashMap.put("video", "default");
                   hashMap.put("voice", mUri2);
                   hashMap.put("isseen", false);
                   reference.child("Chats").push().setValue(hashMap);

                   pd.dismiss();
               } else {
                   Toast.makeText(MessagingActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                   pd.dismiss();
               }
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(MessagingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               pd.dismiss();
           }
       });
   }
    private void startRecording() {

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        if (recordingListener != null)
            recordingListener.onRecordingStarted();

        stopTrackingAction = false;
        layoutMessage.setVisibility(View.GONE);
        select_item.setVisibility(View.INVISIBLE);
        btn_voice.animate().scaleXBy(1f).scaleYBy(1f).setDuration(200).setInterpolator(new OvershootInterpolator()).start();
        timeText.setVisibility(View.VISIBLE);
        layoutLock.setVisibility(View.VISIBLE);
        layoutSlideCancel.setVisibility(View.VISIBLE);
        imageViewMic.setVisibility(View.VISIBLE);
        timeText.startAnimation(animBlink);
        imageViewLockArrow.clearAnimation();
        imageViewLock.clearAnimation();
        imageViewLockArrow.startAnimation(animJumpFast);
        imageViewLock.startAnimation(animJump);


        try {
            mRecorder.prepare();
        } catch (IOException e) {
        }

        mRecorder.start();

        if (audioTimer == null) {
            audioTimer = new Timer();
            timeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        timeText.setText(timeFormatter.format(new Date(audioTotalTime * 1000)));
                        audioTotalTime++;
                    }
                });
            }
        };

        audioTotalTime = 0;
        audioTimer.schedule(timerTask, 0, 1000);
    }





    private void stopRecording(RecordingBehaviour recordingBehaviour) {

        stopTrackingAction = true;
        firstX = 0;
        firstY = 0;
        lastX = 0;
        lastY = 0;

        userBehaviour = UserBehaviour.NONE;

        btn_voice.animate().scaleX(1f).scaleY(1f).translationX(0).translationY(0).setDuration(100).setInterpolator(new LinearInterpolator()).start();
        layoutSlideCancel.setTranslationX(0);
        layoutSlideCancel.setVisibility(View.GONE);

        layoutLock.setVisibility(View.GONE);
        layoutLock.setTranslationY(0);
        imageViewLockArrow.clearAnimation();
        imageViewLock.clearAnimation();

        if (isLocked) {
            return;
        }

        if (recordingBehaviour == RecordingBehaviour.LOCKED) {
            imageViewStop.setVisibility(View.VISIBLE);

            if (recordingListener != null)
                recordingListener.onRecordingLocked();

        } else if (recordingBehaviour == RecordingBehaviour.CANCELED) {
            timeText.clearAnimation();
            timeText.setVisibility(View.INVISIBLE);
            imageViewMic.setVisibility(View.INVISIBLE);
            imageViewStop.setVisibility(View.GONE);

            timerTask.cancel();
            delete();

            if (recordingListener != null)
                recordingListener.onRecordingCanceled();

        } else if (recordingBehaviour == RecordingBehaviour.RELEASED || recordingBehaviour == RecordingBehaviour.LOCK_DONE) {
            timeText.clearAnimation();
            timeText.setVisibility(View.INVISIBLE);
            imageViewMic.setVisibility(View.INVISIBLE);
            layoutMessage.setVisibility(View.VISIBLE);
            select_item.setVisibility(View.VISIBLE);
            imageViewStop.setVisibility(View.GONE);
            try {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                upload();
            }
            catch (Exception e){
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            timerTask.cancel();

            if (recordingListener != null)
                recordingListener.onRecordingCompleted();
        }

    }
//    private void stopRecording() {
//
//
//
//
///        mRecorder.stop();
////        mRecorder.release();
////        mRecorder = null;
//        upload();
//    }



    private void sendMessage(String sender, final String reciver, String message)
    {
        DateFormat df = new SimpleDateFormat("HH:mm");
        Calendar calobj = Calendar.getInstance();
        /*System.out.println(df.format(calobj.getTime()));*/
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("reciver",reciver);
        hashMap.put("message",message);
        hashMap.put("image","default");
        hashMap.put("video","default");
        hashMap.put("voice","default");
        hashMap.put("timestamp",df.format(calobj.getTime()));
        hashMap.put("isseen",false);
        reference.child("Chats").push().setValue(hashMap);
        final DatabaseReference chatref=FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(fuser.getUid()).child(userid);
        chatref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists())
                {
                    chatref.child("userid").setValue(userid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final String msg = message;

        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (notify) {
                    sendNotifiaction(reciver, user.getUser(), msg);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void sendNotifiaction(String receiver, final String username, final String message){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(fuser.getUid(), R.mipmap.ic_launcher, username+": "+message, "New Message",
                            userid);

                    Sender sender = new Sender(data,token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponce>() {
                                @Override
                                public void onResponse(Call<MyResponce> call, Response<MyResponce> response) {
                                    if (response.code() == 200){
                                        if (response.body().success != 1){
                                            Toast.makeText(MessagingActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponce> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMessage(final String id, final String userid, final String photo)
{
    mchat=new ArrayList<>();
    reference=FirebaseDatabase.getInstance().getReference("Chats");
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           mchat.clear();
            for(DataSnapshot snapshot:dataSnapshot.getChildren())
            {
                Chat chat=snapshot.getValue(Chat.class);
                if (chat.getReciver().equals(id)&&chat.getSender().equals(userid) ||
                        chat.getReciver().equals(userid) && chat.getSender().equals(id))
                {
                    mchat.add(chat);
                }
                messageAdapter = new MessageAdapter(MessagingActivity.this,mchat,photo);
                recyclerView.setAdapter(messageAdapter);
                messageAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}

private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
     /*   currentUser(userid);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seeListener);
        status("Offline");
        /*currentUser("none");*/
    }


    public interface RecordingListener {

        void onRecordingStarted();

        void onRecordingLocked();

        void onRecordingCompleted();

        void onRecordingCanceled();

    }

    private void delete() {
        imageViewMic.setVisibility(View.VISIBLE);
        imageViewMic.setRotation(0);
        isDeleting = true;
        btn_voice.setEnabled(false);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isDeleting = false;
                btn_voice.setEnabled(true);
                select_item.setVisibility(View.VISIBLE);
            }
        }, 1250);

        imageViewMic.animate().translationY(-dp * 150).rotation(180).scaleXBy(0.6f).scaleYBy(0.6f).setDuration(500).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                dustin.setTranslationX(-dp * 40);
                dustin_cover.setTranslationX(-dp * 40);

                dustin_cover.animate().translationX(0).rotation(-120).setDuration(350).setInterpolator(new DecelerateInterpolator()).start();

                dustin.animate().translationX(0).setDuration(350).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        dustin.setVisibility(View.VISIBLE);
                        dustin_cover.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageViewMic.animate().translationY(0).scaleX(1).scaleY(1).setDuration(350).setInterpolator(new LinearInterpolator()).setListener(
                        new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                imageViewMic.setVisibility(View.INVISIBLE);
                                imageViewMic.setRotation(0);

                                dustin_cover.animate().rotation(0).setDuration(150).setStartDelay(50).start();
                                dustin.animate().translationX(-dp * 40).setDuration(200).setStartDelay(250).setInterpolator(new DecelerateInterpolator()).start();
                                dustin_cover.animate().translationX(-dp * 40).setDuration(200).setStartDelay(250).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        layoutMessage.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                }).start();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }
                ).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

}
