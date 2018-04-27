package com.pronoy.mukhe.todoapplication.Acitvities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.pronoy.mukhe.todoapplication.Adapters.TodoAdapter;
import com.pronoy.mukhe.todoapplication.Helper.Constants;
import com.pronoy.mukhe.todoapplication.Helper.Messages;
import com.pronoy.mukhe.todoapplication.Objects.Todo;
import com.pronoy.mukhe.todoapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity {
    ListView todoList;
    FloatingActionButton floatingActionButton;
    private static final String TAG_CLASS=TodoActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        initializeViews();
    }
    private void initializeViews(){
        todoList=findViewById(R.id.todoList);
        floatingActionButton=findViewById(R.id.addTodo);
    }
    private void getAllData(){
        JSONArray todoArray= Constants.databaseController.getAllTodo();
        ArrayList<Todo> arrayList=new ArrayList<>();
        Todo todo;
        for (int i = 0; i < todoArray.length(); i++) {
            try{
                JSONObject todoObject=todoArray.getJSONObject(i);
                todo=new Todo(todoObject.getString(Constants.TODO_TABLE_TITLE),
                        todoObject.getString(Constants.TODO_TABLE_DESC),
                        todoObject.getString(Constants.CATEGORY_TABLE_DESC),
                        todoObject.getInt(Constants.TODO_TABLE_PRIOROTY),
                        todoObject.getInt(Constants.TODO_TABLE_ID),
                        todoObject.getInt(Constants.TODO_TABLE_TIME_MILIS));
                arrayList.add(todo);

            }catch (JSONException e){
                Messages.logMessage(TAG_CLASS,e.toString());
            }
        }
        TodoAdapter adapter=new TodoAdapter(TodoActivity.this,arrayList);
        todoList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
