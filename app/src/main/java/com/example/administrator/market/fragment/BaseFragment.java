package com.example.administrator.market.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.application.utils.ViewUtils;
import com.example.administrator.market.widget.LoadingPager;
import com.example.administrator.market.widget.LoadingPager.LoadResult;

import java.util.List;


public abstract class BaseFragment extends Fragment {

    private LoadingPager mContentView;

    //首先走这个方法
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mContentView) {
            mContentView = new LoadingPager(UIUtils.getContext()) {//走完这里之后会回到MainActivity中继续走onPageSelected()中的fragment.show();

                //通过子类的Load()实现，得到服务器的返回值
                @Override
                protected LoadResult loadData() {
                    return BaseFragment.this.loadDataFromNet();
                }

                //通过子类的createLoadedView()实现，得到界面显示
                @Override
                protected View createContentView() {
                    return BaseFragment.this.createChildView();
                }
            };
        } else {
            ViewUtils.removeSelfFromParent(mContentView);
        }
        return mContentView;
    }

    protected abstract View createChildView();

    protected abstract LoadResult loadDataFromNet();

    // 展示具体的页面
    public void show() {
        if (null != mContentView) {
            //调用LoadingPager中的show()
            mContentView.show();
        }
    }

    // 检查服务器返回的数据情况
    protected LoadingPager.LoadResult checkData(Object obj) {
        if (null == obj) {
            return LoadResult.ERROR;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            if (list.size() == 0) {
                return LoadResult.EMPTY;
            }
        }
        return LoadResult.SUCCESS;
    }
}
