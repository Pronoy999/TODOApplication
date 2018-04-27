package com.pronoy.mukhe.todoapplication.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
     * @return: The id after insertion. -1 if error.
     */
    public long inserDataCategory(ContentValues values) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        return sqLiteDatabase.insert(Constants.CATEGORY_TABLE_NAME, null, values);
    }

    /**
     * This is the method to insert the data to the to_do table.
     *
     * @param values: The values mapped with the column name of the TO_DO table.
     * @return: The id after insertion. -1 if error.
     */
    public long insertDataTodo(ContentValues values) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        return sqLiteDatabase.insert(Constants.TODO_TABLE_NAME, null, values);
    }

    /**
     * This is the method to get all the TO_DO from the table.
     *
     * @return: The JSON object mapped with the attribute name and the value.
     */
    public JSONObject getAllTodo() {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        String query = "SELECT * FROM " + Constants.TODO_TABLE_NAME + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        JSONObject todoData = new JSONObject();
        try {
            while (!cursor.moveToNext()) {
                todoData.put(Constants.TODO_TABLE_TITLE, cursor.getString(1));
                todoData.put(Constants.TODO_TABLE_DESC, cursor.getString(2));
                todoData.put(Constants.TODO_TABLE_PRIOROTY, cursor.getString(3));
                todoData.put(Constants.TODO_TABLE_CATEGORYID, cursor.getInt(4));
                todoData.put(Constants.TODO_TABLE_TIME_MILIS, cursor.getString(5));
            }
        } catch (JSONException e) {
            Messages.logMessage(TAG_CLASS, e.toString());
        }
        return todoData;
    }

    /**
     * This is the method to get the Category Desc by the ID.
     *
     * @param categoryID: The ID whose Category Desc is needed.
     * @return: The Category Desc as a String.
     */
    public String getCategoryByID(int categoryID) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        String query = "SELECT " + Constants.CATEGORY_TABLE_DESC + " FROM " +
                Constants.CATEGORY_TABLE_NAME + " WHERE " + Constants.CATEGORY_TABLE_ID +
                "= " + String.valueOf(categoryID) + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        StringBuilder desc = new StringBuilder();
        while (!cursor.moveToNext()) {
            desc.append(cursor.getString(0));
        }
        return desc.toString();
    }

    public static class Helper extends SQLiteOpenHelper {

        public Helper(Context context) {
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
