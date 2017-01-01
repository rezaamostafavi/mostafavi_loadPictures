package com.mostafavi.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 1/1/17.
 */
public class GetPicturesAsyncTask extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private GettingPictureListener gettingPictureListener;
    private int width;
    private final static Map<String, Bitmap> memory = new HashMap<>();

    public interface GettingPictureListener {
        void onGetPicture(Bitmap bitmap);
    }

    public GetPicturesAsyncTask(String url, GettingPictureListener gettingPictureListener, int width) {
        this.url = url;
        this.gettingPictureListener = gettingPictureListener;
        this.width = width;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        return getBitmapFromURL(url);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (!memory.containsKey(url))
            memory.put(url, bitmap);
        if (gettingPictureListener != null)
            gettingPictureListener.onGetPicture(bitmap);
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            if (memory != null && memory.containsKey(url)) {
                return memory.get(url);
            }

            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = MyTools.calculateInSampleSize(options, width, width);
            Bitmap myBitmap = BitmapFactory.decodeStream(input, null, options);
            if (myBitmap != null)
                myBitmap = rotateImage(myBitmap, 90);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap rotateImage(Bitmap src, float degree) {
        // create new matrix
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.postRotate(degree);
        Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bmp;
    }


}
