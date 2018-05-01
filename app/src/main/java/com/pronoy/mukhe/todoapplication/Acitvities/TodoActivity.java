package com.pronoy.mukhe.todoapplication.Acitvities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import java.util.Date;

public class TodoActivity extends AppCompatActivity {

    ListView todoList;
    FloatingActionButton floatingActionButton;

    private static final String TAG_CLASS = TodoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        setTitle(getResources().getString(R.string.todoTitle));
        initializeViews();
        getAllData();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTodoIntent=new Intent(TodoActivity.this,AddTodoDialog.class);
                startActivityForResult(addTodoIntent,Constants.ADD_TODO_DIALOG_REQUEST_CODE);
            }
        });
    }

    private void initializeViews() {
        todoList = findViewById(R.id.todoList);
        floatingActionButton = findViewById(R.id.addTodo);
    }

    /**
     * This is the method to get all the TO_DO from the Database.
     */
    private void getAllData() {
        JSONArray todoArray = Constants.databaseController.getAllTodo();
        ArrayList<Todo> arrayList = new ArrayList<>();
        Todo todo;
        for (int i = 0; i < todoArray.length(); i++) {
            try {
                JSONObject todoObject = todoArray.getJSONObject(i);
                todo = new Todo(todoObject.getString(Constants.TODO_TABLE_TITLE),
                        todoObject.getString(Constants.TODO_TABLE_DESC),
                        todoObject.getString(Constants.TODO_TABLE_CATEGORYID),
                        todoObject.getInt(Constants.TODO_TABLE_PRIOROTY),
                        todoObject.getInt(Constants.TODO_TABLE_ID),
                        todoObject.getLong(Constants.TODO_TABLE_TIME_MILIS));
                arrayList.add(todo);

            } catch (JSONException e) {
                Messages.logMessage(TAG_CLASS, e.toString());
            }
        }
        TodoAdapter adapter = new TodoAdapter(TodoActivity.this, arrayList);
        todoList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Constants.ADD_TODO_DIALOG_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                getAllData();
                Messages.toastMessage(getApplicationContext(),"Added a TODO.","");
            }
            else{
                Messages.toastMessage(getApplicationContext(),"Didn't add.","");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.open_category,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.openCategory:
                Intent intent=new Intent(TodoActivity.this,CategoryActivity.class);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}