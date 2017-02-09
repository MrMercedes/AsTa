package com.example.hp0331.asta.game2048;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 该类用于保存和读取最高分
 * Created by hp0331 on 2017/2/9.
 */

public class TopScore {
    private SharedPreferences sp;


    public TopScore(Context context){
        //读取perference文件，如果没有，则会创建一个名为TopScore的文件
        sp = context.getSharedPreferences("TopScore", context.MODE_PRIVATE);
    }

    /**
     * 用于读取最高分
     * @return 最高分
     */
    public int getTopScode(){
        //对去键“TopScore”对应的值
        int topScore = sp.getInt("TopScore", 0);
        return topScore;
    }

    /**
     * 用于写入最高分
     * @param topScore 新的最高分
     */
    public void setTopScode(int topScore){
        //使用Editor类写入perference文件
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("TopScore", topScore);
        editor.commit();
    }
}
