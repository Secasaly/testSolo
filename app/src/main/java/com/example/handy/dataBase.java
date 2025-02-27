package com.example.handy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dataBase extends SQLiteOpenHelper {
    private static final String db_name = "master";
    private static final int db_version = 1;

    // Users table
    private static final String users_table = "users";
    private static final String users_column_id = "id";
    private static final String users_column_username = "username";
    private static final String users_column_password = "password";
    private static final String users_column_email = "email";

    // Tasks table
    public static final String tasks_table = "tasks";
    public static final String tasks_column_id = "id";
    public static final String tasks_column_title = "title";
    public static final String tasks_column_desc = "description";
    public static final String tasks_column_due = "due_date";
    public static final String tasks_column_status = "status";

    public dataBase(Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + users_table + " (" +
                users_column_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                users_column_username + " TEXT UNIQUE, " +
                users_column_password + " TEXT, " +
                users_column_email + " TEXT UNIQUE);";

        String createTaskTable = "CREATE TABLE " + tasks_table + " (" +
                tasks_column_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                tasks_column_title + " TEXT, " +
                tasks_column_desc + " TEXT, " +
                tasks_column_due + " TEXT, " +
                tasks_column_status + " TEXT);";

        db.execSQL(createUserTable);
        db.execSQL(createTaskTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + users_table);
        db.execSQL("DROP TABLE IF EXISTS " + tasks_table);
        onCreate(db);
    }

    public boolean addUsers(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(users_column_username, username);
        values.put(users_column_password, password);
        values.put(users_column_email, email);
        long result = db.insert(users_table, null, values);
        db.close();
        return result != -1;
    }

    public boolean checkUsers(String usernameOrEmail, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + users_table +
                        " WHERE (" + users_column_username + " =? OR " + users_column_email + " =?) AND " + users_column_password + " =?",
                new String[]{usernameOrEmail, usernameOrEmail, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    public boolean addTask(String title, String description, String dueDate, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tasks_column_title, title);
        values.put(tasks_column_desc, description);
        values.put(tasks_column_due, dueDate);
        values.put(tasks_column_status, status);
        long result = db.insert(tasks_table, null, values);
        db.close();
        return result != -1;
    }

    public Cursor getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + tasks_table, null);
    }

    public boolean updateTask(int id, String title, String description, String dueDate, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tasks_column_title, title);
        values.put(tasks_column_desc, description);
        values.put(tasks_column_due, dueDate);
        values.put(tasks_column_status, status);
        int rows = db.update(tasks_table, values, tasks_column_id + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }

    public boolean deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(tasks_table, tasks_column_id + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }
}
