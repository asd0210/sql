package com.example.android.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactDbOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "debug";

    static final String DATABASE_NAME = "contact.db";
    static final int DATABASE_VERSION = 1;

    public ContactDbOpenHelper(Context context) {
        // データベースファイル名とバージョンを指定しSQLiteOpenHelperクラスを初期化
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "ContactDbOpenHelperのコンストラクタ");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d(TAG, "ContactDbOpenHelper.onCreate");
        // Contactテーブルを生成
        // @formatter:off
	database.execSQL("CREATE TABLE " 
        + Contact.TBNAME + "(" 
        + Contact._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
        + Contact.NAME + " TEXT NOT NULL, " 
        + Contact.AGE + " INTEGER " + ");");
	// @formatter:on
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "ContactDbOpenHelper.onUpgrade");
        // Contactテーブルを再定義するため現在のテーブルを削除
        db.execSQL("DROP TABLE IF EXISTS " + Contact.TBNAME);
        onCreate(db);
    }

}