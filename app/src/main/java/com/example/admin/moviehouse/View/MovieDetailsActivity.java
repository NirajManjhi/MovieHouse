package com.example.admin.moviehouse.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.moviehouse.Model.MovieEntity;
import com.example.admin.moviehouse.Presenter.MovieHouseAdapter;
import com.example.admin.moviehouse.R;
import com.squareup.picasso.Picasso;

/**
 * Created by admin on 05/06/17.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "movie";
    public MovieHouseAdapter mAdapter;
    private MovieEntity mMovie;
    ImageView poster;
    ImageView backdrop;
    TextView title;
    TextView description;
    TextView year;
    TextView ratings;
    TextView popularity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_house_details);
        mAdapter = new MovieHouseAdapter(this);
        backdrop = (ImageView) findViewById(R.id.movie_backdrop);
        title = (TextView) findViewById(R.id.movie_name);
        description = (TextView) findViewById(R.id.overview);
        poster = (ImageView) findViewById(R.id.movie_poster);
        year = (TextView) findViewById(R.id.year);
        ratings = (TextView) findViewById(R.id.ratings);
        popularity = (TextView) findViewById(R.id.popularity);


        if (getIntent().hasExtra(MOVIE_EXTRA)) {
            mMovie = getIntent().getParcelableExtra(MOVIE_EXTRA);
            /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
            setSupportActionBar(toolbar);
            CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
            toolbarLayout.setTitle(mMovie.getTitle());*/

            title.setText(mMovie.getTitle());
            description.setText(mMovie.getDescription());
           // Picasso.with(this).load(mMovie.getPoster()).into(poster);
            Picasso.with(this).load(mMovie.getBackdrop()).into(backdrop);
            String yr = mMovie.getReleaseDate();
            year.setText(yr.substring(0,4));
            ratings.setText(String.valueOf(mMovie.getRatings()));
            popularity.setText(String.valueOf(mMovie.getPopularity()));
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }
    }
}
