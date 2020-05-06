package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotificationTesting extends AppCompatActivity {

    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_testing);

        // Finds you button from the xml layout file
        Button createNotificationButton = findViewById(R.id.button_notification_test);

        // Waits for you to click the button
        createNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starts the function below
                createNotification("Come check your weekly plan!", getApplicationContext());
            }
        });
    }

    /**
     * createNotification() method creates a popup notification with given displayable text as the first parameter when called. !Second parameter must use getApplicationContext()
     *
     * How it works:
     * First a channel for the notification to display in must be created or else it will not be displayed and a developer error will occur when trying to do so.
     * We give the channel an id and a name title.
     * The method creates a NotificationManager called notificationManager AND a NotificationChannel called notificationChannel only ONCE! Therefore it checks if they have already been created.
     * The SDK build version is checked in case of incompatibility.
     * A notification must have an IMPORTANCE level and a PRIORITY level, those are both set to DEFAULT at this time since this is only a notification with the purpose to remind the user.
     *
     * For this NotificationChannel a vibration has been enabled to further help notify the user about an incoming notification! This can be changed by enableVibration()
     * After checking the existence of a NotificationChannel the method proceeds to create tje actual notifications contents by calling the builder.
     * IT IS VITAL TO ENTER THE REQUIRED THREE SETTERS: setSmallIcon() setContentTitle() setContentText(). Otherwise this method will not function properly.
     *
     * After all of the required structure is created this method finally calls the NotificationManager's Builder to build() and initiate the notification with notify()
     * @param displayText is the displayed text in the popup notification
     * @param context context needs to have the getApplicationContext() method as a parameter, this takes the current state of the application
     */
    public void createNotification(String displayText, Context context) {
        final int NOTIFY_ID = 0;            // ID of the notification
        String id = "default_channel_id";   // default_channel_id
        String title = "Default Channel";   // Default Channel
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notificationManager == null) {
            notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(id);
            if (notificationChannel == null) {
                notificationChannel = new NotificationChannel(id, title, importance);
                notificationChannel.enableVibration(true);                                                              // Vibration is enabled for this notification
                notificationChannel.setVibrationPattern(new long[]{200, 200, 200, 200, 500, 500, 500, 500});            // The vibration has a custom made pattern
                notificationManager.createNotificationChannel(notificationChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, NotificationTesting.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle("Fit Summit")                                                   // REQUIRED! The title used for the notification
                    .setSmallIcon(R.mipmap.ic_launcher_round)                                       // REQUIRED! The small icon that is displayed in the notification
                    .setContentText(displayText)                                                    // REQUIRED! The text displayed in the notification, to change this, use a string parameter when calling method!
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(displayText)
                    .setVibrate(new long[]{200, 200, 200, 200, 500, 500, 500, 500});
        }
        else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, NotificationTesting.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle("Fit Summit")                                                   // REQUIRED! The title used for the notification
                    .setSmallIcon(R.mipmap.ic_launcher_round)                                       // REQUIRED! The small icon that is displayed in the notification
                    .setContentText(displayText)                                                    // REQUIRED! The text displayed in the notification, to change this, use a string parameter when calling method!
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(displayText)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_DEFAULT);
        }
        Notification notification = builder.build();
        notificationManager.notify(NOTIFY_ID, notification);
    }
}
