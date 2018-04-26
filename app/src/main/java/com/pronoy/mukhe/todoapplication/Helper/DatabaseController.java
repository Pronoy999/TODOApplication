package com.pronoy.mukhe.todoapplication.Helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
