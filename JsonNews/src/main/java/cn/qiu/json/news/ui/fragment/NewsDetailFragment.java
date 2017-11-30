package cn.qiu.json.news.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import cn.qiu.json.news.R;
import cn.qiu.json.news.base.BaseFragment;
import cn.qiu.json.news.bean.News;
import cn.qiu.json.news.bean.NewsDetail;
import cn.qiu.json.news.network.manager.RetrofitManager;
import cn.qiu.json.news.ui.activity.AboutActivity;
import cn.qiu.json.news.ui.activity.NewsDetailActivity;
import cn.qiu.json.news.utils.HtmlUtil;
import cn.qiu.json.news.utils.PrefUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class NewsDetailFragment extends BaseFragment {

    @Bind(R.id.iv_header)
    ImageView mIvHeader;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_source)
    TextView mTvSource;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.wv_news)
    WebView mWvNews;
    @Bind(R.id.nested_view)
    NestedScrollView mNestedView;
    @Bind(R.id.tv_load_empty)
    TextView mTvLoadEmpty;
    @Bind(R.id.tv_load_error)
    TextView mTvLoadError;
    @Bind(R.id.pb_loading)
    ContentLoadingProgressBar mPbLoading;

    private News mNews;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mNews = getArguments().getParcelable(NewsDetailActivity.KEY_NEWS);
        setHasOptionsMenu(true);
        init();
        loadData();
    }

    public static Fragment newInstance(News news) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NewsDetailActivity.KEY_NEWS, news);
        Fragment fragment = new NewsDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.menu_action_share:
                share();
                return true;
            case R.id.menu_action_about:
                AboutActivity.start(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mCollapsingToolbarLayout.setTitleEnabled(true);
    }

    private void loadData() {
        RetrofitManager.builder().getNewsDetail(mNews.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new Action1<NewsDetail>() {
                    @Override
                    public void call(NewsDetail newsDetail) {
                        hideProgress();
                        Logger.e(""+newsDetail);
                        if (newsDetail == null) {
                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        } else {
                            Glide.with(getActivity())
                                    .load(newsDetail.getImage())
                                    .into(mIvHeader);
                            mTvTitle.setText(newsDetail.getTitle());
                            mTvSource.setText(newsDetail.getImage_source());
                            mWvNews.setDrawingCacheEnabled(true);
                            boolean isNight = PrefUtil.isNight();
//                            StringBuffer stringBuffer = HtmlUtil.handleHtml(newsDetail.getBody(),isNight);
//                            mWvNews.loadDataWithBaseURL("file:///android_asset/", stringBuffer.toString(), "text/html", "utf-8", null);
                            String htmlData = HtmlUtil.createHtmlData(newsDetail, isNight);
                            mWvNews.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
                            mTvLoadEmpty.setVisibility(View.GONE);
                        }
                        mTvLoadError.setVisibility(View.GONE);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideProgress();
                        Logger.e(throwable,"Load news detail error");
                        mTvLoadError.setVisibility(View.VISIBLE);
                        mTvLoadEmpty.setVisibility(View.GONE);
                    }
                });
    }

    public void showProgress() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mPbLoading.setVisibility(View.GONE);
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_from) + mNews.getTitle() + "，http://daily.zhihu.com/story/" + mNews.getId());
        startActivity(Intent.createChooser(intent, mNews.getTitle()));
    }

}
