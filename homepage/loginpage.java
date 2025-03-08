package com.example.homepage;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class loginpage extends Fragment {


    private EditText t8, t9;
    private Button insertbutton,registerbtn;
    private DatabaseHelper dbhelper;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loginpage, container, false);


        t8 = (EditText) view.findViewById(R.id.edittext);
        t9 = (EditText) view.findViewById(R.id.edittext1);
        insertbutton = view.findViewById(R.id.loginbutton);
        registerbtn = view.findViewById(R.id.regbutton);

        dbhelper = new DatabaseHelper(getActivity());

        insertbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = t8.getText().toString();
                String pass = t9.getText().toString();


                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(getActivity(), "Enter All Fileds", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean isAuth = dbhelper.authoUser(name,pass);
                    if (isAuth) {
                        Toast.makeText(getActivity(), "Login Successfully",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = t8.getText().toString();
                String pass = t9.getText().toString();


                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(getActivity(), "Enter All Fileds", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isIn = dbhelper.logindata(name,pass);
                    if (isIn) {
                        Toast.makeText(getActivity(), "User Registered Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });





        return view;

    }
}