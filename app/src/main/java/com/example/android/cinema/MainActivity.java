package com.example.android.cinema;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinema.utilities.NetworkUtils;
import com.example.android.cinema.utilities.OpenMovieJsonUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static com.example.android.cinema.utilities.OpenMovieJsonUtils.movies;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MovieAdapter mAdapter;
    private RecyclerView mMovieList;
    private String menuButtonSelection;
    Context mContext;
    ImageView mListItemView;
    private TextView mErrorMessageDisplay;
    private ImageView mDisplayImageMian;


    Toast mToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mDisplayImageMian = (ImageView) findViewById(R.id.iv_item_poster);
        // The ViewHolders are have a container and data filled in it - the following line of codes will layout the ViewHolder data in Grid Layout
        mMovieList = (RecyclerView) findViewById(R.id.rv_posters);

        //  different column count depending on the orientation (landscape or portrait), this will make your app more user-friendly.
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mMovieList.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            mMovieList.setLayoutManager(new GridLayoutManager(this, 4));
        }

        mMovieList.setHasFixedSize(true);
        mAdapter = new MovieAdapter(this);
        mMovieList.setAdapter(mAdapter);
        mMovieList.invalidate();

        loadMovieData();
    }
    private void loadMovieData() {
        String link = "http://api.themoviedb.org/3/movie/popular";
        URL url = NetworkUtils.buildDbUrl(link);
        new FetchMovieData().execute(url);
    }
    public void showJsonDataView(){
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mMovieList.setVisibility(View.VISIBLE);
    }
    public void showErrorMessage(){
        mMovieList.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    // These are the two buttons POPULAR and TOP-RATED in the Action Bar
    // onCreateOptionsMenu is used to create the buttons
    // onOptionsItemSelected is used to initiate an action after touching the the buttons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAdapter.swapArray();
        int idOfTheButtonSelected = item.getItemId();
        if (idOfTheButtonSelected == R.id.filter_popular){
            menuButtonSelection = "http://api.themoviedb.org/3/movie/popular";
            URL url = NetworkUtils.buildDbUrl(menuButtonSelection);
            Log.v(TAG,"url: "+url);
            new FetchMovieData().execute(url);
            Toast.makeText(this,"You have selected POPULAR",Toast.LENGTH_SHORT).show();
            return true;
        }else if (idOfTheButtonSelected == R.id.filter_topRated){
            menuButtonSelection = "http://api.themoviedb.org/3/movie/top_rated";
            URL url = NetworkUtils.buildDbUrl(menuButtonSelection);
            Log.v(TAG,"url: "+url);
            new FetchMovieData().execute(url);
            Toast.makeText(this,"You have selected TOP RATED",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        MovieProfileData movieProfileData = movies.get(clickedItemIndex);
        Log.v(TAG,"MainActivity movieProfileData.getPosterUrl()" + movieProfileData.getPosterUrl());
        Log.v(TAG,"MainActivity movieProfileData.getTitleText()" + movieProfileData.getTitleText());
        Log.v(TAG,"MainActivity movieProfileData.getPlotText()" + movieProfileData.getPlotText());
        Log.v(TAG,"MainActivity movieProfileData.getRatingText()" + movieProfileData.getRatingText());
        Log.v(TAG,"MainActivity movieProfileData.getReleaseDateText()" + movieProfileData.getReleaseDateText());
        String posterUrl = movieProfileData.getPosterUrl();
        String titleText = movieProfileData.getTitleText();
        String plotText = movieProfileData.getPlotText();
        String ratingText = movieProfileData.getRatingText();
        String releaseDate = movieProfileData.getReleaseDateText();

        MovieDataParcel loadData = new MovieDataParcel(posterUrl , titleText,plotText,ratingText,releaseDate);

        // starting an intent after clicking movie poster on Main screen
        Class destinationActivity = ChildActivity.class;
        Intent startChildActivityIntent = new Intent(MainActivity.this ,destinationActivity);
        startChildActivityIntent.putExtra("dataLoaded",loadData);
        startActivity(startChildActivityIntent);
    }

    // This is the AsyncTask which will help in getting the data from the network
    public class FetchMovieData extends AsyncTask<URL,Void,ArrayList<MovieProfileData>>{

        @Override
        protected ArrayList<MovieProfileData> doInBackground(URL... urls) {
            URL url = urls[0];
            Log.v(TAG,"url: "+url);
            String jsonDataFromHttp ;

            ArrayList<MovieProfileData> filteredJsonData;

            try {
                jsonDataFromHttp = NetworkUtils.getResponseFromHttp(url);
                Log.v(TAG,"jsonDataFromHttp: "+jsonDataFromHttp);
                filteredJsonData = OpenMovieJsonUtils.getSimpleMovieStringsFromJson(MainActivity.this,jsonDataFromHttp);
                Log.v(TAG,"filteredJsonData: "+filteredJsonData);

                // here there is possibility of error - not sure how array will be handled
                return filteredJsonData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MovieProfileData> strings) {

            if (strings != null && !strings.equals("")) {
                showJsonDataView();
                mAdapter.setWeatherData(strings);
                super.onPostExecute(strings);
            }else {
                showErrorMessage();
            }
        }
    }
}
