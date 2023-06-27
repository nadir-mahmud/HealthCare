package com.example.healthcare.presentationlayer;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.applicationlayer.AlarmReceiver;

import java.util.Calendar;

public class AlarmSetter extends AppCompatActivity {
    private Context context;
    private Calendar calendar = Calendar.getInstance() ;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;


    public AlarmSetter(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAlarm(){
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_IMMUTABLE);
        //calendar.setTimeInMillis(500);
        AlarmManager.AlarmClockInfo ac = new AlarmManager.AlarmClockInfo(System.currentTimeMillis() + 5000, null);
        //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
        alarmManager.setAlarmClock(ac,pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,5000,
                AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    public void cancelAlarm() {

        Intent intent = new Intent(this,AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        if (alarmManager == null){

            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
    }

    public void createNotificationChannel() {
        NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    mNotificationManager.createNotificationChannel(channel);

        }


    }

}


