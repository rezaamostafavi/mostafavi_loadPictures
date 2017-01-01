package com.mostafavi.imageloader;

import android.graphics.Bitmap;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

/**
 * Created by admin on 1/1/17.
 */
public interface MainMVP extends MvpView {

    public void success(MainActivity.Holder holder, Bitmap bitmap);

    public void failure(MainActivity.Holder holder);
}
