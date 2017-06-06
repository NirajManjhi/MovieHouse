package com.example.admin.moviehouse;

import com.example.admin.moviehouse.Model.MovieEntity;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by admin on 28/05/17.
 */

public class MovieHouseApiService {
    public static final String BASE_URL = "http://api.themoviedb.org/3";

    public interface ROUTE {

        @GET("/movie/popular")
        void getPopularMovies(Callback<MovieEntity.MovieResult> cb);

        @GET("/movie/upcoming")
        void getUpcomingMovies(Callback<MovieEntity.MovieResult> cb);

        @GET("/movie/top_rated")
        void getTopRatedMovies(Callback<MovieEntity.MovieResult> cb);

        @GET("/movie/now_playing")
        void getNowPlayingMovies(Callback<MovieEntity.MovieResult> cb);

        @GET("/movie/latest")
        void getLatestMovies(Callback<MovieEntity.MovieResult> cb);

        @GET("/search/movie")
        void getSearchedMovies(Callback<MovieEntity.MovieResult> cb);

        @GET("/search/movie")
        void search(@Query("api_key") String apiKey, @Query("query") String query, Callback<MovieEntity.MovieResult> callback);
    }

    public static ROUTE http() {
        return new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", "8f5f6df0585d0d2b95c786ab35858e78");
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(ROUTE.class);
    }
}
