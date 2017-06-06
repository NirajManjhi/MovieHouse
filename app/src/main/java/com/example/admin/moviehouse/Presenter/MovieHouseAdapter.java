package com.example.admin.moviehouse.Presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.moviehouse.Model.MovieEntity;
import com.example.admin.moviehouse.R;
import com.example.admin.moviehouse.View.MovieDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 05/06/17.
 */

public class MovieHouseAdapter extends RecyclerView.Adapter<MovieHouseViewHolder> {
    private List<MovieEntity> mMovieList;
    private Context mContext;
    private LayoutInflater mInflater;

    public MovieHouseAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MovieHouseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_movie, parent, false);
        final MovieHouseViewHolder viewHolder = new MovieHouseViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                intent.putExtra(MovieDetailsActivity.MOVIE_EXTRA, mMovieList.get(position));
                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieHouseViewHolder holder, int position) {
        MovieEntity movie = mMovieList.get(position);
        Picasso.with(mContext).load(movie.getPoster()).placeholder(R.color.colorAccent)
                .into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return mMovieList == null ? 0 : mMovieList.size();
    }

    public void setMovieList(List<MovieEntity> movieList) {
        this.mMovieList = new ArrayList<>();
        this.mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }
}
