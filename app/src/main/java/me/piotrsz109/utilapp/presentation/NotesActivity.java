package me.piotrsz109.utilapp.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import me.piotrsz109.utilapp.R;
import me.piotrsz109.utilapp.database.DatabaseHelper;
import me.piotrsz109.utilapp.database.daos.NoteDAO;
import me.piotrsz109.utilapp.notes.Category;
import me.piotrsz109.utilapp.notes.Note;
import me.piotrsz109.utilapp.notes.NoteItem;
import me.piotrsz109.utilapp.presentation.components.HourlyWeatherItemsAdapter;
import me.piotrsz109.utilapp.presentation.components.NoteItemsAdapter;
import me.piotrsz109.utilapp.weather.WeatherApi;

public class NotesActivity extends AppCompatActivity {

    private RecyclerView notes;
    private FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notes = findViewById(R.id.notesView);
        btnAdd = findViewById(R.id.addNote);

        btnAdd.setOnClickListener(view -> {
            startActivity(new Intent(this, AddEditNoteActivity.class));
        });

        fetchNotes();
    }

    public void fetchNotes() {
        Context context = this;
        new Thread(() -> {
            NoteDAO dao = DatabaseHelper.getNoteDao(context);
            List<NoteItem> items = dao.getAll();
            if(items == null) items = new ArrayList<NoteItem>();
            setupNotes(items);
        }).start();
    }

    private void setupNotes(List<NoteItem> items) {
        Context context = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NoteItemsAdapter adapter = new NoteItemsAdapter(items, () -> {
                    fetchNotes();
                });
                notes.setAdapter(adapter);
                notes.setLayoutManager(new LinearLayoutManager(context));
            }
        });
    }
}