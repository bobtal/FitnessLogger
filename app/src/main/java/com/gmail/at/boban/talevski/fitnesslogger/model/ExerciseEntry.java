package com.gmail.at.boban.talevski.fitnesslogger.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.RESTRICT;

@Entity(tableName = "exercise")
public class ExerciseEntry implements Parcelable {

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

    protected ExerciseEntry(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        category = in.readInt();
        userId = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<ExerciseEntry> CREATOR = new Creator<ExerciseEntry>() {
        @Override
        public ExerciseEntry createFromParcel(Parcel in) {
            return new ExerciseEntry(in);
        }

        @Override
        public ExerciseEntry[] newArray(int size) {
            return new ExerciseEntry[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(category);
        parcel.writeString(userId);
        parcel.writeString(imageUrl);
    }

    @Entity(
            tableName = "exercise_session_join",
            primaryKeys = {"exercise_id", "session_id"},
            foreignKeys = {
                    @ForeignKey(
                            entity = ExerciseEntry.class,
                            parentColumns = "id",
                            childColumns = "exercise_id",
                            onDelete = RESTRICT),
                    @ForeignKey(
                            entity = TrainingSessionEntry.class,
                            parentColumns = "id",
                            childColumns = "session_id",
                            onDelete = RESTRICT)
            },
            indices = {
                    @Index(value = "exercise_id"),
                    @Index(value = "session_id")
            }
    )
    public static class TrainingSessionJoin {
        @ColumnInfo(name = "exercise_id")
        @NonNull public final String exerciseId;
        @ColumnInfo(name = "session_id")
        @NonNull public final String sessionId;

        public TrainingSessionJoin(String exerciseId, String sessionId) {
            this.exerciseId = exerciseId;
            this.sessionId = sessionId;
        }
    }
}
