package com.example.hp0331.asta;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.example.hp0331.asta.login.GestureEditActivity;
import com.example.hp0331.asta.login.GestureVerifyActivity;

public class MainActivity extends AppCompatActivity {
    private String SHARE_APP_TAG="Myutils";
    private int repeatNo=0;
   private  LottieAnimationView animation_view1;
    private String Tag="Lottie";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animation_view1=(LottieAnimationView)findViewById(R.id.animation_view1);
        animation_view1.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.d(Tag,"start");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.d(Tag,"end");
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                Log.d(Tag,"cancle");
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                repeatNo++;
                Log.d(Tag,"REPEAT"+repeatNo);
                SharedPreferences setting = getSharedPreferences(SHARE_APP_TAG, 0);
                Boolean user_first = setting.getBoolean("FIRST",true);
                if(user_first){//µÚÒ»´Î
                    setting.edit().putBoolean("FIRST", false).commit();
                    startActivity(new Intent(MainActivity.this, GestureEditActivity.class));
                    animation_view1.cancelAnimation();
                    finish();
                }else{
                    startActivity(new Intent(MainActivity.this, GestureVerifyActivity.class));
                    animation_view1.cancelAnimation();
                    finish();
                }
            }
        });


    }
}
