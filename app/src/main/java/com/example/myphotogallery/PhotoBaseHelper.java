package com.example.myphotogallery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PhotoBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="photos.db";
    public PhotoBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table "+ PhotoDBSchema.PhotoTable.TABLE_NAME+"("+
                "_id integer primary key autoincrement,"+ PhotoDBSchema.PhotoTable.PhotoTableCols.EMAIL+
                ","+ PhotoDBSchema.PhotoTable.PhotoTableCols.USER_NAME+
                ","+ PhotoDBSchema.PhotoTable.PhotoTableCols.PASSWORD+")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
