package com.example.hp.dicegame;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

import java.util.Random;

import java.util.logging.LogRecord;


public class MainActivity extends AppCompatActivity {

    private static int user_score=0;
    private static int computer_score=0;
    private int user_turn_score=0;
    private int computer_turn_score=0;
    boolean turnFinished=false;

    private Random random = new Random();



    private ImageView dieImageView;
    private TextView myScoreTextView;
    private TextView computerScoreTextView;
    private TextView TurnScoreTextView;
    private Button rollButton;
    private Button holdButton;
    private Button resetButton;
    private Vibrator vibrator;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
             rollButton.setEnabled(false);
             holdButton.setEnabled(false);

                computerTurn();
            if((computer_turn_score<=20) && !turnFinished)
              timerHandler.postDelayed(this, 1000);

           else {
                turnFinished = false;
                rollButton.setEnabled(true);
                holdButton.setEnabled(true);

                TurnScoreTextView.setText("Computer Turn Finished,.. Play now");
                computer_score+=computer_turn_score;
                computerScoreTextView.setText("Computer Score :"+computer_score);

            }


        }

    };

   private Handler victoryHandler = new Handler();
    private Runnable victoryPauser = new Runnable() {
        @Override
        public void run() {
            resetButtonFunction();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        vibrator =(Vibrator) getSystemService(VIBRATOR_SERVICE);




        myScoreTextView = (TextView) findViewById(R.id.myScoreTextView);
        computerScoreTextView = (TextView) findViewById(R.id.computerScoreTextView);
        TurnScoreTextView = (TextView) findViewById(R.id.TurnScoreTextView);
        myScoreTextView.setText("Your score:"+user_score);
        computerScoreTextView.setText("Computer Score :"+computer_score);


        dieImageView = (ImageView) findViewById(R.id.dieImageView);

        rollButton = (Button) findViewById(R.id.rollButton);
        holdButton = (Button) findViewById(R.id.holdButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(50);
                checkWinner();
                rollButtonFunction();
                checkWinner();
            }
        });



        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holdButtonFunction();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetButtonFunction();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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




    public void rollButtonFunction() {
        int score = random.nextInt(6) + 1;



        switch (score) {
            case 1:
               // dieImageView.setImageResource(R.drawable.dice1);
                ImageViewAnimatedChange(this,dieImageView,1);
                user_turn_score = 0;
                holdButtonFunction();
                TurnScoreTextView.setText("Your turn Score :0");
                break;
            case 2:
               // dieImageView.setImageResource(R.drawable.dice2);
                ImageViewAnimatedChange(this,dieImageView,2);
                user_turn_score += 2;
                break;
            case 3:
               // dieImageView.setImageResource(R.drawable.dice3);
                ImageViewAnimatedChange(this,dieImageView,3);
                user_turn_score += 3;
                break;
            case 4:
               // dieImageView.setImageResource(R.drawable.dice4);
                ImageViewAnimatedChange(this,dieImageView,4);
                user_turn_score += 4;
                break;
            case 5:
                //dieImageView.setImageResource(R.drawable.dice5);
                ImageViewAnimatedChange(this,dieImageView,5);
                user_turn_score += 5;
                break;
            case 6:
               // dieImageView.setImageResource(R.drawable.dice6);
                ImageViewAnimatedChange(this,dieImageView,6);
                user_turn_score += 6;
                break;
        }
        TurnScoreTextView.setText("Your turn Score :" + user_turn_score);

    }


    public void holdButtonFunction()
    {
        user_score += user_turn_score;
        user_turn_score = 0;
        TurnScoreTextView.setText("");

        myScoreTextView.setText("Your Score :" + user_score);

        timerHandler.postDelayed(timerRunnable,1000);
        computer_turn_score = 0;


    }


    public void resetButtonFunction()
    {
        user_turn_score=0;
        user_score=0;
        computer_turn_score=0;
        computer_score=0;

        myScoreTextView.setText("Your Score :"+user_score);
        computerScoreTextView.setText("Computer Score :"+computer_score);
       TurnScoreTextView.setText("");
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
        ImageViewAnimatedChange(this,dieImageView,1);
    }


    public void computerTurn()
    {
            int score = random.nextInt(6) + 1;


            switch (score) {
                case 1:
                    //dieImageView.setImageResource(R.drawable.dice1);
                    ImageViewAnimatedChange(this,dieImageView,1);
                    computer_turn_score = 0;
                    turnFinished = true;
                    TurnScoreTextView.setText("Computer turn Score :0");
                    break;
                case 2:
                    //dieImageView.setImageResource(R.drawable.dice2);
                    ImageViewAnimatedChange(this,dieImageView,2);
                    computer_turn_score += 2;
                    break;
                case 3:

                    //dieImageView.setImageResource(R.drawable.dice3);
                    ImageViewAnimatedChange(this,dieImageView,3);
                    computer_turn_score += 3;
                    break;
                case 4:
                    //dieImageView.setImageResource(R.drawable.dice4);
                    ImageViewAnimatedChange(this,dieImageView,4);
                    computer_turn_score += 4;
                    break;
                case 5:
                   // dieImageView.setImageResource(R.drawable.dice5);
                    ImageViewAnimatedChange(this,dieImageView,5);
                    computer_turn_score += 5;
                    break;
                case 6:
                   // dieImageView.setImageResource(R.drawable.dice6);
                    ImageViewAnimatedChange(this,dieImageView,6);
                    computer_turn_score += 6;
                    break;
            }
            TurnScoreTextView.setText("Computer turn Score :" + computer_turn_score);
            checkWinner();



//        computerScoreTextView.setText("Computer Score :"+computer_score);


    }


    public void checkWinner(){
        if((user_score +user_turn_score )>=100) {
            TurnScoreTextView.setText("You Won!!!");

            user_score+=user_turn_score;
            myScoreTextView.setText("Your Score :"+user_score);

            victoryHandler.postDelayed(victoryPauser, 2000);

        }
        else if((computer_score+ computer_turn_score)>=100)
        {
            TurnScoreTextView.setText("You Lose.");

            computer_score+=computer_turn_score;
            computerScoreTextView.setText("Computer Score :"+computer_score);

            victoryHandler.postDelayed(victoryPauser,2000);

        }

    }





    public static void ImageViewAnimatedChange(Activity c, final ImageView v, final int dieNo) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {

                //v.setImageBitmap(new_image);
                switch(dieNo)
                {
                    case 1: v.setImageResource(R.drawable.dice1);
                            break;
                    case 2: v.setImageResource(R.drawable.dice2);
                        break;
                    case 3: v.setImageResource(R.drawable.dice3);
                        break;
                    case 4: v.setImageResource(R.drawable.dice4);
                        break;
                    case 5: v.setImageResource(R.drawable.dice5);
                        break;
                    case 6: v.setImageResource(R.drawable.dice6);
                        break;
                }

                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

}

