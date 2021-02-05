package com.wontanara.dictionnairedelanguedessignes.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.wontanara.dictionnairedelanguedessignes.utils.CalendarConverter;

import java.util.GregorianCalendar;

@Entity(tableName = "category_table")
public class Category {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @NonNull
    @TypeConverters({CalendarConverter.class})
    @ColumnInfo(name = "updated_at")
    private GregorianCalendar mUpdated_at;

    public Category(int mId, @NonNull String mName, @NonNull GregorianCalendar mUpdated_at) {
        this.mId = mId;
        this.mName = mName;
        this.mUpdated_at = mUpdated_at;
    }

    public int getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public GregorianCalendar getUpdated_at() {
        return mUpdated_at;
    }
}
