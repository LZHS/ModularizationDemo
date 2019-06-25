package com.example.lib_wedgit.wedgit.swipe_back.app;


import com.example.lib_wedgit.wedgit.swipe_back.SwipeBackLayout;

public interface SwipeBackActivityBase {

    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    public abstract void scrollToFinishActivity();

}
