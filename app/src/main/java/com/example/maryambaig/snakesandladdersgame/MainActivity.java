package com.example.maryambaig.snakesandladdersgame;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //declaring variables
    ImageButton dice;
    TextView Ascore;
    TextView Pscore;
    TextView levels;
    Random r;
    int numOnroll;
    int aposition=1;
    int hposition=1;
    Boolean turn = false;
    Handler h=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dice = (ImageButton) findViewById(R.id.roll);
        Ascore = (TextView) findViewById(R.id.ascore);
        Pscore = (TextView) findViewById(R.id.pscore);
        levels = (TextView) findViewById(R.id.level);
        r = new Random();

        Toast.makeText(getApplicationContext(), "Player's turn", Toast.LENGTH_SHORT).show();
        dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                            //on button click
                HumanMove();  //move human

                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        AOmove();   //after delay of 3 sec move AO
                    }

                }, 3000);
            }
        });
    }

    public boolean validMove(int pos)  //check if number crossing board limit
    {
        if(pos>100)
            return false;
        else
            return true;
    }

    public void HumanMove(){   //moves human

        if (turn == false) {
            numOnroll = r.nextInt(6) + 1; //after clicking die is rolled
            setdiceImage(numOnroll);         //set image on button
            dice.setEnabled(false);   //disbale button

            int hlpos = checkLadder(hposition+numOnroll);   //get ladder head if exists
            int hspos = checksnakes(hposition+numOnroll);   //get snakes tail if exists

            if (hlpos != 0) { //if ladder appears
                Toast.makeText(getApplicationContext(), "Yayy! You got ladder", Toast.LENGTH_SHORT).show();
                hposition = hlpos;
                Pscore.setText(hposition + "");
                turn = true;
            } else if (hspos != 0) {  //if snake appears
                Toast.makeText(getApplicationContext(), "Ops! You got snake", Toast.LENGTH_SHORT).show();
                hposition = hspos;
                Pscore.setText(hposition + "");
                turn = true;

            } else {
                if (validMove(hposition+numOnroll)==true) { //otherwise add number to position
                    hposition += numOnroll;
                    Pscore.setText(hposition + "");
                }
                else
                    Toast.makeText(getApplicationContext(),"No move",Toast.LENGTH_SHORT).show();
                turn = true;
            }

            if(hposition==100)         //board finish reached 100, reset variables
            {
                Toast.makeText(getApplicationContext(),"You Won!",Toast.LENGTH_SHORT).show();
                reset();
            }
        }
    }

    public void AOmove()  //moves AP
    {
        if (turn == true) {
            Toast.makeText(getApplicationContext(), "AO's turn", Toast.LENGTH_SHORT).show();

            numOnroll = r.nextInt(6) + 1;
            setdiceImage(numOnroll);       //roll dice

            int alpos = checkLadder(aposition+numOnroll);  //check conditions
            int aspos = checksnakes(aposition+numOnroll);

            if (alpos != 0) {   //if ladder appears
                Toast.makeText(getApplicationContext(), "Yayy! AO got ladder", Toast.LENGTH_SHORT).show();
                aposition = alpos;
                Ascore.setText(aposition + "");
                turn = false;
                dice.setEnabled(true);
            } else if (aspos != 0) {  //if snakes appears
                Toast.makeText(getApplicationContext(), "Oops! AO got snake", Toast.LENGTH_SHORT).show();
                aposition = aspos;
                Ascore.setText(aposition + "");
                turn = false;
                dice.setEnabled(true);
            } else {
                if (validMove(aposition+numOnroll)==true) { //otherwise add number to position if board is not exceeded
                    aposition += numOnroll;
                    Ascore.setText(aposition + "");
                    turn=false;
                    dice.setEnabled(true);
                }
                else
                { Toast.makeText(getApplicationContext(),"No move",Toast.LENGTH_SHORT).show(); //no move if exceed board
                    turn = false;
                    dice.setEnabled(true);
                }
            }

            if(aposition==100)    //if board ends
            {
                Toast.makeText(getApplicationContext(),"AO Won!",Toast.LENGTH_SHORT).show();
                reset();
            }
        }
        Toast.makeText(getApplicationContext(), "Player's turn", Toast.LENGTH_SHORT).show();
    }


    public void setdiceImage(int n)   //change dice image button
    {
        if(n==1)
            dice.setImageResource(R.drawable.one);
        else if(n==2)
            dice.setImageResource(R.drawable.two);
        else if(n==3)
            dice.setImageResource(R.drawable.three);
        else if(n==4)
            dice.setImageResource(R.drawable.four);
        else if(n==5)
            dice.setImageResource(R.drawable.five);
        else if(n==6)
            dice.setImageResource(R.drawable.six);

    }

    public int checksnakes(int position) {   //returns snake tail posiition

        switch (position) {
            case 96:
                return 67;
            case 62:
                return 41;
            case 55:
                return 37;
            case 52:
                return 29;
            case 27:
                return 9;
            case 22:
                return 17;
            default:
                return 0;
        }
    }

    public int checkLadder(int position) {

        switch (position) {
            case 4:
                return 35;
            case 12:
                return 47;
            case 39:
                return 56;
            case 51:
                return 89;
            case 64:
                return 80;
            case 74:
                return 84;
            default:
                return 0;
        }
    }   //returns ladder head position

    public void reset()  //resets variables
    {
        Ascore.setText("1");
        Pscore.setText("1");
        aposition=1;
        aposition=1;
    }

}
