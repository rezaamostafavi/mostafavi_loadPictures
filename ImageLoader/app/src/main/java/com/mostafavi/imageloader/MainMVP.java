package com.mostafavi.imageloader;

import android.graphics.Bitmap;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

/**
 * Created by admin on 1/1/17.
 */
public class MainMVP implements MvpView {

    interface MainView extends MvpLceView<List<Bitmap>> {

    }

    public interface MvpPresenter<MainMVP> {

        public void loadPictures(String[] urls, final boolean pullToRefresh);
    }
}
