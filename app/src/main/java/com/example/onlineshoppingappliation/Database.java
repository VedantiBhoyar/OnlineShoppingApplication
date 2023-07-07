package com.example.onlineshoppingappliation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserManager.db";
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";


    // CREATE TABLE user(user_id,user_name,user_email,user_password);

    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " (" + COLUMN_USER_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT , " + COLUMN_USER_EMAIL + " TEXT , " + COLUMN_USER_PASSWORD + " TEXT)";

    private static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public Database(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public void addUser(UserPojo user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues c_values = new ContentValues();
        c_values.put(COLUMN_USER_NAME, user.getName());
        c_values.put(COLUMN_USER_EMAIL, user.getEmail());
        c_values.put(COLUMN_USER_PASSWORD, user.getPassword());
        database.insert(TABLE_USER, null, c_values);
        database.close();


    }

    // SELECT user_id from user WHERE user_email="xyz"
    public boolean checkEmail(String Email) {
        // array of columns to fetch
        String[] columns = {COLUMN_USER_ID};

        SQLiteDatabase database = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " =?";
        // selection argument
        String[] selectionArgs = {Email};

        // query user table with condition
        Cursor cursor = database.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int cursor_count = cursor.getCount();
        cursor.close();
        database.close();

        if (cursor_count > 0) {
            return true;
        } else {
            return false;
        }


    }

    public boolean checkUsernamePassword(String email, String Password) {
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + "=? AND " + COLUMN_USER_PASSWORD + "=?";

        String selectionArgs[] = {email, Password};

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int cursor_count = cursor.getCount();
        cursor.close();
        db.close();

        if (cursor_count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String userName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + "=?";
        String selectionArg[] = {email};

        Cursor cursor = db.query(TABLE_USER, null, selection, selectionArg, null, null, null);

        String user = null;
        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(COLUMN_USER_NAME);
            if (nameIndex >= 0)
                user = cursor.getString(nameIndex);
        }

        cursor.close();
        db.close();
        return user;
    }


}
