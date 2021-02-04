package com.wontanara.dictionnairedelanguedessignes.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.DownloadableCategory;

import java.util.List;

public class DownloadableCategoryViewAdapter extends RecyclerView.Adapter<DownloadableCategoryViewAdapter.ViewHolder> {

    private final List<DownloadableCategory> mValues;

    public DownloadableCategoryViewAdapter(List<DownloadableCategory> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_downloadable_category_item, parent, false);
        return new DownloadableCategoryViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Update RecyclerView with data from the list
        holder.mItem = mValues.get(position);
        holder.mCategoryName.setText(mValues.get(position).name);
        holder.mCategoryWordCount.setText(holder.mView.getContext().getResources().getQuantityString(R.plurals.word_count, mValues.get(position).word_count, mValues.get(position).word_count));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    //    Visually represents each elements
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mCategoryName;
        public final TextView mCategoryWordCount;
        public DownloadableCategory mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCategoryName = (TextView) view.findViewById(R.id.downloadable_category_name);
            mCategoryWordCount = (TextView) view.findViewById(R.id.downloadable_category_word_count);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCategoryName.getText() + "'";
        }
    }
}
