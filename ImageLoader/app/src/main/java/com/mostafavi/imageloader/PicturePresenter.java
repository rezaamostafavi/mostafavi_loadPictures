package com.mostafavi.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by admin on 1/1/17.
 */
public class PicturePresenter extends MvpBasePresenter<MainMVP> {


    private int width;
    private MainActivity.Holder holder;

    public void loadPicture(Context context, String url, final MainActivity.Holder holder) {
        this.holder = holder;

        if (width == 0)
            width = getWidth(context);

        GetPicturesAsyncTask getPictures = new GetPicturesAsyncTask(url, new GetPicturesAsyncTask.GettingPictureListener() {
            @Override
            public void onGetPicture(Bitmap bitmap) {
                if (getView() == null)
                    return;
                if (bitmap != null)
                    getView().success(holder, bitmap);
                else
                    getView().failure(holder);
            }
        }, width);
        getPictures.execute();
    }

    private int getWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
