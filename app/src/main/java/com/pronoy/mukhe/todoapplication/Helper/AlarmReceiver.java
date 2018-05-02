package com.pronoy.mukhe.todoapplication.Helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.pronoy.mukhe.todoapplication.Acitvities.TodoActivity;
import com.pronoy.mukhe.todoapplication.R;

/**
 * Created by mukhe on 30-Apr-18.
 * This is the class to generate the notifications.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG_CLASS = AlarmManager.class.getSimpleName();
    String title = "", desc = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        Messages.logMessage(TAG_CLASS, "In Alarm Receiver.");
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            title = bundle.getString(Constants.TODO_TABLE_TITLE);
            desc = bundle.getString(Constants.TODO_TABLE_DESC);
        }
        Intent todoIntent = new Intent(context, TodoActivity.class);
        todoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0,
                todoIntent,
                0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                String.valueOf(Constants.NOTIFICATION_CHANNEL_ID))
                .setSmallIcon(R.drawable.notification_event)
                .setContentTitle(title)
                .setContentText(desc)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setChannelId(String.valueOf(Constants.NOTIFICATION_CHANNEL_ID))
                .setAutoCancel(true);
        Constants.NOTIFICATION_MANAGER.notify(Constants.NOTIFICATION_ID, builder.build());
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
    }
}
