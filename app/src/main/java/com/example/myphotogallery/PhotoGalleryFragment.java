package com.example.myphotogallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryFragment extends Fragment {
    private static final String TAG="photogalleryfragment";
    private List<GalleryItem> mItems=new ArrayList<>();
    private RecyclerView mPhotoRecyclerView;
    private ThumbnailDownloader<PhotoHolder> mthumbnailDownloader;
    public static PhotoGalleryFragment newInstance(){
        return new PhotoGalleryFragment();
    }
    private Drawable drawableImage;
    private String imageString;
    private PhotoHolder imageHolder;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        new FetchItemsTask().execute();
        Handler responseHandler=new Handler();

        mthumbnailDownloader=new ThumbnailDownloader<>(responseHandler);
        mthumbnailDownloader.setThumbnailDownloadListener(
                new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>(){
                    @Override
                    public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap bitmap){
                        drawableImage = new BitmapDrawable(getResources(),bitmap);
                        photoHolder.bindDrawable(drawableImage);
                    }
                }
        );
        mthumbnailDownloader.start();
        mthumbnailDownloader.getLooper();
        Log.i(TAG,"background task started");

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_photo_gallery,container,false);

        mPhotoRecyclerView=(RecyclerView) v.findViewById(R.id.fragment_photo_gallery_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        setupAdapter();
        return v;
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mthumbnailDownloader.clearQueue();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mthumbnailDownloader.quit();
        Log.i(TAG,"bg thread destroyed");
    }
    private void setupAdapter(){
        if(isAdded()){
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }
    private class FetchItemsTask extends AsyncTask<Void,Void,List<GalleryItem>>{
        @Override
        protected List<GalleryItem> doInBackground(Void... params){
//            try{
//                String result=new FlickerFetcher().getUrlString("https://m.timesofindia.com");
//                Log.i(TAG,"fetched");
//            }catch(IOException ioe){
//                Log.e(TAG,"failed to load");
//            }
            // now using api keys
            return new FlickerFetcher().fetchItems();

        }
        // for showing progress bar
//        @Override
//        protected void onProgressUpdate(Integer... progress){
//            setProgressPercent(progress[0]);
//        }
        @Override
        protected void onPostExecute(List<GalleryItem> items){
            mItems=items;
            setupAdapter();
        }
    }

    public class PhotoHolder extends RecyclerView.ViewHolder{
//        private TextView mTtileTextView;
        private ImageView mItemImageView;

        public PhotoHolder(View itemView){
            super(itemView);
//            mTtileTextView=(TextView) itemView;
            mItemImageView=(ImageView) itemView.findViewById(R.id.fragment_photo_gallery_image_view);

        }

        public void bindDrawable(Drawable drawable){
            mItemImageView.setImageDrawable(drawable);
        }
//        public void bindGalleryItem(GalleryItem item){
//            mTtileTextView.setText(item.toString());
//        }
    }
    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder>{
        private List<GalleryItem> mGalleryItems;
        private ImageView mBigImageView;
        public PhotoAdapter(List<GalleryItem> galleryItems){
            mGalleryItems=galleryItems;

        }

        @NonNull
        @Override
        public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            TextView textView=new TextView(getActivity());
//            return new PhotoHolder(textView);

            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.gallery_item,parent,false);
            mBigImageView=(ImageView) view.findViewById(R.id.fragment_photo_gallery_image_view);
            mBigImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm=getActivity().getSupportFragmentManager();
                    Fragment fragment=SingleImageFragment.newInstance(imageString);
                    fm.beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();
                }
            });
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
            GalleryItem galleryItem=mGalleryItems.get(position);
//            holder.bindGalleryItem(galleryItem);
            imageString=galleryItem.getUrl();

            Drawable placeHolder=getResources().getDrawable(R.drawable.bill_up_close);
            holder.bindDrawable(placeHolder);
            mthumbnailDownloader.queueThumbnail(holder,galleryItem.getUrl());
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }





}
