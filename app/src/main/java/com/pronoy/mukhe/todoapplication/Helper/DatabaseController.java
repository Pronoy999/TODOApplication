package com.pronoy.mukhe.todoapplication.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by mukhe on 26-Apr-18.
 * This is the class to store all the Database related methods.
 */

public class DatabaseController {
    public static final String TAG_CLASS = DatabaseController.class.getSimpleName();
    Helper helper;

    public DatabaseController(Context context) {
        helper = new Helper(context);
    }

    /**
     * This is the method to insert data to the Category table.
     *
     * @param values: The Values mapped with the category table column name.
     * @return id: The id after insertion. -1 if error.
     */
    public long insertDataCategory(ContentValues values) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        return sqLiteDatabase.insert(Constants.CATEGORY_TABLE_NAME, null, values);
    }

    /**
     * This is the method to insert the data to the to_do table.
     *
     * @param values: The values mapped with the column name of the TO_DO table.
     * @return JSON Object: The id after insertion. -1 if error.
     */
    public long insertDataTodo(ContentValues values) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        return sqLiteDatabase.insert(Constants.TODO_TABLE_NAME, null, values);
    }

    /**
     * This is the method to get all the TO_DO from the table.
     *
     * @return JSON Object: The JSON object mapped with the attribute name and the value.
     */
    public JSONArray getAllTodo() {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        String query = "SELECT * FROM " + Constants.TODO_TABLE_NAME + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        JSONArray todoArray = new JSONArray();
        try {
            while (cursor.moveToNext()) {
                JSONObject todoData = new JSONObject();
                todoData.put(Constants.TODO_TABLE_ID, cursor.getInt(0));
                todoData.put(Constants.TODO_TABLE_TITLE, cursor.getString(1));
                todoData.put(Constants.TODO_TABLE_DESC, cursor.getString(2));
                todoData.put(Constants.TODO_TABLE_PRIOROTY, cursor.getString(3));
                todoData.put(Constants.TODO_TABLE_CATEGORYID, cursor.getInt(4));
                todoData.put(Constants.TODO_TABLE_TIME_MILIS, cursor.getString(5));
                todoArray.put(todoData);
            }
        } catch (JSONException | CursorIndexOutOfBoundsException e) {
            Messages.logMessage(TAG_CLASS, e.toString());
        }
        cursor.close();
        return todoArray;
    }

    /**
     * This is the method to get the Category Desc by the ID.
     *
     * @param categoryID: The ID whose Category Desc is needed.
     * @return String: The Category Desc as a String.
     */
    public String getCategoryByID(int categoryID) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        String query = "SELECT " + Constants.CATEGORY_TABLE_DESC + " FROM " +
                Constants.CATEGORY_TABLE_NAME + " WHERE " + Constants.CATEGORY_TABLE_ID +
                "= " + String.valueOf(categoryID) + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        StringBuilder desc = new StringBuilder();
        try {
            while (cursor.moveToNext()) {
                desc.append(cursor.getString(0));
            }
        }catch (CursorIndexOutOfBoundsException e){
            Messages.logMessage(TAG_CLASS,e.toString());
        }
        cursor.close();
        return desc.toString();
    }

    /**
     * This is the method to get all the category.
     *
     * @return JSON Object: JSON Object containing all the category items.
     */
    public JSONArray getAllCategories() {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        String query = "SELECT * FROM " + Constants.CATEGORY_TABLE_NAME;
        JSONArray categoryArray = new JSONArray();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while (cursor.moveToNext()) {
            JSONObject category = new JSONObject();
            try {
                category.put(Constants.CATEGORY_TABLE_ID, cursor.getInt(0));
                category.put(Constants.CATEGORY_TABLE_DESC, cursor.getString(1));
            } catch (JSONException | CursorIndexOutOfBoundsException e) {
                Messages.logMessage(TAG_CLASS, e.toString());
            }
            categoryArray.put(category);
        }
        cursor.close();
        return categoryArray;
    }

    /**
     * This is the method to delete the category by ID.
     *
     * @param categoryID: The ID of the category which is to be deleted.
     */
    public void deleteCategoryByID(int categoryID) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        String query = "DELETE FROM " + Constants.CATEGORY_TABLE_NAME + " WHERE " +
                Constants.CATEGORY_TABLE_ID + "=" + String.valueOf(categoryID) + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToLast();
        Messages.logMessage(TAG_CLASS, "Deleted the Category. ");
        cursor.close();
    }

    /**
     * This is the method to delete the TO_DO from the database by Id.
     *
     * @param todoId: The Id of the TO_DO to be deleted.
     */
    public void deleteTodoById(int todoId) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        String query = "DELETE FROM " + Constants.TODO_TABLE_NAME + " WHERE " +
                Constants.TODO_TABLE_ID + "=" + String.valueOf(todoId) + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToLast();
        Messages.logMessage(TAG_CLASS, "Deleted TODO.");
        cursor.close();
    }

    /**
     * This is the method to get the Category ID by the name.
     *
     * @param category: The name of the category.
     * @return JSON Object: Containing the category Object which matched.
     */
    public JSONObject getCategoryID(String category) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        String query = "SELECT * FROM " + Constants.CATEGORY_TABLE_NAME + " WHERE " +
                Constants.CATEGORY_TABLE_DESC + "='" + category + "';";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        JSONObject categoryObject = new JSONObject();
        try {
            while (cursor.moveToNext()) {

                categoryObject.put(Constants.CATEGORY_TABLE_ID, cursor.getInt(0));
                categoryObject.put(Constants.CATEGORY_TABLE_DESC, cursor.getString(1));
            }
        } catch (JSONException e) {
            Messages.logMessage(TAG_CLASS, e.toString());
        }
        return categoryObject;
    }

    public static class Helper extends SQLiteOpenHelper {

        Helper(Context context) {
            super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
            Messages.logMessage(TAG_CLASS, "In Constructor.");
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(Constants.CREATE_CATEGORY_TABLE);
                sqLiteDatabase.execSQL(Constants.CREATE_TODO_TABLE);
            } catch (SQLException e) {
                Messages.logMessage(TAG_CLASS, e.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL(Constants.DROP_TABLE_CATEGORY);
                sqLiteDatabase.execSQL(Constants.DROP_TABLE_TODO);
                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                Messages.logMessage(TAG_CLASS, e.toString());
            }
        }
    }
}
