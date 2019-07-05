package thole.iot.LEDControl;

import android.app.ActivityManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import android.os.Build;
import android.os.Handler;

import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;

import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import thole.iot.service.MyFirebaseMessagingService;

public class MainActivity extends AppCompatActivity {

    private Button on, on3, on4, on5;
    private Button off, off3, off4, off5;
    private TextView txtHumidity;
    private TextView txtTemperature;
    private DatabaseReference dref;
    private String humidity;
    private String temperature;
    private TextView status1, status2, status3, status4, alarmTxt, alarmCbx;
    //private NetworkInfo mWifi;
    private WifiManager wifiManager;
    private IntentFilter intentFilter;
    private CheckBox realTime, alarmObj;
    String CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    private String TAG = "MyApp";
    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyApp","onCreate");


        //FirebaseApp.initializeApp(this);
        setContentView(R.layout.layout2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        ;
        status1 =  findViewById(R.id.status1);
        status2 =  findViewById(R.id.status2);
        status3 =  findViewById(R.id.status3);
        status4 =  findViewById(R.id.status4);
        realTime =  findViewById(R.id.checkBox3);
        alarmObj =  findViewById(R.id.alarmCbx);
        alarmTxt =  findViewById(R.id.alarmObj);
        alarmCbx =  findViewById(R.id.alarmTxt);
//        LinearLayout mlayout = new LinearLayout(getApplicationContext());
//        mlayout.setOrientation(LinearLayout.VERTICAL);
        on =  findViewById(R.id.on);
        off = findViewById(R.id.off);
        on3 =  findViewById(R.id.on2);
        off3 =  findViewById(R.id.off2);
        on4 = findViewById(R.id.on3);
        off4 =  findViewById(R.id.off3);
        on5 =  findViewById(R.id.on4);
        off5 =  findViewById(R.id.off4);
        txtHumidity = findViewById(R.id.humidity);
        txtTemperature =  findViewById(R.id.temperature);
        dref = FirebaseDatabase.getInstance().getReference();

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        dref.child("Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("MyApp", "Temperature");
                txtTemperature.setTextColor(Color.GREEN);
                new Handler().postDelayed(new Runnable() {@Override
                public void run() {
                    //txtTemperature.setTextColor(Color.DKGRAY);
                    txtTemperature.setTextColor(Color.BLACK);
                }
                },3000);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dref.child("Humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("MyApp", "LED_STATUS4");
                txtHumidity.setTextColor(Color.GREEN);
                new Handler().postDelayed(new Runnable() {@Override
                public void run() {
                    //txtTemperature.setTextColor(Color.DKGRAY);
                    txtHumidity.setTextColor(Color.BLACK);
                }
                },3000);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        dref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("MyApp", dataSnapshot.getValue().toString());
                humidity = dataSnapshot.child("Humidity").getValue().toString();
                txtHumidity.setText("Humidity: " + humidity + " %");
                //txtHumidity.setTextColor(Color.RED);
                temperature = dataSnapshot.child("Temperature").getValue().toString();
                txtTemperature.setText("Temp: " + temperature + (char) 0x00B0 + "C");
                //txtTemperature.setTextColor(Color.RED);
                String pir = dataSnapshot.child("PIR-HC-SR505").getValue().toString();
                if(pir.equals("1")) {
                    alarmObj.setChecked(true);
                    alarmTxt.setTextColor(Color.GREEN);
                    alarmCbx.setText("ON");
                }
                else {
                    alarmObj.setChecked(false);
                    alarmTxt.setTextColor(Color.BLACK);
                    alarmCbx.setText("OFF");
                }
                String realTimeV = dataSnapshot.child("RealTime").getValue().toString();
                if(realTimeV.equals("1")) {
                    realTime.setChecked(true);
                   // alarmTxt.setTextColor(Color.GREEN);
                    //alarmCbx.setText("ON");
                }
                else {
                    realTime.setChecked(false);
                    //alarmTxt.setTextColor(Color.BLACK);
                    //alarmCbx.setText("OFF");
                }
                //MyApp myApp = (MyApp) getApplication();

                Log.d("MyApp", "isStop:"+isStop);
                //if (MyApp.isInBackground) {
                    //Log.d("MyApp", "test test");
//                    finish();
//                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
//                    if (launchIntent != null) {
//                        startActivity(launchIntent);//null pointer check in case package name was not found
//                    }
//                    startActivity(getIntent());
               // }
               // myApp.showMessage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_CHANGE);
        // getApplicationContext().registerReceiver(wifiScanReceiver, intentFilter);
       /* boolean success = wifiManager.startScan();
        if (!success) {
            on.setEnabled(true);
            off.setEnabled(true);
        }
        else  {
            on.setEnabled(false);
            off.setEnabled(false);
        }*/
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("LED_STATUS");
                if (wifiManager != null && wifiManager.isWifiEnabled()) {
                    myRef.setValue(1);
                    status1.setTextColor(Color.GREEN);

                } else {
                    Toast.makeText(getApplication(), "Wifi not available!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("LED_STATUS");
                if (wifiManager != null && wifiManager.isWifiEnabled()) {
                    myRef.setValue(0);
                    status1.setTextColor(Color.RED);
                } else {
                    Toast.makeText(getApplication(), "Wifi not available!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        on4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("LED_STATUS4");
                if (wifiManager != null && wifiManager.isWifiEnabled()) {
                    myRef.setValue(1);
                    status3.setTextColor(Color.GREEN);
                } else {
                    Toast.makeText(getApplication(), "Wifi not available!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        off4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("LED_STATUS4");
                if (wifiManager != null && wifiManager.isWifiEnabled()) {
                    myRef.setValue(0);
                    status3.setTextColor(Color.RED);
                } else {
                    Toast.makeText(getApplication(), "Wifi not available!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        on3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("LED_STATUS3");
                if (wifiManager != null && wifiManager.isWifiEnabled()) {
                    myRef.setValue(1);
                    status2.setTextColor(Color.GREEN);
                } else {
                    Toast.makeText(getApplication(), "Wifi not available!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        off3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("LED_STATUS3");
                if (wifiManager != null && wifiManager.isWifiEnabled()) {
                    myRef.setValue(0);
                    status2.setTextColor(Color.RED);
                } else {
                    Toast.makeText(getApplication(), "Wifi not available!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        on5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("LED_STATUS5");
                if (wifiManager != null && wifiManager.isWifiEnabled()) {
                    myRef.setValue(1);
                    status4.setTextColor(Color.GREEN);
                } else {
                    Toast.makeText(getApplication(), "Wifi not available!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        off5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("LED_STATUS5");
                if (wifiManager != null && wifiManager.isWifiEnabled()) {
                    myRef.setValue(0);
                    status4.setTextColor(Color.RED);
                } else {
                    Toast.makeText(getApplication(), "Wifi not available!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        realTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("RealTime");
                if (realTime.isChecked()) {
                    myRef.setValue(1);
                }
                else {
                    myRef.setValue(0);
                }
            }
        });

        alarmObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("PIR-HC-SR505");
                if (alarmObj.isChecked()) {
                    myRef.setValue(1);
                    alarmTxt.setTextColor(Color.GREEN);
                    alarmCbx.setText("ON");

                }
                else {
                    myRef.setValue(0);
                    alarmTxt.setTextColor(Color.BLACK);
                    alarmCbx.setText("OFF");
                }
            }
        });
        getApplicationContext().registerReceiver(wifiScanReceiver, intentFilter);

        /*FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
//        getApplicationContext().registerReceiver(wifiScanReceiver, intentFilter);
    }

    public static boolean isStop;

    @Override
    protected void onStop() {
        super.onStop();
        isStop = true;
        MyApp.activityResumed();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("RealTime");
        myRef.setValue(0);
        realTime.setChecked(false);
        Log.d("MyApp", "onStop:" + isStop);
        if (!isMyServiceRunning(this, MyFirebaseMessagingService.class)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(new Intent(this, MyFirebaseMessagingService.class));

            } else {
                startService(new Intent(this, MyFirebaseMessagingService.class));

            }
        }

//        getApplicationContext().unregisterReceiver(wifiScanReceiver);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
//                if (launchIntent != null) {
//                    startActivity(launchIntent);//null pointer check in case package name was not found
//                }
//            }
//        },3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getApplicationContext().unregisterReceiver(wifiScanReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApp.activityPaused();
        isStop=false;
        //realTime.setChecked(true);
        //getApplicationContext().registerReceiver(wifiScanReceiver, intentFilter);
        //MyApp myApp = (MyApp) this.getApplication();
    }

    BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            Log.d("thole", "onReceive:" + intent.getAction());
            if (intent.getAction().equals(CONNECTIVITY_CHANGE)) {
                NetworkInfo info = getNetworkInfo(getApplicationContext());
                if (info != null && info.isConnected()) {
                    on.setEnabled(true);
                    off.setEnabled(true);
                    on3.setEnabled(true);
                    off3.setEnabled(true);
                    on4.setEnabled(true);
                    off4.setEnabled(true);
                    on5.setEnabled(true);
                    off5.setEnabled(true);
                    realTime.setEnabled(true);
                    alarmObj.setEnabled(true);
                    Log.d("thole", "ON");
                    txtTemperature.setEnabled(true);
                    txtHumidity.setEnabled(true);
                } else {
                    on.setEnabled(false);
                    off.setEnabled(false);
                    on3.setEnabled(false);
                    off3.setEnabled(false);
                    on4.setEnabled(false);
                    off4.setEnabled(false);
                    on5.setEnabled(false);
                    off5.setEnabled(false);
                    txtHumidity.setEnabled(false);
                    txtTemperature.setEnabled(false);
                    realTime.setEnabled(false);
                    alarmObj.setEnabled(false);
                    Log.d("thole", "OFF");
                }
            }
        }
    };
    /*class MyFirebaseMessagingService extends Service {

        private final IBinder binder = new LocalBinder();
        @Override
        public IBinder onBind(Intent intent) {
            return binder;
        }

        // Class used for the client Binder.
        public class LocalBinder extends Binder {
            MyFirebaseMessagingService getService() {
                // Return this instance of MyService so clients can call public methods
                return MyFirebaseMessagingService.this;
            }
        }

        @Override
        public void onCreate() {
            super.onCreate();
            startMyOwnForeground();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.d("MyApp", "onStartComd");
            Firebase myFirebaseRef = new Firebase("https://winged-bliss-237302.firebaseio.com/");
            myFirebaseRef.child("RealTime").addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    Log.d("MyApp", dataSnapshot.getValue().toString());
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                    Log.d("MyApp", " BG "  + isStop);
                    if (launchIntent != null && isStop) {
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
    }*/



    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        Log.d("MyApp", "isMyServiceRunning ");
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

