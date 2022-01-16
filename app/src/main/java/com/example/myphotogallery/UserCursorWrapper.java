package com.example.myphotogallery;

import android.database.Cursor;
import android.database.CursorWrapper;

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor){
        super(cursor);
    }
    public UsersDataClass getUser(){
        String email=getString(getColumnIndex(PhotoDBSchema.PhotoTable.PhotoTableCols.EMAIL));
        String userName=getString(getColumnIndex(PhotoDBSchema.PhotoTable.PhotoTableCols.USER_NAME));
        String password=getString(getColumnIndex(PhotoDBSchema.PhotoTable.PhotoTableCols.PASSWORD));
        UsersDataClass user=new UsersDataClass(email);
        user.setUserName(userName);
        user.setPassword(password);

        return user;
    }
}
