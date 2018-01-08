package com.example.android.cinema;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cinema.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class ChildActivity extends AppCompatActivity {
    private static final String TAG = ChildActivity.class.getSimpleName();

    private ImageView mDisplayPoster;
    private TextView mDisplayTitle;
    private TextView mDisplayPlot;
    private TextView mDisplayRating;
    private TextView mDisplayReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        mDisplayPoster = (ImageView) findViewById(R.id.child_iv);
        mDisplayTitle = (TextView) findViewById(R.id.child_tv_title);
        mDisplayPlot = (TextView) findViewById(R.id.child_tv_plot);
        mDisplayRating = (TextView) findViewById(R.id.child_tv_rating);
        mDisplayReleaseDate = (TextView) findViewById(R.id.child_tv_releaseDate);

        MovieDataParcel fetchData = getIntent().getParcelableExtra("dataLoaded");
        if (fetchData != null){
            String recoveredUrl = fetchData.moviePoster;
            String recoveredTitle = fetchData.movieTitle;
            String recoveredPlot = fetchData.moviePlot;
            String recoveredRating = fetchData.movieRating;
            String recoveredReleaseDate = fetchData.movieReleaseDate;
            Log.v(TAG,"recoveredUrl: "+recoveredUrl);
            Log.v(TAG,"recoveredTitle: "+recoveredTitle);
            Log.v(TAG,"recoveredPlot: "+recoveredPlot);
            Log.v(TAG,"recoveredRating: "+recoveredRating);
            Log.v(TAG,"recoveredReleaseDate: "+recoveredReleaseDate);
            String picassoUrl = NetworkUtils.buildPosterUrl(recoveredUrl).toString();
            Picasso.with(this).load(picassoUrl).into(mDisplayPoster);
            mDisplayTitle.setText(recoveredTitle);
            mDisplayPlot.setText(recoveredPlot);
            mDisplayRating.setText(recoveredRating);
            mDisplayReleaseDate.setText(recoveredReleaseDate);
        }

    }
}
