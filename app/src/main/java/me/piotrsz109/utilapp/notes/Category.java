package me.piotrsz109.utilapp.notes;

import android.graphics.Color;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.net.IDN;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    public int Id;
    public String Name;
    public int Color;

    public Category() {}

    public Category(int id, String name, int color) {
        Id = id;
        Name = name;
        Color = color;
    }

    @Override
    public String toString() {
        return Name;
    }
}
