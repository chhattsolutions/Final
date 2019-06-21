package com.example.afinal.fragments;

import com.example.afinal.Notification.MyResponce;
import com.example.afinal.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Sajid on 1/26/2019.
 */

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAm2135KU:APA91bFsATeJ2ZafxX6e1MuCmn0HJlWjO4lCRhS3pJ2GbQ2VXQriVJwguRM3qXs94zAHCVO2uD352M5XsKHxox3sd6wEiOfdsSzY1Kln8Co7fEsRN-JKkwAwbnV7Z-T4CLTp-DJBtzeV"
            }
    )

    @POST("fcm/send")
    Call<MyResponce> sendNotification(@Body Sender body);
}
