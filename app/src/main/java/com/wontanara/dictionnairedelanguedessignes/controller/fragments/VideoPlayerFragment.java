package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.Word;
import com.wontanara.dictionnairedelanguedessignes.model.WordViewModel;

import java.util.Objects;

public class VideoPlayerFragment extends BaseFragment {

    private int mWordId;
    private Word mWord;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mWordId = getArguments().getInt("id-word");
        }
        WordViewModel mWordViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity()), ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(this.getActivity()).getApplication())).get(WordViewModel.class);
        mWordViewModel.getWord(mWordId).observe(this, word -> {
            this.mWord = word;
            mVideoView.setVideoPath(getActivity().getExternalFilesDir("") + "/" + mWord.getVideo_path());
        });
    }

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

    private void initializePlayer() {
//        Uri videoUri = getMedia(VIDEO_SAMPLE);
//        mVideoView.setVideoURI(videoUri);

        mVideoView.start();
    }

    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

}