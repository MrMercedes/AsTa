package com.example.hp0331.asta.bannerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.hp0331.asta.R;
import com.example.hp0331.asta.TenStory.StoryOneActivity;
import com.example.hp0331.asta.TenStory.guaguaka.GuaGuaKaActivity;
import com.example.hp0331.asta.coordinator.CoordinatorActivity;
import com.example.hp0331.asta.view.BannerBean;
import com.example.hp0331.asta.view.BannerView;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {
    BannerView mBannerView;
    private int ids[] = new int[]{R.mipmap.pic1,R.mipmap.pic2, R.mipmap.pic3,R.mipmap.pic4,R.mipmap.pic5,
            R.mipmap.pic6,R.mipmap.pic7,R.mipmap.pic8, R.mipmap.pic9,R.mipmap.pic10,};
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
                        startActivity(new Intent(BannerActivity.this, StoryOneActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(BannerActivity.this, GuaGuaKaActivity.class));
                        break;
                    case 2:
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
