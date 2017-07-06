package com.zzti.fengyongge.jpushdemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zzti.fengyongge.jpushdemo.MessageActivity;
import com.zzti.fengyongge.jpushdemo.util.PreferencesUtils;
import com.zzti.fengyongge.jpushdemo.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

//	extras:
//	key:cn.jpush.android.ALERT, value:6666
//	key:cn.jpush.android.EXTRA, value:{"notify":"fyg"}
//	key:cn.jpush.android.NOTIFICATION_ID, value:1456866604
//	key:cn.jpush.android.NOTIFICATION_CONTENT_TITLE, value:如意社
//	key:cn.jpush.android.MSG_ID, value:1456866604

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...


        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

            try {
                boolean isLogin =  PreferencesUtils.getBoolean(context,"login",false);
                if(true){
                    JPushInterface.clearNotificationById(context,bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));//清除notification
                    Intent intent1 = new Intent(context, MessageActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );//context启动activity时需要创建堆栈
                    context.startActivity(intent1);
                }else{
//                    Intent intent2 = new Intent(context, LoginActivity.class);
//                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//                    context.startActivity(intent2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


////			{"id": 2,"type_id":1,"intro":"234","title":"小胡苏宁","source_id":"2016061418333064109","context": "3"}
//            //1订单2消息公告3我的订单
//            JSONObject jsonObject;
//            JSONObject jsonObject1 = null;
//            try {
//                jsonObject = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
//                jsonObject1 = new JSONObject(jsonObject.getString("notice"));
//
//                if(jsonObject1.getString("type_id").equals("1")){
////                    JPushInterface.clearNotificationById(context,bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));//清除notification
////                    Intent intent1 = new Intent(context, OrderSearchActivity.class);
////                    intent1.putExtra("search_key", jsonObject1.getString("source_id"));
////                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
////                    context.startActivity(intent1);
//                }else if(jsonObject1.getString("type_id").equals("2")){
////                    JPushInterface.clearNotificationById(context,bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));//清除notification
////                    Intent intent1 = new Intent(context, MessageManageActivity.class);
////                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
////                    context.startActivity(intent1);
//
//                }else if(jsonObject1.getString("type_id").equals("3")){
////                    JPushInterface.clearNotificationById(context,bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));//清除notification
////                    Intent intent1 = new Intent(context, MyOrderCenterActivity.class);
////                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
////                    context.startActivity(intent1);
//                }else {
//                    JPushInterface.clearNotificationById(context,bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));//清除notification
//                    Intent intent1 = new Intent(context, MessageActivity.class);
//                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//                    intent1.putExtra("isNew",true);
//                    context.startActivity(intent1);
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }


        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            }
            else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MessageActivity
    private void processCustomMessage(Context context, Bundle bundle) {

        if (MessageActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(MessageActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(MessageActivity.KEY_MESSAGE, message);
            if (!StringUtils.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (null != extraJson && extraJson.length() > 0) {
                        msgIntent.putExtra(MessageActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            context.sendBroadcast(msgIntent);
        }

    }


}
