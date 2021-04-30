package com.wontanara.dictionnairedelanguedessignes.utils;

import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.text.StringEscapeUtils;

public abstract class Validation {

    public final int MAX_SIZE_IMAGE_KB = 200;
    public final int MAX_SIZE_VIDEO_KB = 500;

    public boolean isEmpty(TextInputLayout input) {
        return valInput(input).isEmpty();
    }

    public String valInput(TextInputLayout input) {
        return String.valueOf(input.getEditText().getText());
    }

    public void WordValidation (TextInputLayout wordInput) {
        if (isEmpty(wordInput)) {
            wordInput.setError("Le mot est requis");
        } else {
            wordInput.setError(null);
        }
    }

    public boolean ImageSizeValidation (int size) {
        return size < MAX_SIZE_IMAGE_KB;
    }

    public boolean VideoSizeValidation (int size) {
        return size < MAX_SIZE_VIDEO_KB;
    }
}
