package com.example.vedikajadhav.geoquiz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends ActionBarActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private Button mCheatButton;

    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private boolean mIsCheater;
    private static final String KEY_M_IS_CHEATER = "com.example.vedikajadhav.geoquiz.mIsCheater";
    //private static final String EXTRA_ANSWER_IS_TRUE = "com.example.vedikajadhav.geoquiz.answer_is_true";

    @TargetApi(11)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setSubtitle(R.string.actionBar_subtitle);
           // ActionBar actionBar = getActionBar();
           // actionBar.setSubtitle("Bodies of Water");
        }

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_M_IS_CHEATER, false);
        }

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
       // int question = mQuestionBank[mCurrentIndex].getQuestion();
       // mQuestionTextView.setText(question);
        updateQuestion();

        mTrueButton = (Button)findViewById(R.id.true_button);
        //mTrueButton = (Button)findViewById(R.id.question_text_view);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               // Toast.makeText(QuizActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(QuizActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
                checkAnswer(false);
            }
        });

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
             //   int question = mQuestionBank[mCurrentIndex].getQuestion();
             //   mQuestionTextView.setText(question);
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //start cheatActivity
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                Boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                //startActivity(i);
                startActivityForResult(i,0);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState called");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putBoolean(KEY_M_IS_CHEATER, mIsCheater);
    }

    private void updateQuestion() {
        //Log.d(TAG, "Updating question text for question #" + mCurrentIndex, new Exception());
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressed)
    {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageResId = 0;

        if(mIsCheater){
            messageResId = R.string.judgement_toast;
        }
        else{
            if(userPressed == answerIsTrue)
            {
                messageResId = R.string.correct_toast;
            }
            else
            {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i(TAG, "onStart() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.i(TAG, "onStop() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "onPause() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG, "onResume() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy() called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null){
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
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
}
