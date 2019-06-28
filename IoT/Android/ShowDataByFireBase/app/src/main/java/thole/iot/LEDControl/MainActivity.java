package thole.iot.LEDControl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button on, on3, on4, on5;
    private Button off, off3, off4, off5;
    private TextView txtHumidity;
    private TextView txtTemperature;
    private DatabaseReference dref;
    private String humidity;
    private String temperature;
    //private NetworkInfo mWifi;
    private WifiManager wifiManager;
    private IntentFilter intentFilter;
    String CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseApp.initializeApp(this);
        setContentView(R.layout.layout2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        ;
//        LinearLayout mlayout = new LinearLayout(getApplicationContext());
//        mlayout.setOrientation(LinearLayout.VERTICAL);
        on = (Button) findViewById(R.id.on);
        off = (Button) findViewById(R.id.off);
        on3 = (Button) findViewById(R.id.on2);
        off3 = (Button) findViewById(R.id.off2);
        on4 = (Button) findViewById(R.id.on3);
        off4 = (Button) findViewById(R.id.off3);
        on5 = (Button) findViewById(R.id.on4);
        off5 = (Button) findViewById(R.id.off4);
        txtHumidity = (TextView) findViewById(R.id.humidity);
        txtTemperature = (TextView) findViewById(R.id.temperature);
        dref = FirebaseDatabase.getInstance().getReference();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

//        dref.child("Humidity").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d("MyApp", "Humidity");
//                if (dataSnapshot.child("Humidity").getValue() != null) {
//                    humidity = dataSnapshot.child("Humidity").getValue().toString();
//                    txtHumidity.setText("Humidity: " + humidity + " %");
//                    txtHumidity.setTextColor(Color.RED);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            txtTemperature.setTextColor(Color.DKGRAY);
//                            txtHumidity.setTextColor(Color.DKGRAY);
//                        }
//                    }, 3000);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

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


                //MyApp myApp = (MyApp) getApplication();

                Log.d("MyApp", "isStop:"+isStop);
                if (MyApp.isInBackground) {
                    Log.d("MyApp", "test test");
//                    finish();
//                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
//                    if (launchIntent != null) {
//                        startActivity(launchIntent);//null pointer check in case package name was not found
//                    }
//                    startActivity(getIntent());
                }
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
                } else {
                    Toast.makeText(getApplication(), "Wifi not available!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        getApplicationContext().registerReceiver(wifiScanReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        getApplicationContext().registerReceiver(wifiScanReceiver, intentFilter);
    }

    private boolean isStop;

    @Override
    protected void onStop() {
        super.onStop();
        isStop = true;
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
        isStop=false;
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
                    Log.d("thole", "OFF");
                }
            }
        }
    };
}
