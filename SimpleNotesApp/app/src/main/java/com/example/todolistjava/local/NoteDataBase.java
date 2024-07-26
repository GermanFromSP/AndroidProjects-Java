package com.example.todolistjava.local;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todolistjava.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {
    private static final String DB_NAME = "notes.db";
    private static NoteDataBase instance = null;

    public static NoteDataBase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            application,
                            NoteDataBase.class,
                            DB_NAME)
                    .build();
        }
        return instance;
    }

    public abstract NotesDao notesDao();
}
