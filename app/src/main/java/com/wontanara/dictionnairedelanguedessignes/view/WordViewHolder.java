package com.wontanara.dictionnairedelanguedessignes.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wontanara.dictionnairedelanguedessignes.R;

public class WordViewHolder extends RecyclerView.ViewHolder {
    private final TextView wordTextView;

    private WordViewHolder(View itemView) {
        super(itemView);
        wordTextView = itemView.findViewById(R.id.content_mot);
    }

    public void bind(String text) { wordTextView.setText(text); }

    static WordViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_categorie_in_list, parent, false);
        return new WordViewHolder(view);

    }
}
