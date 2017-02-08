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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public ArrayList<String> datas = null;
    public MyAdapter(ArrayList<String> datas) {
        this.datas = datas;
    }
    //������View����LayoutManager������
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        return new ViewHolder(view);
    }
    //�������������а󶨵Ĳ���
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTextView.setText(datas.get(position));
    }
    //��ȡ���ݵ�����
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //�Զ����ViewHolder������ÿ��Item�ĵ����н���Ԫ��
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);
        }
    }
}
