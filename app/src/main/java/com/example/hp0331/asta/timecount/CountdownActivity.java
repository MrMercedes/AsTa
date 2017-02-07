package com.example.hp0331.asta.timecount;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp0331.asta.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hp0331 on 2017/2/7.
 */

public class CountdownActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();// ȫ��handler

    Button btn_choose;
    private String date;
    int time = 0;// ʱ���
    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        txt=(TextView)findViewById(R.id.txt);
        btn_choose=(Button)this.findViewById(R.id.btn_choose);
        btn_choose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(CountdownActivity.this,TimeCountActivity.class);
                startActivity(intent);
                //MainActivity.this.finish();
            }
        });
        date = 2017 + "-" + (getDateFormat(1 + 1)) + "-" + getDateFormat(14) + " " + getDateFormat(0) + ":" + getDateFormat(0) + ":00";

        time = getTimeInterval(date)-getAlarmTiqian("0");// ��ȡʱ���

        new Thread(new TimeCount()).start();// �����߳�
    }
    class TimeCount implements Runnable
    {
        @Override
        public void run()
        {
            while (time > 0)// ��������ʱִ�е�ѭ��
            {
                time--;
                mHandler.post(new Runnable() // ͨ������UI���߳����޸���ʾ��ʣ��ʱ��
                {
                    public void run()
                    {
                        txt.setText(getInterval(time));// ��ʾʣ��ʱ��
                    }
                });
                try
                {
                    Thread.sleep(1000);// �߳�����һ���� ������ǵ���ʱ�ļ��ʱ��
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            // �����ǵ���ʱ�����߼�
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    txt.setText("�趨��ʱ�䵽��");
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
     * ��ȡ�������ڵ�ʱ���
     */
    public static int getTimeInterval(String data)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int interval = 0;
        try
        {
            Date currentTime = new Date();// ��ȡ���ڵ�ʱ��
            Date beginTime = dateFormat.parse(data);
            interval = (int) ((beginTime.getTime() - currentTime.getTime()) / (1000));// ʱ��� ��λ��
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return interval;
    }

    /**
     * ��ȡ��ǰ���ѵ�����
     */
    public int getAlarmTiqian(String tiqian)
    {
        int time=0;

        return time;
    }

    /**
     * �趨��ʾ����
     */
    public static String getInterval(int time) {
        String txt = null;
        if (time >= 0)
        {
            long day = time / (24 * 3600);// ��
            long hour = time % (24 * 3600) / 3600;// Сʱ
            long minute = time % 3600 / 60;// ����
            long second = time % 60;// ��

            txt =" ������μ��滹�У�" + day + "��" + hour + "Сʱ" + minute + "��" + second + "��";
        }
        else
        {
            txt="�ѹ���";
        }
        return txt;
    }
}
