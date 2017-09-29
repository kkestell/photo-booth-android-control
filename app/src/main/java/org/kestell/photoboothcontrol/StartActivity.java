package org.kestell.photoboothcontrol;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity implements View.OnTouchListener {
    private ImageView imageView;
    private SoundPoolPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setBackgroundDrawableResource(android.R.color.white);

        setContentView(R.layout.activity_start);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        imageView = findViewById(R.id.imageView);
        imageView.setOnTouchListener(this);

        sound = new SoundPoolPlayer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sound = new SoundPoolPlayer(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        sound.playShortResource(R.raw.click);

        Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(100);

        Intent myIntent = new Intent(StartActivity.this, PreviewActivity.class);
        StartActivity.this.startActivity(myIntent);

        return false;
    }

    @Override
    protected void onStop() {
        sound.release();
        super.onStop();
    }
}
