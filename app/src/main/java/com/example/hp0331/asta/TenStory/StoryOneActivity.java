package com.example.hp0331.asta.TenStory;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.hp0331.asta.R;
import com.example.hp0331.asta.view.FlyTxtView;

import java.util.Random;

public class StoryOneActivity extends AppCompatActivity {
    private FlyTxtView tv_fly;
    private FlyTxtView tv_fly1;
    private FlyTxtView tv_fly2;
    private FlyTxtView tv_fly3;
    private FlyTxtView tv_fly4;
    private FlyTxtView tv_fly5;
    private FlyTxtView tv_fly6;
    private FlyTxtView tv_fly7;
    private FlyTxtView tv_fly8;
    private LinearLayout ll_view;
    private Button back;
    private int NO=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_one);
        String [] aaa=new String[]{"人生若只如初见，","何事秋风悲画扇。",
                "等闲变却故人心，","却道故人心易变。",
                "骊山语罢清宵半，","泪雨零铃终不怨。",
                "何如薄幸锦衣郎，","比翼连枝当日愿。"};
        ll_view=(LinearLayout)findViewById(R.id.ll_view);

        Random random1 = new Random();

        int a = 255;
        int r = 50 + random1.nextInt(150);
        int g = 50 + random1.nextInt(150);
        int b= 50 + random1.nextInt(150);
        tv_fly=(FlyTxtView)findViewById(R.id.tv_fly);
        tv_fly.setTextColor(Color.argb(a, r, g, b));
        tv_fly.setTexts("木兰词");
        tv_fly.setTextSize(24);

        int a1 = 255;
        int r1 = 50 + random1.nextInt(150);
        int g1 = 50 + random1.nextInt(150);
        int b1= 50 + random1.nextInt(150);
        tv_fly1=(FlyTxtView)findViewById(R.id.tv_fly1);
        tv_fly1.setTextColor(Color.argb(a1, r1, g1, b1));
        tv_fly1.setTexts(aaa[0]);
        tv_fly1.setTextSize(24);
        tv_fly1.setVisibility(View.INVISIBLE);


        int a2 = 255;
        int r2 = 50 + random1.nextInt(150);
        int g2 = 50 + random1.nextInt(150);
        int b2= 50 + random1.nextInt(150);
        tv_fly2=(FlyTxtView)findViewById(R.id.tv_fly2);
        tv_fly2.setTextColor(Color.argb(a2, r2, g2, b2));
        tv_fly2.setTexts(aaa[1]);
        tv_fly2.setTextSize(24);
        tv_fly2.setVisibility(View.INVISIBLE);


        int a3 = 255;
        int r3 = 50 + random1.nextInt(150);
        int g3 = 50 + random1.nextInt(150);
        int b3= 50 + random1.nextInt(150);
        tv_fly3=(FlyTxtView)findViewById(R.id.tv_fly3);
        tv_fly3.setTextColor(Color.argb(a3, r3, g3, b3));
        tv_fly3.setTexts(aaa[2]);
        tv_fly3.setTextSize(24);
        tv_fly3.setVisibility(View.INVISIBLE);

        int a4 = 255;
        int r4 = 50 + random1.nextInt(150);
        int g4 = 50 + random1.nextInt(150);
        int b4= 50 + random1.nextInt(150);
        tv_fly4=(FlyTxtView)findViewById(R.id.tv_fly4);
        tv_fly4.setTextColor(Color.argb(a4, r4, g4, b4));
        tv_fly4.setTexts(aaa[3]);
        tv_fly4.setTextSize(24);
        tv_fly4.setVisibility(View.INVISIBLE);


        int a5 = 255;
        int r5 = 50 + random1.nextInt(150);
        int g5 = 50 + random1.nextInt(150);
        int b5= 50 + random1.nextInt(150);
        tv_fly5=(FlyTxtView)findViewById(R.id.tv_fly5);
        tv_fly5.setTextColor(Color.argb(a5, r5, g5, b5));
        tv_fly5.setTexts(aaa[4]);
        tv_fly5.setTextSize(24);
        tv_fly5.setVisibility(View.INVISIBLE);


        int a6 = 255;
        int r6 = 50 + random1.nextInt(150);
        int g6 = 50 + random1.nextInt(150);
        int b6= 50 + random1.nextInt(150);
        tv_fly6=(FlyTxtView)findViewById(R.id.tv_fly6);
        tv_fly6.setTextColor(Color.argb(a6, r6, g6, b6));
        tv_fly6.setTexts(aaa[5]);
        tv_fly6.setTextSize(24);
        tv_fly6.setVisibility(View.INVISIBLE);


        int a7 = 255;
        int r7 = 50 + random1.nextInt(150);
        int g7 = 50 + random1.nextInt(150);
        int b7= 50 + random1.nextInt(150);
        tv_fly7=(FlyTxtView)findViewById(R.id.tv_fly7);
        tv_fly7.setTextColor(Color.argb(a7, r7, g7, b7));
        tv_fly7.setTexts(aaa[6]);
        tv_fly7.setTextSize(24);
        tv_fly7.setVisibility(View.INVISIBLE);


        int a8 = 255;
        int r8 = 50 + random1.nextInt(150);
        int g8 = 50 + random1.nextInt(150);
        int b8= 50 + random1.nextInt(150);
        tv_fly8=(FlyTxtView)findViewById(R.id.tv_fly8);
        tv_fly8.setTextColor(Color.argb(a8, r8, g8, b8));
        tv_fly8.setTexts(aaa[7]);
        tv_fly8.setTextSize(24);
        tv_fly8.setVisibility(View.INVISIBLE);



        back=(Button)findViewById(R.id.back);
        back.setVisibility(View.INVISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (NO){
                    case 0:
                        tv_fly1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        tv_fly2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        tv_fly3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        tv_fly4.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        tv_fly5.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        tv_fly6.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        tv_fly7.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        tv_fly8.setVisibility(View.VISIBLE);
                        break;
                    case 8:
                        back.setVisibility(View.VISIBLE);
                        break;

                }
                NO++;

            }
        });


    }

}
