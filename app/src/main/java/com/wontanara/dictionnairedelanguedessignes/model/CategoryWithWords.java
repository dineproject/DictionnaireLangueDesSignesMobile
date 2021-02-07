package com.wontanara.dictionnairedelanguedessignes.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithWords {
    @Embedded public Category category;
    @Relation(
            parentColumn = "id",
            entityColumn = "category_id"
    )
    public List<Word> words;
}
