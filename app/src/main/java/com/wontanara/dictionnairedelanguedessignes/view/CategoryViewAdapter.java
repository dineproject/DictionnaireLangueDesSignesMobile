package com.wontanara.dictionnairedelanguedessignes.view;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.databinding.FragmentCategoriesInListBinding;
import com.wontanara.dictionnairedelanguedessignes.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewAdapter extends ListAdapter<Category, CategoryViewHolder> {

    private OnItemClick itemClick;
    private final SparseBooleanArray selectedItems;

    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public CategoryViewAdapter(@NonNull DiffUtil.ItemCallback<Category> diffCallback) {
        super(diffCallback);
        selectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentCategoriesInListBinding bi = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.fragment_categories_in_list, parent, false);
        return new CategoryViewHolder(bi);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category current = getItem(position);
        holder.bind(current.getName());
        holder.itemView.setOnClickListener(view -> {
            if (itemClick == null) return;
            itemClick.onItemClick(view, getCategory(position), position);
        });
        holder.itemView.setOnLongClickListener(view -> {
            if (itemClick == null) {
                return false;
            } else {
                itemClick.onLongPress(view, getCategory(position), position);
                return true;
            }
        });
        toggleIcon(holder.bi, position);
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

    public interface OnItemClick {
        void onItemClick(View view, Category category, int position);
        void onLongPress(View view, Category category, int position);
    }

    private void toggleIcon(FragmentCategoriesInListBinding bi, int position) {
        if (selectedItemCount() > 0) {
            if (selectedItems.get(position, false)) {
                ImageButton icon = bi.iconCategorie;
                icon.setImageResource(R.drawable.ic_baseline_check_box_24);
            } else {
                ImageButton icon = bi.iconCategorie;
                icon.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24);
            }
        } else {
            ImageButton icon = bi.iconCategorie;
            icon.setImageResource(R.drawable.baseline_info_24);
        }
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void clearSelection() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyDataSetChanged();
    }

    public int selectedItemCount() {
        return selectedItems.size();
    }
}
