package com.mikefirer.golfscorecard;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_FILE = "com.mikefirer.golfscorecard.preferences";
    public static final String KEY_HOLE_NUMBER = "KEY_HOLE_NUMBER";
    private Integer[] mScores = new Integer[18];

    private SharedPreferences mSharedPrefs;
    private SharedPreferences.Editor mEditor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPrefs = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPrefs.edit();

        RecyclerView scoreRecyclerView = (RecyclerView) findViewById(R.id.scoreRecyclerView);

        ScoreAdapter adapter = new ScoreAdapter(mScores);
        scoreRecyclerView.setAdapter(adapter);
        scoreRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0; i < 18; i++) {
            String holeNumberKey = KEY_HOLE_NUMBER + (i + 1);
            mScores[i] = mSharedPrefs.getInt(holeNumberKey, 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (int i = 0; i < 18; i++) {
            String holeNumberKey = KEY_HOLE_NUMBER + (i + 1);
            mEditor.putInt(holeNumberKey, mScores[i]);
        }
        mEditor.apply();
    }
}
