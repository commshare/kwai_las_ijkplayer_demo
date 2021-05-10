package com.github.isuperred.ijkplayerdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import tv.danmaku.ijk.media.example.application.Settings;
import tv.danmaku.ijk.media.example.widget.media.AndroidMediaController;
import tv.danmaku.ijk.media.example.widget.media.IjkVideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {
    private IjkVideoView mVideoView;
    private AndroidMediaController mMediaController;
    private TextView mTextView;
    private String mVideoPath;
    private TableLayout mHudView;

    private Settings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startVideo();
        return;
       // https://blog.csdn.net/colinandroid/article/details/72781498?utm_source=blogxgwz1

    }
    private void onStartAPP()
    {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("tv.danmaku.ijk.media.example.application",
                "tv.danmaku.ijk.media.example.application.AppActivity"));
        startActivity(intent);
    }
    private void startVideo() {
        String manifest_string =
                "{\n" +
                        "    \"version\": \"1.0.0\",\n" +
                        "    \"adaptationSet\": [\n" +
                        "        {\n" +
                        "            \"duration\": 1000,\n" +
                        "            \"id\": 1,\n" +
                        "            \"representation\": [\n" +
                        "                {\n" +
                        "                    \"id\": 1,\n" +
                        "                    \"codec\": \"avc1.64001e,mp4a.40.5\",\n" +
                        "                    \"url\": \"http://las-tech.org.cn/kwai/las-test_ld500d.flv\",\n" +
                        "                    \"backupUrl\": [],\n" +
                        "                    \"host\": \"las-tech.org.cn\",\n" +
                        "                    \"maxBitrate\": 700,\n" +
                        "                    \"width\": 640,\n" +
                        "                    \"height\": 360,\n" +
                        "                    \"frameRate\": 25,\n" +
                        "                    \"qualityType\": \"SMOOTH\",\n" +
                        "                    \"qualityTypeName\": \"流畅\",\n" +
                        "                    \"hidden\": false,\n" +
                        "                    \"disabledFromAdaptive\": false,\n" +
                        "                    \"defaultSelected\": false\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"id\": 2,\n" +
                        "                    \"codec\": \"avc1.64001f,mp4a.40.5\",\n" +
                        "                    \"url\": \"http://las-tech.org.cn/kwai/las-test_sd1000d.flv\",\n" +
                        "                    \"backupUrl\": [],\n" +
                        "                    \"host\": \"las-tech.org.cn\",\n" +
                        "                    \"maxBitrate\": 1300,\n" +
                        "                    \"width\": 960,\n" +
                        "                    \"height\": 540,\n" +
                        "                    \"frameRate\": 25,\n" +
                        "                    \"qualityType\": \"STANDARD\",\n" +
                        "                    \"qualityTypeName\": \"标清\",\n" +
                        "                    \"hidden\": false,\n" +
                        "                    \"disabledFromAdaptive\": false,\n" +
                        "                    \"defaultSelected\": true\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"id\": 3,\n" +
                        "                    \"codec\": \"avc1.64001f,mp4a.40.5\",\n" +
                        "                    \"url\": \"http://las-tech.org.cn/kwai/las-test.flv\",\n" +
                        "                    \"backupUrl\": [],\n" +
                        "                    \"host\": \"las-tech.org.cn\",\n" +
                        "                    \"maxBitrate\": 2300,\n" +
                        "                    \"width\": 1280,\n" +
                        "                    \"height\": 720,\n" +
                        "                    \"frameRate\": 30,\n" +
                        "                    \"qualityType\": \"HIGH\",\n" +
                        "                    \"qualityTypeName\": \"高清\",\n" +
                        "                    \"hidden\": false,\n" +
                        "                    \"disabledFromAdaptive\": false,\n" +
                        "                    \"defaultSelected\": false\n" +
                        "                }\n" +
                        "            ]\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}";

        mSettings = new Settings(this);
        mVideoPath =manifest_string;// "rtmp://192.168.50.199:1935/live/stream";//"http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8";

        mMediaController = new AndroidMediaController(this, false);

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        // 报错，恢复了xml再打开
        mVideoView = (IjkVideoView) findViewById(R.id.videoView);
        mHudView = (TableLayout) findViewById(R.id.hud_view);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setHudView(mHudView);

        if (TextUtils.isEmpty(mVideoPath)) {
            Toast.makeText(this,
                    "No Video Found! Press Back Button To Exit",
                    Toast.LENGTH_LONG).show();
        } else {
            //mVideoView.setVideoURI(Uri.parse(mVideoPath));
            //for las
            mVideoView.setVideoPath(mVideoPath);
            mVideoView.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mVideoView.stopPlayback();
        mVideoView.release(true);
        mVideoView.stopBackgroundPlay();
        IjkMediaPlayer.native_profileEnd();
    }
}
