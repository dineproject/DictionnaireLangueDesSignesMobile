package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.DownloadableCategory;

import org.jetbrains.annotations.NotNull;

public class DownloadCategoryDialogFragment extends DialogFragment {

    private final DownloadableCategory mCategory;

    public DownloadCategoryDialogFragment(DownloadableCategory category) {
        super();
        this.mCategory = category;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.download_category, mCategory.name))
                .setMessage(getString(R.string.download_category_approcimated_size, mCategory.word_count * 0.7))
                .setPositiveButton(R.string.download, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), "Catégorie doit être téléchargée", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
