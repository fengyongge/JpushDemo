package com.zzti.fengyongge.jpushdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.orhanobut.logger.Logger;

public class MessageActivity extends AppCompatActivity {
    public static final String MESSAGE_RECEIVED_ACTION = "com.diankai.meetting.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static boolean isForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }


    /**
     *注册极光
     */
    public void registerMessageReceiver() {
//        MessageReceiver  mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(messageReceiver, filter);
    }

    BroadcastReceiver messageReceiver =new  BroadcastReceiver (){
        @Override
        public void onReceive(Context context, Intent intent) {

            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                Logger.i("-接受推送消息--"+KEY_MESSAGE + " : " + messge + "\n");
//                if (!ExampleUtil.isEmpty(extras)) {
//                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
//                }
//                setCostomMsg(showMsg.toString());
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (messageReceiver != null) {
            try {
                MessageActivity.this.getApplicationContext().unregisterReceiver(messageReceiver);
            } catch (IllegalArgumentException e) {

                if (e.getMessage().contains("Receiver not registered")) {
                    // Ignore this exception. This is exactly what is desired
                } else {
                    // unexpected, re-throw
                    throw e;
                }
            }
        }

    }
}
