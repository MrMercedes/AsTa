package com.example.hp0331.asta.coordinator;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp0331.asta.R;
import com.example.hp0331.asta.TenStory.StoryOneActivity;
import com.example.hp0331.asta.bannerview.BannerActivity;
import com.example.hp0331.asta.games.GamesListActivity;
import com.example.hp0331.asta.music.MusicActivity;
import com.example.hp0331.asta.timecount.CountdownActivity;
import com.example.hp0331.asta.timecount.TimeCountActivity;
import com.example.hp0331.asta.view.BannerBean;
import com.example.hp0331.asta.view.BannerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hp0331 on 2017/2/6.
 */

public class CoordinatorActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView mHeadIv;
    private long latestBackTime = 0;
    private static final long WAIT_TIME = 1500;
    private CoordinatorMenu mCoordinatorMenu;
    private LinearLayout ll_bpic,ll_games;
    Button btn_choose;
    private Handler mHandler = new Handler();// 全局handler
    private BannerView mBannerView;
    private String date;
    int time = 0;// 时间差
    private TextView txt;
    LinearLayout ll_music;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        initview();
        setlinstener();
        date = 2017 + "-" + (getDateFormat(1 + 1)) + "-" + getDateFormat(14) + " " + getDateFormat(0) + ":" + getDateFormat(0) + ":00";

        time = getTimeInterval(date)-getAlarmTiqian("0");// 获取时间差

        new Thread(new TimeCount()).start();// 开启线程

    }
    public void initview(){
        mCoordinatorMenu = (CoordinatorMenu) findViewById(R.id.menu);
        ll_music=(LinearLayout)findViewById(R.id.ll_music);
        txt=(TextView)findViewById(R.id.txt);
        btn_choose=(Button)this.findViewById(R.id.btn_choose);
        mHeadIv = (ImageView) findViewById(R.id.iv_head);
        ll_bpic=(LinearLayout)findViewById(R.id.ll_bpic);
        ll_games=(LinearLayout)findViewById(R.id.ll_games);
    }
    public void setlinstener(){
        ll_music.setOnClickListener(this);
        btn_choose.setOnClickListener(this);
        mHeadIv.setOnClickListener(this);
        ll_bpic.setOnClickListener(this);
        ll_games.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_music:
                startActivity(new Intent(CoordinatorActivity.this, MusicActivity.class));
                break;
            case R.id.btn_choose:
                Intent intent=new Intent(CoordinatorActivity.this,TimeCountActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_head:
                if (mCoordinatorMenu.isOpened()) {
                    mCoordinatorMenu.closeMenu();
                } else {
                    mCoordinatorMenu.openMenu();
                }
                break;
            case R.id.ll_bpic:
                startActivity(new Intent(CoordinatorActivity.this, BannerActivity.class));
                break;
            case R.id.ll_games:
                startActivity(new Intent(CoordinatorActivity.this, GamesListActivity.class));
                break;
        }
    }
    class TimeCount implements Runnable
    {
        @Override
        public void run()
        {
            while (time > 0)// 整个倒计时执行的循环
            {
                time--;
                mHandler.post(new Runnable() // 通过它在UI主线程中修改显示的剩余时间
                {
                    public void run()
                    {
                        txt.setText(getInterval(time));// 显示剩余时间
                    }
                });
                try
                {
                    Thread.sleep(1000);// 线程休眠一秒钟 这个就是倒计时的间隔时间
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            // 下面是倒计时结束逻辑
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    txt.setText("设定的时间到。");
                }
            });
        }
    }
    public String getDateFormat(int time)
    {
        String date = time + "";
        if (time < 10)
        {
            date = "0" + date;
        }
        return date;
    }
    /**
     * 获取两个日期的时间差
     */
    public static int getTimeInterval(String data)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int interval = 0;
        try
        {
            Date currentTime = new Date();// 获取现在的时间
            Date beginTime = dateFormat.parse(data);
            interval = (int) ((beginTime.getTime() - currentTime.getTime()) / (1000));// 时间差 单位秒
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return interval;
    }

    /**
     * 获取提前提醒的秒数
     */
    public int getAlarmTiqian(String tiqian)
    {
        int time=0;

        return time;
    }

    /**
     * 设定显示文字
     */
    public static String getInterval(int time) {
        String txt = null;
        if (time >= 0)
        {
            long day = time / (24 * 3600);// 天
            long hour = time % (24 * 3600) / 3600;// 小时
            long minute = time % 3600 / 60;// 分钟
            long second = time % 60;// 秒

            txt =" 距离情人节还有：" + day + "天" + hour + "小时" + minute + "分" + second + "秒";
        }
        else
        {
            txt="已过期";
        }
        return txt;
    }
    @Override
    public void onBackPressed() {
        if (mCoordinatorMenu.isOpened()) {
            mCoordinatorMenu.closeMenu();
        } else {
            long currentBackTime = System.currentTimeMillis();
            if (currentBackTime - latestBackTime > WAIT_TIME) {
                Toast.makeText(CoordinatorActivity.this,getString(R.string.toast_exit_confirm),Toast.LENGTH_SHORT).show();
                latestBackTime = currentBackTime;
            } else {
                super.onBackPressed();
            }
        }
    }
}
