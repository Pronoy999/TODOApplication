package com.pronoy.mukhe.todoapplication.Adapters;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.pronoy.mukhe.todoapplication.Acitvities.AddTodoDialog;
import com.pronoy.mukhe.todoapplication.Helper.AlarmReceiver;
import com.pronoy.mukhe.todoapplication.Helper.Constants;
import com.pronoy.mukhe.todoapplication.Helper.Messages;
import com.pronoy.mukhe.todoapplication.Objects.Todo;
import com.pronoy.mukhe.todoapplication.R;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by mukhe on 27-Apr-18.
 * This is the TO_DO Adapter class.
 */

public class TodoAdapter extends ArrayAdapter {
    private ArrayList<Todo> todoList;
    Context context;
    public TodoAdapter(Activity context, ArrayList<Todo> todoList) {
        super(context, 0, todoList);
        this.context=context;
        this.todoList=todoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View todoItem = convertView;
        if (todoItem == null) {
            todoItem = LayoutInflater.from(getContext()).inflate(R.layout.todo_item,
                    parent,
                    false);
            AppCompatTextView _title = todoItem.findViewById(R.id.todoTitle);
            AppCompatTextView _desc = todoItem.findViewById(R.id.todoDesc);
            AppCompatTextView _time = todoItem.findViewById(R.id.todoDateTime);
            AppCompatTextView _category = todoItem.findViewById(R.id.categoryTodo);
            AppCompatTextView _priority = todoItem.findViewById(R.id.priority);
            AppCompatImageView _deleteButton = todoItem.findViewById(R.id.deleteTodo);
            final AppCompatImageView _circle = todoItem.findViewById(R.id.circleTodo);
            final Todo todo = todoList.get(position);
            _title.setText(todo.getTitle());
            _desc.setText(todo.getDesc());
            String date=String.valueOf(todo.getDate());
            String parts[]=date.split(":");
            String newDate=parts[0]+":"+parts[1];
            _time.setText(newDate);
            _priority.setText(String.valueOf(todo.getPriority()));
            _category.setText(todo.getCategory());
            _deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    todoList.remove(todo);
                    Constants.databaseController.deleteTodoById(todo.getId());
                    notifyDataSetChanged();
                    Calendar calendar=Calendar.getInstance();
                    calendar.setTime(todo.getDate());
                    cancelAlarm(todo.getTitle(),todo.getDesc(),calendar.getTimeInMillis());
                    Messages.snackbar(view,todo.getTitle()+" deleted.","");
                }
            });
            _circle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _circle.setBackgroundColor(Color.GREEN);
                    todoList.remove(todo);
                    Constants.databaseController.deleteTodoById(todo.getId());
                    notifyDataSetChanged();
                }
            });
        }
        return todoItem;
    }
    private void cancelAlarm(String title, String desc, long reminderTime) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                Constants.NOTIFICATION_CHANNEL_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
