package com.pronoy.mukhe.todoapplication.Acitvities;

import android.app.Dialog;
import android.content.ContentValues;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ListView;

import com.pronoy.mukhe.todoapplication.Adapters.CategoryAdapter;
import com.pronoy.mukhe.todoapplication.Helper.Constants;
import com.pronoy.mukhe.todoapplication.Helper.Messages;
import com.pronoy.mukhe.todoapplication.Objects.Category;
import com.pronoy.mukhe.todoapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddCategoryActivity extends AppCompatActivity {
    ListView todoList;
    FloatingActionButton floatingActionButton;
    CategoryAdapter categoryAdapter;
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
                showAddCategoryDialog();
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
        categoryAdapter = new CategoryAdapter(AddCategoryActivity.this,
                categoryArrayList);
        todoList.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

    /**
     * This the method to add the category to the list.
     * @param category: The new Category.
     */
    private void addCategory(String category){
        ContentValues values=new ContentValues();
        values.put(Constants.CATEGORY_TABLE_DESC,category);
        if(Constants.databaseController.insertDataCategory(values)<0){
            Messages.toastMessage(getApplicationContext(),"Couldn't add Category.","");
            return;
        }
        Messages.toastMessage(getApplicationContext(),"Category Added.","");
        categoryAdapter.notifyDataSetChanged();
    }

    /**
     * This is the method to show the add category dialog.
     */
    private void showAddCategoryDialog(){
        final Dialog dialog=new Dialog(getApplicationContext());
        dialog.setContentView(R.layout.add_category_dialog);
        AppCompatButton saveButton=dialog.findViewById(R.id.addCategoryButton);
        AppCompatButton discardButton=dialog.findViewById(R.id.discardCategoryButton);
        final AppCompatEditText categoryText=dialog.findViewById(R.id.categoryDescEnter);
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategory(categoryText.getText().toString());
            }
        });
        dialog.show();
    }
}
