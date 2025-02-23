package com.example.handy;

public class Tasks {
    int id;
    String title;
    String description;
    String dueDate;
    String status;

    public Tasks(int id, String title, String description, String dueDate, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getDueDate() {
        return dueDate;
    }
    public String getStatus() {
        return status;
    }
}
