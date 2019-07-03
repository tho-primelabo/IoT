package thole.iot.LEDControl;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class MyApp extends Application implements LifecycleObserver {
    public static boolean isInBackground = false;
    @Override
    public void onCreate() {
        super.onCreate();
        //Previous versions of Firebase
        Firebase.setAndroidContext(this);

        //Newer version of Firebase
        if(!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    public static boolean isActivityVisible() {
        Log.d("MyApp", "isActivityVisible: " + activityVisible);
        return activityVisible;
    }

    public static void activityResumed() {
        Log.d("MyApp", "activityResumed:" + activityVisible);
        activityVisible = true;
    }

    public static void activityPaused() {
        Log.d("MyApp", "activityPaused:" + activityVisible);
        activityVisible = false;
    }

    private static boolean activityVisible = true;
    public void showMessage() {
        Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
    }
    
}
