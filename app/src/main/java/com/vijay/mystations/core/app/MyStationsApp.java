package com.vijay.mystations.core.app;

import android.app.Application;

public class MyStationsApp extends Application {
    public String url = "";
    private static MyStationsApp myStationsApp;

    @Override
    public void onCreate() {
        super.onCreate();
        myStationsApp =this;
    }

    public static MyStationsApp getInstance(){
        return myStationsApp;
    }

}
