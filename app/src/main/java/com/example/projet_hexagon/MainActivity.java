package com.example.projet_hexagon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button _play =null;
    private ImageView plus_x=null,plus_y=null,minus_x=null,minus_y=null;
    private EditText _X, _Y;
    private TextView _error;
    public RadioButton easy,intermediate, hard;


    //https://jsfiddle.net/xL53zs6q/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        _play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                BlankFragment fragment = new  BlankFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.fragmcont, fragment).commit();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        minus_x.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            if(Integer.parseInt(_X.getText().toString())>3)
            {
                _X.setText(Integer.parseInt(_X.getText().toString()) - 1);
            }
           }
       });


        plus_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                if(Integer.parseInt(_Y.getText().toString())>3)
                {
                    int a = Integer.parseInt(_Y.getText().toString()) - 1;
                    _Y.setText(String.valueOf(a));
                }
            }
        });

        /*_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Game.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                i.putExtra("X",_X.getText().toString());
                i.putExtra("Y",_Y.getText().toString());
                i.putExtra("Diff",String.valueOf(getdifficulty()));
                if((Integer.parseInt(_X.getText().toString()) >=3 && Integer.parseInt(_X.getText().toString()) <=9) && (Integer.parseInt(_Y.getText().toString()) >=3 && Integer.parseInt(_Y.getText().toString()) <=18))
                {
                    startActivity(i);
                    finish();
                }
                else { _error.setText("Size problem (verify your X and Y value)"); }
            }
        });*/
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
}

