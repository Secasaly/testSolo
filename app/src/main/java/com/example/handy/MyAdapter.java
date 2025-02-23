package com.example.handy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MySingleViewHolder> {
    Context context;
    List<Tasks> items;

    public MyAdapter(Context context, List<Tasks> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MySingleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new MySingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySingleViewHolder holder, int position) {
        Tasks task = items.get(position);
        holder.txtTitle.setText(task.getTitle());
        holder.txtDue.setText(task.getDueDate());
        holder.txtStatus.setText(task.getStatus());

        // Whole item click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditTaskActivity.class);
            intent.putExtra("task_id", task.getId());
            intent.putExtra("task_title", task.getTitle());
            intent.putExtra("task_desc", task.getDescription());
            intent.putExtra("task_due", task.getDueDate());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
