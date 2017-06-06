package com.example.admin.moviehouse.Presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.admin.moviehouse.R;

/**
 * Created by admin on 05/06/17.
 */

public class MovieHouseViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgView;

    public MovieHouseViewHolder(View itemView) {
        super(itemView);
        imgView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
