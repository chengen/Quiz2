package com.example.chengen.quiz;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class QuizActivity extends Activity {
  private static final String TAG = "QuizActivity";
  private static final String KEY_INDEX = "index";
  private static final String KEY_IS_CHEATER = "is_cheater";

  private Button mTrueButton;
  private Button mFalseButton;
  private ImageButton mNextButton;
  private ImageButton mPreviousButton;
  private Button mCheatButton;
  private TextView mQuestionTextView;
  private TextView mSdkVersionTextView;

  private TrueFalse[] mQuestionBank = new TrueFalse[]{
      new TrueFalse(R.string.question_oceans, true),
      new TrueFalse(R.string.question_africa, false),
      new TrueFalse(R.string.question_americas, true),
      new TrueFalse(R.string.question_asia, true),
      new TrueFalse(R.string.question_mideast, false)
  };
  private int mCurrentIndex = 0;
  private boolean mIsCheatingArray[] = new boolean[5];

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate(Bundle) called.");
    setContentView(R.layout.activity_quiz);

/*    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      ActionBar actionBar = getActionBar();
      actionBar.setSubtitle("Bodies of water.");
    }*/

    mSdkVersionTextView = (TextView) findViewById(R.id.sdk_version);
    mSdkVersionTextView.setText(String.format("API Level: %d", Build.VERSION.SDK_INT));

    if (savedInstanceState != null) {
      mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
      mIsCheatingArray = savedInstanceState.getBooleanArray(KEY_IS_CHEATER);
    }

    mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
    updateQuestion();

    mQuestionTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
      }
    });

    mTrueButton = (Button) findViewById(R.id.true_button);
    mTrueButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkAnswer(true);
      }
    });

    mFalseButton = (Button) findViewById(R.id.false_button);
    mFalseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkAnswer(false);
      }
    });

    mNextButton = (ImageButton) findViewById(R.id.next_button);
    mNextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
      }
    });

    mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
    mPreviousButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex -= 1;
        if (mCurrentIndex < 0) {
          mCurrentIndex += mQuestionBank.length;
        }
        updateQuestion();
      }
    });

    mCheatButton = (Button) findViewById(R.id.cheat_button);
    mCheatButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(QuizActivity.this, CheatActiviy.class);
        boolean answer_is_true = mQuestionBank[mCurrentIndex].isTrueQuestion();
        i.putExtra(CheatActiviy.EXTRA_ANSWER_IS_TRUE, answer_is_true);
        //startActivity(i);
        startActivityForResult(i, 0);
      }
    });

  }

  private void updateQuestion() {
    int question = mQuestionBank[mCurrentIndex].getQuestion();
    mQuestionTextView.setText(question);
    Log.d(TAG, String.format("index: %d", mCurrentIndex));
  }

  private void checkAnswer(boolean userPressedTrue) {
    boolean anwserIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
    int messageId = 0;
    boolean isCheating = mIsCheatingArray[mCurrentIndex];
    if (isCheating) {
      messageId = R.string.judgment_toast;
    } else {
      if (anwserIsTrue == userPressedTrue) {
        messageId = R.string.correct_toast;
      } else {
        messageId = R.string.incorrect_toast;
      }
    }
    Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_quiz, menu);
    return true;
  }

  @Override
  public void onStart() {
    super.onStart();
    Log.d(TAG, "onStart() called.");
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d(TAG, "onResume() called.");
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.d(TAG, "onPause() called.");
  }

  @Override
  public void onStop() {
    super.onStop();
    Log.d(TAG, "onStop() called.");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy() called.");
  }


  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.d(TAG, "onSaveInstanceState() called.");
    outState.putInt(KEY_INDEX, mCurrentIndex);
    outState.putBooleanArray(KEY_IS_CHEATER, mIsCheatingArray);
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (data != null) {
      boolean isCheating = data.getBooleanExtra(CheatActiviy.EXTRA_ANSWER_SHOWN, false);
      mIsCheatingArray[mCurrentIndex] = isCheating;
    }
  }
}
