package com.greymatter.sesmo.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;


import com.greymatter.sesmo.MainActivity;
import com.greymatter.sesmo.R;

public class MyService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI, new Handler());
        return START_STICKY;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta; // perform low-cut filter

        if (mAccel > 45) {
           // showNotification();
            Toast.makeText(this, "Shaked " + String.valueOf(mAccel) + " EarthQuake Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
    }

    /**
     * show notification when Accel is more then the given int.
     */

    private void showNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String NOTIFICATION_CHANNEL_ID = getPackageName();
            String channelName = "My Background Service";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);
            PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
                    MainActivity.class), 0);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setContentTitle("Device Accelerometer Notification")
                    .setTicker("New Message Alert!")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setPriority(NotificationManager.IMPORTANCE_MIN)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();
            startForeground(101, notification);
        }
        else
        {
            final NotificationManager mgr = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder note = new NotificationCompat.Builder(this);
            note.setContentTitle("Device Accelerometer Notification");
            note.setTicker("New Message Alert!");
            note.setAutoCancel(true);
            // to set default sound/light/vibrate or all
            note.setDefaults(Notification.DEFAULT_ALL);
            // Icon to be set on Notification
            note.setSmallIcon(R.drawable.ic_launcher_foreground);
            note.setAutoCancel(true);
            // This pending intent will open after notification click
            PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
                    MainActivity.class), 0);
            // set pending intent to notification builder
            note.setContentIntent(pi);
            mgr.notify(101, note.build());
        }


    }

}

