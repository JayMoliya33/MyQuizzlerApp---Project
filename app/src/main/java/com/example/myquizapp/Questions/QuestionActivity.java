package com.example.myquizapp.Questions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myquizapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private TextView question, numIndicator;
    private Button sharebtn, nextbtn;
    private FloatingActionButton bookmarkBtn;
    private LinearLayout optionscontainer;

    private int count = 0;
    private List<QuestionModel> list;
    private int position = 0;
    private int score = 0;

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

        // Dummy Questions
        list = new ArrayList<>();
        list.add(new QuestionModel("question1", "a", "b", "c", "d", "c"));
        list.add(new QuestionModel("question2", "a", "b", "c", "d", "d"));
        list.add(new QuestionModel("question3", "a", "b", "c", "d", "c"));
        list.add(new QuestionModel("question4", "a", "b", "c", "d", "b"));
        list.add(new QuestionModel("question5", "a", "b", "c", "d", "a"));

        for (int i = 0; i < 4; i++) {
            optionscontainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer(((Button) view));
                }
            });
        }

        // default one question load
        playAnim(question, 0, list.get(position).getQuestion());

        // goto next Question
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextbtn.setEnabled(false);
                nextbtn.setAlpha(0.7f);
                position++;
                if (position == list.size()) {
                    // goto Score Activity
                    return;
                }
                count = 0;
                playAnim(question, 0, list.get(position).getQuestion());
            }
        });

    }

    // for Question and option
    // we applied Animation to that
    private void playAnim(final View view, final int value, final String data) {

        view.animate()
                .alpha(value)
                .scaleX(value)
                .scaleY(value)
                .setDuration(500)
                .setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {

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

            @Override
            public void onAnimationEnd(Animator animator) {
                // set Data
                if (value == 0) {
                    try {
                        ((TextView) view).setText(data);
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

    private void enableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            optionscontainer.getChildAt(i).setEnabled(enable);
        }
    }
}
