package com.example.hp0331.asta.games;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.hp0331.asta.R;



import java.util.ArrayList;

public class GamesListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ArrayList<String> listData;
    private int refreshTime = 0;
    private int times = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView)this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

//        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
//        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                refreshTime ++;
//                times = 0;
//                new Handler().postDelayed(new Runnable(){
//                    public void run() {
//
//                        listData.clear();
//                        for(int i = 0; i < 15 ;i++){
//                            listData.add("Îå×ÓÆå" + i + "after " + refreshTime + " times of refresh");
//                        }
//                        mAdapter.notifyDataSetChanged();
//                        mRecyclerView.refreshComplete();
//                    }
//
//                }, 1000);            //refresh data here
//            }
//
//            @Override
//            public void onLoadMore() {
//                if(times < 2){
//                    new Handler().postDelayed(new Runnable(){
//                        public void run() {
//                            mRecyclerView.loadMoreComplete();
//                            for(int i = 0; i < 15 ;i++){
//                                listData.add("item" + (i + listData.size()) );
//                            }
//                            mRecyclerView.loadMoreComplete();
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    }, 1000);
//                } else {
//                    new Handler().postDelayed(new Runnable() {
//                        public void run() {
//                            for(int i = 0; i < 9 ;i++){
//                                listData.add("item" + (1 + listData.size() ) );
//                            }
//                            mRecyclerView.setNoMore(true);
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    }, 1000);
//                }
//                times ++;
//            }
//        });

        listData = new ArrayList<String>();
        for(int i = 0; i < 15 ;i++){
            listData.add("item" + i);
        }
        mAdapter = new MyAdapter(listData);
        mRecyclerView.setAdapter(mAdapter);
    }
}
