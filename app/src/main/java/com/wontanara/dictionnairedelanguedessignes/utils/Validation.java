package com.wontanara.dictionnairedelanguedessignes.utils;

import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;


public abstract class Validation {

    public final int MAX_SIZE_IMAGE_KB = 200;
    public final int MAX_SIZE_VIDEO_KB = 500;

    public boolean isEmpty(TextInputLayout input) {
        return valInput(input).isEmpty();
    }

    public String valInput(TextInputLayout input) {
        return String.valueOf(input.getEditText().getText());
    }

    public boolean WordValidation (TextInputLayout wordInput) {
        if (isEmpty(wordInput)) {
            wordInput.setError("Le mot est requis");
            return false;
        } else {
            wordInput.setError(null);
            return true;
        }
    }

    public boolean ImageSizeValidation (int size) {
        return size < MAX_SIZE_IMAGE_KB;
    }

    public boolean VideoSizeValidation (int size) {
        return size < MAX_SIZE_VIDEO_KB;
    }
}
