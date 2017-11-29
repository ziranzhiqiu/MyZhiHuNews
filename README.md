

## 实现的功能
已完成
* 日报列表显示
* 日报详情显示
* 下拉加载更多
* 已读内容标记
* 已读内容缓存
* 滑动返回
* 分享
* 夜间模式

##极光推送买坑：
* 1.进入激光官网注册APP，得到APP Key
* 2.module 的 build.gradle中 的 defaultConfig{} 里面添加ndk {
                                                             //选择要添加的对应cpu类型的.so库（不需要的删除即可）。
                                                             abiFilters 'armeabi', 'armeabi-v7a', 'armeabi-v8a', 'x86', 'x86_64', 'mips', 'mips64'
                                                         }

                                                         manifestPlaceholders = [
                                                                 JPUSH_PKGNAME: applicationId,
                                                                 JPUSH_APPKEY : "？？？？c964f81d8b61c066e7888de3？？？？？", //JPush上注册的包名对应的appkey（*换成你的*）
                                                                 JPUSH_CHANNEL: "developer-default", //暂时填写默认值即可.
                                                         ]
*3.清单文件
         permission android:name="cn.qiu.json.news.permission.JPUSH_MESSAGE" android:protectionLevel="signature" />
        uses-permission android:name="cn.qiu.json.news.permission.JPUSH_MESSAGE" />

*4.清单文件
     receiver
       android:name=".MyReceiver"
       android:enabled="true"
       android:exported="false">
       <intent-filter>
           <action android:name="cn.jpush.android.intent.REGISTRATION" /> <!--Required  用户注册SDK的intent-->
           <action android:name="cn.jpush.android.intent.MESSAGE_RECEIVED" /> <!--Required  用户接收SDK消息的intent-->
           <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED" /> <!--Required  用户接收SDK通知栏信息的intent-->
           <action android:name="cn.jpush.android.intent.NOTIFICATION_OPENED" /> <!--Required  用户打开自定义通知栏的intent-->
           <action android:name="cn.jpush.android.intent.ACTION_RICHPUSH_CALLBACK" /> <!--Optional 用户接受Rich Push Javascript 回调函数的intent-->
           <action android:name="cn.jpush.android.intent.CONNECTION" /><!-- 接收网络变化 连接/断开 since 1.6.3 -->
           <category android:name="？？？？？cn.qiu.json.news？？？？？" />
       </intent-filter>
   </receiver>
*5.写MyReciver或者RxJava传递处理消息
*6.module 的 build.gradle中 compile 'cn.jiguang.sdk:jpush:3.0.9'  // 此处以JPush 3.0.9 版本为例。
       compile 'cn.jiguang.sdk:jcore:1.1.7'  // 此处以JCore 1.1.7 版本为例。




软件采用RxJava + Retrofit + OkHttp框架实现，遵循Material Design，界面简单干净，运行速度快，自动离线缓存，节省流量，对于页面加载及错误等都做了处理，可以作为自己以后开发的一个模板。

##  依赖库
* [RxJava 响应式编程框架](https://github.com/ReactiveX/RxJava)
* [Retrofit2.0 REST安卓客户端请求库](https://github.com/square/retrofit)
* [OkHttp3 网络请求](https://github.com/square/okhttp)
* [Glide 图片加载](https://github.com/bumptech/glide)
* [ButterKnife 依赖注入](https://github.com/JakeWharton/butterknife) 

感谢这些开源库的大神们
## 四、用到的知识点
Retrofit — Getting Started and Create an Android Client
<br>https://futurestud.io/blog/retrofit-getting-started-and-android-client</br>

给 Android 开发者的 RxJava 详解
<br>http://gank.io/post/560e15be2dca930e00da1083</br>

## 五、声明
应用中展示的所有内容均搜集自互联网，若内容有侵权请联系作者进行删除处理。本应用仅用作分享与学习。


