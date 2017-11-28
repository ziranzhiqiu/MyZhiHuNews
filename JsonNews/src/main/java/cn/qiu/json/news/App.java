package cn.qiu.json.news;

import android.app.Application;
import android.content.Context;

import cn.qiu.json.news.utils.AppContextUtil;
import cn.qiu.json.news.utils.LogUtils;


public class App extends Application {

    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this;
        AppContextUtil.init(this);
        LogUtils.init();
    }

    // 获取ApplicationContext
    public static Context getContext() {
        return mApplicationContext;
    }
}
