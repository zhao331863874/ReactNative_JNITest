package com.jni.reactnative;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.jni.reactnative.agora.VideoChatViewActivity;
import com.jni.reactnative.agora.VoiceChatViewActivity;
import com.jni.reactnative.wx.WeChatManager;

import java.util.HashMap;
import java.util.Map;

import io.agora.rtc.RtcEngine;

/**
 * Created by HDS on 2018/3/10.
 */

public class MyNativeModule extends ReactContextBaseJavaModule {
    private final static String MODULE_NAME = "RCTKONGZHONG_RNJNI";
    private static final  String TestEvent = "TestEvent";
    private ReactApplicationContext mContext;
    private static final String LOG_TAG = MyNativeModule.class.getSimpleName();

    // 声网所需
    private RtcEngine mRtcEngine;// Tutorial Step 1

    public MyNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("SHORT", Toast.LENGTH_SHORT);
        constants.put("LONG", Toast.LENGTH_LONG);
        constants.put("NATIVE_MODULE_NAME", MODULE_NAME);
        constants.put(TestEvent, TestEvent);
        return constants;
    }

    @ReactMethod
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

    @ReactMethod
    public void showToast(String msg, int duration){
        Toast.makeText(mContext, msg, duration).show();
    }

    /****************************声网SDK接口********************************/
    // Tutorial Step 1
    @ReactMethod
    public void init(String appid) {
        //创建 RtcEngine 对象（初始化）
        JNIManager.getInstance().init(getReactApplicationContext(), appid);
    }

    /** 加入频道
     * @param token token
     * @param channelName  标识通话的频道名称
     * @param optionalInfo 附加信息
     * @param optionalUid  用户ID
     */
    // Tutorial Step 2
    @ReactMethod
    public void joinChannel(String token, String channelName, String optionalInfo, int optionalUid) {
        //加入频道
        JNIManager.getInstance().joinChannel(token, channelName, optionalInfo, optionalUid);
    }

    // Tutorial Step 3
    @ReactMethod
    public void leaveChannel() {
        //离开频道
        JNIManager.getInstance().leaveChannel();
    }

    // Tutorial Step 4
    @ReactMethod
    public void onSwitchSpeakerphone(boolean enabled) {
        //扬声器开关
        JNIManager.getInstance().onSwitchSpeakerphone(enabled);
    }

    // Tutorial Step 5
    @ReactMethod
    public void onSwitchMicrophone(boolean enabled) {
        //麦克风开关
        JNIManager.getInstance().onSwitchMicrophone(enabled);
    }

    /****************************视频相关接口********************************/
    /** 打开视频
     * @param profile 视频属性
     * @param swapWidthAndHeight 是否交换宽和高
     */
    @ReactMethod
    public void openVideo(int profile, boolean swapWidthAndHeight) {
        //打开视频
        JNIManager.getInstance().openVideo(profile, swapWidthAndHeight);
    }

    @ReactMethod
    public void onSwitchCamera() {
        //切换前置/后置摄像头
        JNIManager.getInstance().onSwitchCamera();
    }

    @ReactMethod
    public void muteLocalVideoStream(boolean muted) {
        //暂停本地视频流
        JNIManager.getInstance().muteLocalVideoStream(muted);
    }

    /****************************微信SDK接口********************************/
    @ReactMethod
    public void initWX(String appid) {
        WeChatManager.getInstance().initWX(getReactApplicationContext(), appid);
    }

    @ReactMethod
    public void login() {
        WeChatManager.getInstance().login();
    }
}
