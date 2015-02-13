package com.example.vedikajadhav.geoquiz;

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

import org.w3c.dom.Text;


public class CheatActivity extends ActionBarActivity {

    public static final String EXTRA_ANSWER_IS_TRUE = "com.example.vedikajadhav.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.example.vedikajadhav.geoquiz.answer_shown";
    public static final String EXTRA_ANSWER_TEXT = "com.example.vedikajadhav.geoquiz.answer_text";
    public static final String EXTRA_BUTTON_CLICKED = "com.example.vedikajadhav.geoquiz.button_clicked";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private static final String TAG = "CheatActivity";
    private boolean mButtonClicked = false;
    private TextView mApiLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        setAnswerShownResult(false);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);
        mShowAnswerButton = (Button)findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }
                else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                mButtonClicked = true;
                setAnswerShownResult(true);
            }
        });

        mApiLevel = (TextView)findViewById(R.id.api_level);
        Log.i(TAG, "Vedika API" + Build.VERSION.SDK_INT, new Exception());
        mApiLevel.setText("API Level " + Build.VERSION.SDK_INT);

        if(savedInstanceState != null){
            Log.i(TAG, "Vedika text saved" + savedInstanceState.getString(EXTRA_ANSWER_TEXT, ""));
            mAnswerTextView.setText(savedInstanceState.getString(EXTRA_ANSWER_TEXT, ""));
            //setAnswerShownResult(mButtonClicked);
            mButtonClicked = savedInstanceState.getBoolean(EXTRA_BUTTON_CLICKED, false);
            setAnswerShownResult(mButtonClicked);
        }
        //setAnswerShownResult(mButtonClicked);
    }

   private void setAnswerShownResult(boolean isAnswerShown){
       Intent data = new Intent();
       data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
       setResult(RESULT_OK, data);
   }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(EXTRA_ANSWER_TEXT, mAnswerTextView.getText().toString());
        savedInstanceState.putBoolean(EXTRA_BUTTON_CLICKED, mButtonClicked);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cheat, menu);
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
