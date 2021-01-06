package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.wontanara.dictionnairedelanguedessignes.R;

public class VideoPlayerFragment extends BaseFragment {

    private static final String VIDEO_SAMPLE = "gentil_bien";
    private VideoView mVideoView;


    public VideoPlayerFragment() {
        // Required empty public constructor
    }

//    ------ BASE METHODS ------

    public BaseFragment newInstance() {
        return new VideoPlayerFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_video_player;
    }

    @Override
    protected void configureDesign(View view) {
        MediaController controller = new MediaController(getContext());
        controller.setMediaPlayer(mVideoView);
//        controller.setAnchorView(mVideoView);
        mVideoView.setMediaController(controller);

    }

    @Override
    protected void findElements(View view) {
        mVideoView = view.findViewById(R.id.videoview);
    }


//    ------ OVERRIDE METHODS ------
    @Override
    public void onStart() {
        super.onStart();

        initializePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();

        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }



    private Uri getMedia(String mediaName) {
//        TODO: changer quand mise en place de la bdd SQLite
        return Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw/"
                + mediaName);
    }

    private void initializePlayer() {
        Uri videoUri = getMedia(VIDEO_SAMPLE);
        mVideoView.setVideoURI(videoUri);

        mVideoView.start();
    }

    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

}