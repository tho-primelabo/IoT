package thole.iot.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import thole.iot.LEDControl.MainActivity;
import thole.iot.LEDControl.MyDialog;
import thole.iot.LEDControl.R;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyFirebaseMessagingService extends Service {

    private  Vibrator v;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startMyOwnForeground();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Firebase myFirebaseRef = new Firebase("https://winged-bliss-237302.firebaseio.com/");
        Log.d("MyApp", "onStartCommand");
        myFirebaseRef.child("SR505").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("MyApp", dataSnapshot.getValue().toString());
                //Intent launchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                Intent launchIntent=new Intent(MyFirebaseMessagingService.this, MyDialog.class);
                launchIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                Log.d("MyApp", " BG "  + MainActivity.isStop);
                if (launchIntent != null && !MyDialog.active) {
                    DatabaseReference fb = FirebaseDatabase.getInstance().getReference("RealTime");
                    fb.setValue(0);

                   startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("The read failed: ", firebaseError.getMessage());
            }
        });


        return START_STICKY;
    }


    private void startMyOwnForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String NOTIFICATION_CHANNEL_ID = this.getPackageName();
            String channelName = "My Background Service";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);
            // NotificationCompat
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("App is downloading file")
                    .setPriority(NotificationManager.IMPORTANCE_MIN)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
            startForeground(2, notification);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        v.cancel();
    }
}
