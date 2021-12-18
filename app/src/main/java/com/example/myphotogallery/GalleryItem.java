package com.example.myphotogallery;

public class GalleryItem {
    private String mUrl;
    private String mCaption;
    private String mId;
    public String getCaption() {
        return mCaption;
    }
    public void setCaption(String mCaption) {
        this.mCaption = mCaption;
    }
    public String getUrl() {
        return mUrl;
    }
    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }


    @Override
    public String toString(){
        return mCaption;
    }
}
