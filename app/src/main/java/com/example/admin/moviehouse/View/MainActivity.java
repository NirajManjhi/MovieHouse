package com.example.admin.moviehouse.View;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.moviehouse.Model.MovieEntity;
import com.example.admin.moviehouse.MovieHouseApiService;
import com.example.admin.moviehouse.Presenter.MovieHouseAdapter;
import com.example.admin.moviehouse.R;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityView {

    private boolean backPressedToExitOnce = false;
    private RecyclerView mRecyclerView;
    private MovieHouseAdapter mAdapter;
    private Toolbar toolbar;
    private static String NAV_SELECTOR = "Popular";
    private ProgressBar mProgressBar;
    private String typedQuery;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(NAV_SELECTOR);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MovieHouseAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        searchView = (SearchView) findViewById(R.id.search_view);
        typedQuery = searchView.getQuery().toString();

        searchView.setIconifiedByDefault(true);
        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSearchedMovies(typedQuery);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                typedQuery = newText;
                return false;
            }
        });*/
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchedMovies(typedQuery);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        getMovies();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (backPressedToExitOnce) {
                super.onBackPressed();
            } else {
                this.backPressedToExitOnce = true;
                Toast.makeText(this, "Please back press again", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backPressedToExitOnce = false;
                    }
                }, 2000);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.popular) {
            NAV_SELECTOR = "Popular";
            toolbar.setTitle(NAV_SELECTOR);
            getMovies();
        } else if (id == R.id.upcoming) {
            NAV_SELECTOR = "Upcoming";
            toolbar.setTitle(NAV_SELECTOR);
            getMovies();
        } else if (id == R.id.now_playing) {
            NAV_SELECTOR = "Now Playing";
            toolbar.setTitle(NAV_SELECTOR);
            getMovies();
        } else if (id == R.id.top_rated) {
            NAV_SELECTOR = "Top Rated";
            toolbar.setTitle(NAV_SELECTOR);
            getMovies();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getMovies() {
        mProgressBar.setVisibility(View.VISIBLE);
        /*RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", "8f5f6df0585d0d2b95c786ab35858e78");
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieHouseApiService service = restAdapter.create(MovieHouseApiService.class);*/

        if (NAV_SELECTOR.equalsIgnoreCase("Popular")) {
            MovieHouseApiService.http().getPopularMovies(new Callback<MovieEntity.MovieResult>() {

                @Override
                public void success(MovieEntity.MovieResult movieResult, Response response) {
                    mAdapter.setMovieList(movieResult.getResults());
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        } else if (NAV_SELECTOR.equalsIgnoreCase("Upcoming")) {
            MovieHouseApiService.http().getUpcomingMovies(new Callback<MovieEntity.MovieResult>() {

                @Override
                public void success(MovieEntity.MovieResult movieResult, Response response) {
                    mAdapter.setMovieList(movieResult.getResults());
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        }

        if (NAV_SELECTOR.equalsIgnoreCase("Now Playing")) {
            MovieHouseApiService.http().getNowPlayingMovies(new Callback<MovieEntity.MovieResult>() {

                @Override
                public void success(MovieEntity.MovieResult movieResult, Response response) {
                    mAdapter.setMovieList(movieResult.getResults());
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        }

        if (NAV_SELECTOR.equalsIgnoreCase("Top Rated")) {
            MovieHouseApiService.http().getTopRatedMovies(new Callback<MovieEntity.MovieResult>() {

                @Override
                public void success(MovieEntity.MovieResult movieResult, Response response) {
                    mAdapter.setMovieList(movieResult.getResults());
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        }
    }

    public void getSearchedMovies(String query) {
        MovieHouseApiService.http().search(query, "8f5f6df0585d0d2b95c786ab35858e78", new Callback<MovieEntity.MovieResult>() {
            @Override
            public void success(MovieEntity.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(),"something wrong", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
    }

}
