package com.example.finalyearproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String channelID ="channel1";
    public static final String channelName ="Channel 1";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannel(){
        NotificationChannel channel1 = new NotificationChannel(channelID,channelName, NotificationManager.IMPORTANCE_HIGH);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.grey);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);
    }

    public NotificationManager getManager(){

        if(mManager == null){
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannel1Notification(){
        return new NotificationCompat.Builder(getApplicationContext(),channelID)
                .setContentTitle("Have you completed today's task?")
                .setContentText("Check Your Habits")
                .setSmallIcon(R.drawable.ic_notify);
    }
}
