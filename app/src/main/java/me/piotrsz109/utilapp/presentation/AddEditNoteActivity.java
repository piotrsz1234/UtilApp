package me.piotrsz109.utilapp.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import me.piotrsz109.utilapp.R;
import me.piotrsz109.utilapp.database.DatabaseHelper;
import me.piotrsz109.utilapp.notes.Category;
import me.piotrsz109.utilapp.notes.Note;
import me.piotrsz109.utilapp.notes.NoteItem;
import me.piotrsz109.utilapp.presentation.components.CategoryAdapter;
import me.piotrsz109.utilapp.presentation.components.NoteItemsAdapter;

public class AddEditNoteActivity extends AppCompatActivity {

    private int currentItemId;
    private Spinner _category;
    private TextInputLayout title;
    private TextInputLayout content;
    private Button btnSave;
    private Button btnBack;
    private Button btnAddCategory;
    private List<Category> _categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);
        Context context = this;

        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            currentItemId = extra.getInt("id");
        }

        _category = findViewById(R.id.category);
        title = findViewById(R.id.titleTextField);
        content = findViewById(R.id.contentTextField);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        _category.setAdapter(new CategoryAdapter(new ArrayList<Category>()));

        new Thread(() -> {
            List<Category> items = DatabaseHelper.getNoteDao(context).getAllCategories();
            _categories = items;
            items.add(0, new Category(-1, "Select category", 0));
            setupCategories(items);
        }).start();

        btnSave.setOnClickListener(v -> {
            save();
        });

        btnBack.setOnClickListener(v -> {
            backToList();
        });
    }

    private void setupCategories(List<Category> items) {
        Context context = this;
        runOnUiThread(() -> {
            _category.setAdapter(new ArrayAdapter<Category>(context,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items) {
                @Override
                public boolean isEnabled(int position) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return position != 0;
                }

                @Override
                public View getDropDownView(
                        int position, View convertView,
                        @NonNull ViewGroup parent) {

                    // Get the item view
                    View view = super.getDropDownView(
                            position, convertView, parent);
                    TextView textView = (TextView) view;
                    if (position == 0) {
                        // Set the hint text color gray
                        textView.setTextColor(Color.GRAY);
                    } else {
                        textView.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            });

            if (currentItemId > 0) {
                new Thread(() -> {
                    Note item = DatabaseHelper.getNoteDao(context).getById(currentItemId);
                    setAsEdit(item);
                }).start();
            }
        });
    }

    private void setAsEdit(Note item) {
        runOnUiThread(() -> {
            title.getEditText().setText(item.Title);
            content.getEditText().setText(item.Content);

            _category.setId(_categories.indexOf(getById(item.Id)) + 1);
        });
    }

    private Category getById(int id) {
        for (int i = 0; i < _categories.size(); i++)
            if (_categories.get(i).Id == id)
                return _categories.get(i);

        return null;
    }

    private void save() {
        if (title.getEditText().getText().length() == 0 || title.getEditText().getText().length() == 0 || _category.getId() == 0) {

            return;
        }

        Context context = this;
        boolean toUpdate = currentItemId > 0;
        int test = _category.getSelectedItemPosition();
        new Thread(() -> {
            Note note = new Note(currentItemId, title.getEditText().getText().toString(),
                    title.getEditText().getText().toString(),
                    _categories.get(_category.getSelectedItemPosition()).Id);
            if (toUpdate)
                DatabaseHelper.getNoteDao(context).update(note);
            else
                DatabaseHelper.getNoteDao(context).insertAll(note);

            runOnUiThread(() -> {
                backToList();
            });
        }).start();
    }

    private void backToList() {
        startActivity(new Intent(this, NotesActivity.class));
    }
}