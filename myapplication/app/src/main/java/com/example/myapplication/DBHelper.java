package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyQQ.db";
    private static final int DATABASE_VERSION = 1;

    // 用户表
    public static final String TABLE_USERS = "users";
    public static final String COL_ID = "id";
    public static final String COL_NICKNAME = "nickname";  // 昵称即账号
    public static final String COL_PASSWORD = "password";
    public static final String COL_SIGNATURE = "signature";
    public static final String COL_GENDER = "gender";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NICKNAME + " TEXT UNIQUE NOT NULL, " +
                    COL_PASSWORD + " TEXT NOT NULL, " +
                    COL_SIGNATURE + " TEXT, " +
                    COL_GENDER + " TEXT" + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // 检查账号是否已存在（用于注册去重）
    public boolean isAccountExists(String nickname) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_ID},
                COL_NICKNAME + "=?", new String[]{nickname},
                null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // 插入新用户（注册）
    public long insertUser(String nickname, String password, String signature, String gender) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NICKNAME, nickname);
        values.put(COL_PASSWORD, password);
        values.put(COL_SIGNATURE, signature);
        values.put(COL_GENDER, gender);
        return db.insert(TABLE_USERS, null, values);
    }

    // 登录验证：根据账号查询密码，返回密码字符串，不存在返回null
    public String getPasswordByAccount(String nickname) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_PASSWORD},
                COL_NICKNAME + "=?", new String[]{nickname},
                null, null, null);
        String password = null;
        if (cursor.moveToFirst()) {
            password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD));
        }
        cursor.close();
        return password;
    }
}
