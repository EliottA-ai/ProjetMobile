package com.example.projet_hexagon;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;


public class MainActivity extends AppCompatActivity {

    private Button _play =null;
    private ImageView plus_x=null,plus_y=null,minus_x=null,minus_y=null,_setting;
    private EditText _X, _Y;
    private TextView _error, _exp;
    public RadioButton easy,intermediate, hard;
    public String win,lose,replay;
    private ProgressBar p;
    private Calculation c;
    private View v;

    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.settings);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.blop);

        _X = findViewById(R.id.editTextNumberX);

        _Y = findViewById(R.id.editTextNumberY);
        _play = findViewById(R.id.play);
        _error = findViewById(R.id.error);
        plus_x=findViewById(R.id.px);
        plus_y=findViewById(R.id.py);
        minus_y=findViewById(R.id.my);
        minus_x=findViewById(R.id.mx);
        easy=findViewById(R.id.Easy);
        intermediate=findViewById(R.id.Intermediate);
        hard=findViewById(R.id.Hard);
        _exp=findViewById(R.id.exp);
        _setting=findViewById(R.id.setting);
        p=findViewById(R.id.p);
        v=findViewById(R.id.loadbackground);
    }

    @Override
    protected void onStart() {
        super.onStart();
        c = new Calculation();
        _setting.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation));

    }

public void setlanguage(String s,String e, String i, String h,String p,String r, String l, String w)
{
    _exp.setText(s);
    hard.setText(h);
    easy.setText(e);
    intermediate.setText(i);
    _play.setText(p);
    win=w;
    lose=l;
    replay=l;

}
    @Override
    protected void onResume()
    {
        super.onResume();

        _setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                BlankFragment fragment = new  BlankFragment();
                fragment.setArguments(bundle);
                mediaPlayer.start();
                getSupportFragmentManager().beginTransaction().add(R.id.fragmcont, fragment).commit();

            }
        });

        minus_x.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               minus_y.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.my_animation));

               if(Integer.parseInt(_X.getText().toString())>3)
            {
                _X.setText(Integer.parseInt(_X.getText().toString()) - 1);
            }
           }
       });
        plus_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus_y.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.my_animation));

                if(Integer.parseInt(_Y.getText().toString())<18)
                {
                    int a = Integer.parseInt(_Y.getText().toString()) + 1;
                    _Y.setText(String.valueOf(a));

                }
            }
        });
        plus_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus_x.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.my_animation));

                if(Integer.parseInt(_X.getText().toString())<9)
                {
                    int a = Integer.parseInt(_X.getText().toString()) + 1;
                    _X.setText(String.valueOf(a));

                }
            }
        });
        minus_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minus_y.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.my_animation));

                if(Integer.parseInt(_Y.getText().toString())>3)
                {
                    int a = Integer.parseInt(_Y.getText().toString()) - 1;
                    _Y.setText(String.valueOf(a));
                }
            }
        });

        _play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.setVisibility(View.VISIBLE);
                v.setVisibility(View.VISIBLE);
                mediaPlayer2.start();
                _play.setVisibility(View.INVISIBLE);

                c.execute();

            }
        });
    }

    public int getdifficulty()
    {

        if (intermediate.isChecked())
        {
            return 2;
        }
        if (hard.isChecked())
        {
            return 3;
        }
        else return 1;
    }


    private class Calculation extends AsyncTask<Void, Integer,Void>
    {

        @Override
        protected void onPreExecute()
        {
            p.setProgress(0);
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            p.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            for(int i=0;i<=10;i++)
            {
                try{
                    Thread.sleep(500L);}
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                publishProgress(i*10);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Intent i = new Intent(MainActivity.this, Game.class);
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            i.putExtra("X",_X.getText().toString());
            i.putExtra("Y",_Y.getText().toString());
            i.putExtra("win",win);
            i.putExtra("lose",lose);
            i.putExtra("replay",replay);
            i.putExtra("Diff",String.valueOf(getdifficulty()));
            if((Integer.parseInt(_X.getText().toString()) >=3 && Integer.parseInt(_X.getText().toString()) <=9) && (Integer.parseInt(_Y.getText().toString()) >=3 && Integer.parseInt(_Y.getText().toString()) <=18))
            {

                startActivity(i);
                finish();

            }
            else { _error.setText("Size problem (verify your X and Y value)"); }
        }
    }
}


