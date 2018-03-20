package com.jni.reactnative;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceView;
import android.widget.Toast;

import com.jni.reactnative.agora.VideoChatViewActivity;
import com.jni.reactnative.agora.VoiceChatViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;


/**
 * Created by HDS on 2018/3/13.
 */

public class JNIManager {

    public static JNIManager sAgoraManager;
    private Context mContext;
    private static final String LOG_TAG = JNIManager.class.getSimpleName();

    // 声网所需
    private RtcEngine mRtcEngine;// Tutorial Step 1
    private int mLocalUid = 0;

    private JNIManager() {
        mSurfaceViews = new SparseArray<SurfaceView>();
    }

    private SparseArray<SurfaceView> mSurfaceViews;

    public static JNIManager getInstance() {
        if (sAgoraManager == null) {
            synchronized (JNIManager.class) {
                if (sAgoraManager == null) {
                    sAgoraManager = new JNIManager();
                }
            }
        }
        return sAgoraManager;
    }

    public void startActivity(String name){
        Log.d(LOG_TAG, "===startActivity///name="+name);
        if(name.equals("Voice")) { //判断传入的值
            //打开语音通话Activity
            Intent intent = new Intent(mContext,VoiceChatViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } else if(name.equals("Video")) {
            //打开视频通话Activity
            Intent intent = new Intent(mContext,VideoChatViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    public void showToast(String msg, int duration){
        Toast.makeText(mContext, msg, duration).show();
    }

    // Tutorial Step 1
    public void init(Context context, String appid) {
        Log.d(LOG_TAG, "===init///appid="+appid);
        this.mContext = context;
        try {
            // 创建 RtcEngine 对象（初始化）
            mRtcEngine = RtcEngine.create(context, appid, mRtcEventHandler);
            showLongToast("初始化成功");
        } catch (Exception e) {
            Log.e(LOG_TAG, Log.getStackTraceString(e));

            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() { // Tutorial Step 1

        @Override
        public void onUserOffline(final int uid, final int reason) { // Tutorial Step 4
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showLongToast(String.format(Locale.US, "user %d left %d", (uid & 0xFFFFFFFFL), reason));
                }
            });
        }

        @Override
        public void onUserMuteAudio(final int uid, final boolean muted) { // Tutorial Step 6
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showLongToast(String.format(Locale.US, "user %d muted or unmuted %b", (uid & 0xFFFFFFFFL), muted));
                }
            });
        }
    };



    /** 加入频道
     * @param token token
     * @param channelName  标识通话的频道名称
     * @param optionalInfo 附加信息
     * @param optionalUid  用户ID
     */
    // Tutorial Step 2
    public void joinChannel(String token, String channelName, String optionalInfo, int optionalUid) {
        Log.d(LOG_TAG, "===joinChannel///token="+token+";channelName="+channelName+";optionalInfo="+optionalInfo+";optionalUid="+optionalUid);
        mRtcEngine.joinChannel(token, channelName, optionalInfo, optionalUid); // if you do not specify the uid, we will generate the uid for you
        showLongToast("加入频道："+channelName+" 成功");
    }

    // Tutorial Step 3
    public void leaveChannel() {
        Log.d(LOG_TAG, "===leaveChannel");
        // 离开频道
        mRtcEngine.leaveChannel();
        showLongToast("离开频道");
    }

    // Tutorial Step 4
    public void onSwitchSpeakerphone(boolean enabled) {
        Log.d(LOG_TAG, "===onSwitchSpeakerphone///enabled="+enabled);
        // 打开外放
        mRtcEngine.setEnableSpeakerphone(enabled);
        if(enabled) {
            showLongToast("打开扬声器");
        } else {
            showLongToast("关闭扬声器");
        }
    }

    // Tutorial Step 5
    public void onSwitchMicrophone(boolean enabled) {
        Log.d(LOG_TAG, "===onSwitchMicrophone///enabled="+enabled);
        // 将自己静音
        mRtcEngine.muteLocalAudioStream(enabled);
        if(enabled) {
            showLongToast("打开麦克风");
        } else {
            showLongToast("关闭麦克风");
        }
    }

    /****************************视频相关接口********************************/
    /** 打开视频
     * @param profile 视频属性
     * @param swapWidthAndHeight 是否交换宽和高
     */
    public void openVideo(int profile, boolean swapWidthAndHeight) {
        //打开视频模式
        mRtcEngine.enableVideo();
        //设置本地视频属性
        mRtcEngine.setVideoProfile(profile, swapWidthAndHeight);
    }

    /**
     * 设置本地视频，即前置摄像头预览
     */
    public JNIManager setupLocalVideo() {
        //创建一个SurfaceView用作视频预览
        SurfaceView surfaceView = RtcEngine.CreateRendererView(mContext);
        //将SurfaceView保存起来在SparseArray中，后续会将其加入界面。key为视频的用户id，这里是本地视频, 默认id是0

        mSurfaceViews.put(mLocalUid, surfaceView);

        //设置本地视频，渲染模式选择VideoCanvas.RENDER_MODE_HIDDEN，如果选其他模式会出现视频不会填充满整个SurfaceView的情况，
        //具体渲染模式参考官方文档https://docs.agora.io/cn/user_guide/API/android_api.html#set-local-video-view-setuplocalvideo
        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, mLocalUid));
        return this;//返回AgoraManager以作链式调用
    }

    public JNIManager setupRemoteVideo(int uid) {
        SurfaceView surfaceView = RtcEngine.CreateRendererView(mContext);
        mSurfaceViews.put(uid, surfaceView);
        //设置远端视频显示属性
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, uid));
        return this;
    }

    public void onSwitchCamera() {
        //切换前置/后置摄像头
        mRtcEngine.switchCamera();
    }

    public void muteLocalVideoStream(boolean muted) {
        //暂停本地视频流
        mRtcEngine.muteLocalVideoStream(muted);
    }


    public void removeSurfaceView(int uid) {
        mSurfaceViews.remove(uid);
    }

    public List<SurfaceView> getSurfaceViews() {
        List<SurfaceView> list = new ArrayList<SurfaceView>();
        for (int i = 0; i < mSurfaceViews.size(); i++) {
            SurfaceView surfaceView = mSurfaceViews.valueAt(i);
            list.add(surfaceView);
        }
        return list;
    }

    public SurfaceView getLocalSurfaceView() {
        return mSurfaceViews.get(mLocalUid);
    }

    public SurfaceView getSurfaceView(int uid) {
        return mSurfaceViews.get(uid);
    }



    public final void showLongToast(final String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
}
