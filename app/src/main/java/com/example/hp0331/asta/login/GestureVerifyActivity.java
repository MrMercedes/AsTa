package com.example.hp0331.asta.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp0331.asta.R;
import com.example.hp0331.asta.coordinator.CoordinatorActivity;

public class GestureVerifyActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout mTopLayout;
    private TextView mTextTitle;
    private TextView mTextCancel,text_forget_gesture;
    private ImageView mImgUserLogo;
    private TextView mTextPhoneNumber,usefinger;
    private String TAG="Login";
    private TextView mTextTip;
    private FrameLayout mGestureContainer;
    private GestureContentView mGestureContentView;
    private TextView mTextForget;
    private TextView mTextOther;
    private String mParamPhoneNumber;
    private long mExitTime = 0;
    private int mParamIntentCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_verify);
        setUpViews();
        setUpListeners();
    }
    private void setUpViews() {
        mTopLayout = (RelativeLayout) findViewById(R.id.top_layout);
        mTextTitle = (TextView) findViewById(R.id.text_title);
        mTextCancel = (TextView) findViewById(R.id.text_cancel);
        mImgUserLogo = (ImageView) findViewById(R.id.user_logo);
        mTextPhoneNumber = (TextView) findViewById(R.id.text_phone_number);
        mTextTip = (TextView) findViewById(R.id.text_tip);
        mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
        mTextForget = (TextView) findViewById(R.id.text_forget_gesture);
        mTextOther = (TextView) findViewById(R.id.text_other_account);
        usefinger=(TextView)findViewById(R.id.tv_usefinger);
        usefinger.setVisibility(View.GONE);
        text_forget_gesture=(TextView)findViewById(R.id.text_forget_gesture);


        SharedPreferences preferences=getSharedPreferences("message", Context.MODE_PRIVATE);
        String password=preferences.getString("password","");
        // 初始化一个显示各个点的viewGroup
        mGestureContentView = new GestureContentView(this, true,password,
                new GestureDrawline.GestureCallBack() {

                    @Override
                    public void onGestureCodeInput(String inputCode) {

                    }

                    @Override
                    public void checkedSuccess() {
                        mGestureContentView.clearDrawlineState(0L);
                        Toast.makeText(GestureVerifyActivity.this, getString(R.string.right_code), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(GestureVerifyActivity.this, CoordinatorActivity.class));
                        finish();
                    }

                    @Override
                    public void checkedFail() {
                        mGestureContentView.clearDrawlineState(1300L);
                        mTextTip.setVisibility(View.VISIBLE);
                        mTextTip.setText(Html
                                .fromHtml(getString(R.string.wrong_code)));
                        // 左右移动动画
                        Animation shakeAnimation = AnimationUtils.loadAnimation(GestureVerifyActivity.this, R.anim.shake);
                        mTextTip.startAnimation(shakeAnimation);
                    }
                });
        // 设置手势解锁显示到哪个布局里面
        mGestureContentView.setParentView(mGestureContainer);
    }

    private void setUpListeners() {
        mTextCancel.setOnClickListener(this);
        mTextForget.setOnClickListener(this);
        mTextOther.setOnClickListener(this);
        usefinger.setOnClickListener(this);
        text_forget_gesture.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_cancel:
                this.finish();
                break;
            case R.id.tv_usefinger:
                break;
            case  R.id.text_forget_gesture:
                SharedPreferences preferences=getSharedPreferences("message", Context.MODE_PRIVATE);
                String password=preferences.getString("password", "");
                if (password==null||password==""){
                    Toast.makeText(GestureVerifyActivity.this,"未设置密码",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(GestureVerifyActivity.this,"密码是:" + password,Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
