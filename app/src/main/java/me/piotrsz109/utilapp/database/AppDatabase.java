package me.piotrsz109.utilapp.database;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import me.piotrsz109.utilapp.database.daos.NoteDAO;
import me.piotrsz109.utilapp.notes.Category;
import me.piotrsz109.utilapp.notes.Note;

@Database(entities = {Note.class, Category.class}, version = 2, autoMigrations = {
        @AutoMigration(from = 1, to = 2)
})

//@Database(entities = {Note.class, Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDAO noteDao();
}
