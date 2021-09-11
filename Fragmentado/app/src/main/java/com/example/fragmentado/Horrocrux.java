package com.example.fragmentado;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Horrocrux extends Fragment {
    public Horrocrux (){
        super(R.layout.horrocrux);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        int someInt = requireArguments().getInt("noHorrocrux");

    }
}
