package com.example.myphotogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

public class SingleImageHandler<T> extends HandlerThread {
    private static final String TAG="SingleImageHandler";
    private static final int MESSAGE_DOWLOAD=0;
    private static String mUrl;
    private imageDownloadListener mImageDownloadListener;
    private Handler mResponseHandler,mRequestHandler;
    public interface imageDownloadListener{
        void onImageDownloaded(Bitmap bigImage);
    }
    public void onSetImageDownloadListener(imageDownloadListener listener){
        mImageDownloadListener=listener;
    }
    public SingleImageHandler(Handler responseHandler) {
        super(TAG);
        mResponseHandler=responseHandler;
    }
//    @Override
//    public void handleMessage(Message msg){
//        Log.d(TAG,"inside handle message");
//    }
    public static void getUrl(String gotUrl){
        mUrl=gotUrl;
    }
    @Override
    protected void onLooperPrepared(){
        mRequestHandler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==MESSAGE_DOWLOAD){
                    Log.d(TAG,"got url of one image: ");
                    handleRequest(mUrl);
                }
            }
        };
    }
    private void handleRequest(String url){
        if(url==null){
            return;
        }
        try{
            byte[] bitmapBytes=new FlickerFetcher().getURLBytes(url);
            final Bitmap bitmap= BitmapFactory.decodeByteArray(bitmapBytes,0,bitmapBytes.length);
            Log.i(TAG,"bitmap created of single image");

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    mImageDownloadListener.onImageDownloaded(bitmap);
                }
            });
        }catch(IOException ioe){
            Log.e(TAG,"error in downloading image");
        }

    }

}
