package com.example.homepage;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

public class Demo extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_demo, container, false);
        VideoView video =view.findViewById(R.id.videoView);
        video.setVideoURI(Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.demo_video));
        video.start();
        return view;
    }
}