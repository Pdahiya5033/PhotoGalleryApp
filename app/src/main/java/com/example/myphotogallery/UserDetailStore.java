package com.example.myphotogallery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class UserDetailStore {
    private static UserDetailStore mUserDetailStore;
    private Context mContext;
    private SQLiteDatabase mDatabase;
//    private List<UsersDataClass> mUserDataClass;
    public static UserDetailStore get(Context context){
        if(mUserDetailStore==null){
            return new UserDetailStore(context);
        }
        return mUserDetailStore;
    }
    private UserDetailStore(Context context){
        mContext=context.getApplicationContext();
        mDatabase=new PhotoBaseHelper(mContext).getWritableDatabase();
//        mUserDataClass=new ArrayList<>();
        
    }
    public void addUser(UsersDataClass user){
        ContentValues values=getContentValues(user);
        mDatabase.insert(PhotoDBSchema.PhotoTable.TABLE_NAME,null,values);
    }
    public List<UsersDataClass> getUsers(){
        List<UsersDataClass> users=new ArrayList<>();
        UserCursorWrapper cursor=queryUsers(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                users.add(cursor.getUser());
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }
        return users;
    }
    public UsersDataClass getUser(String userName){
        UserCursorWrapper cursor=queryUsers(PhotoDBSchema.PhotoTable.PhotoTableCols.USER_NAME+"=?",
                new String[]{userName});
        try{
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToNext();
            return cursor.getUser();
        }finally{
            cursor.close();
        }
    }
    // use of where clause
    public void updateUser(UsersDataClass user){
        String email=user.getEmail();
        ContentValues values=getContentValues(user);
        mDatabase.update(PhotoDBSchema.PhotoTable.TABLE_NAME,values,
                PhotoDBSchema.PhotoTable.PhotoTableCols.EMAIL+"=?",new String[]{email});
    }
    private static ContentValues getContentValues(UsersDataClass user){
        ContentValues values=new ContentValues();
        values.put(PhotoDBSchema.PhotoTable.PhotoTableCols.EMAIL,user.getEmail());
        values.put(PhotoDBSchema.PhotoTable.PhotoTableCols.USER_NAME,user.getUserName());
        values.put(PhotoDBSchema.PhotoTable.PhotoTableCols.PASSWORD,user.getPassword());
        return values;
    }
    // how to pass one column in columns tag
    private UserCursorWrapper queryUsers(String whereClause,String[] whereArgs){
        Cursor cursor=mDatabase.query(PhotoDBSchema.PhotoTable.TABLE_NAME,
                null,whereClause,whereArgs,
                null,null,null);
        return new UserCursorWrapper(cursor);
    }
}


