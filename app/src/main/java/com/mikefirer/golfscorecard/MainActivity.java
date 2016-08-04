package com.mikefirer.golfscorecard;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_FILE = "com.mikefirer.golfscorecard.preferences";
    public static final String KEY_HOLE_NUMBER = "KEY_HOLE_NUMBER";
    private Integer[] mScores = new Integer[18];

    private SharedPreferences mSharedPrefs;
    private SharedPreferences.Editor mEditor;

    private RecyclerView mScoreRecyclerView;
    private ScoreAdapter mScoreAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPrefs = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPrefs.edit();

        mScoreRecyclerView = (RecyclerView) findViewById(R.id.scoreRecyclerView);

        mScoreAdapter = new ScoreAdapter(mScores);
        mScoreRecyclerView.setAdapter(mScoreAdapter);
        mScoreRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mScoreRecyclerView.setHasFixedSize(true);

        for (int i = 0; i < mScores.length; i++) {
            String holeNumberKey = KEY_HOLE_NUMBER + (i + 1);
            mScores[i] = mSharedPrefs.getInt(holeNumberKey, 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (int i = 0; i < mScores.length; i++) {
            String holeNumberKey = KEY_HOLE_NUMBER + (i + 1);
            mEditor.putInt(holeNumberKey, mScores[i]);
        }
        mEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, adding items to the action bar if present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically
        // handle clicks on the Home/Up button, as long as you specify a parent
        // activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.clear_strokes) {
            mEditor.clear();
            mEditor.apply();

            for (int i = 0; i < mScores.length; i++) {
                mScores[i] = 0;
            }
            mScoreAdapter.notifyDataSetChanged();

            return true;
        };

        return super.onOptionsItemSelected(item);
    }
}
