package com.sinric.esp32_android_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class JoisTick extends Activity {
    private TextView mTextViewAngleRight;
    private TextView mTextViewStrengthRight;
    private TextView mTextViewCoordinateRight;
    private  JoystickView joystickRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        mTextViewAngleRight = (TextView) findViewById(R.id.textView_angle_right);
        mTextViewStrengthRight = (TextView) findViewById(R.id.textView_strength_right);
        mTextViewCoordinateRight = findViewById(R.id.textView_coordinate_right);
        joystickRight = (JoystickView) findViewById(R.id.joystickView_right);
        this.joystickRight.setOnMoveListener(new JoystickView.OnMoveListener() {
            //@SuppressLint("DefaultLocale")
            @Override
            public void onMove(int angle, int strength) {
                Log.e("thole", "testetetetretet");
                mTextViewAngleRight.setText(angle + "°");
                mTextViewStrengthRight.setText(strength + "%");
                mTextViewCoordinateRight.setText(
                        String.format("x%03d:y%03d",
                                joystickRight.getNormalizedX(),
                                joystickRight.getNormalizedY())
                );
            }
        });

        Button btn5 = (Button)findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                //intent.putExtra("key", value);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
