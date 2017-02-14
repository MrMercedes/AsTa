package com.example.hp0331.asta;

import android.app.Application;

import com.testfairy.TestFairy;

/**
 * Created by hp0331 on 2017/2/6.
 */

public class MyApplication extends Application {
    public static boolean isfirst=true;

    @Override
    public void onCreate() {
        super.onCreate();
        TestFairy.begin(this,"11cb9c0c648d9d55ac62d94487744436e83dd8f4");
    }
}
