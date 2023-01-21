package me.piotrsz109.utilapp.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Insert;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import me.piotrsz109.utilapp.R;
import me.piotrsz109.utilapp.database.DatabaseHelper;
import me.piotrsz109.utilapp.notes.Category;
import me.piotrsz109.utilapp.notes.Colors;

public class AddCategoryActivity extends AppCompatActivity {

    private TextInputLayout name;
    private Spinner color;
    private Button btnSave;
    private Button btnBack;

    private List<Colors> colors;
    private Bundle inputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        inputData = getIntent().getExtras();

        name = findViewById(R.id.nameTextField);
        color = findViewById(R.id.color);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        colors = Colors.getColors(this);
        Colors selectColor = new Colors(getString(R.string.selectColor), Color.GRAY);
        colors.add(0, selectColor);

        color.setAdapter(new ArrayAdapter<Colors>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, colors) {
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
                    textView.setTextColor(colors.get(position).Code);
                }
                return view;
            }
        });

        btnSave.setOnClickListener(v -> {
            save();
        });

        btnBack.setOnClickListener(v -> {
            goBack();
        });
    }

    private void save() {
        if (name.getEditText().getText().length() == 0 || color.getSelectedItemPosition() == 0) {
            Toast.makeText(this, getString(R.string.addCategoryValidationMessage), Toast.LENGTH_SHORT).show();
            return;
        }
        Context context = this;
        new Thread(() -> {
            DatabaseHelper.getNoteDao(context).insertCategory(new Category(0, name.getEditText().getText().toString(),
                    colors.get(color.getSelectedItemPosition()).Code));

            runOnUiThread(() -> {
                goBack();
            });
        }).start();
    }

    private void goBack() {
        Intent intent = new Intent(this, AddEditNoteActivity.class);
        if (inputData != null) {
            intent.putExtras(inputData);
        }

        startActivity(intent);
    }
}