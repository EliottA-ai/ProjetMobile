package com.example.projet_hexagon;

import android.content.Context;
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
    private int a=0, _X=0,_Y=0,_width=0,tot=0;
    private boolean line = true;


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

    public void add(int position, HexaCase item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<HexaCase> myDataset, int X, int Y, int width)
    {
        values = myDataset;
        _X=X;
        _Y=Y;
        _width=width;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.hexa, parent, false);
        // set the view's size, margins, paddings and layout parameters
        v.getLayoutParams().width=_width/(_X+1);
        v.getLayoutParams().height=_width/(_X+1);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
       // final HexaCase image = values.get(position);

        int ab=position+1;

       GridLayoutManager.LayoutParams param = (GridLayoutManager.LayoutParams) holder.txtHeader.getLayoutParams();
        int topMargin = pxFromDp(holder.txtHeader.getContext(), -8);
        int leftMargin = pxFromDp(holder.txtHeader.getContext(), -0.2333f*(float)Math.pow(_X,3) + 5.7667f*(float)Math.pow(_X,2) - 49.267f*_X + 167.33f);//3 times of 17

        if (ab <_X+1) param.setMargins(0, 0,0, 0);

        else if (ismodulo(ab,_X)) param.setMargins(leftMargin, topMargin, 0, 0);

        else param.setMargins(0, topMargin, 0, 0);

        if (values.get(position).getBomb() ==false) {
            if (values.get(position).getNeighbors() == 7) {

                holder.txtHeader.setBackgroundResource(R.drawable.h0);
            }
            if (values.get(position).getNeighbors() == 11) {

                holder.txtHeader.setBackgroundResource(R.drawable.h1);
            }
            if (values.get(position).getNeighbors() == 12) {

                holder.txtHeader.setBackgroundResource(R.drawable.h2);
            }
            if (values.get(position).getNeighbors() == 13) {

                holder.txtHeader.setBackgroundResource(R.drawable.h3);
            }
            if (values.get(position).getNeighbors() == 14) {

                holder.txtHeader.setBackgroundResource(R.drawable.h4);
            }
            if (values.get(position).getNeighbors() == 15) {

                holder.txtHeader.setBackgroundResource(R.drawable.h5);
            }
            if (values.get(position).getNeighbors() == 16) {

                holder.txtHeader.setBackgroundResource(R.drawable.h6);
            }
        }

        holder.txtHeader.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                holder.txtHeader.setBackgroundResource(R.drawable.drapeau);
                if (values.get(position).getBomb() == true) tot+=1;
                if(holder.txtHeader.getBackground()==holder.txtHeader.getResources().getDrawable(R.drawable.drapeau))
                {
                    holder.txtHeader.setBackgroundResource(R.drawable.hexagonpng);
                }

                return true;
            }
        });


        holder.txtHeader.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    if (values.get(position).getBomb() == true)
                    {
                        holder.txtHeader.setBackgroundResource(R.drawable.bombpng);
                        Game.end(false);
                    } else {
                        switch (values.get(position).getNeighbors()) {
                            case 1:
                                holder.txtHeader.setBackgroundResource(R.drawable.h1);

                                break;
                            case 2:
                                holder.txtHeader.setBackgroundResource(R.drawable.h2);

                                break;
                            case 3:
                                holder.txtHeader.setBackgroundResource(R.drawable.h3);

                                break;
                            case 4:
                                holder.txtHeader.setBackgroundResource(R.drawable.h4);

                                break;
                            case 5:
                                holder.txtHeader.setBackgroundResource(R.drawable.h5);

                                break;
                            case 6:
                                holder.txtHeader.setBackgroundResource(R.drawable.h6);

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
    public void recursive(int i)
    {
        ArrayList<Integer> a=new ArrayList<>();
        a.add(i+1);
        a.add(i-1);
        a.add(i+_X);
        a.add(i-_X);
        a.add(i+(_X-1));
        a.add(i+(_X+1));

        for (int k:a) //parcours autour
        {

            if((k>=0) && (k<getItemCount()))
            {
                if (values.get(k).getNeighbors() == 0 && values.get(k).getNeighbors() != 7) {
                    values.get(k).setNeighbors(7);
                    notifyItemChanged(k);
                    recursive(k);
                }
                for (int j=1;j<=6;j++)
                {
                    if (values.get(k).getNeighbors() == j && values.get(k).getNeighbors() != 7) {
                        values.get(k).setNeighbors(10+j);
                        notifyItemChanged(k);
                    }
                }

            }
        }

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //return values.size();
        return _X*_Y-(_Y/2);
    }

    private int pxFromDp(final Context context, final float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
    public boolean ismodulo(int nb, int X)
    {

        for (int i=0;i<X-1;i++)
        {
            if ( (nb+i) % (X+(X-1))==0) return true;
        }
        return false;

    }
}