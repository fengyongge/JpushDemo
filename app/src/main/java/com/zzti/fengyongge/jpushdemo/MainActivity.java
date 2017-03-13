package com.zzti.fengyongge.jpushdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.zzti.fengyongge.jpushdemo.util.ExampleUtil;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends AppCompatActivity {

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;

    private final Handler mHandler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            // final JSONObject object = (JSONObject) msg.obj;
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    try {
                        Log.i("fyg","添加别名开始");
                        JPushInterface.setAliasAndTags(getApplicationContext(),(String) msg.obj, null, mAliasCallback);
                    } catch (Exception e) {
                    }
//                    JPushInterface.setAliasAndTags(getApplicationContext(),(String) msg.obj, null, mAliasCallback);
                    break;
                case MSG_SET_TAGS:
                    try {
                        Logger.i("添加tag开始");
                        JPushInterface.setAliasAndTags(getApplicationContext(), null,
                                (Set<String>) msg.obj, mAliasCallback);
                    } catch (Exception e) {

                    }
                    break;
                default:
                    Logger.i("Unhandled msg - " + msg.what);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void rigist(){
        String getRegistrationID= JPushInterface.getRegistrationID(getApplicationContext());
        setAlias("");
        setTag("");
    }


    private void setAlias(String alias) {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private void setTag(String tag) {
        tag = tag.replace("[", "");
        tag = tag.replace("]", "");
        tag = tag.replace("\"", "");
        String[] sArray = tag.split(",");
        Set<String> tagSet = new LinkedHashSet<String>();
        for (String sTagItme : sArray) {
            // if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
            // Toast.makeText(MainActivity.this,"tag不合法",
            // Toast.LENGTH_SHORT).show();
            // return;
            // }
//            Logger.i("sTagItme:"+sTagItme);
            tagSet.add(sTagItme);
        }
        // 调用JPush API设置Tag
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;

            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Logger.i(logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Logger.i(logs);
                    if (ExampleUtil.isConnected(getApplicationContext())) {

                        mHandler.sendMessageDelayed(
                                mHandler.obtainMessage(MSG_SET_ALIAS, alias),
                                1000 * 60);
                    } else {
                        logs = "No network";
                        Logger.i(logs);
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Logger.i(logs);
            }
            // ExampleUtil.showToast(logs, getApplicationContext());
        }
    };
}
