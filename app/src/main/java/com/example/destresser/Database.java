package com.example.destresser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper{
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(name text, matricID text, email text, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
    }

    public int register(String name, String matricID, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE matricID=?", new String[]{matricID});
        if (cursor.getCount() > 0) {
            // User with the same matric number already exists
            return 1;
        } else {
            ContentValues cv = new ContentValues();
            cv.put("name", name);
            cv.put("matricID", matricID);
            cv.put("email", email);
            cv.put("password", password);
            db.insert("users", null, cv);
            return 0;
        }
    }


    public int login(String matricID, String password) {
        int result=0;
        String[] str = new String[2];
        str[0] = matricID;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from users where matricID=? and password=?",str);
        if(c.moveToFirst()){
            result=1;
        }
        return result;
    }
}

