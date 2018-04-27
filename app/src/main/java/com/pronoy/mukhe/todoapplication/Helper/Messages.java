package com.pronoy.mukhe.todoapplication.Helper;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by mukhe on 26-Apr-18.
 * This is the class to Display Log or Toast messages.
 */

public class Messages {
    /**
     * This is the method to display Log Messages.
     *
     * @param TAG: The Tag for the log.
     * @param msg: The message that is to be displayed in the Log.
     */
    public static void logMessage(String TAG, String msg) {
        Log.d(TAG, msg);
    }

    /**
     * This is the method to display the toast message across the app.
     *
     * @param context: The context for the Toast message.
     * @param msg:     The message that is to be displayed in the toast.
     * @param length:  "long" for Long Toast, else a short Toast will be displayed.
     */
    public static void toastMessage(Context context, String msg, String length) {
        if (length.equalsIgnoreCase("long")) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This is the method to show a snackBar.
     *
     * @param view:   The View or the Layout where the Snack bar is to be shown.
     * @param msg:    The Message that is to be displayed.
     * @param length: The Length for the Snack Bar.
     */
    public static void snackbar(View view, String msg, String length) {
        Snackbar snackbar;
        if (length.equalsIgnoreCase("long")) {
            snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        } else
            snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
