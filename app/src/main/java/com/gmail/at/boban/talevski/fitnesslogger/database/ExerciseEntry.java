package com.gmail.at.boban.talevski.fitnesslogger.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "exercise")
public class ExerciseEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private int category;
    @ColumnInfo(name = "user_id")
    private String userId;
    @ColumnInfo(name = "image_url")
    private String imageUrl;

    public ExerciseEntry(int id, String name, String description, int category, String userId, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.userId = userId;
        this.imageUrl = imageUrl;
    }

    @Ignore
    public ExerciseEntry(String name, String description, int category, String userId, String imageUrl) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.userId = userId;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
