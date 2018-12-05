package c.dragos.qrcodesapp;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Bogdan on 26.09.2018.
 */

public class NotificationPublisher extends BroadcastReceiver {

    private NotificationUtils mNotificationUtils;
    private transient Notification.Builder notification;
    private String ItemName;
    private String ItemID;


    public void onReceive(final Context context, final Intent intent) {

        //mNotificationUtils = new NotificationUtils(context);

        //Read notification's information between Add_or_Remove... activity
        Bundle extras = new Bundle();

        //ItemName = intent.getExtras().getString("ItemName");
        //ItemID = intent.getExtras().getString("ItemID");

        //Create a notification and a notification channel with the channel id equal with ItemID
        //For deleting the notification if the Item is return before the time is up
        //notification = mNotificationUtils.getChannelNotification("IMPORTANT!","You have to return next item: " + ItemName, ItemID);


        //Verify if notification channel exist anymore which means that the Item was not returned yet
        //if (notification.)
        //Notification.Builder notification = mNotificationUtils.getChannelNotification("IMPORTANT!","You have to return next item: " + ItemName, ItemID);
        //mNotificationUtils.getManager().notify(101, notification.build());


        /* Function for scheduling a notification which has to be used in another class

          public void scheduleNotification(int delay, String itemID, String ItemName) {

        alarm++;
        if (alarm == 1) {

            Intent notificationIntent = new Intent(this, NotificationPublisher.class);

            //Send data for creating the notification in another activity
            notificationIntent.putExtra("ItemName",ItemName);
            notificationIntent.putExtra("ItemID", itemID);


            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            long futureInMillis = SystemClock.elapsedRealtime() + delay;
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        }
         */

    }

}
