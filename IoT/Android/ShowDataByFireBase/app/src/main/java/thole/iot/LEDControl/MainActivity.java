package thole.iot.LEDControl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button on;
    private Button off;
    private TextView txtHumidity;
    private TextView txtTemperature;
    private DatabaseReference dref;
    private String humidity;
    private String temperature;
    //private NetworkInfo mWifi;
    private WifiManager wifiManager;
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

        if (getSupportActionBar() != null ) {
            getSupportActionBar().hide();
        };
        LinearLayout mlayout = new LinearLayout(getApplicationContext());
        mlayout.setOrientation(LinearLayout.VERTICAL);
        on = (Button) findViewById(R.id.on);
        off = (Button) findViewById(R.id.off);
        txtHumidity = (TextView) findViewById(R.id.humidity);
        txtTemperature = (TextView) findViewById(R.id.temperature);
        dref = FirebaseDatabase.getInstance().getReference();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        dref.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                humidity = dataSnapshot.child("Humidity").getValue().toString();
                txtHumidity.setText("Humidity: " + humidity + " %");
                temperature = dataSnapshot.child("Temperature").getValue().toString();
                txtTemperature.setText("Temp: " + temperature + " *C");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_CHANGE);
        getApplicationContext().registerReceiver(wifiScanReceiver, intentFilter);
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


    }

    BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            Log.d("thole", "onReceive:" + intent.getAction());
            if (intent.getAction().equals(CONNECTIVITY_CHANGE)) {
                NetworkInfo info = getNetworkInfo(MainActivity.this);
                if (info != null && info.isConnected()) {
                    on.setEnabled(true);
                    off.setEnabled(true);
                    Log.d("thole", "ON");
                    txtTemperature.setEnabled(true);
                    txtHumidity.setEnabled(true);
                } else {
                    on.setEnabled(false);
                    off.setEnabled(false);
                    txtHumidity.setEnabled(false);
                    txtTemperature.setEnabled(false);
                    Log.d("thole", "OFF");
                }
            }
        }
    };
}
