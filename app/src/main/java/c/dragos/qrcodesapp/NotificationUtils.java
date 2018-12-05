package c.dragos.qrcodesapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;


/**
 * Created by Bogdan on 25.09.2018.
 */

public class NotificationUtils extends ContextWrapper {

    public NotificationManager mManager;
    public static final String name = "Notification_channel";

    public NotificationUtils(Context base) {

        super(base);

    }

    private void createChannel(String CHANNEL_ID) {


        String description = "This is a notification channel";

        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        getManager().createNotificationChannel(channel);

    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    //Next function will create the notification for my channel
    public Notification.Builder getChannelNotification(String title, Notification.BigTextStyle body, String Channel_ID) {

        //for navigation from every screen to ScanQRForReturningItem after the notification appeared and selected

        createChannel(Channel_ID);

        Intent ResultIntent = new Intent(this, Main_Page.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(ResultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        return new Notification.Builder(getApplicationContext(), Channel_ID)
                .setContentTitle(title)
                .setStyle(body)
                .setSmallIcon(R.mipmap.fev)
                .setAutoCancel(true)
                .setChannelId(Channel_ID)
                .setContentIntent(resultPendingIntent);
    }

}
