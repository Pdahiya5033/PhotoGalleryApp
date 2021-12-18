package com.example.myphotogallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class PhotoGallery extends SingleFragmentActivity {
    @Override
    public Fragment createFragment(){
        return PhotoGalleryFragment.newInstance();
    }
}
