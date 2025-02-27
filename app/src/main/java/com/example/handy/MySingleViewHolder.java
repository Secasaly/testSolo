package com.example.handy;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MySingleViewHolder extends RecyclerView.ViewHolder {
    TextView txtTitle, txtDue, txtStatus;
    Button btnDeleteTask;

    public MySingleViewHolder(@NonNull View itemView) {
        super(itemView);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtDue = itemView.findViewById(R.id.txtDue);
        txtStatus = itemView.findViewById(R.id.txtStatus);
        btnDeleteTask = itemView.findViewById(R.id.btnDeleteTask);
    }
}
