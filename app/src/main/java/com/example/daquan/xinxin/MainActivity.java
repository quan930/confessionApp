package com.example.daquan.xinxin;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.daquan.xinxin.heart.TreeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private String message = "***\r\n一次次的邂逅\r\n一次次的心动\r\n希望能在以后的日子\n彼此的视野都有对方\n不计较付出\n不计较收获\n无怨无悔\n从始至终\n坚持心中所想\n相识若相知\n相恋若相依";
    static private MediaPlayer mediaPlayer;
    private TextView textView;
    private TextView time;
    private TreeView treeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        time = findViewById(R.id.time);
        treeView = findViewById(R.id.treeView);
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();

        ViewGroup.LayoutParams params = treeView.getLayoutParams();
        params.height=width/8*7;
        params.width =width/8*7;
        treeView.setLayoutParams(params);

        new Thread(new Runnable() {

            @Override
            public void run() {
                final StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0;i < message.length();i ++){
                    stringBuilder.append(message.charAt(i));
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(stringBuilder);
                        }
                    });
                }
            }
        }).start();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            time.setText(time());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        },0,500);

        mediaPlayer =MediaPlayer.create(this,R.raw.music);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.start();
            }
        }).start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("结束", "onCompletion: ");
            }
        });
    }
    private String time() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date start=df.parse("2017-11-07 12:00:00");
        Date now = new Date();
        long l=now.getTime()-start.getTime();
        long day=l/(24*60*60*1000);
        long hour=(l/(60*60*1000)-day*24);
        long min=((l/(60*1000))-day*24*60-hour*60);
        long s=(l/1000-day*24*60*60-hour*60*60-min*60);
        String mes = "第"+day+"天"+hour+"小时"+min+"分"+s+"秒";
//        System.out.println(mes);
        return mes;
    }
}
