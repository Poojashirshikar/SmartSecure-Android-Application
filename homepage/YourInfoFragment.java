package com.example.homepage;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class YourInfoFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_your_info, container, false);

        Button secondActivityButton = view.findViewById(R.id.secondActivityButton);
        secondActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment1 = new personaldetails();
                //FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment1);
                fragmentTransaction.commit();

            }
        });

        Button thirdActivityButton = view.findViewById(R.id.thirdActivityButton);
        thirdActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment2 = new loginpage();
                //FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment2);
                fragmentTransaction.commit();
            }
        });


        Button fifthActivityButton = view.findViewById(R.id.fifthActivityButton);
        fifthActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment4 = new Demo();
                //FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment4);
                fragmentTransaction.commit();
            }
        });

        Button SixActivityButton = view.findViewById(R.id.SixActivityButton);
        SixActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment5 = new Help();
                //FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment5);
                fragmentTransaction.commit();
            }
        });


        Button SevenActivityButton = view.findViewById(R.id.SevenActivityButton);
        SevenActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment6 = new SendFeedback();
                //FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment6);
                fragmentTransaction.commit();

            }
        });


        return view;


    }



}
