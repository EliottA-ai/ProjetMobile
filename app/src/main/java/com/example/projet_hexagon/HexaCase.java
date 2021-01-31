package com.example.projet_hexagon;

import android.text.Layout;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import androidx.annotation.Dimension;

import java.util.List;

public class HexaCase {



    private ImageView _Image;
    private boolean _bomb,_discover;
    private int _neighbors;

    public HexaCase(ImageView img, boolean bomb) {
        _bomb = bomb;
        _Image = img;
        _discover=false;

    }

    public Boolean getBomb() {
        return _bomb;
    }
    public void  setNeighbors(int Neighbors) {_neighbors = Neighbors;}
    public int getNeighbors() {return _neighbors;}

    public void  setdiscover() {_discover = true;}
    public Boolean getdiscover() {return _discover;}


}
