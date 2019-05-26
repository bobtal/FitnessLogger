package com.gmail.at.boban.talevski.fitnesslogger.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class TrainingSessionEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;

    public TrainingSessionEntry(int id, Date date) {
        this.id = id;
        this.date = date;
    }

    @Ignore
    public TrainingSessionEntry(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
