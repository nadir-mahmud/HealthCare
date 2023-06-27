package com.example.healthcare.applicationlayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.healthcare.R;
import com.example.healthcare.presentationlayer.Notifications;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, Notifications.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_IMMUTABLE);


        NotificationManager mNotificationManager;

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context, "notify_001");


                NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                bigText.bigText("You have an appointment with Ruhul Amin on Saturday 9:00 to 12:00.");
                bigText.setBigContentTitle("Healthcare");
                bigText.setSummaryText("Text in detail");


                mBuilder.setSmallIcon(R.drawable.medical_equipment);
                mBuilder.setContentTitle("Healthcare");
                mBuilder.setContentText("You have an appointment with Ruhul Amin on Saturday 9:00 to 12:00.");
                //mBuilder.setSubText(" ");
                mBuilder.setPriority(Notification.PRIORITY_MAX);
//                mBuilder.setStyle(bigText);
                //mBuilder.setContentIntent(pendingIntent);

                mNotificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    mNotificationManager.createNotificationChannel(channel);
                    mBuilder.setChannelId(channelId);
                }

                mNotificationManager.notify(0, mBuilder.build());

            }
    }

