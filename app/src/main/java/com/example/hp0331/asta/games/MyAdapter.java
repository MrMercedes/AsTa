package com.example.hp0331.asta.games;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp0331.asta.R;

import java.util.ArrayList;

/**
 * Created by hp0331 on 2017/2/8.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{
    public ArrayList<String> datas = null;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public MyAdapter(ArrayList<String> datas) {
        this.datas = datas;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTextView.setText(datas.get(position));
        viewHolder.itemView.setTag(datas.get(position));
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);

        }
    }
    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }
}
