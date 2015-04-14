package com.aidindonesia.project.sign.parsePushNotification;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by sagungw on 2/3/2015.
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        Parse.initialize(this, );
    }

}
