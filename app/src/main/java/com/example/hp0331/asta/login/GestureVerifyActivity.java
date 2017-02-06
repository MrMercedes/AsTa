package com.example.hp0331.asta.login;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
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

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class GestureVerifyActivity extends AppCompatActivity implements View.OnClickListener{
    /** 手机号码*/
    public static final String PARAM_PHONE_NUMBER = "PARAM_PHONE_NUMBER";
    /** 意图 */
    public static final String PARAM_INTENT_CODE = "PARAM_INTENT_CODE";

    private static final String DIALOG_FRAGMENT_TAG = "myFragment";
    private static final String SECRET_MESSAGE = "Very secret message";
    /** Alias for our key in the Android Key Store */
    private static final String KEY_NAME = "mykey";

    private static final int FINGERPRINT_PERMISSION_REQUEST_CODE = 0;

    private RelativeLayout mTopLayout;
    private TextView mTextTitle;
    private TextView mTextCancel;
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

    KeyguardManager mKeyguardManager;
    FingerprintAuthenticationDialogFragment mFragment;
    KeyStore mKeyStore;
    KeyGenerator mKeyGenerator;
    Cipher mCipher;
    SharedPreferences mSharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_verify);
        ObtainExtraData();
        init();
        setUpViews();
        setUpListeners();
    }
    private void init() {
        mKeyguardManager = getSystemService(KeyguardManager.class);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mFragment = new FingerprintAuthenticationDialogFragment();
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            throw new RuntimeException("Failed to get an instance of KeyStore", e);
        }
        try {
            mKeyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to get an instance of KeyGenerator", e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException("Failed to get an instance of KeyGenerator", e);
        }
        try {
            mCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to get an instance of Cipher", e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get an instance of Cipher", e);
        }
    }
    private void ObtainExtraData() {
        mParamPhoneNumber = getIntent().getStringExtra(PARAM_PHONE_NUMBER);
        mParamIntentCode = getIntent().getIntExtra(PARAM_INTENT_CODE, 0);
    }
    public void onPurchased(boolean withFingerprint) {
        if (withFingerprint) {
            // If the user has authenticated with fingerprint, verify that using
            // cryptography and
            // then show the confirmation message.
            tryEncrypt();
        } else {
            // Authentication happened with backup password. Just show the
            // confirmation message.

        }
    }
    // Show confirmation, if fingerprint was used show crypto information.

    /**
     * Tries to encrypt some data with the generated key in {@link #createKey}
     * which is only works if the user has just authenticated via fingerprint.
     */
    private void tryEncrypt() {
        try {
            byte[] encrypted = mCipher.doFinal(SECRET_MESSAGE.getBytes());

        } catch (BadPaddingException e) {
            Toast.makeText(this, "Failed to encrypt the data with the generated key. " + "Retry the purchase",
                    Toast.LENGTH_LONG).show();
            Log.e(TAG, "Failed to encrypt the data with the generated key." + e.getMessage());
        } catch (IllegalBlockSizeException e) {
            Toast.makeText(this, "Failed to encrypt the data with the generated key. " + "Retry the purchase",
                    Toast.LENGTH_LONG).show();
            Log.e(TAG, "Failed to encrypt the data with the generated key." + e.getMessage());
        }
    }
    public boolean createKey() {
        // The enrolling flow for fingerprint. This is where you ask the user to
        // set up fingerprint
        // for your flow. Use of keys is necessary if you need to know if the
        // set of
        // enrolled fingerprints has changed.
        try {
            mKeyStore.load(null);
            // Set the alias of the entry in Android KeyStore where the key will
            // appear
            // and the constrains (purposes) in the constructor of the Builder
            mKeyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    // Require the user to authenticate with a
                    // fingerprint to authorize every use
                    // of the key
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
            mKeyGenerator.generateKey();
            return true;
        } catch (IllegalStateException e) {
            // This happens when no fingerprints are registered.
            Toast.makeText(this, "Go to 'Settings -> Security -> Fingerprint' and register at least one fingerprint",
                    Toast.LENGTH_LONG).show();
            return false;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    }

    private String getProtectedMobile(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(phoneNumber.subSequence(0,3));
        builder.append("****");
        builder.append(phoneNumber.subSequence(7,11));
        return builder.toString();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_cancel:
                this.finish();
                break;
            case R.id.tv_usefinger:
                if (needfinger()){
                    if (initCipher()) {

                        // Show the fingerprint dialog. The user has the option
                        // to use the fingerprint with
                        // crypto, or you can fall back to using a server-side
                        // verified password.
                        mFragment.setCryptoObject(new FingerprintManager.CryptoObject(mCipher));
                        boolean useFingerprintPreference = mSharedPreferences
                                .getBoolean(getString(R.string.use_fingerprint_to_authenticate_key), true);
                        if (useFingerprintPreference) {
                            mFragment.setStage(FingerprintAuthenticationDialogFragment.Stage.FINGERPRINT);
                        } else {
                            mFragment.setStage(FingerprintAuthenticationDialogFragment.Stage.PASSWORD);
                        }
                        mFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
                    } else {
                        // This happens if the lock screen has been disabled or
                        // or a fingerprint got
                        // enrolled. Thus show the dialog to authenticate with
                        // their password first
                        // and ask the user if they want to authenticate with
                        // fingerprints in the
                        // future
                        mFragment.setCryptoObject(new FingerprintManager.CryptoObject(mCipher));
                        mFragment.setStage(FingerprintAuthenticationDialogFragment.Stage.NEW_FINGERPRINT_ENROLLED);
                        mFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
                    }
                }else {
                    Toast.makeText(getBaseContext(), getString(R.string.notsupportfingerprint), Toast.LENGTH_SHORT).show();
                }
            default:
                break;
        }
    }
    FingerprintManager manager;
    KeyguardManager keyManager;
    public boolean needfinger(){
        boolean isneed=false;
        int currentapiVersion=android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion>22){
            manager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
            keyManager= (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            //硬件设备是否支持指纹解锁功能
            if (!manager.isHardwareDetected()) {
                return isneed;
            }else {
                isneed=true;
            }
        }
        return isneed;
    }
    private boolean initCipher() {
        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
            mCipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        } catch (CertificateException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        } catch (UnrecoverableKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to init Cipher", e);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to init Cipher", e);

        } catch (InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);

        }
    }
}
