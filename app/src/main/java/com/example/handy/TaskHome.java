package com.example.handy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskHome extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapter adapter;
    ArrayList<Tasks> taskList = new ArrayList<>();
    dataBase database;
    Button btnAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_home);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnAddTask = findViewById(R.id.btnAddTask);
        database = new dataBase(this);

        btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(TaskHome.this, TaskActions.class);
            startActivity(intent);
        });

        loadTasks();
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadTasks();
    }

    private void loadTasks() {
        taskList.clear();
        Cursor cursor = database.getAllTasks();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(dataBase.tasks_column_id));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(dataBase.tasks_column_title));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(dataBase.tasks_column_desc));
                @SuppressLint("Range") String dueDate = cursor.getString(cursor.getColumnIndex(dataBase.tasks_column_due));
                @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex(dataBase.tasks_column_status));
                taskList.add(new Tasks(id, title, description, dueDate, status));
            } while (cursor.moveToNext());
            cursor.close();
        }
        adapter = new MyAdapter(this, taskList);
        recyclerView.setAdapter(adapter);
    }
}
