package com.jni.reactnative.agora;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.helloworld.R;

public class DemoActivity extends FragmentActivity implements OnClickListener {
    private String TAG = "KONGZHONGSDK";
    private Activity mActivity;

    private Button LoginButton;
    private Button logoutButton;
    private Button setInfoButton;
    private Button payButton;
    private Button shareButton;
    private Button inviteButton;
    private Button showAdButton;
    private Button exitButton;

    private TextView userName;
    private TextView uid;
    private TextView token;
    private TextView channel;
    private TextView channlelabel;

    String YOUR_PLACEMENT_ID = "YOUR_PLACEMENT_ID";
    private int mTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mActivity = this;

        LoginButton = (Button) findViewById(R.id.btn_Login);
        logoutButton = (Button) findViewById(R.id.btn_logout);
        setInfoButton = (Button) findViewById(R.id.btn_setInfo);
        payButton = (Button) findViewById(R.id.btn_pay);
        shareButton = (Button) findViewById(R.id.btn_share);
        inviteButton = (Button) findViewById(R.id.btn_invite);
        showAdButton = (Button) findViewById(R.id.btn_showAd);
        exitButton = (Button) findViewById(R.id.btn_exit);

        userName = (TextView) findViewById(R.id.tv_username);
        uid = (TextView) findViewById(R.id.tv_uid);
        token = (TextView) findViewById(R.id.tv_token);
        channel = (TextView) findViewById(R.id.tv_channel);
        channlelabel = (TextView) findViewById(R.id.tv_channellabel);

        LoginButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        setInfoButton.setOnClickListener(this);
        showAdButton.setOnClickListener(this);
        payButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);
        inviteButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

        ShowDialog(DemoActivity.this, "ReactNative调用Android接口");
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btn_Login:
                this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                    }
                });
                break;

            case R.id.btn_logout:
                break;

            case R.id.btn_setInfo:
                break;

            case R.id.btn_pay:
                break;

            case R.id.btn_share:
                break;

            case R.id.btn_invite:
                break;

            case R.id.btn_showAd:
                break;

            case R.id.btn_exit:
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Log.i(TAG, " click back key");
            mActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                }
            });
        }
        return super.onKeyDown(keyCode, event);
    }



    private void ShowDialog(Context context, String text) {
        new AlertDialog.Builder(context).setTitle("提示")//设置对话框标题
                .setMessage(text)//设置显示的内容
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        // TODO Auto-generated method stub
                    }
                }).show();//在按键响应事件中显示此对话框
    }

    public void onStart() {
        super.onStart();
    }

    //Activity开始和用户交互的时候调用
    public void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity///onResume");
    }

    //Activity被暂停时调用(一个Activity转到另一个Activity调用)
    public void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity///onPause");
    }

    public void onStop() {
        super.onStop();
    }

    public void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    //Activity被从内存中移除，一般发生在执行finish方法时或者Android回收内存的时候
    public void onDestroy() {
        super.onDestroy();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "MainActivity///onActivityResult");
    }
}
