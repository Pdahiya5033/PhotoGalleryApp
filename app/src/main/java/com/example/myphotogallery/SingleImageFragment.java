package com.example.myphotogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.IOException;

public class SingleImageFragment extends Fragment {
    private static final String TAG = "SingleImageFragment";
    private ImageView mSingleImage;
    private static final int MESSAGE_DOWNLOAD=0;
    private static String imageUrl;
    private Drawable drawable;
//    private SingleImageThread looperThread=new SingleImageThread();
//    private Handler responseHandler;
    private SingleImageHandler imageHandler;
    public static SingleImageFragment newInstance(String url) {
        imageUrl = url;
        SingleImageHandler.getUrl(imageUrl);
        return new SingleImageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG,"inside onCreate");
        Handler responseHandler1=new Handler();
        imageHandler=new SingleImageHandler(responseHandler1);
        imageHandler.onSetImageDownloadListener(new SingleImageHandler.imageDownloadListener() {
            @Override
            public void onImageDownloaded(Bitmap bigImage) {
                drawable=new BitmapDrawable(getResources(),bigImage);
                mSingleImage.setImageDrawable(drawable);
            }
        });
        imageHandler.start();
        imageHandler.getLooper();
        Log.i(TAG,"bg of single started");
//        responseHandler=new Handler(getActivity().getMainLooper());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_single_image, container, false);
        Log.d(TAG,"Inside on create view");
        displayImage(view);

        return view;
    }
//    public void startThread(View view){
//        looperThread.looper.quit();
//    }



    public void displayImage(View view) {
        Log.d(TAG,"inside display image");

        mSingleImage = (ImageView) view.findViewById(R.id.single_image_view);
        mSingleImage.setImageDrawable(getResources().getDrawable(R.drawable.bill_up_close));
//        Log.d(TAG,"exiting display image");
//        mSingleImageDownloader.queueThumbnail(mPhotoHolder,imageUrl);

        Log.d(TAG,"got url for one image: "+imageUrl);
//        startThread(view);
    }

//    public void bindImage(PhotoGalleryFragment.PhotoHolder holder, Drawable drawable) {
//        mSingleImage.setImageDrawable(drawable);
//        Log.d(TAG,"inside bingImage");
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "in destroy of single image");
    }
}