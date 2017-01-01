package com.mostafavi.imageloader;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpActivity<MainMVP, PicturePresenter> implements MainMVP {


    @BindView(R2.id.recyclerView)RecyclerView recyclerView;
    final String[] urls = new String[]{"https://hmomeni.com/images/christmas.jpg", "https://hmomeni.com/images/photo.jpeg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(getListAdapter());
    }

    @NonNull
    @Override
    public PicturePresenter createPresenter() {
        return new PicturePresenter();
    }

    @Override
    public void success(Holder holder, Bitmap bitmap) {
        holder.ivPicture.setImageBitmap(bitmap);
    }

    @Override
    public void failure(Holder holder) {
        holder.progressBar.setVisibility(View.GONE);
        holder.tvError.setText(getString(R.string.errorInNetwork));
    }

    private RecyclerView.Adapter<Holder> getListAdapter() {
        return new RecyclerView.Adapter<Holder>() {
            @Override
            public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.lst_item_picture, parent, false);
                return new Holder(view);
            }

            @Override
            public void onBindViewHolder(Holder holder, int position) {

                presenter.loadPicture(getApplicationContext(), urls[position],holder);
            }

            @Override
            public int getItemCount() {
                return urls.length;
            }
        };
    }


    class Holder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;
        ImageView ivPicture;
        TextView tvError;

        public Holder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.loadingView);
            ivPicture = (ImageView) itemView.findViewById(R.id.ivPicture);
            tvError = (TextView) itemView.findViewById(R.id.errorView);
        }
    }

}
