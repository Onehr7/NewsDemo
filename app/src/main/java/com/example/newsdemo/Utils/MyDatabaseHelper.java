//package com.example.newsdemo.Utils;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class MyDatabaseHelper extends SQLiteOpenHelper {
//
//    private Context mContext;
//    private SQLiteDatabase db;
//
//    //创建用户表
//    public static final String CREATE_USER = "create table User ("
//            + "id integer primary key autoincrement,"
//            + "name text,"
//            + "password text)";
//
//    //创建收藏表
//    public static final String CREATE_COLLECTION_NEWS = "create table Collection_News ("
//            + "id integer primary key autoincrement,"
//            + "news_title text,"
//            + "news_date text,"
//            + "news_author text,"
//            + "news_picurl text,"
//            + "news_url text)";
//
//
//    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//        mContext = context;
//        db = getReadableDatabase();
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(CREATE_USER);
//        sqLiteDatabase.execSQL(CREATE_COLLECTION_NEWS);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }
//
//    //插入数据
//    public void add(String name,String password){
//        db.execSQL("INSERT INTO user (name,password) VALUES(?,?)",new Object[]{name,password});
//    }
//}