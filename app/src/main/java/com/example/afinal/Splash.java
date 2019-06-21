package com.example.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {


    FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    //private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


            Thread thread = new Thread()
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


                        /*startActivity(new Intent(Splash.this ,MainActivity.class));
                        finish();*/

                        boolean a = isSignedIn();
                        Intent mapTab;

                        if (a == true )
                        {
                            mapTab = new Intent(Splash.this ,login.class);

                        }
                        else {
                            mapTab = new Intent(Splash.this ,MainActivity.class);

                        }

                       // Intent loginTab = new Intent(getApplicationContext() ,LoginActivity.class);
                        startActivity(mapTab);
                        finish();
                    }
                }
            };

            thread.start();
        }


        private boolean isSignedIn() {
            if (GoogleSignIn.getLastSignedInAccount(this) != null)
            {

                return true;
            }
            else
            {
                return false;
            }
        }
    }




