package com.example.hp0331.asta.timecount;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.hp0331.asta.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeCountActivity extends AppCompatActivity {
    private NumberPicker mDateSpinner;
    private NumberPicker mHourSpinner;
    private NumberPicker mMinuteSpinner;
    private TextView txt, timeCount,showtiqian;
    private Button btn_start,btn_tiqian;
    private Calendar mDate;
    private int mYear, mMonth, mDay;
    private int mHour, mMinute;
    private String date;
    private Handler mHandler = new Handler();// ȫ��handler
    int time = 0;// ʱ���
    private String[] mDateDisplayValues = new String[7];
    private String[] week =	{ "������", "����һ", "���ڶ�", "������", "������", "������", "������" };
    private static String[] remind = new String[]{"׼ʱ����","��ǰ5����", "��ǰ10����", "��ǰ30����", "��ǰ1��Сʱ", "��ǰ2��Сʱ", "��ǰ1��" };
    private RadioOnClick radioOnClick = new RadioOnClick(0);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_time_count);
        super.onCreate(savedInstanceState);

        mDate = Calendar.getInstance();
        mYear = mDate.get(Calendar.YEAR);
        mMonth = mDate.get(Calendar.MONTH);
        mDay = mDate.get(Calendar.DAY_OF_MONTH);
        mHour = mDate.get(Calendar.HOUR_OF_DAY);
        mMinute = mDate.get(Calendar.MINUTE);

        mDateSpinner = (NumberPicker) this.findViewById(R.id.np1);
        mDateSpinner.setMaxValue(6);
        mDateSpinner.setMinValue(0);
        updateDateControl();
        mDateSpinner.setOnValueChangedListener(mOnDateChangedListener);

        mHourSpinner = (NumberPicker) this.findViewById(R.id.np2);
        mHourSpinner.setMaxValue(23);
        mHourSpinner.setMinValue(0);
        mHourSpinner.setValue(mHour);
        mHourSpinner.setOnValueChangedListener(mOnHourChangedListener);

        mMinuteSpinner = (NumberPicker) this.findViewById(R.id.np3);
        mMinuteSpinner.setMaxValue(59);
        mMinuteSpinner.setMinValue(0);
        mMinuteSpinner.setValue(mMinute);
        mMinuteSpinner.setOnValueChangedListener(mOnMinuteChangedListener);

        txt = (TextView) this.findViewById(R.id.txt);
        timeCount = (TextView) this.findViewById(R.id.showcount);
        showtiqian=(TextView)this.findViewById(R.id.showtiqian);
        showtiqian.setText(remind[0]);//��ʼ������

        btn_tiqian=(Button)this.findViewById(R.id.btn_tiqian);
        btn_tiqian.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showAlarmDialog();
            }
        });

        btn_start = (Button) this.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                time = getTimeInterval(date)-getAlarmTiqian(showtiqian.getText().toString());// ��ȡʱ���

                new Thread(new TimeCount()).start();// �����߳�
            }
        });
        updateDateTime();
    }

    private NumberPicker.OnValueChangeListener mOnDateChangedListener = new NumberPicker.OnValueChangeListener()
    {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal)
        {
            mDate.add(Calendar.DAY_OF_MONTH, newVal - oldVal);
            updateDateControl();
            updateDateTime();
        }
    };

    private NumberPicker.OnValueChangeListener mOnHourChangedListener = new NumberPicker.OnValueChangeListener()
    {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal)
        {
            mHour = mHourSpinner.getValue();
            updateDateTime();
        }
    };

    private NumberPicker.OnValueChangeListener mOnMinuteChangedListener = new NumberPicker.OnValueChangeListener()
    {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal)
        {
            mMinute = mMinuteSpinner.getValue();
            updateDateTime();
        }
    };

    private String getWeek()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);
        int number = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return week[number];
    }

    private void updateDateControl()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mDate.getTimeInMillis());
        cal.add(Calendar.DAY_OF_YEAR, -7 / 2 - 1);
        mDateSpinner.setDisplayedValues(null);
        for (int i = 0; i < 7; ++i)
        {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            mDateDisplayValues[i] = (String) DateFormat.format("MM.dd EEEE", cal);
        }
        mDateSpinner.setDisplayedValues(mDateDisplayValues);
        mDateSpinner.setValue(7 / 2);
        mDateSpinner.invalidate();
    }

    public void updateDateTime()
    {
        mYear = mDate.get(Calendar.YEAR);
        mMonth = mDate.get(Calendar.MONTH);
        mDay = mDate.get(Calendar.DAY_OF_MONTH);
        mHour = mHourSpinner.getValue();
        mMinute = mMinuteSpinner.getValue();

        date = mYear + "-" + (getDateFormat(mMonth + 1)) + "-" + getDateFormat(mDay) + " " + getDateFormat(mHour) + ":" + getDateFormat(mMinute) + ":00";

        txt.setText("��ѡ����ǣ�" + date + " " + getWeek());
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

    private void showAlarmDialog()
    {
        AlertDialog ad =new AlertDialog.Builder(TimeCountActivity.this).setTitle("ѡ������ʱ��").setSingleChoiceItems(remind,radioOnClick.getIndex(),radioOnClick).create();
        ad.getListView();
        ad.show();
    }
    /**
     * �����ѡ���¼�
     * @author xmz
     *
     */
    class RadioOnClick implements DialogInterface.OnClickListener
    {
        private int index;

        public RadioOnClick(int index)
        {
            this.index = index;
        }
        public void setIndex(int index)
        {
            this.index=index;
        }
        public int getIndex(){
            return index;
        }
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            setIndex(which);
            showtiqian.setText(remind[index]);
            dialog.dismiss();
        }
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
                        timeCount.setText(getInterval(time));// ��ʾʣ��ʱ��
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
                    timeCount.setText("�趨��ʱ�䵽��");
                }
            });
        }
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
        if (tiqian.equals(remind[0]))
        {
            time=0;//׼ʱ����
        }
        else if(tiqian.equals(remind[1]))
        {
            time=5*60;//��ǰ5����
        }
        else if(tiqian.equals(remind[2]))
        {
            time=10*60;//��ǰ10����
        }
        else if(tiqian.equals(remind[3]))
        {
            time=30*60;//��ǰ30����
        }
        else if(tiqian.equals(remind[4]))
        {
            time=60*60;//��ǰ1��Сʱ
        }
        else if(tiqian.equals(remind[5]))
        {
            time=2*60*60;//��ǰ2��Сʱ
        }
        else{
            time=24*60*60;//��ǰһ��
        }
        return time;
    }

    /**
     * �趨��ʾ����
     */
    public static String getInterval(int time)
    {
        String txt = null;
        if (time >= 0)
        {
            long day = time / (24 * 3600);// ��
            long hour = time % (24 * 3600) / 3600;// Сʱ
            long minute = time % 3600 / 60;// ����
            long second = time % 60;// ��

            txt =" �������ڻ��У�" + day + "��" + hour + "Сʱ" + minute + "��" + second + "��";
        }
        else
        {
            txt="�ѹ���";
        }
        return txt;
    }
}
