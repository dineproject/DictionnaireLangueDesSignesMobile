package com.wontanara.dictionnairedelanguedessignes.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.DownloadableCategory;

public class DownloadableCategoryViewHolder extends RecyclerView.ViewHolder {
    private final View view;
    private final TextView downloadableCategoryTextView;
    public final TextView downloadableCategoryWordCount;

    private DownloadableCategoryViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        downloadableCategoryTextView = itemView.findViewById(R.id.downloadable_category_name);
        downloadableCategoryWordCount = itemView.findViewById(R.id.downloadable_category_word_count);
    }

    public void bind(DownloadableCategory category) {
        downloadableCategoryTextView.setText(category.getName());
        downloadableCategoryWordCount.setText(view.getContext().getResources().getQuantityString(R.plurals.word_count, category.getWord_count(), category.getWord_count()));
    }

    static DownloadableCategoryViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_downloadable_category_item, parent, false);
        return new DownloadableCategoryViewHolder(view);
    }
}
