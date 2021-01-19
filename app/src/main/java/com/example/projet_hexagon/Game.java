package com.example.projet_hexagon;

import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
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


public class Game extends AppCompatActivity {

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
    private static ImageView _end;
    public static int nbb;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;

        _button = findViewById(R.id.button);
        _end = findViewById(R.id.lost);

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
        nbb=numberofbomb;
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

               /* //leftborder
                else if ()
                {
                    input.get(k + 1).setNeighbors(input.get(k + 1).getNeighbors() + 1);
                    input.get(k + _X).setNeighbors(input.get(k + _X).getNeighbors() + 1);
                    input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);

                    if ((k + 1) % (_X + ((_X - 1))) == 0))
                    {
                        input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
                        input.get(k + (_X - 1)).setNeighbors(input.get(k + (_X - 1)).getNeighbors() + 1);
                    }

                }
                //righborder
                else if()
                {
                    input.get(k - 1).setNeighbors(input.get(k + 1).getNeighbors() - 1);
                    input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
                    input.get(k + (_X - 1)).setNeighbors(input.get(k + (_X - 1)).getNeighbors() + 1);

                    if (ligne plus petite)
                    {
                        input.get(k + _X).setNeighbors(input.get(k + _X).getNeighbors() + 1);
                        input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);
                    }

                }*/
                else {
                    input.get(k - 1).setNeighbors(input.get(k - 1).getNeighbors() + 1);
                    input.get(k + 1).setNeighbors(input.get(k + 1).getNeighbors() + 1);
                    input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);
                    input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
                    input.get(k + (_X - 1)).setNeighbors(input.get(k + (_X - 1)).getNeighbors() + 1);
                    input.get(k + _X).setNeighbors(input.get(k + _X).getNeighbors() + 1);
                }


            }
        }

        mAdapter = new MyAdapter(input, _X, _Y, width);
        hexaRcv.setAdapter(mAdapter);


    }


    @Override
    public void onResume() {
        super.onResume();

        _button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Game.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                finish();


            }
        });
    }

    //Nombres de bombes selon la difficulte choisi
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

    //Répartition des bombes aléatoirement dans un tableau
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

    /* public List<HexaCase> getneighbors(List<HexaCase> _input,int x, int y, int all) {

         List<HexaCase> arrayofneighbors = new ArrayList<>();

         for (int k = 0; k < all; k++) //Placement des nombres de voisins
         {
             //first line
             if (k < x) {
                 if (k > 0) {
                     arrayofneighbors.add(_input.get(k - 1));
                     arrayofneighbors.add(_input.get(k + (x - 1)));
                 }
                 if (k != x - 1) {
                     arrayofneighbors.add(_input.get(k + 1));
                     arrayofneighbors.add(_input.get(k + x));
                 }
             }
             //last line
             else if (k > all - (x - 1)) {
                 if (k > all - (x - 1))
                     arrayofneighbors.add(_input.get(k - 1));
                 if ((k < all - 1) || y % 2 == 0) {
                     arrayofneighbors.add(_input.get(k + 1));
                     arrayofneighbors.add(_input.get(k - (x - 1)));
                 }
                 arrayofneighbors.add(_input.get(k - x));
             }

                /* //leftborder
                 else if ()
                 {
                     input.get(k + 1).setNeighbors(input.get(k + 1).getNeighbors() + 1);
                     input.get(k + _X).setNeighbors(input.get(k + _X).getNeighbors() + 1);
                     input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);

                     if ((k + 1) % (_X + ((_X - 1))) == 0))
                     {
                         input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
                         input.get(k + (_X - 1)).setNeighbors(input.get(k + (_X - 1)).getNeighbors() + 1);
                     }

                 }
                 //righborder
                 else if()
                 {
                     input.get(k - 1).setNeighbors(input.get(k + 1).getNeighbors() - 1);
                     input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
                     input.get(k + (_X - 1)).setNeighbors(input.get(k + (_X - 1)).getNeighbors() + 1);

                     if (ligne plus petite)
                     {
                         input.get(k + _X).setNeighbors(input.get(k + _X).getNeighbors() + 1);
                         input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);
                     }

                 }*//*
            else {
                arrayofneighbors.add(_input.get(k - 1));
                arrayofneighbors.add(_input.get(k + 1));
                arrayofneighbors.add(_input.get(k - (x - 1)));
                arrayofneighbors.add(_input.get(k - x));
                arrayofneighbors.add(_input.get(k + (x - 1)));
                arrayofneighbors.add(_input.get(k + x));
            }

            //_input.get(k + 1).setNeighbors(input.get(k + 1).getNeighbors() + 1);
          // _input.get(k - 1).setNeighbors(input.get(k - 1).getNeighbors() + 1);
          // _input.get(k - (_X - 1)).setNeighbors(input.get(k - (_X - 1)).getNeighbors() + 1);
         //  _input.get(k - _X).setNeighbors(input.get(k - _X).getNeighbors() + 1);
        }
        return arrayofneighbors;
    }*/
    public static void end(boolean end) {
        if (end == false) {
            _end.setBackgroundResource(R.drawable.lose);
        }
        if (end == true) {
            _end.setBackgroundResource(R.drawable.win);
        }
    }
}


