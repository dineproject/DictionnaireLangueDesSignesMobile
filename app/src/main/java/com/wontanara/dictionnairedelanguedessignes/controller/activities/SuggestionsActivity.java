package com.wontanara.dictionnairedelanguedessignes.controller.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.ApiViewModel;
import com.wontanara.dictionnairedelanguedessignes.model.Resource;
import com.wontanara.dictionnairedelanguedessignes.utils.Validation;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Objects;

// TODO: faire un bouton (?) dans la barre pour expliquer ce que sont les suggestions ?

public class SuggestionsActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String ARG_MOT_INPUT = "word-input";
    private static final String ARG_DEFINITION_INPUT = "definition-input";
    private static final String ARG_URI_IMAGE = "image-uri";
    private static final String ARG_URI_VIDEO = "video-uri";
    private static final String ARG_LARGE_IMAGE = "large-image";
    private static final String ARG_LARGE_VIDEO = "large-video";

    private static final int PICK_IMAGE = 1;
    private static final int PICK_VIDEO = 2;

    private int sizeImage = 0;
    private int sizeVideo = 0;

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private TextInputLayout mMotInput;
    private TextInputLayout mDefinitionInput;
    private Button mImageButton;
    private Button mVideoButton;
    private Button mButton;
    private TextView mImageSizeTextView;
    private TextView mVideoSizeTextView;

    private ImageButton mDeleteImageButton;
    private ImageButton mDeleteVideoButton;
//    private TextView mImageTextView;

    private ImageView mPreviewImageView;
    private VideoView mPreviewVideoView;

    private Uri selectedImageUri;
    private Uri selectedVideoUri;

    private boolean largeImage = false;
    private boolean largeVideo = false;

    private Validation val = new Validation() {};
    private ApiViewModel mApiViewModel;



//    TODO: Faire le back dans ApiRepository, la requete post grace à Jsonarrayrequest ?

//    ------ BASE METHODS ------

    @Override
    protected int getLayout() {
        return R.layout.activity_suggestions;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void configureDesign() {
        // Elements graphiques configurés dans onCreate
        this.configureToolbar(R.string.titre_lien_suggestions);
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureWordInput();
        this.configureOnClickListener();
    }

    @Override
    protected void findElements() {
        this.mApiViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(this.getActivity()).getApplication())).get(ApiViewModel.class);

        // Enregistre les éléments dont on a besoin au démarrage
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.dictionnaire_activity_drawer_layout);
        this.mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        this.mMotInput = (TextInputLayout) findViewById(R.id.word_input_suggestions);
        this.mDefinitionInput = (TextInputLayout) findViewById(R.id.definition_input_suggestion);

        this.mImageButton = (Button) findViewById(R.id.image_button_suggestion);
        this.mVideoButton = (Button) findViewById(R.id.video_button_suggestion);

        this.mDeleteImageButton = (ImageButton) findViewById(R.id.delete_image_button);
        this.mDeleteVideoButton = (ImageButton) findViewById(R.id.delete_video_button);

//        this.mImageTextView = (TextView) findViewById(R.id.nom_image_suggestion);

        this.mPreviewImageView = (ImageView) findViewById(R.id.preview_image_suggestion);
        this.mPreviewVideoView = (VideoView) findViewById(R.id.preview_video_suggestion);

        this.mImageSizeTextView = (TextView) findViewById(R.id.max_size_image_textview_suggestion);
        this.mVideoSizeTextView = (TextView) findViewById(R.id.max_size_video_textview_suggestion);

        this.mButton = (Button) findViewById(R.id.validation_button_suggestion);

    }


//    ------ OVERRIDE METHODS ------

    @Override
    public void onPause() {
        super.onPause();
        getIntent().putExtra(ARG_MOT_INPUT, this.mMotInput.getEditText().getText().toString());
        getIntent().putExtra(ARG_DEFINITION_INPUT, this.mDefinitionInput.getEditText().getText().toString());
        if (this.selectedImageUri != null) {
            getIntent().putExtra(ARG_URI_IMAGE, this.selectedImageUri.toString());
            if (this.largeImage) {
                getIntent().putExtra(ARG_LARGE_IMAGE, this.largeImage);
            }
        }
        if (this.selectedVideoUri != null) {
            getIntent().putExtra(ARG_URI_VIDEO, this.selectedVideoUri.toString());
            if (this.largeVideo) {
                getIntent().putExtra(ARG_LARGE_VIDEO, this.largeVideo);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        String word = getIntent().getStringExtra(ARG_MOT_INPUT);
        String definition = getIntent().getStringExtra(ARG_DEFINITION_INPUT);
        String imageUri = getIntent().getStringExtra(ARG_URI_IMAGE);
        String videoUri = getIntent().getStringExtra(ARG_URI_VIDEO);

        if (word != null && !word.equals("")) {
            this.mMotInput.getEditText().setText(word);
        }

        this.mDefinitionInput.getEditText().setText(definition);

        if (null != imageUri) {
            this.selectedImageUri = Uri.parse(imageUri);
            showPreviewImage();
            if (getIntent().getBooleanExtra(ARG_LARGE_IMAGE, false)) {
                messageImageTooLarge();
            }
        }
        if (null != videoUri) {
            this.selectedVideoUri = Uri.parse(videoUri);
            showPreviewVideo();
            if (getIntent().getBooleanExtra(ARG_LARGE_VIDEO, false)) {
                messageVideoTooLarge();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                this.selectedImageUri = data.getData();

                if (null != this.selectedImageUri) {
                    String scheme = this.selectedImageUri.getScheme();
                    if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                        try {
                            InputStream fileInputStream = getApplicationContext().getContentResolver().openInputStream(this.selectedImageUri);
                            this.sizeImage = fileInputStream.available()/1000;
                            fileInputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (!val.ImageSizeValidation(this.sizeImage)) {
                        this.largeImage = true;
                        messageImageTooLarge();
                    }

                    showPreviewImage();
                }
            } else if (requestCode == PICK_VIDEO) {
                this.selectedVideoUri = data.getData();

                if (null != this.selectedVideoUri) {
                    String scheme = this.selectedVideoUri.getScheme();
                    if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                        try {
                            InputStream fileInputStream = getApplicationContext().getContentResolver().openInputStream(this.selectedVideoUri);
                            this.sizeVideo = fileInputStream.available()/1000;
                            fileInputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (!val.VideoSizeValidation(this.sizeVideo)) {
                        this.largeVideo = true;
                        messageVideoTooLarge();
                    }

                    showPreviewVideo();
                }
            }
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.image_button_suggestion:
                pickImage();
                break;
            case R.id.video_button_suggestion:
                pickVideo();
                break;
            case R.id.delete_image_button:
                deleteImage();
                break;
            case R.id.delete_video_button:
                deleteVideo();
                break;

            case R.id.validation_button_suggestion:
                if (val.WordValidation(mMotInput)) {
                    Log.e("TEST", "VALIDATION");

                    mApiViewModel.postSuggestion(val.valInput(this.mMotInput), val.valInput(this.mDefinitionInput), getPath(this.selectedImageUri), this.selectedVideoUri).observe(this, ressource -> {
//                            ressource.data
//                            ressource.status -> statut de la reponse,
//                            Resource.Status.ERROR -> voir discord
                    });
                    break;
                }
                break;
//                TODO: appel de ApiViewModel
        }
    }

    //    ---- Menu ----

    @Override
    public void onBackPressed() {
        // Handle back click to close menu
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Actions au clic dans les items du menu
        int id = item.getItemId();

        switch (id){ //TODO: R.id. etc a voir par quoi on change
            case R.id.navigation_drawer_accueil:
                this.navigationAccueil();
                break;
            case R.id.navigation_drawer_alphabetique:
                this.navigationDictionnaire();
                break;
            case R.id.navigation_drawer_categories:
                this.navigationCategories();
                break;
            case R.id.navigation_drawer_suggestions:
                break;
            case R.id.navigation_drawer_bases:
                break;
            case R.id.navigation_drawer_apropos:
                this.navigationAPropos();
                break;
            default:
                break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


//    ------ CONFIGURATION ------


    // Configure Drawer Layout
    private void configureDrawerLayout(){
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.suggestions_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(SuggestionsActivity.this, mDrawerLayout, mToolbar, R.string.description_navigation_drawer_open, R.string.description_navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureWordInput() {
        mMotInput.setHint(mMotInput.getHint() + " *");
        mMotInput.setHelperText("* Champ requis");

        mMotInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() == 0 ) {
                    mMotInput.setError("Le mot est requis");
                } else {
                    mMotInput.setError(null);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    private void configureOnClickListener(){
        this.mImageButton.setOnClickListener(this);
        this.mVideoButton.setOnClickListener(this);
        this.mButton.setOnClickListener(this);
        this.mDeleteImageButton.setOnClickListener(this);
        this.mDeleteVideoButton.setOnClickListener(this);
    }

    private void pickImage() {
        Intent i = new Intent();
        i.setType("image/*");
        String[] mimeTypes = {
                "image/jpeg",
                "image/jpg",
                "image/png",
                "image/gif"
        };
        i.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE);
    }

    private void pickVideo() {
        Intent i = new Intent();
        i.setType("video/mp4");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Video"), PICK_VIDEO);
    }

    private void deleteImage() {
        mImageButton.setText("Choisir une image...");
        mPreviewImageView.setImageURI(null);
        mPreviewImageView.setVisibility(View.GONE);
        mDeleteImageButton.setVisibility(View.GONE);
        mImageSizeTextView.setText(R.string.size_image_max);
        mImageSizeTextView.setTextColor(-1979711488);
        getIntent().removeExtra(ARG_URI_IMAGE);
        this.selectedImageUri = null;
    }

    private void deleteVideo() {
        mVideoButton.setText("Choisir une vidéo...");
        mPreviewVideoView.setVideoURI(null);
        mPreviewVideoView.setVisibility(View.GONE);
        mDeleteVideoButton.setVisibility(View.GONE);
        mVideoSizeTextView.setText(R.string.size_video_max);
        mVideoSizeTextView.setTextColor(-1979711488);
        getIntent().removeExtra(ARG_URI_VIDEO);
        this.selectedVideoUri = null;
    }

    private void showPreviewImage() {
        mImageButton.setText("Image selectionnée");
        mPreviewImageView.setImageURI(this.selectedImageUri);
        mPreviewImageView.setVisibility(View.VISIBLE);
        mDeleteImageButton.setVisibility(View.VISIBLE);
    }

    private void showPreviewVideo() {
        mVideoButton.setText("Vidéo selectionnée");
        mPreviewVideoView.setVideoURI(this.selectedVideoUri);
        mPreviewVideoView.setVisibility(View.VISIBLE);
        mPreviewVideoView.seekTo(1);
        mDeleteVideoButton.setVisibility(View.VISIBLE);
    }

    private void messageImageTooLarge() {
        this.mImageSizeTextView.setText(R.string.size_image_too_large);
        this.mImageSizeTextView.setTextColor(getColor(R.color.design_default_color_error));
    }

    private void messageVideoTooLarge() {
        this.mVideoSizeTextView.setText(R.string.size_video_too_large);
        this.mVideoSizeTextView.setTextColor(getColor(R.color.design_default_color_error));
    }


    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(),    contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}