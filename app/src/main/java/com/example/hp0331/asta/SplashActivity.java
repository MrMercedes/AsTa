package com.example.hp0331.asta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.hp0331.asta.login.GestureEditActivity;
import com.example.hp0331.asta.login.GestureVerifyActivity;

public class SplashActivity extends AppCompatActivity {
    private ImageView img;
    private String SHARE_APP_TAG="Myutils";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        img = (ImageView) findViewById(R.id.img);

        ScaleAnimation anim = new ScaleAnimation(1.0f,1.5f,1.0f,1.5f,0.5f,0.5f);
        anim.setDuration(3000);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SharedPreferences setting = getSharedPreferences(SHARE_APP_TAG, 0);
                Boolean user_first = setting.getBoolean("FIRST",true);
                if(user_first){//µÚÒ»´Î
                    setting.edit().putBoolean("FIRST", false).commit();
                    startActivity(new Intent(SplashActivity.this, GestureEditActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this, GestureVerifyActivity.class));
                    finish();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        img.startAnimation(anim);
    }

}
