package com.example.hp0331.asta.games;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.hp0331.asta.R;
import com.example.hp0331.asta.game2048.Game2048Activity;
import com.example.hp0331.asta.wuziqi.WuziqiActivity;


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

        listData = new ArrayList<String>();

        listData.add("五子棋" );
        listData.add("2048" );
        mAdapter = new MyAdapter(listData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                if (data.startsWith("五子棋")){
                startActivity(new Intent(GamesListActivity.this, WuziqiActivity.class));
                }
                if (data.startsWith("2048")){
                    startActivity(new Intent(GamesListActivity.this, Game2048Activity.class));
                }
            }
        });
    }
}
