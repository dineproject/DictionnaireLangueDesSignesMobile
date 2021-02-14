package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.CategoriesActivity;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.DictionnaireActivity;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.VideoPlayerActivity;
import com.wontanara.dictionnairedelanguedessignes.model.Word;
import com.wontanara.dictionnairedelanguedessignes.model.WordViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;


public class WordFragment extends BaseFragment implements View.OnClickListener{

    private static final String ARG_ID_MOT = "id-mot";
    public final static String EXTRA_MOT = "nom-mot";
    public final static String ARG_LISTE = "liste-entiere";
    private int mIdWord;
    private Toolbar mToolbar;
    private Word mWord;
    private boolean mListeEntiere;

    private TextView mTextViewTitle;
    private TextView mTextViewDefinition;
    private ImageView mImageView;

    private WordViewModel mWordViewModel;


    public WordFragment() {
        // Required empty public constructor
    }

//    ------ BASE METHODS ------

    @Override
    protected BaseFragment newInstance() {
        return new WordFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_mot;
    }

    @Override
    protected void configureDesign(View view) {
        mWordViewModel.getWord(mIdWord).observe(this, word -> {
            this.mWord = word;
            this.mToolbar.setTitle(this.mWord.getName());
            view.findViewById(R.id.imageButton_play_video).setOnClickListener(this);
            this.configureTextView();
            Bitmap bmp = BitmapFactory.decodeFile(Objects.requireNonNull(getActivity()).getExternalFilesDir("") + File.separator + mWord.getCategory_id() + File.separator + mWord.getImage_path());
            this.mImageView.setImageBitmap(bmp);
        });
    }

    @Override
    protected void findElements(View view) {
        this.mTextViewTitle = view.findViewById(R.id.titre_mot);
        this.mTextViewDefinition = view.findViewById(R.id.definition_mot);
        this.mImageView = view.findViewById(R.id.imageView);

        if(mListeEntiere){
            this.mToolbar = ((DictionnaireActivity) Objects.requireNonNull(getActivity())).getToolbar();
        } else {
            this.mToolbar = ((CategoriesActivity) Objects.requireNonNull(getActivity())).getToolbar();
        }

    }

//    ------ OVERRIDE METHODS ------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mIdWord = getArguments().getInt(ARG_ID_MOT);
            this.mListeEntiere = getArguments().getBoolean(ARG_LISTE);
        }
        mWordViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity()), ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(this.getActivity()).getApplication())).get(WordViewModel.class);
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(ARG_ID_MOT, this.mIdWord);
        bundle.putBoolean(ARG_LISTE, this.mListeEntiere);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getActivity(), VideoPlayerActivity.class);
        i.putExtra("id-word", mWord.getId());
        startActivityForResult(i, 0);

    }


//    // création du callback pour pointer vers l'activité
//    private void createCallbackToParentActivity(){
//        try {
//            //Parent activity will automatically subscribe to callback
//            mCallback = (WordFragment.OnButtonClickedListener) getActivity();
//        } catch (ClassCastException e) {
//            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
//        }
//    }



//    ------ CONFIGURATION ------

    public void configureTextView() {
        mTextViewTitle.setText(mWord.getName());
        mTextViewDefinition.setText(mWord.getDescription());
    }


}