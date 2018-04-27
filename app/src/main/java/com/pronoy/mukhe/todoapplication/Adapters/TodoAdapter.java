package com.pronoy.mukhe.todoapplication.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.pronoy.mukhe.todoapplication.Objects.Todo;
import com.pronoy.mukhe.todoapplication.R;

import java.util.ArrayList;

/**
 * Created by mukhe on 27-Apr-18.
 * This is the TO_DO Adapter class.
 */

public class TodoAdapter extends ArrayAdapter {
    ArrayList<Todo> todoList;

    public TodoAdapter(Activity context, ArrayList<Todo> todoList) {
        super(context, 0, todoList);
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
            _time.setText(String.valueOf(todo.getDate()));
            _priority.setText(String.valueOf(todo.getPriority()));
            _category.setText(todo.getCategory());
            _deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    todoList.remove(todo);
                    //TODO: Delete the data from Database.
                    notifyDataSetChanged();
                }
            });
            _circle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _circle.setBackgroundColor(Color.parseColor(String.valueOf(R.color.complete)));
                }
            });
        }
        return todoItem;
    }
}
