package com.pronoy.mukhe.todoapplication.Acitvities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pronoy.mukhe.todoapplication.Adapters.CategoryAdapter;
import com.pronoy.mukhe.todoapplication.Helper.Constants;
import com.pronoy.mukhe.todoapplication.Helper.Messages;
import com.pronoy.mukhe.todoapplication.Objects.Category;
import com.pronoy.mukhe.todoapplication.Objects.Todo;
import com.pronoy.mukhe.todoapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddCategoryActivity extends AppCompatActivity {
    ListView todoList;
    FloatingActionButton floatingActionButton;
    private static final String TAG_CLASS = AddCategoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        initializeViews();
        getDataFromDatabase();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(Constants.ADD_TODO_DIALOG);
            }
        });
    }

    private void initializeViews() {
        todoList = findViewById(R.id.todoList);
        floatingActionButton = findViewById(R.id.addTodo);
    }

    /**
     * This is the method to get the data from the database and display it.
     */
    private void getDataFromDatabase() {
        JSONArray categories = Constants.databaseController.getAllCategories();
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        Category category;
        try {
            for (int i = 0; i < categories.length(); i++) {
                JSONObject jsonObject = categories.getJSONObject(i);
                category = new Category(jsonObject.getString(Constants.CATEGORY_TABLE_DESC),
                        jsonObject.getInt(Constants.CATEGORY_TABLE_ID));
                categoryArrayList.add(category);
            }
        } catch (JSONException e) {
            Messages.logMessage(TAG_CLASS, e.toString());
        }
        CategoryAdapter categoryAdapter = new CategoryAdapter(AddCategoryActivity.this,
                categoryArrayList);
        todoList.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        LayoutInflater layoutInflater = this.getLayoutInflater();
        return builder.create();
    }
}
