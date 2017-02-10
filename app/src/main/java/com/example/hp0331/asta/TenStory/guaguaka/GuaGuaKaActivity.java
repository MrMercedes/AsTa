package com.example.hp0331.asta.TenStory.guaguaka;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.hp0331.asta.R;


/**
 * Created by hp0331 on 2016/12/8.
 */

public class GuaGuaKaActivity extends Activity {
    public final int GGKDONE=0;
    Context mContext;
    private GuaGuaKa ggk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guaguaka);
        mContext=this.getApplicationContext();
        ggk=(GuaGuaKa)findViewById(R.id.view_ggk);
        ggk.sethandler(new Myhandler());
    }



    public class Myhandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case  GGKDONE:
//            startActivity(new Intent(GuaGuaKaActivity.this,WuZiQiActivity.class));
//                    GuaGuaKaActivity.this.finish();
                    break;

            }
        }
    }
}
