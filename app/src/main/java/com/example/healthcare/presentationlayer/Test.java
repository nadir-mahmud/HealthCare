package com.example.healthcare.presentationlayer;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.Encryption;
import com.example.healthcare.applicationlayer.MyOwnAdapter;
import com.example.healthcare.applicationlayer.RecyclerViewClickInterface;
import com.example.healthcare.entity.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Test extends AppCompatActivity {
    Button click;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //AlarmSetter alarmSetter = new AlarmSetter(getApplicationContext());
        //alarmSetter.setAlarm();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //The code here will run after 5 sec, do what you want
                NotificationManager mNotificationManager;

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getApplicationContext(), "notify_001");


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

                mNotificationManager =
                        (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

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
//

            }
        }, 10*1000);







    }
}



//                NotificationManager mNotificationManager;
//
//                NotificationCompat.Builder mBuilder =
//                        new NotificationCompat.Builder(getApplicationContext(), "notify_001");
//
//
//                NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//                bigText.bigText("You have an appointment with Ruhul Amin on Saturday 9:00 to 12:00.");
//                bigText.setBigContentTitle("Healthcare");
//                bigText.setSummaryText("Text in detail");
//
//
//                mBuilder.setSmallIcon(R.drawable.medical_equipment);
//                mBuilder.setContentTitle("Healthcare");
//                mBuilder.setContentText("You have an appointment with Ruhul Amin on Saturday 9:00 to 12:00.");
//                //mBuilder.setSubText(" ");
//                mBuilder.setPriority(Notification.PRIORITY_MAX);
////                mBuilder.setStyle(bigText);
//
//                mNotificationManager =
//                        (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
//// === Removed some obsoletes
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//                {
//                    String channelId = "Your_channel_id";
//                    NotificationChannel channel = new NotificationChannel(
//                            channelId,
//                            "Channel human readable title",
//                            NotificationManager.IMPORTANCE_HIGH);
//                    mNotificationManager.createNotificationChannel(channel);
//                    mBuilder.setChannelId(channelId);
//                }
//
//                mNotificationManager.notify(0, mBuilder.build());
//
//            }
//        });










//        Button click = findViewById(R.id.click);
//        click.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onClick(View view) {
//                TimeZone zone = TimeZone.getTimeZone("Etc/GMT");
//                Date cal = Calendar.getInstance().getTime();
//                String calString = DateFormat.getDateTimeInstance().format(cal);
//
//                Toast.makeText(getApplicationContext(), calString  ,Toast.LENGTH_SHORT).show();
//            }
//        });

//        String[] email = {"nadirmahmud5@gmail.com"};
//
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, email);
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Email and password");
//        intent.putExtra(Intent.EXTRA_TEXT, "Your password is Shejuti");
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }


//    NotificationManager mNotificationManager;
//
//                NotificationCompat.Builder mBuilder =
//                        new NotificationCompat.Builder(getApplicationContext(), "notify_001");
//
//
//                NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//                bigText.bigText("You have an appointment with Ruhul Amin on Saturday 9:00 to 12:00.");
//                bigText.setBigContentTitle("Healthcare");
//                //bigText.setSummaryText("Text in detail");
//
//
//                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//                mBuilder.setContentTitle("Healthcare");
//               mBuilder.setContentText("You have an appointment with Ruhul Amin");
//               mBuilder.setSubText(" on Saturday 9:00 to 12:00.");
//                mBuilder.setPriority(Notification.PRIORITY_MAX);
//                mBuilder.setStyle(bigText);
//
//                mNotificationManager =
//                        (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
//// === Removed some obsoletes
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//                {
//                    String channelId = "Your_channel_id";
//                    NotificationChannel channel = new NotificationChannel(
//                            channelId,
//                            "Channel human readable title",
//                            NotificationManager.IMPORTANCE_HIGH);
//                    mNotificationManager.createNotificationChannel(channel);
//                    mBuilder.setChannelId(channelId);
//                }
//
//                mNotificationManager.notify(0, mBuilder.build());
//            }