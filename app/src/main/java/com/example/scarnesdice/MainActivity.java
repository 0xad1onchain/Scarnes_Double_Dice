package com.example.scarnesdice;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int userScore;
    private int userTurnScore;

    private int computerScore;
    private TextView usertotal;
    private TextView userturn;
    private TextView comptotal;
    private TextView compturn;
    private TextView l_abel;
    private int computerTurnScore;
    //private int count=0;
    private ImageView diceImageView1;
    private ImageView diceImageView2;
    private ImageView turnimage;
    private Random random;
    private Button rollButton;
    private Button holdButton;
    private Boolean user_Turn = true;
    private Handler turnHandler;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = new Random();
        diceImageView1 = (ImageView) findViewById(R.id.image_view_dice1);
        diceImageView2 = (ImageView) findViewById(R.id.image_view_dice2);
        turnimage = (ImageView) findViewById(R.id.turn_image);
        usertotal = (TextView) findViewById(R.id.user_total);
        l_abel = (TextView) findViewById(R.id.label);
        userturn = (TextView) findViewById(R.id.user_turn);
        comptotal = (TextView) findViewById(R.id.comp_total);
        compturn = (TextView) findViewById(R.id.comp_turn);
        holdButton = (Button) findViewById(R.id.button_hold);
        rollButton = (Button) findViewById(R.id.button_roll);

        turnHandler = new Handler();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void roll(View view) {

        int rolledValue1 = rollDice1();
        int rolledValue2 = rollDice2();
        if (rolledValue1 == 1 && rolledValue2 == 1) {
            userScore = 0;
            userTurnScore = 0;
            user_Turn = false;
            String turnLabel = "";
            int drawableId1;
            showUserScore();
            drawableId1 = R.drawable.computer;
            turnimage.setImageResource(drawableId1);
            turnLabel = " Computer's Turn!";
            l_abel.setText(turnLabel);
            computerTurn();
            Toast.makeText(getApplicationContext(),
                    "You Rolled a Both One!", Toast.LENGTH_SHORT).show();
        }
        else if (rolledValue2 == 1 || rolledValue1 == 1) {
            userTurnScore = 0;
            user_Turn = false;
            userTurnScore = 0;

            showUserScore();

            Toast.makeText(getApplicationContext(),
                    "You Rolled a One!", Toast.LENGTH_SHORT).show();
            computerTurn();

        }


        else {

            userTurnScore += rolledValue1;
            userTurnScore += rolledValue2;

            if (rolledValue1 == rolledValue2) {
                userScore += userTurnScore;
                userTurnScore = 0;
                Toast.makeText(getApplicationContext(),
                        "You Rolled a Double! :)", Toast.LENGTH_SHORT).show();
            }
            showUserScore();
        }
    }

    private void showUserScore() {
        String turnLabel = "";
        int drawableId1;
        if (user_Turn == true) {

            drawableId1 = R.drawable.user;
            turnimage.setImageResource(drawableId1);
            turnLabel = " Your Turn!";
            l_abel.setText(turnLabel);
            userturn.setText("Turn Score: " + userTurnScore);
            usertotal.setText("Total Score: " + userScore);
            comptotal.setText("Total Score: " + computerScore);
            compturn.setText("Turn Score: " + computerTurnScore);

        }
        if (user_Turn == false) {

            drawableId1 = R.drawable.computer;
            turnimage.setImageResource(drawableId1);
            turnLabel = " Computer's Turn!";
            l_abel.setText(turnLabel);
            userturn.setText("Turn Score: " + userTurnScore);
            usertotal.setText("Total Score: " + userScore);
            comptotal.setText("Total Score: " + computerScore);
            compturn.setText("Turn Score: " + computerTurnScore);
        }
        if (userScore >= 100) {
            drawableId1 = R.drawable.user;
            turnimage.setImageResource(drawableId1);

            turnLabel = "Game Over! You win!";
            l_abel.setText(turnLabel);
            userturn.setText("Turn Score: " + userTurnScore);
            usertotal.setText("Total Score: " + userScore);
            comptotal.setText("Total Score: " + computerScore);
            compturn.setText("Turn Score: " + computerTurnScore);
            rollButton.setEnabled(false);
            Toast.makeText(getApplicationContext(),
                    "Game Over! You Win!", Toast.LENGTH_LONG).show();
            holdButton.setEnabled(false);
            user_Turn = true;
        } else if (computerScore >= 100) {
            drawableId1 = R.drawable.computer;
            turnimage.setImageResource(drawableId1);
            turnLabel = "Game Over! Computer Wins!";
            Toast.makeText(getApplicationContext(),
                    "Game Over! Computer Wins!", Toast.LENGTH_LONG).show();
            l_abel.setText(turnLabel);

            userturn.setText("Turn Score: " + userTurnScore);
            usertotal.setText("Total Score: " + userScore);
            comptotal.setText("Total Score: " + computerScore);
            compturn.setText("Turn Score: " + computerTurnScore);
            rollButton.setEnabled(false);
            holdButton.setEnabled(false);
            user_Turn = true;
        }
    }

    private void computerTurn() {
        Log.d("ComputerTurn", "Called Here! " + user_Turn);

        //1. Disable the Button
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);
        //2. Call RollDice after resetting turnscore for computer
        computerTurnScore = 0;
        //int count = 0;

        Runnable computerRunable = new Runnable() {
            @Override
            public void run() {
                int rolledValue1 = rollDice1();
                int rolledValue2 = rollDice2();

                if (rolledValue1 != 1 && rolledValue2 != 1) {
                    computerTurnScore += rolledValue1;
                    computerTurnScore += rolledValue2;
                    //count +=1;

                    if (rolledValue1 == rolledValue2) {
                        computerScore += computerTurnScore;
                        computerTurnScore = 0;
                        //count =0;
                        user_Turn = false;
                    }
                    showUserScore();

                    if (computerTurnScore >= 30 || ((computerScore + computerTurnScore) >= userScore + 15)||((computerScore + computerTurnScore) >= 100)||((userScore + userTurnScore) >= 100) ) {
                        computerScore += computerTurnScore;
                        computerTurnScore = 0;


                        //showUserScore();
                        //statusTextView.setText("Computer Holds!");
                        Toast.makeText(getApplicationContext(),
                                "Computer Holds!", Toast.LENGTH_SHORT).show();
                        rollButton.setEnabled(true);
                        holdButton.setEnabled(true);
                        user_Turn = true;
                        showUserScore();

                    }
                    //Re Roll
                    else
                        turnHandler.postDelayed(this, 2500);
                }

                if ((rolledValue1 == 1) && (rolledValue2 != 1)) {
                    //Computer rolled a one.
                    computerTurnScore = 0;
                    user_Turn = true;
                    //count +=1;
                    showUserScore();
                    user_Turn = true;
                    Toast.makeText(getApplicationContext(),
                            "Computer Rolled a One!", Toast.LENGTH_SHORT).show();
                    rollButton.setEnabled(true);
                    holdButton.setEnabled(true);
                    //statusTextView.setText("Computer Rolled a One!");
                    //turnHandler.postDelayed(this, 2000);


                }
                if ((rolledValue2 == 1) && (rolledValue1 != 1)) {
                    //Computer rolled a one.
                    computerTurnScore = 0;
                    //count +=1;
                    user_Turn = true;
                    showUserScore();
                    user_Turn = true;
                    Toast.makeText(getApplicationContext(),
                            "Computer Rolled a One!", Toast.LENGTH_SHORT).show();
                    rollButton.setEnabled(true);
                    holdButton.setEnabled(true);
                    //statusTextView.setText("Computer Rolled a One!");
                    //turnHandler.postDelayed(this, 2000);


                }
                if (rolledValue1 == 1 && rolledValue2 == 1) {   //Computer rolled both one
                    computerTurnScore = 0;
                    computerScore = 0;
                    //count +=1;
                    user_Turn = true;
                    showUserScore();
                    Toast.makeText(getApplicationContext(),
                            "Computer Rolled Both One!", Toast.LENGTH_SHORT).show();
                    //statusTextView.setText("Computer Rolled Both One!");
                    rollButton.setEnabled(true);
                    holdButton.setEnabled(true);
                    user_Turn = true;


                }

            }
        };
        turnHandler.postDelayed(computerRunable, 2500);

    }

    public void reset(View view) {
        //statusTextView.setText("");
        userTurnScore = 0;
        userScore = 0;
        computerScore = 0;
        computerTurnScore = 0;
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
        user_Turn = true;
        showUserScore();

    }

    public void hold(View view) {
        //statusTextView.setText("");
        userScore += userTurnScore;
        userTurnScore = 0;
        user_Turn = false;
        showUserScore();
        computerTurn();
    }


    private int rollDice1() {
        int rolledValue = random.nextInt(6) + 1;
        setDice1(rolledValue);
        return rolledValue;
    }

    private void setDice1(int rolledValue) {
        final int drawableId;
        switch (rolledValue) {
            case 1:
                drawableId = R.drawable.dice1;
                break;
            case 2:
                drawableId = R.drawable.dice2;
                break;

            case 3:
                drawableId = R.drawable.dice3;
                break;
            case 4:
                drawableId = R.drawable.dice4;
                break;
            case 5:
                drawableId = R.drawable.dice5;
                break;
            case 6:
                drawableId = R.drawable.dice6;
                break;
            default:
                drawableId = R.drawable.dice1;


        }
        diceImageView1.setImageResource(android.R.color.transparent);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 7500ms
                diceImageView1.setImageResource(drawableId);
            }
        }, 750);


    }

    private int rollDice2() {
        int rolledValue = random.nextInt(6) + 1;
        setDice2(rolledValue);
        return rolledValue;
    }

    private void setDice2(int rolledValue) {

            final int drawableId;
            switch (rolledValue) {
                case 1:
                    drawableId = R.drawable.dice1;
                    break;
                case 2:
                    drawableId = R.drawable.dice2;
                    break;

                case 3:
                    drawableId = R.drawable.dice3;
                    break;
                case 4:
                    drawableId = R.drawable.dice4;
                    break;
                case 5:
                    drawableId = R.drawable.dice5;
                    break;
                case 6:
                    drawableId = R.drawable.dice6;
                    break;
                default:
                    drawableId = R.drawable.dice1;


            }

            diceImageView2.setImageResource(android.R.color.transparent);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 7500ms
                diceImageView2.setImageResource(drawableId);
            }
        }, 750);

        }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.scarnesdice/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.scarnesdice/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
