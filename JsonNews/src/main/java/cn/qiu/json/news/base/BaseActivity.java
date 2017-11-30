package cn.qiu.json.news.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import butterknife.ButterKnife;
import cn.qiu.json.news.utils.PrefUtil;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

//抽象基类BaseActivity 继承SwipeBackActivity，这样所有类实现滑动退出当前页面效果
public abstract class BaseActivity extends SwipeBackActivity {

    protected SwipeBackLayout mSwipeBackLayout;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            scrollToFinishActivity();
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(PrefUtil.getThemeRes());
        setContentView(getLayoutId());
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        ButterKnife.bind(this);
        afterCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);
}
