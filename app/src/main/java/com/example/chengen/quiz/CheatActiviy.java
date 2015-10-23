package com.example.chengen.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by chengen on 10/15/15.
 */
public class CheatActiviy  extends Activity{

  private static final String TAG = "CheatActivity";

  public static final String EXTRA_ANSWER_IS_TRUE = "com.example.chengen.quiz.answer_is_true";
  public static final String EXTRA_ANSWER_SHOWN = "com.example.chengen.quiz.answer_shown";
  private static final String IS_CHEAT = "IS_CHEAT";

  private boolean mAnswerIsTrue;
  private Button mShowAnswer;
  private TextView mAnswerTextView;
  private boolean mIsCheat;


  private void setAnswerShown(boolean isAnswerShown) {
    Intent i = new Intent();
    i.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
    setResult(RESULT_OK, i);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);

    if(savedInstanceState != null) {
      mIsCheat = savedInstanceState.getBoolean(IS_CHEAT, false);
      Log.d(TAG, String.format("value of mIsCheat: %b", mIsCheat));
    }

    setAnswerShown(mIsCheat);

    mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
    mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
    mShowAnswer = (Button) findViewById(R.id.showAnswerButton);
    mShowAnswer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mIsCheat = true;
        setAnswerShown(true);
        if(mAnswerIsTrue == true) {
          mAnswerTextView.setText(R.string.true_button);
        }else {
          mAnswerTextView.setText(R.string.false_button);
        }
      }
    });
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.d(TAG, "onSaveInstanceState(Bundle) called.");
    outState.putBoolean(IS_CHEAT, mIsCheat);
  }
}
