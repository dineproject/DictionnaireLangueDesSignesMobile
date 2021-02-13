package com.wontanara.dictionnairedelanguedessignes.controller.activities;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.fragments.WordFragment;
import com.wontanara.dictionnairedelanguedessignes.controller.fragments.VideoPlayerFragment;

import static androidx.core.view.WindowInsetsCompat.Type.navigationBars;
import static androidx.core.view.WindowInsetsCompat.Type.systemBars;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoPlayerActivity extends BaseActivity {

    private WindowInsetsControllerCompat controller;
    private int mWordId;

    private TextView mTextView;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

//            TODO: ICCCCCCCCCCCCIII !
            controller.hide(systemBars());

        }
    };
    private final Runnable mShowPart2Runnable = () -> {
        // Delayed display of UI elements
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = this::hide;

    @Override
    protected int getLayout() {
        return R.layout.activity_video_player;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void configureDesign() {
//
        this.configureTitle();

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(view -> toggle());

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void findElements() {
        mVisible = true;
//        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        mTextView = findViewById(R.id.title_fullscreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            this.mWordId = b.getInt("id-word");
        }
        this.configureAndShowListCategoriesFragment();
    }

    //    ------ CONFIGURATION ------

    //    ---- Fragment ----
    private void configureAndShowListCategoriesFragment(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.list_categories_frame_layout);

        if (fragment == null) {
            VideoPlayerFragment mVideoPlayerFragment = new VideoPlayerFragment();
            Bundle args = new Bundle();
            args.putInt("id-word", mWordId);
            mVideoPlayerFragment.setArguments(args);
            this.showFragment(mVideoPlayerFragment, R.id.fullscreen_content);
        }
    }

    private void configureTitle() {
        String mot = getIntent().getStringExtra(WordFragment.EXTRA_MOT);
        mTextView.setText(mot);
    }

    //    ---- Fullscreen ----
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide();
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
//        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);

//        mTextView.setTextColor(getResources().getColor(R.color.black));
    }

    private void show() {
        // Show the system bar
//        TODO: ICCCCCCCI
        controller.show(navigationBars());

//        mTextView.setTextColor(getResources().getColor(R.color.white));

        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide() {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 100);
    }
}