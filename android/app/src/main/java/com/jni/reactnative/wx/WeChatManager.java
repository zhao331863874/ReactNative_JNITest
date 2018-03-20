package com.jni.reactnative.wx;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.Callback;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by HDS on 2018/3/13.
 */

public class WeChatManager {
    private Context mContext;
    private static final String TAG = WeChatManager.class.getSimpleName();
    public static WeChatManager mWeChatManager;

    //微信开放后台分配的APPID
    static private String APP_ID = null;
    //第三方app和微信通信的openapi接口
    static private IWXAPI api = null;


    private WeChatManager() {

    }

    public static WeChatManager getInstance() {
        if (mWeChatManager == null) {
            if (mWeChatManager == null) {
                mWeChatManager = new WeChatManager();
            }
        }
        return mWeChatManager;
    }

    // 初始化微信
    public void initWX(Context context, String appId) {
        Log.d(TAG, "===initWX///appid="+appId);
        this.mContext = context;
        this.APP_ID = appId;

        //获取IWXAPI实例
        api = WXAPIFactory.createWXAPI(mContext, APP_ID, false);
        //注册到微信
        api.registerApp(APP_ID);

        showToast("初始化成功");
    }

    // 登录微信
    public void login() {
        Log.d(TAG, "===login");

        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo"; //应用授权作用域(获取用户个人信息)
        req.state = "none"; //用于保持请求和回调的状态，授权请求后原样带回给第三方
        api.sendReq(req);
    }



    public void isWXAppInstalled(Callback callback) {
        callback.invoke(null, api.isWXAppInstalled());
    }

    public void isWXAppSupportApi(Callback callback) {
        callback.invoke(null, api.isWXAppSupportAPI());
    }

    public final void showToast(final String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }


}
