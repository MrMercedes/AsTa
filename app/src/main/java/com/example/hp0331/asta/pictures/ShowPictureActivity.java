package com.example.hp0331.asta.pictures;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp0331.asta.R;

import java.io.File;
import java.io.IOException;

public class ShowPictureActivity extends AppCompatActivity {
    private final String IMAGE_TYPE = "image/*";

    private final int IMAGE_CODE = 0;   //�����IMAGE_CODE���Լ����ⶨ���

    private Button addPic=null,showPicPath=null;

    private ImageView imgShow=null;

    private TextView imgPath=null;

    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);
        init();
        setImage();

    }
    private void init() {
        // TODO Auto-generated method stub

        addPic=(Button) findViewById(R.id.btnClose);
        showPicPath=(Button) findViewById(R.id.btnSend);
        imgPath=(TextView) findViewById(R.id.img_path);
        imgShow=(ImageView) findViewById(R.id.imgShow);

        addPic.setOnClickListener(listener);

        showPicPath.setOnClickListener(listener);
        imgShow.setOnClickListener(listener);
        imgShow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                shareSingleImage(v,path);
                return false;
            }
        });

    }
    private View.OnClickListener listener=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            switch(v.getId()){
                case R.id.imgShow:

                    break;
                case R.id.btnClose:
                    setImage();
                    break;

                case R.id.btnSend:

                    break;
            }

        }

    };
    /**
     * ������ͼƬ
     *
     * @param view
     * @param imagePath  ͼƬ·��
     */
    public void shareSingleImage(View view, String imagePath) {
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Log.d("share", "uri:" + imageUri);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        //��ת���������   ���÷������
        startActivity(Intent.createChooser(shareIntent, "����"));
    }
    public void setImage() {
        // TODO Auto-generated method stub
        //ʹ��intent����ϵͳ�ṩ����Ṧ�ܣ�ʹ��startActivityForResult��Ϊ�˻�ȡ�û�ѡ���ͼƬ
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType(IMAGE_TYPE);
        startActivityForResult(getAlbum, IMAGE_CODE);


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (resultCode != RESULT_OK) {        //�˴��� RESULT_OK ��ϵͳ�Զ����һ������

            Log.e("TAG->onresult","ActivityResult resultCode error");

            return;

        }



        Bitmap bm = null;



        //���ĳ������ContentProvider���ṩ���� ����ͨ��ContentResolver�ӿ�

        ContentResolver resolver = getContentResolver();



        //�˴��������жϽ��յ�Activity�ǲ�������Ҫ���Ǹ�

        if (requestCode == IMAGE_CODE) {

            try {

                Uri originalUri = data.getData();        //���ͼƬ��uri



                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                //�Եõ�bitmapͼƬ
                imgShow.setImageBitmap(bm);


//    ���￪ʼ�ĵڶ����֣���ȡͼƬ��·����



                String[] proj = {MediaStore.Images.Media.DATA};



                //������android��ý�����ݿ�ķ�װ�ӿڣ�����Ŀ�Android�ĵ�

                Cursor cursor = managedQuery(originalUri, proj, null, null, null);

                //���Ҹ������ ����ǻ���û�ѡ���ͼƬ������ֵ

                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                //�����������ͷ ���������Ҫ����С�ĺ���������Խ��

                cursor.moveToFirst();

                //����������ֵ��ȡͼƬ·��

                 path = cursor.getString(column_index);
                imgPath.setText(path);
            }catch (IOException e) {

                Log.e("TAG-->Error",e.toString());

            }

        }

    }
}
