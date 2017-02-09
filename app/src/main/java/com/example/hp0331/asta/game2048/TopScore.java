package com.example.hp0331.asta.game2048;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * �������ڱ���Ͷ�ȡ��߷�
 * Created by hp0331 on 2017/2/9.
 */

public class TopScore {
    private SharedPreferences sp;


    public TopScore(Context context){
        //��ȡperference�ļ������û�У���ᴴ��һ����ΪTopScore���ļ�
        sp = context.getSharedPreferences("TopScore", context.MODE_PRIVATE);
    }

    /**
     * ���ڶ�ȡ��߷�
     * @return ��߷�
     */
    public int getTopScode(){
        //��ȥ����TopScore����Ӧ��ֵ
        int topScore = sp.getInt("TopScore", 0);
        return topScore;
    }

    /**
     * ����д����߷�
     * @param topScore �µ���߷�
     */
    public void setTopScode(int topScore){
        //ʹ��Editor��д��perference�ļ�
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("TopScore", topScore);
        editor.commit();
    }
}
