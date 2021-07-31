package com.codepath.quarterstep;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("enZHblDYQaWkE4smRmfZwLrO2BhSekGzLa35xdUr")
                .clientKey("vTkerXtvGuCMKIHLO1Cv5xRcoknDjqOAPlX260vk")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
