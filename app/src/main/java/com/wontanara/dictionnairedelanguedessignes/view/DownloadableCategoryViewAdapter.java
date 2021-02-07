package com.wontanara.dictionnairedelanguedessignes.view;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.wontanara.dictionnairedelanguedessignes.model.DownloadableCategory;

public class DownloadableCategoryViewAdapter extends ListAdapter<DownloadableCategory, DownloadableCategoryViewHolder> {

    public DownloadableCategoryViewAdapter(@NonNull DiffUtil.ItemCallback<DownloadableCategory> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public DownloadableCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return DownloadableCategoryViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadableCategoryViewHolder holder, int position) {
        DownloadableCategory current = getItem(position);
        holder.bind(current);
    }

    public static class DownloadableCategoryDiff extends DiffUtil.ItemCallback<DownloadableCategory> {

        @Override
        public boolean areItemsTheSame(@NonNull DownloadableCategory oldItem, @NonNull DownloadableCategory newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DownloadableCategory oldItem, @NonNull DownloadableCategory newItem) {
            return oldItem.getId() == newItem.getId()
                    && oldItem.getName().equals(newItem.getName())
                    && oldItem.getUpdated_at().equals(newItem.getUpdated_at())
                    && oldItem.getWord_count() == newItem.getWord_count();
        }
    }

    public DownloadableCategory getDownloadableCategory(int position) {
        return this.getItem(position);
    }
}
