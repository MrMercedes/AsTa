package com.example.hp0331.asta.music;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.hp0331.asta.R;

import java.util.Map;

/**
 * Created by hp0331 on 2017/2/7.
 */

public class SingleMusicInfo extends PopupWindow {
    private View view;
    private TableLayout tableLayout;

    public SingleMusicInfo(Context context, Map<String, Object> map) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.singlemusicinfo, null);
        //ȡ����ť
      /*  view.findViewById(R.id.singlemusicinfo_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //���ٵ�����
                dismiss();
            }
        });*/

        tableLayout = (TableLayout) view.findViewById(R.id.tablelayout);

        map.remove("bitmap");//�Ƴ�ͼƬ�Ǹ���ֵ��
        for(String keys : map.keySet()){
            TableRow tableRow = new TableRow(context);
            TextView key = new TextView(context);
            TextView value = new TextView(context);
            Log.i("MusicPlayerService", "SingleMusicInfo..........." + tableRow.hashCode());
            key.setText("   " + keys + "    ");
            value.setText(map.get(keys).toString());
            tableRow.addView(key);
            tableRow.addView(value);
            tableLayout.addView(tableRow);
        }

        //����SingleMusicInfo��View
        this.setContentView(view);
        //���õ�������Ŀ�
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //���õ�������ĸ�
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //����S��������ɵ��
        this.setFocusable(true);
        //ʵ����һ��ColorDrawable��ɫΪ��͸��
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
        ColorDrawable dw = new ColorDrawable(Color.rgb(255,228,181));
        //���õ�������ı���
        this.setBackgroundDrawable(dw);
        //view���OnTouchListener�����жϻ�ȡ����λ�������ѡ������������ٵ�����
        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.tablelayout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
}
