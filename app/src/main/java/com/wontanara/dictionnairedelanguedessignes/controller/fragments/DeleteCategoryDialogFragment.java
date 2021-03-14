package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.DialogFragment;

import com.wontanara.dictionnairedelanguedessignes.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DeleteCategoryDialogFragment extends DialogFragment {

    DeleteCategoryDialogListener listener;
    ActionMode mode;

    public DeleteCategoryDialogFragment(ActionMode mode) {
        super();
        this.mode = mode;
    }

    public interface DeleteCategoryDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, ActionMode mode);
        void onDialogNegativeClick(DialogFragment dialog, ActionMode mode);
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.delete_categories))
                .setPositiveButton(R.string.delete, (dialog, id) -> listener.onDialogPositiveClick(DeleteCategoryDialogFragment.this, mode))
                .setNegativeButton(R.string.cancel, (dialog, id) -> listener.onDialogNegativeClick(DeleteCategoryDialogFragment.this, mode));
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (DeleteCategoryDialogListener) Objects.requireNonNull(getTargetFragment());
        } catch (ClassCastException e) {
            throw new ClassCastException(Objects.requireNonNull(getTargetFragment()).toString() + " must implement DeleteCategoryListener");
        }
    }
}
