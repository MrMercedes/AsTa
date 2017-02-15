package com.example.hp0331.asta.paodayangren;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.hp0331.asta.R;

public class PaodDaYangRenActivity extends AppCompatActivity {
    ChessView cv;
    final int MENU_START= Menu.FIRST;
    final int MENU_BIN=Menu.FIRST+1;
    final int MENU_PAO=Menu.FIRST+2;
    final int MENU_HELP=Menu.FIRST+3;
    final int MENU_QUIT=Menu.FIRST+4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //����ȫ��
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_paod_da_yang_ren);
        cv = (ChessView) findViewById(R.id.myview);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,MENU_START, 0, "��ʼ��Ϸ");
        menu.add(0,MENU_BIN, 0, "ѡ��С��");
        menu.add(0,MENU_PAO, 0, "ѡ�����");
        menu.add(0,MENU_HELP, 0, "��Ϸ����");
        menu.add(0,MENU_QUIT, 0, "�˳�");
        return true;

    }
    /* (non-Javadoc)
 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case MENU_START:
                cv.init();
                break;
            case MENU_HELP:
                Intent in = new Intent();
                in.setClass(PaodDaYangRenActivity.this, AboutActivy.class);
                startActivity(in);
                break;
            case MENU_QUIT:
                openDialog();
                break;
            case MENU_BIN:
                cv.choiceBin();
                break;
            case MENU_PAO:
                cv.choicePao();
                break;
        }

        return true;
    }

    /**
     * �û�����˳��Ի���
     *@author chenbo
     *@date 2010-12-17
     */
    private void openDialog(){
        AlertDialog.Builder bl = new AlertDialog.Builder(this);
        bl.setTitle("�˳���Ϸ");
        bl.setMessage("ȷ��Ҫ�˳���Ϸ��");
        bl.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        bl.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        bl.show();
    }
}
