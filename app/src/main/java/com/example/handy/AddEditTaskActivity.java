package com.example.handy;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEditTaskActivity extends AppCompatActivity {
    EditText edtTitle, edtDescription, edtDueDate;
    Button btnSaveTask;
    dataBase database;
    int taskId = -1;

    Calendar dueCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        edtDueDate = findViewById(R.id.edtDueDate);
        btnSaveTask = findViewById(R.id.btnSaveTask);
        database = new dataBase(this);

        // existing task or not !
        if(getIntent().hasExtra("task_id")) {
            taskId = getIntent().getIntExtra("task_id", -1);
            edtTitle.setText(getIntent().getStringExtra("task_title"));
            edtDescription.setText(getIntent().getStringExtra("task_desc"));
            edtDueDate.setText(getIntent().getStringExtra("task_due"));

        }

        // no manual input !!
        edtDueDate.setFocusable(false);
        edtDueDate.setClickable(true);
        edtDueDate.setOnClickListener(v -> openDateTimePicker());

        btnSaveTask.setOnClickListener(v -> {
            String title = edtTitle.getText().toString();
            String description = edtDescription.getText().toString();
            String dueDate = edtDueDate.getText().toString();
            String status = "Pending";

            if (title.isEmpty() || description.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(AddEditTaskActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
                }

            if (taskId == -1) {
                boolean inserted = database.addTask(title, description, dueDate, status);
                if (inserted) {
                    scheduleNotification(title, dueDate);
                    Toast.makeText(AddEditTaskActivity.this, "Task Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddEditTaskActivity.this, "Failed to add task", Toast.LENGTH_SHORT).show();
                }
            } else {
                boolean updated = database.updateTask(taskId, title, description, dueDate, status);
                if (updated) {
                    scheduleNotification(title, dueDate);
                    Toast.makeText(AddEditTaskActivity.this, "Task Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddEditTaskActivity.this, "Failed to update task", Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        });
    }

    private void openDateTimePicker() {
        // First open DatePickerDialog
        Calendar currentCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddEditTaskActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dueCalendar.set(Calendar.YEAR, year);
                        dueCalendar.set(Calendar.MONTH, month);
                        dueCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        // After date is picked, open TimePickerDialog
                        openTimePicker();
                    }
                },
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void openTimePicker() {
        Calendar currentCalendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                AddEditTaskActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dueCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        dueCalendar.set(Calendar.MINUTE, minute);
                        // Format the selected date and time and set it to the EditText
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                        edtDueDate.setText(sdf.format(dueCalendar.getTime()));
                    }
                },
                currentCalendar.get(Calendar.HOUR_OF_DAY),
                currentCalendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    @SuppressLint("ScheduleExactAlarm")
    private void scheduleNotification(String taskTitle, String dueDate) {
        try {
            Intent intent = new Intent(this, ReminderReceiver.class);
            intent.putExtra("task_title", taskTitle);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, dueCalendar.getTimeInMillis(), pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
