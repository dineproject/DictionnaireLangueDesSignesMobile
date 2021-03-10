package com.wontanara.dictionnairedelanguedessignes.model;

import java.util.GregorianCalendar;

public class DownloadableCategory {

    public int id;
    public String name;
    public GregorianCalendar updated_at;
    public int word_count;
    public String status = "";

    public DownloadableCategory(int id, String name, GregorianCalendar updated_at, int word_count) {
        this.id = id;
        this.name = name;
        this.updated_at = updated_at;
        this.word_count = word_count;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public GregorianCalendar getUpdated_at() { return updated_at; }

    public void setUpdated_at(GregorianCalendar updated_at) { this.updated_at = updated_at; }

    public int getWord_count() { return word_count; }

    public void setWord_count(int word_count) { this.word_count = word_count; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
