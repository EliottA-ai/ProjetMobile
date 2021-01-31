package com.example.projet_hexagon;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Handler;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<HexaCase> values;
    private int a = 0, _X = 0, _Y = 0, _width = 0;
    private int tot = 0;


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<HexaCase> myDataset, int X, int Y, int width) {
        values = myDataset;
        _X = X;
        _Y = Y;
        _width = width;
    }

    public void add(int position, HexaCase item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.hexa, parent, false);
        // set the view's size, margins, paddings and layout parameters
        v.getLayoutParams().width = _width / (_X + 1);
        v.getLayoutParams().height = _width / (_X + 1);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // final HexaCase image = values.get(position);

        int ab = position + 1;

        GridLayoutManager.LayoutParams param = (GridLayoutManager.LayoutParams) holder.txtHeader.getLayoutParams();
        int topMargin = pxFromDp(holder.txtHeader.getContext(), -8);
        int leftMargin = pxFromDp(holder.txtHeader.getContext(), -0.2333f * (float) Math.pow(_X, 3) + 5.7667f * (float) Math.pow(_X, 2) - 49.267f * _X + 167.33f);//3 times of 17

        if (ab < _X + 1) param.setMargins(0, 0, 0, 0);

        else if (ismodulo(ab, _X)) param.setMargins(leftMargin, topMargin, 0, 0);

        else param.setMargins(0, topMargin, 0, 0);

        if (values.get(position).getdiscover() == true) {

            switch (values.get(position).getNeighbors()) {

                case 1:
                    holder.txtHeader.setBackgroundResource(R.drawable.h1);
                    win();
                    break;
                case 2:
                    holder.txtHeader.setBackgroundResource(R.drawable.h2);
                    win();
                    break;
                case 3:
                    holder.txtHeader.setBackgroundResource(R.drawable.h3);
                    win();
                    break;
                case 4:
                    holder.txtHeader.setBackgroundResource(R.drawable.h4);
                    win();
                    break;
                case 5:
                    holder.txtHeader.setBackgroundResource(R.drawable.h5);
                    win();
                    break;
                case 6:
                    holder.txtHeader.setBackgroundResource(R.drawable.h6);
                    win();
                    break;
                case 0:
                    holder.txtHeader.setBackgroundResource(R.drawable.h0);
                    win();
                    break;
            }
        }


        holder.txtHeader.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.txtHeader.setBackgroundResource(R.drawable.drapeau); //Affichage du drapeau sur la case

                return true;
            }
        });


        holder.txtHeader.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (values.get(position).getBomb() == true) {
                    holder.txtHeader.setBackgroundResource(R.drawable.bombpng);

                    Game.end(false);
                } else {


                    switch (values.get(position).getNeighbors()) {

                        case 1:
                            holder.txtHeader.setBackgroundResource(R.drawable.h1);
                            values.get(position).setdiscover();
                            win();
                            break;
                        case 2:
                            holder.txtHeader.setBackgroundResource(R.drawable.h2);
                            values.get(position).setdiscover();
                            win();
                            break;
                        case 3:
                            holder.txtHeader.setBackgroundResource(R.drawable.h3);
                            values.get(position).setdiscover();
                            win();
                            break;
                        case 4:
                            holder.txtHeader.setBackgroundResource(R.drawable.h4);
                            values.get(position).setdiscover();
                            win();
                            break;
                        case 5:
                            holder.txtHeader.setBackgroundResource(R.drawable.h5);
                            values.get(position).setdiscover();
                            win();
                            break;
                        case 6:
                            holder.txtHeader.setBackgroundResource(R.drawable.h6);
                            values.get(position).setdiscover();
                            win();
                            break;
                        case 0:
                            holder.txtHeader.setBackgroundResource(R.drawable.h0);
                            recursive(position);
                            break;
                    }
                }
            }


        });

        holder.txtHeader.setLayoutParams(param);
    }

    public void recursive(int k) {

        ArrayList<Integer> a = new ArrayList<>();
        a.clear();

        // Si la case est sur le bord du haut :
        if (k < _X) {
            if (k > 0) {
                a.add(k - 1);
                a.add(k + (_X - 1));
            }
            if (k != _X - 1) {
                a.add(k + 1);
                a.add(k + _X);
            }
        }

        //Si la case est sur le bord du bas :
        else if ((_Y % 2 == 0 && k > getItemCount() - _X) || (_Y % 2 != 0 && k >= getItemCount() - _X)) {
            if (_Y % 2 == 0) //petite ligne
            {
                if (k != getItemCount() - 1) //Si different de la derniere case
                {
                    a.add(k + 1);
                }
                if  ((((k+(_X-1))%((2*_X)-1))!=0) && (((k % ((2*_X)-1))!=0))) { //si je suis pas sur 1ere case
                    a.add(k - 1);
                }
                a.add(k - (_X - 1));
                a.add(k - _X);
            } else //grande ligne
            {
                if (k != getItemCount() - 1) //Si different de la derniere case
                {
                    a.add(k + 1);
                    a.add(k - (_X - 1));
                }
                if ((((k+(_X-1))%((2*_X)-1))!=0) && (((k % ((2*_X)-1))!=0))) { //si je suis pas sur 1ere case
                    a.add(k - 1);
                    a.add(k - _X);
                }
            }

        }
        //Si la case est sur le bord gauche :
        else if ((((k + (_X - 1)) % ((2 * _X) - 1)) == 0) || (((k % ((2 * _X) - 1)) == 0))) {
            a.add(k + 1);
            a.add(k + _X);
            a.add(k - (_X - 1));

            if ((k + 1) % (_X + ((_X - 1))) == 0) {
                a.add(k - _X);
                a.add(k + (_X - 1));
            }

        }
        //Si la case est sur le bord droit :
        else if ((((k + _X) % ((2 * _X) - 1)) == 0) || ((((k + 1) % ((2 * _X) - 1)) == 0))) {
            a.add(k - 1);
            a.add(k - _X);
            a.add(k + (_X - 1));

            if ((k + 1) % (_X + ((_X - 1))) == 0) {
                a.add(k + _X);
                a.add(k - (_X - 1));
            }
        } else { // Si la case n'est pas un contour :
            a.add(k - 1);
            a.add(k + 1);
            a.add(k - (_X - 1));
            a.add(k - _X);
            a.add(k + (_X - 1));
            a.add(k + _X);
        }

        for (int p : a) //parcours autour
        {
            if ((p >= 0) && (p < getItemCount())) {

                if (values.get(p).getNeighbors() == 0 && values.get(p).getdiscover() == false) {
                    values.get(p).setdiscover();
                    notifyItemChanged(p);
                    recursive(p);
                }
                for (int j = 1; j <= 6; j++) {
                    if (values.get(p).getNeighbors() == j && values.get(p).getdiscover() == false) {
                        values.get(p).setdiscover();
                        notifyItemChanged(p);
                    }
                }

            }

        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //return values.size();
        return _X * _Y - (_Y / 2);
    }

    private int pxFromDp(final Context context, final float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public boolean ismodulo(int nb, int X) {

        for (int i = 0; i < X - 1; i++) {
            if ((nb + i) % (X + (X - 1)) == 0) return true;
        }
        return false;

    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView txtHeader;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = v.findViewById(R.id.tv_1);
        }
    }

    public void win() {
        tot=0;
        for (int y = 0; y < getItemCount(); y++) {
            if (values.get(y).getdiscover() == true && values.get(y).getBomb()==false) {
                tot += 1;
            }
            Log.d("test", String.valueOf(tot));

            if (tot == getItemCount()-Game.nbb) {
                Game.end(true);
            }

        }

    }
}
