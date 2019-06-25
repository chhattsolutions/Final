package com.example.afinal;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.afinal.connectionchecker.ConnectionDetector;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class login extends AppCompatActivity{
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    SignInButton signInButton;
    ProgressBar   loginProgress;
    FirebaseUser user;
    DatabaseReference ref;
    ImageView splash;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindLogo();
        cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnected()) {
            Snackbar.make(findViewById(android.R.id.content),"Check Internet Connection",Snackbar.LENGTH_INDEFINITE).show();
        }
        /*img=(ImageView) findViewById(R.id.splash);
        img.setImageResource(R.drawable.png);*/
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        loginProgress = (ProgressBar) findViewById(R.id.login_progress);
       /* signInButton.setVisibility(View.GONE);
        loginProgress.setVisibility(View.GONE);*/
        splash = (ImageView) findViewById(R.id.splash);
        splash.setImageResource(R.drawable.chattlogo1);
        user = mAuth.getCurrentUser();
        if (user != null) {
            signInButton.setVisibility(View.GONE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    while (!cd.isConnected())
                    {}
                    Intent mainIntent = new Intent(login.this, MainActivity.class);
                    login.this.startActivity(mainIntent);
                    login.this.finish();
                    /*if(cd.isConnected())
                    {
                        Intent mainIntent = new Intent(login.this, MainActivity.class);
                        login.this.startActivity(mainIntent);
                        login.this.finish();
                    }*/

                }
            }, SPLASH_DISPLAY_LENGTH);
            //startActivity(new Intent(login.this,Verification.class));
        }
       /* while (!cd.isConnected())
        {}*/

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });





    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                if (task.isSuccessful()) {

                    /*hread thread = new Thread()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                sleep(3000);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            finally {
                                String a = isSignedIn();
                                Toast.makeText(getApplicationContext(),a , Toast.LENGTH_LONG).show();

                                if (a == "true" )
                                {
                                    loginProgress.setVisibility(View.VISIBLE);
                                    signInButton.setVisibility(View.GONE);
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                   i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    finish();

                                }
                                else if(a == "false") {
                                    loginProgress.setVisibility(View.GONE);
                                    signInButton.setVisibility(View.VISIBLE);
                                    Intent o = new Intent(getApplicationContext(), Verification.class);
                                    o.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(o);
                                    finish();

                                }
                                finish();

                            }*/
                    // }
                    //};

                    // thread.start();
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                }
            } catch (ApiException e) {
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            user = mAuth.getCurrentUser();
                            assert user!=null;

                            String id=user.getUid();
                            final String email=account.getEmail();
                            final String user =account.getDisplayName();
                            final Uri photouri =account.getPhotoUrl();
                            final String photo=photouri.toString();
                            ref = FirebaseDatabase.getInstance().getReference("Users").child(id);
                            HashMap <String,String> hashMap=new HashMap<>();
                            hashMap.put("id",id);
                            hashMap.put("user",user);
                            hashMap.put("photo",photo);
                            hashMap.put("email",email);
                            hashMap.put("status","Offline");
                            ref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        //Toast.makeText(login.this, "you login", Toast.LENGTH_SHORT).show();
                                        /*oast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();*/
                                    }
                                }
                            });
                            loginProgress.setVisibility(View.GONE);
                            signInButton.setVisibility(View.VISIBLE);
                            Intent i = new Intent(getApplicationContext(), Verification.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();


                        }
                        else {
                            Toast.makeText(getApplicationContext(), "LOGIN FAILED", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private String isSignedIn() {
        if (GoogleSignIn.getLastSignedInAccount(this) != null)
        {
            return "true";
        }
        else
        {
            return "false";
        }
    }
    private void bindLogo() {
        // Start animating the image
        final ImageView splash = (ImageView) findViewById(R.id.splash);
        final AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
        animation1.setDuration(700);
        final AlphaAnimation animation2 = new AlphaAnimation(1.0f, 0.2f);
        animation2.setDuration(700);
        //animation1 AnimationListener
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                // start animation2 when animation1 ends (continue)
                splash.startAnimation(animation2);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

        //animation2 AnimationListener
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                // start animation1 when animation2 ends (repeat)
                splash.startAnimation(animation1);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

        splash.startAnimation(animation1);
    }


}
