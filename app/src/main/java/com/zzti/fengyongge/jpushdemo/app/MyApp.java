package com.zzti.fengyongge.jpushdemo.app;

import android.app.Application;
import android.app.Notification;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.zzti.fengyongge.jpushdemo.R;



import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by fengyongge on 2017/3/11.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);//jpush
        JPushInterface.init(this);

        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.statusBarDrawable = R.mipmap.ic_launcher;
//        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND| Notification.DEFAULT_VIBRATE;  // 设置为铃声与震动都要
        JPushInterface.setPushNotificationBuilder(1, builder);

        Logger.init("fyg").logLevel(LogLevel.FULL);//debug
    }
}
