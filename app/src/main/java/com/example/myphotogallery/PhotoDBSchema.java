package com.example.myphotogallery;

import android.provider.BaseColumns;

public class PhotoDBSchema implements BaseColumns {
    public static final class PhotoTable{
        public static final String TABLE_NAME="Photos";
        public static final class PhotoTableCols{
            public static final String EMAIL="Email";
            public static final String USER_NAME="UserName";
            public static final String PASSWORD="Password";
        }
    }
}
