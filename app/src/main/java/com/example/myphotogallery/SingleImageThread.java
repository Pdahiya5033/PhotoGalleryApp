package com.example.myphotogallery;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class SingleImageThread extends Thread{
    private static final String TAG="SingleImageThread";
    public Looper looper;
    public Handler handler;
    private String urlImage;
    @Override
    public void run(){
//        Looper.prepare();
//        looper=Looper.myLooper();
//        handler=new SingleImageHandler();
//        Looper.loop();
//        Log.d(TAG,"end of run");

    }

}
