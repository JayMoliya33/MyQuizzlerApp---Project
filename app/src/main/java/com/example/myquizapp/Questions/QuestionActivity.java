package com.example.myquizapp.Questions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquizapp.R;
import com.example.myquizapp.Scores.ScoreActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    public static final String FIILE_NAME = "QUIZZLER";
    public static final String KEY_NAME = "QUESTIONS";

    // Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private TextView question, numIndicator;
    private Button sharebtn, nextbtn;
    private FloatingActionButton bookmarkBtn;
    private LinearLayout optionscontainer;
    private List<QuestionModel> bookmarksList;

    private int count = 0;
    private List<QuestionModel> list;
    private int position = 0;
    private int score = 0;
    private String category;
    private int setNo;
    private Dialog loadingDialog;

    // Bookmark variable;
    private SharedPreferences prefrences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private int matchedQuestionPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialization
        question = findViewById(R.id.questions);
        numIndicator = findViewById(R.id.numindicator);
        bookmarkBtn = findViewById(R.id.bookmark_btn);
        optionscontainer = findViewById(R.id.options_containers);
        sharebtn = findViewById(R.id.share_btn);
        nextbtn = findViewById(R.id.next_btn);

        // Shared Preferences
        prefrences = getSharedPreferences(FIILE_NAME, Context.MODE_PRIVATE);
        editor = prefrences.edit();
        gson = new Gson();

        getBookmarks();

        // click on Bookmarkbtn
        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modelMatch()) {
                    if(android.os.Build.VERSION.SDK_INT>=16) {
                        bookmarksList.remove(matchedQuestionPosition);
                        bookmarkBtn.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_border));
                    }
                } else {
                    if(android.os.Build.VERSION.SDK_INT>=16) {
                        // add question to bookmark activity
                        bookmarksList.add(list.get(position));
                        bookmarkBtn.setImageDrawable(getResources().getDrawable(R.drawable.bookmark));
                    }
                }
            }
        });

        // Get Category and setNo
        category = getIntent().getStringExtra("category");
        setNo = getIntent().getIntExtra("setNo", 1);

        // Create Loading Dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.setCancelable(false);
        // set dialog Width & Height
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // set Rounded Loading Dialog
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));
        loadingDialog.show();

        list = new ArrayList<>();

        // get Data from Firebase
        myRef.child("SETS").child(category).child("questions").orderByChild("setNo").equalTo(setNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    list.add(snapshot.getValue(QuestionModel.class));
                }
                if (list.size() > 0) {

                    for (int i = 0; i < 4; i++) {
                        optionscontainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                checkAnswer(((Button) view));
                            }
                        });
                    }
                    playAnim(question, 0, list.get(position).getQuestion());
                    // goto next Question
                    nextbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            nextbtn.setEnabled(false);
                            nextbtn.setAlpha(0.7f);
                            enableOption(true);
                            position++;
                            if (position == list.size()) {
                                // goto Score Activity
                                Intent scoreIntent = new Intent(QuestionActivity.this, ScoreActivity.class);
                                scoreIntent.putExtra("score", score);
                                scoreIntent.putExtra("total", list.size());
                                startActivity(scoreIntent);
                                finish(); // finish Question Activity
                                return;
                            }
                            count = 0;
                            playAnim(question, 0, list.get(position).getQuestion());
                        }
                    });

                    // Share Question code
                    sharebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String body = "Q. "+list.get(position).getQuestion() + "\n" +
                                    list.get(position).getOptionA() + "\n" +
                                    list.get(position).getOptionB() + "\n" +
                                    list.get(position).getOptionC() + "\n" +
                                    list.get(position).getOptionD();
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quizzler Challenge");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, body);
                            startActivity(Intent.createChooser(shareIntent, "Share Via"));
                        }
                    });
                } else {
                    finish();
                    Toast.makeText(QuestionActivity.this, "no question", Toast.LENGTH_SHORT).show();
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(QuestionActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        storeBookmarks();
    }

    // for Question and options
    // we applied Animation to that
    private void playAnim(final View view, final int value, final String data) {

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (value == 0 && count < 4) {
                    String option = "";
                    if (count == 0) {
                        option = list.get(position).getOptionA();
                    } else if (count == 1) {
                        option = list.get(position).getOptionB();
                    } else if (count == 2) {
                        option = list.get(position).getOptionC();
                    } else if (count == 3) {
                        option = list.get(position).getOptionD();
                    }
                    playAnim(optionscontainer.getChildAt(count), 0, option);
                    count++;
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onAnimationEnd(Animator animator) {
                // set Data
                if (value == 0) {
                    try {
                        ((TextView) view).setText(data);
                        numIndicator.setText(position + 1 + "/" + list.size()); // setNum Indicator
                        if (modelMatch()) {
                            // setImages
                            bookmarkBtn.setImageDrawable(getDrawable(R.drawable.bookmark));
                        } else {
                            bookmarkBtn.setImageDrawable(getDrawable(R.drawable.bookmark_border));
                        }
                    } catch (ClassCastException e) {
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view, 1, data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    // Check Answer
    private void checkAnswer(Button selectOption) {
        enableOption(false);
        nextbtn.setEnabled(true);
        nextbtn.setAlpha(1);
        if (selectOption.getText().toString().equals(list.get(position).getCorrectANS())) {
            // for Correct Ans
            score++;
            selectOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#17F385")));
        } else {
            // for Incorrect Ans
            selectOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctOption = (Button) optionscontainer.findViewWithTag(list.get(position).getCorrectANS());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }
    }

    // next button will Enable
    // After any option will select
    private void enableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            optionscontainer.getChildAt(i).setEnabled(enable);
            if (enable) {
                optionscontainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
            }
        }
    }

    // called in onCreate()
    private void getBookmarks() {
        String json = prefrences.getString(KEY_NAME, "");

        Type type = new TypeToken<List<QuestionModel>>() {
        }.getType();

        bookmarksList = gson.fromJson(json, type);

        if (bookmarksList == null) {
            bookmarksList = new ArrayList<>();
        }
    }

    private boolean modelMatch() {
        boolean matched = false;
        int i = 0;
        for (QuestionModel model : bookmarksList) {
            if (model.getQuestion().equals(list.get(position).getQuestion())
                    && model.getCorrectANS().equals(list.get(position).getCorrectANS())
                    && model.getSetNo() == list.get(position).getSetNo()) {
                matched = true;
                matchedQuestionPosition = i;
            }
            i++;
        }
        return matched;
    }

    // called in onPause()
    private void storeBookmarks() {
        String json = gson.toJson(bookmarksList);
        editor.putString(KEY_NAME, json);
        editor.commit();
    }

}
