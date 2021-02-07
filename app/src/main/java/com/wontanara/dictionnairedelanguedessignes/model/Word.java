package com.wontanara.dictionnairedelanguedessignes.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @NonNull
    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "category_id")
    private int mCategory_id;

    public Word(int mId, @NonNull String mName, @NonNull String mDescription, int mCategory_id) {
        this.mId = mId;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mCategory_id = mCategory_id;
    }

    public int getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public String getDescription() {
        return mDescription;
    }

    public int getCategory_id() {
        return mCategory_id;
    }
}
