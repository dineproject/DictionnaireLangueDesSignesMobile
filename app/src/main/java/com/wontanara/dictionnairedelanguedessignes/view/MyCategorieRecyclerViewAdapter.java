package com.wontanara.dictionnairedelanguedessignes.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.Categorie;
import com.wontanara.dictionnairedelanguedessignes.model.Mot;

import java.util.ArrayList;
import java.util.List;

public class MyCategorieRecyclerViewAdapter extends RecyclerView.Adapter<MyCategorieRecyclerViewAdapter.ViewHolder> {

    private final List<Mot> mValues;

    public MyCategorieRecyclerViewAdapter(List<Mot> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_categorie_in_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyCategorieRecyclerViewAdapter.ViewHolder holder, int position) {
//        Met à jour la recyclerview avec les données de la liste
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).nom);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public Mot getMot(int position) {
        return this.mValues.get(position);
    }

    //    Permet de représenter visuellement chaque éléemtns de la recyclerview
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //        public final TextView mIdView;
        public final TextView mContentView;
        public Mot mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content_mot);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}
