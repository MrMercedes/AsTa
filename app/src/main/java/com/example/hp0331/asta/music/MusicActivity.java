package com.example.hp0331.asta.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hp0331.asta.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MusicActivity extends AppCompatActivity {
    private ListView musicListView = null;
    private ImageView imageView = null;
    private ArrayList<Map<String, Object>> listems = null;//��Ҫ��ʾ��listview�����Ϣ
    private ArrayList<MusicMedia> musicList = null; //������Ϣ�б�
    //    private ImageButton btn_previous = null,btn_play_pause = null,btn_next = null;
    private ImageView btn_play_pause = null;
    public static SeekBar audioSeekBar = null;//���������
    public static TextView textView = null;
    private Intent intent = null;
    private int currentposition = -1;//��ǰ�����б�����������
    private boolean isplay = false;//�����Ƿ��ڲ���
    private MusicPlayerService musicPlayerService = null;
    private MediaPlayer mediaPlayer = null;
    private Handler handler = null;//���������£�seekbar ,textview
    private boolean isservicerunning = false;//�˳�Ӧ���ٽ���ʱ�����appͼ�������֪ͨ�����service��ʹ�ã��жϷ����Ƿ�������
    private SingleMusicInfo singleMusicInfo = null;//���ֵ���ϸ��Ϣ
    private boolean isExit = false;//���ؼ�
    private TextView playtime ,totaltime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Log.i("MusicPlayerService", "MusicActivity...onCreate........." + Thread.currentThread().hashCode());
//        init();
    }

    private void init() {

        intent = new Intent();
        intent.setAction("player");
        intent.setPackage(getPackageName());

        handler = new Handler();
        imageView = (ImageView)findViewById(R.id.click_share);
        imageView.setVisibility(View.INVISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,"��ô����");
                shareIntent.setType("text/plain");
                //���÷����б�
                startActivity(Intent.createChooser(shareIntent,"����"));
            }
        });

        textView  = (TextView)findViewById(R.id.musicinfo);
        playtime=(TextView)findViewById(R.id.playtime);
        totaltime=(TextView)findViewById(R.id.totaletime);
        musicListView = (ListView)findViewById(R.id.musicListView);

//        btn_previous = (ImageButton)findViewById(R.id.previous);
        //������ͣʱҪ�л�ͼ��
//        btn_play_pause = (ImageButton)findViewById(R.id.play_pause);
        btn_play_pause = (ImageView)findViewById(R.id.play_pause);
//        btn_next = (ImageButton)findViewById(R.id.next);

        musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //����������֣�������Ҫ�ж�һ�µ�ǰ�Ƿ��������ڲ��ţ���Ҫ�ر����ڲ��ŵ�
                //position ���Ի�ȡ�����������һ����ȥ musicList ��Ѱ�Ҳ���
                currentposition = position;
                player(currentposition);
            }
        });

        musicList  = scanAllAudioFiles();
        //������ʵ����ֱ����ɨ��ʱ���� ArrayList<Map<String, Object>>()
        listems = new ArrayList<Map<String, Object>>();
        for (Iterator iterator = musicList.iterator(); iterator.hasNext();) {
            Map<String, Object> map = new HashMap<String, Object>();
            MusicMedia mp3Info = (MusicMedia) iterator.next();
//            map.put("id",mp3Info.getId());
            map.put("title", mp3Info.getTitle());
            map.put("artist", mp3Info.getArtist());
            map.put("album", mp3Info.getAlbum());
//            map.put("albumid", mp3Info.getAlbumId());
            map.put("duration", mp3Info.getTime());
            map.put("size", mp3Info.getSize());
            map.put("url", mp3Info.getUrl());

            map.put("bitmap", R.mipmap.musicfile);

            listems.add(map);

        }

        /*SimpleAdapter�Ĳ���˵��
         * ��һ������ ��ʾ��������androidӦ�ó���ӿڣ����������е��������Ҫ
         * �ڶ���������ʾ����һ��Map(String ,Object)�б�ѡ��
         * ������������ʾ���沼�ֵ�id  ��ʾ���ļ���Ϊ�б�������
         * ���ĸ�������ʾ��Map�������Щkey��Ӧvalue�������б���
         * �����������ʾ��������� Map����key��Ӧ����Դһ���������� ˳���ж�Ӧ��ϵ
         * ע�����map�������key�����Ҳ��� ������ı���Ҫ����Դ���  ��Ϊ �Ҳ���keyҲ�᷵��null ��ʵ���൱�ڸ���һ��null��Դ
         * ����ĳ�������� new String[] { "name", "head", "desc","name" } new int[] {R.id.name,R.id.head,R.id.desc,R.id.head}
         * ���head������ᱻname��Դ����
         * */
        SimpleAdapter mSimpleAdapter = new SimpleAdapter(
                this,
                listems,
                R.layout.music_item,
                new String[] {"bitmap","title","artist", "size","duration"},
                new int[] {R.id.video_imageView,R.id.video_title,R.id.video_singer,R.id.video_size,R.id.video_duration}
        );
        //listview���������
        musicListView.setAdapter(mSimpleAdapter);

        //������
        audioSeekBar = (SeekBar) findViewById(R.id.seekBar);

        //�˳����ٴν�ȥ����ʱ�����������ֳ�������
        if(MusicPlayerService.mediaPlayer!=null){
            reinit();//����ҳ�沼���Լ��������
        }

        //���Ž��ȼ� ��ʹ�þ�̬����ʱ������Service���滹�и�������ˢ��
        audioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (currentposition == -1) {
                    Log.i("MusicPlayerService", "MusicActivity...showInfo(��ѡ��Ҫ���ŵ�����);.........");
                    //��û��ѡ��Ҫ���ŵ�����
                    showInfo("��ѡ��Ҫ���ŵ�����");
                } else {
                    //����ı�Դ���û��϶�
                    if (fromUser) {
                        //�����и����⣬�������ʱ�û��Ͻ���������˵�������������ͣʱ��������Զ����ţ����Ի���Ҫ��ͼ������һ��
                        btn_play_pause.setBackgroundResource(R.mipmap.pause);
                        MusicPlayerService.mediaPlayer.seekTo(progress);// ����������ֵ�ı�ʱ�����ֲ��������µ�λ�ÿ�ʼ����
                    }

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                }

//                MusicPlayerService.mediaPlayer.pause(); // ��ʼ�϶�������ʱ��������ͣ����
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
//                MusicPlayerService.mediaPlayer.start(); // ֹͣ�϶�������ʱ�����ֿ�ʼ����
            }
        });

        //textView ����������ֵ���ϸ��Ϣ
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("MusicPlayerService", "MusicActivity...textView.setOnClickListener;.........");
                if (textView.getText().length() > 0) {
                    singleMusicInfo = new SingleMusicInfo(MusicActivity.this,listems.get(currentposition));
                    //��ʾ����
                    singleMusicInfo.showAtLocation(MusicActivity.this.findViewById(R.id.contentmusic), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //����layout��PopupWindow����ʾ��λ��
                }
            }

        });
        Log.i("MusicPlayerService", "MusicActivity...init done;.........");

    }

    private void reinit() {
        //���ý��������ֵ
//        audioSeekBar.setMax(MusicPlayerService.mediaPlayer.getDuration());
//        audioSeekBar.setProgress(MusicPlayerService.mediaPlayer.getCurrentPosition());
//        currentposition = MusicPlayerService.getCurposition();
        Log.i("MusicPlayerService","reinit.........");
        isservicerunning = true;
        //��������ڲ���
        if(MusicPlayerService.mediaPlayer.isPlaying()){
            isplay = true;
            btn_play_pause.setBackgroundResource(R.mipmap.pause);
        }
        //���°�service
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    /*����ý��������Ƶ*/
    public ArrayList<MusicMedia> scanAllAudioFiles(){
        //���ɶ�̬���飬����ת������
        ArrayList<MusicMedia> mylist = new ArrayList<MusicMedia>();

        /*��ѯý�����ݿ�
        �����ֱ�Ϊ��·����Ҫ��ѯ��������������䣬��������������
        ��Ƶ��MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        ͼƬ;MediaStore.Images.Media.EXTERNAL_CONTENT_URI

         */
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        //����ý�����ݿ�
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                //�������
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                //��������
                String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                //������ר������MediaStore.Audio.Media.ALBUM
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                //�����ĸ������� MediaStore.Audio.Media.ARTIST
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                //�����ļ���·�� ��MediaStore.Audio.Media.DATA
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                //�������ܲ���ʱ�� ��MediaStore.Audio.Media.DURATION
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                //�����ļ��Ĵ�С ��MediaStore.Audio.Media.SIZE
                Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));


                if (size >1024*800){//����800K
                    MusicMedia musicMedia = new MusicMedia();
                    musicMedia.setId(id);
                    musicMedia.setArtist(artist);
                    musicMedia.setSize(size);
                    musicMedia.setTitle(tilte);
                    musicMedia.setTime(duration);
                    musicMedia.setUrl(url);
                    musicMedia.setAlbum(album);
                    musicMedia.setAlbumId(albumId);

                    mylist.add(musicMedia);

                }
                cursor.moveToNext();
            }
        }
        return mylist;
    }

    public void previous(View view) {
        previousMusic();
    }



    public void play_pause(View view) {
        Log.i("MusicPlayerService", "MusicActivity...play_pause........." +isplay);
        //��ǰ��pause��ͼ��,��ʹ��ͼ�����ж��Ƿ񲥷ţ��Ͳ���Ҫ���¶������Ϊ״̬��,��ʾû���ҵ��õ���ǰ������ͼƬ�ģ�ʵ���ϲ����ŵģ���ͣ
//        if(btn_play_pause.getBackground().getCurrent().equals(R.drawable.play)){
        if(isservicerunning){//���������ţ�������������ͣ��ťʱֻ��Ҫ��ǰ������ͣ���߲��žͺ�
            if (isplay) {
                pause();
            } else {
                //��ͣ--->��������
                player("2");
            }
        }else {
            if (isplay) {
                pause();
            } else {
                Log.i("MusicPlayerService", "MusicActivity...notplay.........");
                //��ǰ��play��ͼ��,�� ��ͣ �ŵ�
                //��ʼ��ʱ��û�е���б�ֱ�ӵ���˲��Ű�ť
                if (currentposition == -1) {
                    showInfo("��ѡ��Ҫ���ŵ�����");
                } else {
                    //��ͣ--->��������
                    player("2");
                }
            }
        }

    }

    public void next(View view) {
        nextMusic();
    }

    private void player() {
        player(currentposition);
    }

    private void player(int position){

        textView.setText(musicList.get(position).getTitle()+"   playing...");

        intent.putExtra("curposition", position);//��λ�ô���ȥ������������ʱ����
        intent.putExtra("url", musicList.get(position).getUrl());
        intent.putExtra("MSG","0");
        isplay = true;
        //����ʱ�͸ı�btn_play_pauseͼ�꣬�������������
//        btn_play_pause.setBackgroundDrawable(getResources().getDrawable(R.drawable.pause));
        btn_play_pause.setBackgroundResource(R.mipmap.pause);

        startService(intent);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        Log.i("MusicPlayerService","MusicActivity...bindService.......");

    }
    private ServiceConnection conn = new ServiceConnection() {
        /** ��ȡ�������ʱ�Ĳ��� */
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            musicPlayerService = ((MusicPlayerService.musicBinder)service).getPlayInfo();
            mediaPlayer = musicPlayerService.getMediaPlayer();
            Log.i("MusicPlayerService", "MusicActivity...onServiceConnected.......");
            currentposition = musicPlayerService.getCurposition();
            //���ý��������ֵ
            audioSeekBar.setMax(mediaPlayer.getDuration());
            //���￪��һ���̴߳��������,�����ʽ�ٷ�ò�Ʋ��Ƽ���˵Υ��ʲô���߳�ʲô��
//            new Thread(seekBarThread).start();
            //ʹ��runnable + handler
            handler.post(seekBarHandler);
        }

        /** �޷���ȡ���������ʱ�Ĳ��� */
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            musicPlayerService = null;
        }

    };

    //1s����һ�ν�����
    Runnable seekBarThread = new Runnable() {
        @Override
        public void run() {
            while (musicPlayerService != null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                Log.i("MusicPlayerService", "seekBarThread run.......");

                audioSeekBar.setProgress(musicPlayerService.getCurrentPosition());
                // Only the original thread that created a view hierarchy can touch its views.
           /*     textView.setText(musicList.get(currentposition).getTitle()+"       "+
                        musicPlayerService.toTime(musicPlayerService.getCurrentPosition() )+
                        "  / "+musicPlayerService.toTime(musicPlayerService.getDuration() ));
                        */
            }
        }
    };
    Runnable seekBarHandler = new Runnable() {
        @Override
        public void run() {

            Log.i("MusicPlayerService", "MusicActivity...seekBarHandler run......."+Thread.currentThread().hashCode()+" "+handler.hashCode());
            audioSeekBar.setProgress(musicPlayerService.getCurrentPosition());
            textView.setText( "(Click Me)  "+ musicList.get(currentposition).getTitle());
            playtime.setText(musicPlayerService.toTime(musicPlayerService.getCurrentPosition()) );
            totaltime.setText(musicPlayerService.toTime(musicPlayerService.getDuration()));
            handler.postDelayed(seekBarHandler, 1000);

        }
    };


    private  void player(String info){

        intent.putExtra("MSG",info);
        isplay = true;
        btn_play_pause.setBackgroundResource(R.mipmap.pause);
        startService(intent);

    }
    /*
    * MSG :
    *  0  δ����--->����
    *  1    ����--->��ͣ
    *  2    ��ͣ--->��������
    *
    * */
    private void pause() {
        intent.putExtra("MSG","1");
        isplay = false;
        btn_play_pause.setBackgroundResource(R.mipmap.play);
        startService(intent);
    }
    private void previousMusic() {
        if(currentposition > 0){
            currentposition -= 1;
            player();
        }else{
            showInfo("�Ѿ��ǵ�һ��������");
        }
    }

    private void nextMusic() {
        if(currentposition < musicList.size()-2){
            currentposition += 1;
            player();
        }else{
            showInfo("�Ѿ������һ��������");
        }
    }

    private void showInfo(String info) {
        Toast.makeText(this,info,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MusicPlayerService", "MusicActivity...onResume........." + Thread.currentThread().hashCode());
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MusicPlayerService", "MusicActivity...onPause........." + Thread.currentThread().hashCode());
        //�󶨷�����
        if(musicPlayerService != null){
            unbindService(conn);
        }
        handler.removeCallbacks(seekBarHandler);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(conn);
        Log.i("MusicPlayerService", "MusicActivity...onDestroy........." + Thread.currentThread().hashCode());
    }
//    private void exit(String info) {
//        if(!isExit) {
//            isExit = true;
//            Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    isExit = false;
//                }
//            }, 2000);
//        } else {
//            finish();
//        }
//    }
    //�����η��ؼ��˳�
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getRepeatCount() == 0) {
//            //���ַ��������ˣ�������֪ͨ��
//            if(musicPlayerService != null){
//                exit("�ٰ�һ������");
//            }else{
//                exit("�ٰ�һ���˳�");
//            }
//
//        }
//        return false;
//    }

}
