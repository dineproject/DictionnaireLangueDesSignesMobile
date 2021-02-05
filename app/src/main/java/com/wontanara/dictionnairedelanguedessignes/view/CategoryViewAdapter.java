package com.wontanara.dictionnairedelanguedessignes.view;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.wontanara.dictionnairedelanguedessignes.model.Category;

public class CategoryViewAdapter extends ListAdapter<Category, CategoryViewHolder> {

    public CategoryViewAdapter(@NonNull DiffUtil.ItemCallback<Category> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CategoryViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category current = getItem(position);
        holder.bind(current.getName());
    }

    public static class CategoryDiff extends DiffUtil.ItemCallback<Category> {

        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getId() == newItem.getId()
                    && oldItem.getName().equals(newItem.getName())
                    && oldItem.getUpdated_at().equals(newItem.getUpdated_at());
        }
    }

    public Category getCategory(int position) {
        return this.getItem(position);
    }
}
