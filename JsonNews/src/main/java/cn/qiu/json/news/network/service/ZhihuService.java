package cn.qiu.json.news.network.service;

import cn.qiu.json.news.bean.NewsDetail;
import cn.qiu.json.news.bean.NewsList;
import cn.qiu.json.news.network.manager.RetrofitManager;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;


public interface ZhihuService {

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("stories/latest")
    Observable<NewsList> getLatestNews();

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_LONG)
    @GET("stories/before/{date}")
    Observable<NewsList> getBeforeNews(@Path("date") String date);

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_LONG)
    @GET("story/{id}")
    Observable<NewsDetail> getNewsDetail(@Path("id") int id);
}
