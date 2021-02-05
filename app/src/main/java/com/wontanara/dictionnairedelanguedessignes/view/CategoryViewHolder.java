package com.wontanara.dictionnairedelanguedessignes.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wontanara.dictionnairedelanguedessignes.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    private final TextView categoryTextView;

    private CategoryViewHolder(View itemVew) {
        super(itemVew);
        categoryTextView = itemVew.findViewById(R.id.content);
    }

    public void bind(String text) {
        categoryTextView.setText(text);
    }

    static CategoryViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_categories_in_list, parent, false);
        return new CategoryViewHolder(view);
    }
}
