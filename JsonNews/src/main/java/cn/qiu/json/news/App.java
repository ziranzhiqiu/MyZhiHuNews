package cn.qiu.json.news;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.qiu.json.news.utils.AppContextUtil;


public class App extends Application {

    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this;
        AppContextUtil.init(this);
        Logger.addLogAdapter(new AndroidLogAdapter());

        initJGuang();
    }

    private void initJGuang() {
        //初始化sdk
        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了
        Set<String> set = new HashSet<>();
        set.add("JsonQIu");//名字任意，可多添加几个
        JPushInterface.setTags(this, set, null);//设置标签
    }

    // 获取ApplicationContext
    public static Context getContext() {
        return mApplicationContext;
    }
}
