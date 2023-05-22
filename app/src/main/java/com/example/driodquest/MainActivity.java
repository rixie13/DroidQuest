package com.example.driodquest;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mBeckButton;
    private TextView mQuestionTextView;
    private Button mDeceitButton;
    private static final int REQUEST_CODE_DECEIT = 0;

    private void updateQuestion() {
        int question = mQuestionnBank[mCurrentIndex].getTextResID();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionnBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if(mIsDeceiter){
            messageResId = R.string.judgment_toast;
        }
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }



    private Question[]  mQuestionnBank = new Question[]{
            new  Question(R.string.question_android,true),
            new  Question(R.string.question_nat,false),
            new  Question(R.string.question_nob,true),
            new  Question(R.string.question_res,false),
            new  Question(R.string.question_city,true),
            new  Question(R.string.question_bio,false),
    };
    private int mCurrentIndex = 0;
private boolean mIsDeceiter;


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_DECEIT) {
            if (data == null) {
                return;
            }
            mIsDeceiter = DeceitActivity.wasAnswerShown(data);
        }
    }



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle)вызван");
        setContentView(R.layout.activity_main);

if (savedInstanceState != null){
    mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
}

        Log.d(TAG, "Current question index: "+ mCurrentIndex);
Question question;
try {
    question = mQuestionnBank[mCurrentIndex];

} catch (ArrayIndexOutOfBoundsException ex){
    Log.e(TAG,"Index was out of bounds", ex);
}
mDeceitButton= (Button) findViewById(R.id.deceit_button);


mDeceitButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        boolean answerIsTrue = mQuestionnBank[mCurrentIndex].isAnswerTrue();
        Intent i = DeceitActivity.newIntent(MainActivity.this, answerIsTrue);
        startActivityForResult(i, REQUEST_CODE_DECEIT);
    }
});

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton=(Button) findViewById(R.id.false_button);



        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

mBeckButton=(Button) findViewById(R.id.back_button);
mBeckButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
            mCurrentIndex = (mCurrentIndex -1 )% mQuestionnBank.length;
            updateQuestion();
    }
});
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   mCurrentIndex = (mCurrentIndex +1 )% mQuestionnBank.length;
                   mIsDeceiter = false;
                updateQuestion();
            }
        });
        updateQuestion();
    }


    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() вызван");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() вызван");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() вызван");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() вызван");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() вызван");
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }



}
