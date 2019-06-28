package thole.iot.LEDControl;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

public class MyApp extends Application implements LifecycleObserver {
    public static boolean isInBackground = false;
    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onAppBackgrounded() {
        Log.d("MyApp", "App in background");
        isInBackground = true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onAppForegrounded() {
        Log.d("MyApp", "App in foreground");
        isInBackground =  false;
    }
    public void showMessage() {
        Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
    }
    
}
