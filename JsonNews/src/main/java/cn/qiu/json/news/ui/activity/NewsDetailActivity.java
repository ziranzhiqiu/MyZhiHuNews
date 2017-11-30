package cn.qiu.json.news.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;

import cn.qiu.json.news.R;
import cn.qiu.json.news.base.BaseActivity;
import cn.qiu.json.news.bean.News;
import cn.qiu.json.news.ui.fragment.NewsDetailFragment;


public class NewsDetailActivity extends BaseActivity {

    public static final String KEY_NEWS = "key_news";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    public static void start(Context context, News news) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(KEY_NEWS, news);
        context.startActivity(intent);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mSwipeBackLayout.setEdgeSize(120); //设置滑动返回触发范围
        News news = getIntent().getParcelableExtra(KEY_NEWS);
        showNewsDetailFragment(news);
    }

    private void showNewsDetailFragment(News news) {
        Fragment fragment = NewsDetailFragment.newInstance(news);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_container, fragment, NewsDetailFragment.TAG);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
