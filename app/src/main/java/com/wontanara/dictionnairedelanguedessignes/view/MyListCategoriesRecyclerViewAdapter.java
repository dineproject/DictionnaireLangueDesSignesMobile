package com.wontanara.dictionnairedelanguedessignes.view;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.Categorie;

import java.util.List;

// Permet la liaision entre la recyclerview et la liste de données
public class MyListCategoriesRecyclerViewAdapter extends RecyclerView.Adapter<MyListCategoriesRecyclerViewAdapter.ViewHolder> {

    private final List<Categorie> mValues;

    public MyListCategoriesRecyclerViewAdapter(List<Categorie> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_categories_in_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        Met à jour la recyclerview avec les données de la liste
        holder.mItem = mValues.get(position);
        String no = Integer.toString(mValues.get(position).id);
        holder.mIdView.setText(no);
        holder.mContentView.setText(mValues.get(position).nom);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public Categorie getCategorie(int position) {
        return this.mValues.get(position);
    }

//    Permet de représenter visuellement chaque éléemtns de la recyclerview
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Categorie mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}