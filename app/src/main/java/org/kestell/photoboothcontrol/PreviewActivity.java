package org.kestell.photoboothcontrol;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class PreviewActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private MediaDecoder rtpMediaDecoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setBackgroundDrawableResource(android.R.color.white);

        setContentView(R.layout.activity_preview);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        surfaceView = findViewById(R.id.surfaceView);
        ImageView shadeView = findViewById(R.id.shadeView);

        rtpMediaDecoder = new MediaDecoder(surfaceView);
        rtpMediaDecoder.useNio = true;
        rtpMediaDecoder.bufferType = "min-delay";
        rtpMediaDecoder.start();

        shadeView.animate()
                .alpha(0f)
                .setDuration(1000)
                .setStartDelay(2500)
                .withEndAction(() -> shadeView.setVisibility(View.GONE))
                .start();

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            shadeView.setVisibility(View.VISIBLE);
            shadeView.animate()
                    .alpha(1f)
                    .setDuration(1000)
                    .withEndAction(() -> {
                        this.surfaceView.setVisibility(View.GONE);
                        this.finish();
                    })
                    .start();
        }, 45000);

        Api api = new Api();
        api.takePhotos();
    }

    @Override
    protected void onStop() {
        rtpMediaDecoder.release();
        super.onStop();
    }
}
