package com.example.projet_hexagon;

import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.media.MediaPlayer;



public class Game extends AppCompatActivity {

    public static int nbb;
    private static ImageView _end;
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;
    private static TextView nblose,nbwin;
    private RecyclerView hexaRcv;
    private RecyclerView.Adapter mAdapter;
    private HexaCase imageh;
    //private RecyclerView.GriLayoutManager layoutManager;
    private List<HexaCase> input;
    private List<HexaCase> testb;
    private int width, numberofbomb;
    private ImageView _button = null;
    private int[] test;
    private int _X, _Y, _All;



    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;

        prefs = getSharedPreferences("Nb of game", MODE_PRIVATE);
        editor = prefs.edit();
        nblose = findViewById(R.id.nblose);
        nbwin=findViewById(R.id.nbwin);

        _button = findViewById(R.id.button);

        _end = findViewById(R.id.lost);

        mediaPlayer = MediaPlayer.create(this, R.raw.start);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.cowboy);

        Intent intent = getIntent();
        String X = intent.getStringExtra("X");
        String Y = intent.getStringExtra("Y");
        String Diff = intent.getStringExtra("Diff");
        _X = Integer.parseInt(X);
        _Y = Integer.parseInt(Y);

        _All = _X * _Y - (_Y / 2);


        ImageView image = new ImageView(this);

        hexaRcv = (RecyclerView) findViewById(R.id.hexa_rcv);
        hexaRcv.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this, _X);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int size = 1;
                if ((position + 1) % (_X + ((_X - 1))) == 0) {
                    size = 2;

                }
                return size;
            }
        });

        hexaRcv.setLayoutManager(manager);
        input = new ArrayList<>();

        numberofbomb = getnumberofbomb(_X, _Y, Integer.parseInt(Diff));
        nbb = numberofbomb;
        //numberofbomb=2;

        int j = 0;
        test = setbomblocalisation(_All, numberofbomb);

        for (int i = 0; i < _Y * _X; i++) {
            if (j < numberofbomb) {
                if (i == test[j]) {
                    imageh = new HexaCase(image, true);
                    j++;
                } else imageh = new HexaCase(image, false);
            } else imageh = new HexaCase(image, false);

            input.add(imageh);
        }

        setneighbors(input, _X, _Y, _All);

/*
        for (int k = 0; k < _All; k++) //if bomb
        {

            if (input.get(k).getBomb() == true) {
                //first line
                if (k < _X) {
                    if (k > 0) {
                        input.get(k - 1).setNeighbors(input.get(k - 1).getNeighbors() + 1);
                        input.get(k + (_X - 1)).setNeighbors(input.get(k + (_X - 1)).getNeighbors() + 1);
                    }
                    if (k != _X - 1) {
                        input.get(k + 1).setNeighbors(input.get(k + 1).getNeighbors() + 1);
                        input.get(k + _X).setNeighbors(input.get(k + _X).getNeighbors() + 1);
                    }
                }

                //last line
                else if (k > _All - (_X - 1)) {
                    if (k > _All - (_X - 1))
                        input.get(k - 1).setNeighbors(input.get(k - 1).getNeighbors() + 1);
                    if ((k < _All - 1) || _Y % 2 == 0) {
                        input.get(k + 1).setNeighbors(input.get(k + 1).getNeighbors() + 1);
                        input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);
                    }
                    input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
                }

               //leftborder
                else if (k==9)
                {
                    input.get(k + 1).setNeighbors(input.get(k + 1).getNeighbors() + 1);
                    input.get(k + _X).setNeighbors(input.get(k + _X).getNeighbors() + 1);
                    input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);

                    if ((k + 1) % (_X + ((_X - 1))) == 0)
                    {
                        input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
                        input.get(k + (_X - 1)).setNeighbors(input.get(k + (_X - 1)).getNeighbors() + 1);
                    }

                }
                //righborder
                else if(k==8)
                {
                    input.get(k - 1).setNeighbors(input.get(k + 1).getNeighbors() - 1);
                    input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
                    input.get(k + (_X - 1)).setNeighbors(input.get(k + (_X - 1)).getNeighbors() + 1);

                    if ((k + 1) % (_X + ((_X - 1))) == 0)
                    {
                        input.get(k + _X).setNeighbors(input.get(k + _X).getNeighbors() + 1);
                        input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);
                    }

                }
                else {
                    input.get(k - 1).setNeighbors(input.get(k - 1).getNeighbors() + 1);
                    input.get(k + 1).setNeighbors(input.get(k + 1).getNeighbors() + 1);
                    input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);
                    input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
                    input.get(k + (_X - 1)).setNeighbors(input.get(k + (_X - 1)).getNeighbors() + 1);
                    input.get(k + _X).setNeighbors(input.get(k + _X).getNeighbors() + 1);
                }
            }
        }*/
        mAdapter = new MyAdapter(input, _X, _Y, width);
        hexaRcv.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        mediaPlayer2.start();


        nblose.setText(prefs.getString("lose", "0"));
        nbwin.setText(prefs.getString("win", "0"));

        _button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Game.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                mediaPlayer.start();
                finish();
            }
        });
    }
    //#########################################################################################################

    //###################################   Nombres de bombes selon la difficulte choisi   ###################################
    public int getnumberofbomb(int x, int y, int difficulty) {
        switch (difficulty) {
            case 2: //intermediate
                return x * y / 6;
            case 3: //hard
                return x * y / 5;
            default: //easy or default
                return x * y / 7;
        }
    }
    //#########################################################################################################

    //###################################   Répartition des bombes aléatoirement dans un tableau   ###################################
    public int[] setbomblocalisation(int size, int nb) {

        int[] a = new int[nb];

        for (int i = 0; i < nb; i++) {
            a[i] = (int) (Math.random() * size);

            for (int j = 0; j < i; j++) {
                if (a[i] == a[j]) {
                    a[i] = (int) (Math.random() * size); //si deux fois le meme nombre
                    i--;
                    break;
                }
            }
        }
        Arrays.sort(a); //tri dans l'ordre croissant pour placement des bomb dans le main
        return a;
    }

    //#########################################################################################################

    //###################################   Creation du tableau du nombres de bobmbes voisines   ###################################
    public void setneighbors(List<HexaCase> _input, int x, int y, int all) {


        for (int k = 0; k < _All; k++)
        {

            if (input.get(k).getBomb() == true) { // Si la case comporte une bombe on incrémente autour de celle-ci le nombre de bombes voisines :

                // Si la case est sur le bord du haut :
                if (k < _X) {
                    if (k > 0) {
                        input.get(k - 1).setNeighbors(input.get(k - 1).getNeighbors() + 1);
                        input.get(k + (_X - 1)).setNeighbors(input.get(k + (_X - 1)).getNeighbors() + 1);
                    }
                    if (k != _X - 1) {
                        input.get(k + 1).setNeighbors(input.get(k + 1).getNeighbors() + 1);
                        input.get(k + _X).setNeighbors(input.get(k + _X).getNeighbors() + 1);
                    }
                }

                //Si la case est sur le bord du bas :
                else if ((_Y%2==0 && k > _All - _X )|| (_Y%2!=0 && k >= _All -_X)) {

                 if(_Y%2==0) //petite ligne
                    {
                        if (k != _All-1) //Si different de la derniere case
                        {
                            input.get(k + 1).setNeighbors(input.get(k + 1).getNeighbors() + 1);
                        }
                        if ((((k+(_X-1))%((2*_X)-1))!=0) && (((k % ((2*_X)-1))!=0))) { //si je suis pas sur 1ere case
                            input.get(k - 1).setNeighbors(input.get(k - 1).getNeighbors() + 1);
                        }
                        input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);
                        input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
                    }
                    else //grande ligne
                    {
                        if (k != _All-1) //Si different de la derniere case
                        {
                            input.get(k + 1).setNeighbors(input.get(k + 1).getNeighbors() + 1);
                            input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);
                        }
                        if ((((k+(_X-1))%((2*_X)-1))!=0) && (((k % ((2*_X)-1))!=0))) { //si je suis pas sur 1ere case
                            input.get(k - 1).setNeighbors(input.get(k - 1).getNeighbors() + 1);
                            input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
                        }
                    }

                }

                //Si la case est sur le bord gauche :
                else  if ((((k+(_X-1))%((2*_X)-1))==0) || (((k % ((2*_X)-1))==0))) {
                    input.get(k + 1).setNeighbors(input.get(k + 1).getNeighbors() + 1);
                    input.get(k + _X).setNeighbors(input.get(k + _X).getNeighbors() + 1);
                    input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);

                    if ((k + 1) % (_X + ((_X - 1))) == 0) {
                        input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
                        input.get(k + (_X - 1)).setNeighbors(input.get(k + (_X - 1)).getNeighbors() + 1);
                    }

                }
                //Si la case est sur le bord droit :
                else if ((((k+_X)%((2*_X)-1))==0) || ((((k+1) % ((2*_X)-1))==0)))
                {
                    input.get(k - 1).setNeighbors(input.get(k + 1).getNeighbors() + 1);
                    input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
                    input.get(k + (_X - 1)).setNeighbors(input.get(k + (_X - 1)).getNeighbors() + 1);

                    if ((k + 1) % (_X + ((_X - 1))) == 0) {
                        input.get(k + _X).setNeighbors(input.get(k + _X).getNeighbors() + 1);
                        input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);
                    }
                }
                else { // Si la case n'est pas un contour :
                    input.get(k - 1).setNeighbors(input.get(k - 1).getNeighbors() + 1);
                    input.get(k + 1).setNeighbors(input.get(k + 1).getNeighbors() + 1);
                    input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);
                    input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
                    input.get(k + (_X - 1)).setNeighbors(input.get(k + (_X - 1)).getNeighbors() + 1);
                    input.get(k + _X).setNeighbors(input.get(k + _X).getNeighbors() + 1);


                }
            }
        }
    }
    // #########################################################################################################

    //###################################  Affichage du logo victoire ou defaite et du nombre de parties gagnees ou perdues  ###################################
    public static void end(boolean end) {
        if (end == false) {
           _end.setBackgroundResource(R.drawable.lose); //logo defaite
            editor.putString("lose", String.valueOf(Integer.parseInt(nblose.getText().toString()) + 1)); //incrementation du nombre de parties perdues
            editor.apply();
        }
        if (end == true) {
           _end.setBackgroundResource(R.drawable.win); //logo victoire
            editor.putString("win", String.valueOf(Integer.parseInt(nbwin.getText().toString()) + 1)); //incrementation du nombre de parties gagnes
            editor.apply();
        }
    }



    // #########################################################################################################

}


