package com.example.android.background.utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.example.android.background.MainActivity;
import com.example.android.background.R;
import com.example.android.background.sync.ReminderTasks;
import com.example.android.background.sync.WaterReminderIntentService;

public class NotificationUtils {
    public static final Integer WaterReminderPendingIntentID = 3417;

    public static PendingIntent contentIntent(Context context){
        Intent i = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(context, WaterReminderPendingIntentID, i, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public static void clearAllNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUserOfCharging(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("1101", "Main", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1101")
                .setContentIntent(contentIntent(context))
                .addAction(ignoreReminderAction(context))
                .addAction(updateCount(context))
                .setAutoCancel(true);

        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(1101,builder.build());
    }

    private static NotificationCompat.Action ignoreReminderAction(Context context){
        Intent ignoreReminder = new Intent(context, WaterReminderIntentService.class);
        ignoreReminder.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);

        PendingIntent ignorePendingIntent = PendingIntent.getService(context, 1120, ignoreReminder, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action ignore = new NotificationCompat.Action(R.drawable.ic_cancel_black_24px, "No Thanks", ignorePendingIntent);

        return ignore;
    }

    private static NotificationCompat.Action updateCount(Context context){
        Intent ignoreReminder = new Intent(context, WaterReminderIntentService.class);
        ignoreReminder.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);

        PendingIntent ignorePendingIntent = PendingIntent.getService(context, 1121, ignoreReminder, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action ignore = new NotificationCompat.Action(R.drawable.ic_drink_notification, "I did it", ignorePendingIntent);

        return ignore;
    }

    private static Bitmap largeItem(Context context){
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px);
        return largeIcon;
    }
}
