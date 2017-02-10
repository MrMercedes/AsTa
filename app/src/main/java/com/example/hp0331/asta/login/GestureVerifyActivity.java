package com.example.hp0331.asta.login;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
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
@TargetApi(Build.VERSION_CODES.M)
public class GestureVerifyActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout mTopLayout;
    private TextView mTextTitle,tv_usemima;
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
    private FingerprintManager manager;//����ָ��Ӳ������
    private KeyguardManager keyManager;//������������
    private CancellationSignal signal = new CancellationSignal();
    //�������Java����API��һ����װ��,���ڷ�ֹ��ָ��ɨ���б����������⹥��
    private FingerprintManager.CryptoObject cryptoObject;
    private static final int REQUST_CODE=1;
    private ImageView image;
    private String minSdkVersion;
    private  int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_verify);
        setUpViews();
        setUpListeners();
        minSdkVersion= Build.VERSION.SDK;
        if(Double.parseDouble(minSdkVersion)>=23) {
            initFinger();
        }}
    private void initLowSDK() {
        Toast.makeText(getBaseContext(), "Ŀǰָ��ʶ��ֻ֧��6.0���ϵ�ϵͳ!", Toast.LENGTH_LONG).show();
    }
    private void initFinger() {
        //ͨ��V4����ö���
        manager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
        keyManager= (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);

    }
    @TargetApi(Build.VERSION_CODES.M)
    private boolean isFingerprint(){
        //�˷���Ϊ�˱�֤�ж��Ƿ�֧��֧��ָ�Ʋ�����
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getBaseContext(), "û��ָ�ƽ�����Ȩ��", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Ӳ���豸�Ƿ�֧��ָ�ƽ�������
        if (!manager.isHardwareDetected()) {
            Toast.makeText(getBaseContext(), "���ֻ���֧��ָ�ƽ���", Toast.LENGTH_SHORT).show();
            return false;
        }
        //�ж��Ƿ�����������
        if(!keyManager.isKeyguardSecure()){
            Toast.makeText(getBaseContext(), "��������������", Toast.LENGTH_SHORT).show();
            return false;
        }
        //�ж��Ƿ�¼��ָ��
        if (!manager.hasEnrolledFingerprints()) {
            Toast.makeText(getBaseContext(), "û��¼��ָ��", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /**
     * ��ʼָ��ʶ��
     */
    private void startListen() {
        usezhiwen();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        FingerprintManager.AuthenticationCallback callBack=new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getBaseContext(), "��������Ƶ��,���Ժ�����",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
            }

            //ָ��ʶ��ɹ�
            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getBaseContext(), "ָ��ʶ��ɹ�",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(GestureVerifyActivity.this, CoordinatorActivity.class));
                finish();
            }

            //ָ��ʶ��ʧ��
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getBaseContext(), "ָ��ʶ��ʧ��", Toast.LENGTH_SHORT).show();
                i++;
                if(i==6){
                    Toast.makeText(getBaseContext(), "ʧ�ܴ�������,��������������", Toast.LENGTH_SHORT).show();
                    showLockScreenPass();
                    i=0;
                }
            }
        };
        manager.authenticate(cryptoObject, signal, 0, callBack, null);

    }
    /**
     *ָ��ʶ������������,��ʾ�ֻ���������
     */
    private void showLockScreenPass() {
        Intent intent=keyManager.createConfirmDeviceCredentialIntent("finger","������������");
        if(intent!=null){
            startActivityForResult(intent, REQUST_CODE);
        }
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
        tv_usemima=(TextView)findViewById(R.id.tv_usemima);
        image= (ImageView) findViewById(R.id.iv_imgage);
        image.setVisibility(View.GONE);
//        usefinger.setVisibility(View.GONE);
        text_forget_gesture=(TextView)findViewById(R.id.text_forget_gesture);


        SharedPreferences preferences=getSharedPreferences("message", Context.MODE_PRIVATE);
        String password=preferences.getString("password","");
        // ��ʼ��һ����ʾ�������viewGroup
        mGestureContentView = new GestureContentView(this, true,password,
                new GestureDrawline.GestureCallBack() {

                    @Override
                    public void onGestureCodeInput(String inputCode) {

                    }

                    @Override
                    public void checkedSuccess() {
                        mGestureContentView.clearDrawlineState(0L);
//                        Toast.makeText(GestureVerifyActivity.this, getString(R.string.right_code), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(GestureVerifyActivity.this, CoordinatorActivity.class));
                        finish();
                    }

                    @Override
                    public void checkedFail() {
                        mGestureContentView.clearDrawlineState(1300L);
                        mTextTip.setVisibility(View.VISIBLE);
                        mTextTip.setText(Html
                                .fromHtml(getString(R.string.wrong_code)));
                        // �����ƶ�����
                        Animation shakeAnimation = AnimationUtils.loadAnimation(GestureVerifyActivity.this, R.anim.shake);
                        mTextTip.startAnimation(shakeAnimation);
                    }
                });
        // �������ƽ�����ʾ���ĸ���������
        mGestureContentView.setParentView(mGestureContainer);
    }
public void usezhiwen(){
    mTextTitle.setText(getString(R.string.ues_finger));
    mGestureContainer.setVisibility(View.GONE);
    image.setVisibility(View.VISIBLE);
    image.setImageDrawable(getDrawable(R.mipmap.zhiwen));
    text_forget_gesture.setVisibility(View.GONE);
    usefinger.setVisibility(View.GONE);
    tv_usemima.setVisibility(View.VISIBLE);

}
    public void usemima(){
        mTextTitle.setText(getString(R.string.use_mima));
        mGestureContainer.setVisibility(View.VISIBLE);
        image.setVisibility(View.GONE);
        text_forget_gesture.setVisibility(View.VISIBLE);
        usefinger.setVisibility(View.VISIBLE);
        tv_usemima.setVisibility(View.GONE);
    }
    private void setUpListeners() {
        mTextCancel.setOnClickListener(this);
        mTextForget.setOnClickListener(this);
        mTextOther.setOnClickListener(this);
        usefinger.setOnClickListener(this);
        text_forget_gesture.setOnClickListener(this);
        tv_usemima.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_cancel:
                this.finish();
                break;
            case R.id.tv_usemima:
                usemima();
                break;
            case R.id.tv_usefinger:
                if (isFingerprint()) {
                    startListen();

                    Toast.makeText(GestureVerifyActivity.this,"��ʼ��ָ֤��",Toast.LENGTH_SHORT).show();
                }
        else{
            initLowSDK();
        }
                break;
            case  R.id.text_forget_gesture:
                SharedPreferences preferences=getSharedPreferences("message", Context.MODE_PRIVATE);
                String password=preferences.getString("password", "");
                if (password==null||password==""){
                    Toast.makeText(GestureVerifyActivity.this,"δ��������",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(GestureVerifyActivity.this,"������:" + password,Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
