package com.example.hp0331.asta.music;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.example.hp0331.asta.R;

import java.io.IOException;

/**
 * Created by hp0331 on 2017/2/7.
 */

public class MusicPlayerService extends Service {
    private static final String TAG = "MusicPlayerService";
    private static final int NOTIFICATION_ID = 1; // ���id����Ϊ0,�ᵼ�²�������Ϊǰ̨service
    public static MediaPlayer mediaPlayer = null;
    private String url = null;
    private String MSG = null;
    private static int curposition;//�ڼ�������
    private musicBinder musicbinder = null;
    private int currentPosition = 0;// ����Ĭ�Ͻ�������ǰλ��
    public MusicPlayerService() {
        Log.i(TAG,"MusicPlayerService......1");
        musicbinder = new musicBinder();
    }

    //ͨ��bind ����һ��IBinder����Ȼ��Ķ����������ķ���ʵ�ֲ����Ĵ���
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind......");
        return musicbinder;
    }
    /**
     * �Զ���� Binder����
     */
    public class musicBinder extends Binder {
        public MusicPlayerService getPlayInfo(){
            return MusicPlayerService.this;
        }
    }
    //�õ���ǰ����λ��
    public  int getCurrentPosition(){

        if(mediaPlayer != null){
            int total = mediaPlayer.getDuration();// ��ʱ��
            if( currentPosition < total){
                currentPosition = mediaPlayer.getCurrentPosition();
            }
        }
        return currentPosition;
    }
    //�õ���ǰ����λ��
    public  int getDuration(){
        return mediaPlayer.getDuration();// ��ʱ��
    }

    //�õ� mediaPlayer
    public MediaPlayer getMediaPlayer(){
//        if(mediaPlayer != null){
//            return mediaPlayer;
//        }
        return mediaPlayer;
    }
    //�õ� ��ǰ���ŵڼ�������
    public int getCurposition(){
        return curposition;
    }
    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate......2");
        super.onCreate();
        if (mediaPlayer == null) {
           /* mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;*/
            mediaPlayer = new MediaPlayer();
        }
        // ���������Ƿ����
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //��ĿǰҲ��֪���ø���

            }
        });



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand......3");
        // /storage/emulated/0/Music/Download/Selena Gomez - Revival/Hands to Myself.mp3
        if(intent != null){
            MSG = intent.getStringExtra("MSG");
            if(MSG.equals("0")){
                url = intent.getStringExtra("url");
                curposition = intent.getIntExtra("curposition",0);
                Log.i(TAG, url + "......." + Thread.currentThread().getName());
                palyer();
            }else if(MSG.equals("1")){
                mediaPlayer.pause();
            }else if(MSG.equals("2")){
                mediaPlayer.start();
            }

            String name = "Current: "+ url.substring(url.lastIndexOf("/") + 1 , url.lastIndexOf("."));
            Log.i(TAG,name);
//        //����ǰ̨service
            Notification notification = null;
            if (Build.VERSION.SDK_INT < 16) {
                notification = new Notification.Builder(this)
                        .setContentTitle("Enter the MusicPlayer").setContentText(name)
                        .setSmallIcon(R.mipmap.musicfile).getNotification();
            } else {
                Notification.Builder builder = new Notification.Builder(this);
                PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                        new Intent(this, MusicActivity.class), 0);
                builder.setContentIntent(contentIntent);
                builder.setSmallIcon(R.mipmap.musicfile);
//        builder.setTicker("Foreground Service Start");
                builder.setContentTitle("Enter the MusicPlayer");
                builder.setContentText(name);
                notification = builder.build();
            }

            startForeground(NOTIFICATION_ID, notification);
        }

        return super.onStartCommand(intent, flags, startId);
    }


    private void palyer() {
        Log.i(TAG,"palyer......");
        //������ڲ��ţ���ֹͣ�ٲ����µ�
       /* if(mediaPlayer.isPlaying()){
            Log.i(TAG,"palyer......running....");
            // ��ͣ
            mediaPlayer.pause();
            mediaPlayer.reset();
        }*/
        //���о����û�����ͣ�ǵ�����������֣����Բ��ܵ�ǰ״̬��������һ��
        //������δ������ʵ�ּ򵥵����ֲ���
        try {
//            Log.i(TAG,"palyer......new....");
            mediaPlayer.reset();

            mediaPlayer.setDataSource(url);
            mediaPlayer.setLooping(true);
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
            // ���ý��������ֵ
//            MusicActivity.audioSeekBar.setMax(mediaPlayer.getDuration());
            //�������߳�
//            new Thread(this).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // ˢ�½����� ,ʱ��

/*
    @Override
    public void run() {

        Log.i(TAG,Thread.currentThread().getName()+"......run...");

        int total = mediaPlayer.getDuration();// ��ʱ��
        while (mediaPlayer != null && currentPosition < total) {
            try {
                Thread.sleep(1000);
                if (mediaPlayer != null) {
                    currentPosition = mediaPlayer.getCurrentPosition();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            MusicActivity.audioSeekBar.setProgress(CurrentPosition);

        }


    }
*/


    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG,"onUnbind......");
        return super.onUnbind(intent);

    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG, "onRebind......");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy......");
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        //�ر��߳�
        Thread.currentThread().interrupt();
        stopForeground(true);
    }
    public String toTime(int time){
        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }
}
