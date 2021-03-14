package com.wontanara.dictionnairedelanguedessignes.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.databinding.FragmentCategoriesInListBinding;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    private final TextView categoryTextView;
    public FragmentCategoriesInListBinding bi;

    public CategoryViewHolder(@NonNull FragmentCategoriesInListBinding itemView) {
        super(itemView.getRoot());
        bi = itemView;
        categoryTextView = itemView.content;
    }

    public void bind(String text) {
        categoryTextView.setText(text);
    }

//    static CategoryViewHolder create(ViewGroup parent) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fragment_categories_in_list, parent, false);
//        return new CategoryViewHolder(DataBindingUtil.setContentView((Activity) parent.getContext(), R.layout.fragment_categories_in_list));
//    }
}
