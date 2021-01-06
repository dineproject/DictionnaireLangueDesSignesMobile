package com.wontanara.dictionnairedelanguedessignes.controller.activities;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsController;
import android.widget.TextView;
import android.widget.VideoView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.fragments.MotFragment;
import com.wontanara.dictionnairedelanguedessignes.controller.fragments.VideoPlayerFragment;

import static androidx.core.view.WindowInsetsCompat.Type.captionBar;
import static androidx.core.view.WindowInsetsCompat.Type.navigationBars;
import static androidx.core.view.WindowInsetsCompat.Type.systemBars;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoPlayerActivity extends BaseActivity {

    private VideoPlayerFragment mVideoPlayerFragment;
    private WindowInsetsControllerCompat controler;

    private TextView mTextView;


    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

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
            controler.hide(systemBars());

            mTextView.setTextColor(getResources().getColor(R.color.black));

        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
//            mControlsView.setVisibility(View.VISIBLE);
            mTextView.setTextColor(getResources().getColor(R.color.white));
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        delayedHide(AUTO_HIDE_DELAY_MILLIS);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return false;
        }
    };

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
        this.configureAndShowListCategoriesFragment();
        this.configureTitle();

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

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
        controler = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        mTextView = findViewById(R.id.title_fullscreen);
    }



//    ------ CONFIGURATION ------

    //    ---- Fragment ----
    private void configureAndShowListCategoriesFragment(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.list_categories_frame_layout);

        if (fragment == null) {
            mVideoPlayerFragment = new VideoPlayerFragment();
            this.showFragment(mVideoPlayerFragment, R.id.fullscreen_content);
        }
    }

    private void configureTitle() {
        String mot = getIntent().getStringExtra(MotFragment.EXTRA_MOT);
        mTextView.setText(mot);
    }

    //    ---- Fullscreen ----
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
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
        controler.show(navigationBars());

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
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}