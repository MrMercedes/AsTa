package com.example.hp0331.asta.bannerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.hp0331.asta.R;
import com.example.hp0331.asta.TenStory.Story2Activity;
import com.example.hp0331.asta.TenStory.StoryOneActivity;
import com.example.hp0331.asta.TenStory.guaguaka.GuaGuaKaActivity;
import com.example.hp0331.asta.coordinator.CoordinatorActivity;
import com.example.hp0331.asta.view.BannerBean;
import com.example.hp0331.asta.view.BannerView;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {
    BannerView mBannerView;
    private int ids[] = new int[]{R.mipmap.happy1,R.mipmap.ds5,R.mipmap.ds6,
            R.mipmap.ds7,R.mipmap.ds8,R.mipmap.ds9,R.mipmap.ds10,R.mipmap.ds11,R.mipmap.ds12,R.mipmap.ds13,R.mipmap.ds14,
            R.mipmap.ds15,R.mipmap.ds16,R.mipmap.ds17,R.mipmap.ds18,R.mipmap.ds19,R.mipmap.ds20,R.mipmap.ds21,R.mipmap.ds22,
            R.mipmap.ds23,R.mipmap.ds24,R.mipmap.ds25,R.mipmap.ds26,R.mipmap.ds27,R.mipmap.ds28,R.mipmap.ds29};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        mBannerView=(BannerView)findViewById(R.id.bv_bannerview);
        List<BannerBean> mList = new ArrayList<BannerBean>();
        for(int i = 0 ;i<ids.length;i++){
            BannerBean bean = new BannerBean();
            bean.setType(0);
            bean.setDrawableforint(ids[i]);
            mList.add(bean);
        }
        mBannerView.setData(mList);
        mBannerView.setItemClickListener(new BannerView.ItemClickListener() {
            @Override
            public void click(View view, BannerBean bean, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(BannerActivity.this, Story2Activity.class));
                        break;
                    case 1:
                        startActivity(new Intent(BannerActivity.this, StoryOneActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(BannerActivity.this, GuaGuaKaActivity.class));
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }

        });
    }
}
