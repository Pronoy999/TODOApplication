package com.pronoy.mukhe.todoapplication.Helper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.pronoy.mukhe.todoapplication.Acitvities.TodoActivity;
import com.pronoy.mukhe.todoapplication.R;

/**
 * Created by mukhe on 30-Apr-18.
 * This is the class to generate the notifications.
 */

public class AlarmReceiver extends BroadcastReceiver {
    String title="",desc="";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            title=bundle.getString(Constants.TODO_TABLE_TITLE);
            desc=bundle.getString(Constants.TODO_TABLE_DESC);
        }
        Intent todoIntent=new Intent(context, TodoActivity.class);
        todoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,todoIntent,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notification_event)
                .setContentTitle(title)
                .setContentText(desc)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManager notificationManager= (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_CHANNEL_ID,builder.build());
    }
}
