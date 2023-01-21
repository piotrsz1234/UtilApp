package me.piotrsz109.utilapp.notes;

import android.text.Editable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Category.class,
        parentColumns = "Id",
        childColumns = "CategoryId",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE)})
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int Id;

    public String Title;
    public String Content;
    public boolean RequiresAuthentication;
    public int CategoryId;

    public Note() {}

    public Note(int id, String title, String content, int categoryId) {
        Id = id;
        Title = title;
        Content = content;
        CategoryId = categoryId;
    }

}
