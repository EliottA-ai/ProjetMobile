package com.example.projet_hexagon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;


public class BlankFragment extends Fragment {

    public Switch language;
    public String lg;



    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.languagefragment, container, false);
            language = view.findViewById(R.id.language);

            return view;
        }


    }







