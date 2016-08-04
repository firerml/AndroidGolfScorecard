package com.mikefirer.golfscorecard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
    private Integer[] mScores;

    public ScoreAdapter(Integer[] scores) {
        mScores = scores;
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.score_list_item, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, int position) {
        int holeNumber = position + 1;
        String score = "";
        if (mScores[position] != 0) {
            score += mScores[position];
        }
        holder.mPlusButton.setTag(position);
        holder.mMinusButton.setTag(position);
        holder.mScoreLabel.setText(String.format("Hole %s:", holeNumber));
        holder.mScoreValue.setText(score);
    }

    @Override
    public int getItemCount() {
        return mScores.length;
    }


    public class ScoreViewHolder extends RecyclerView.ViewHolder {

        public TextView mScoreValue;
        public TextView mScoreLabel;
        public Button mPlusButton;
        public Button mMinusButton;

        public ScoreViewHolder(View itemView) {
            super(itemView);

            mScoreLabel = (TextView) itemView.findViewById(R.id.scoreLabel);
            mScoreValue = (TextView) itemView.findViewById(R.id.scoreValue);
            mPlusButton = (Button) itemView.findViewById(R.id.plusButton);
            mMinusButton = (Button) itemView.findViewById(R.id.minusButton);

            mPlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer position = (Integer) view.getTag();
                    mScores[position] += 1;
                    mScoreValue.setText(mScores[position] + "");
                }
            });

            mMinusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer position = (Integer) view.getTag();
                    if (mScores[position] > 0) {
                        mScores[position] -= 1;
                        mScoreValue.setText(mScores[position] + "");
                    }
                }
            });
        }
    }
}
