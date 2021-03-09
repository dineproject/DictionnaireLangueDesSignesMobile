package com.wontanara.dictionnairedelanguedessignes.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word implements Comparable<Word> {

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

    @NonNull
    @ColumnInfo(name = "video_path")
    private String mVideo_path;

    @NonNull
    @ColumnInfo(name = "image_path")
    private String mImage_path;

    public Word(int mId, @NonNull String mName, @NonNull String mDescription, int mCategory_id, @NonNull String mVideo_path, @NonNull String mImage_path) {
        this.mId = mId;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mCategory_id = mCategory_id;
        this.mVideo_path = mVideo_path;
        this.mImage_path = mImage_path;
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

    @NonNull
    public String getVideo_path() { return mVideo_path; }

    @NonNull
    public String getImage_path() { return mImage_path; }

    @Override
    public int compareTo(@NonNull Word word) {
        return this.mName.compareTo(word.mName);
    }
}
