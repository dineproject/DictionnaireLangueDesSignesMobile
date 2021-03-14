package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.DownloadableCategory;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class UpdateCategoryDialogFragment extends DialogFragment {

    private final DownloadableCategory mCategory;
    UpdateCategoryDialogListener listener;

    public UpdateCategoryDialogFragment(DownloadableCategory category) {
        super();
        this.mCategory = category;
    }

    public interface UpdateCategoryDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, DownloadableCategory downloadableCategory);
        void onDialogNegativeClick(DialogFragment dialog, DownloadableCategory downloadableCategory);
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.update_category, mCategory.name))
                .setMessage(getString(R.string.download_category_approximated_size, mCategory.word_count * 0.7))
                .setPositiveButton(R.string.update, (dialog, id) -> listener.onDialogPositiveClick(UpdateCategoryDialogFragment.this, mCategory))
                .setNegativeButton(R.string.cancel, (dialog, id) -> listener.onDialogNegativeClick(UpdateCategoryDialogFragment.this, mCategory));
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateCategoryDialogListener) Objects.requireNonNull(getTargetFragment());
        } catch (ClassCastException e) {
            throw new ClassCastException(Objects.requireNonNull(getTargetFragment()).toString() + " must implement DownloadCategoryListener");
        }
    }
}