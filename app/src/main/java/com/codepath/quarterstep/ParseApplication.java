package com.codepath.quarterstep;

import android.app.Application;

import com.codepath.quarterstep.models.Post;
import com.codepath.quarterstep.models.Song;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Song.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("enZHblDYQaWkE4smRmfZwLrO2BhSekGzLa35xdUr")
                .clientKey("vTkerXtvGuCMKIHLO1Cv5xRcoknDjqOAPlX260vk")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
