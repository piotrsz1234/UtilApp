package me.piotrsz109.utilapp.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import me.piotrsz109.utilapp.notes.Category;
import me.piotrsz109.utilapp.notes.Note;
import me.piotrsz109.utilapp.notes.NoteItem;

@Dao
public interface NoteDAO {
    @Query("SELECT note.Id, category.Id AS CategoryId, note.Title, note.Content, category.Name AS CategoryName, category.Color"
            + " FROM note "+
            "INNER JOIN category ON category.Id = note.CategoryId")
    List<NoteItem> getAll();

    @Query("SELECT * FROM note WHERE id = :id")
    Note getById(int id);

    @Insert
    void insertAll(Note... notes);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Insert
    void insertCategory(Category... categories);

    @Query("DELETE FROM note WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM category")
    List<Category> getAllCategories();
}
