package com.example.testsolo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import androidx.annotation.Nullable;

public class appDB extends SQLiteOpenHelper {
    private static final String dbname="userdb";
    private static final String db_table="users";
    private static final String column_username="username";
    private static final String column_password="password";
    private static final String column_email="email";
    private static final String column_fname="fname";
    private static final String column_lname="lname";
    private static final int version=1;

    public appDB(@Nullable Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creat_table="CREATE TABLE "+db_table+" ( ID INTEGER PRIMARY KEY AUTOINCREMENT, " + column_username + " TEXT, " + column_password + " TEXT, " + column_email + " TEXT, " + column_fname + " TEXT, " + column_lname + " TEXT );";
        db.execSQL(creat_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + db_table );
        onCreate(db);

    }
    public boolean insertUser (String username, String password, String email, String fname, String lname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(column_username, username);
        values.put(column_password, password);
        values.put(column_email, email);
        values.put(column_fname, fname);
        values.put(column_lname, lname);
        long result = db.insert(db_table, null, values);
        db.close();
        return result != -1 ;
    }
    public boolean checkUser(String usernameORemail, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+  db_table +" where ( " + column_username + " =? OR " + column_email + " =?) AND " + column_password + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{usernameORemail, usernameORemail, password});
        boolean exists = (cursor.getCount()>0);
        cursor.close();
        db.close();
        return exists;

    }
}
