package me.piotrsz109.utilapp.database;

import android.content.Context;

import androidx.room.Room;

import me.piotrsz109.utilapp.database.daos.NoteDAO;

public final class DatabaseHelper {
    private static AppDatabase appDatabase;
    private static String databaseName = "AppDB";

    public static NoteDAO getNoteDao(Context context) {
        if(appDatabase == null) {
            appDatabase = Room.databaseBuilder(context,
                    AppDatabase.class, databaseName).build();
        }

        return appDatabase.noteDao();
    }
}
