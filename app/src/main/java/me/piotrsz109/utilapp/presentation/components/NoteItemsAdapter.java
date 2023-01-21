package me.piotrsz109.utilapp.presentation.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.piotrsz109.utilapp.R;
import me.piotrsz109.utilapp.database.DatabaseHelper;
import me.piotrsz109.utilapp.notes.Category;
import me.piotrsz109.utilapp.notes.Note;
import me.piotrsz109.utilapp.notes.NoteItem;
import me.piotrsz109.utilapp.presentation.AddEditNoteActivity;
import me.piotrsz109.utilapp.presentation.utils.SimpleCallback;

public class NoteItemsAdapter extends RecyclerView.Adapter<NoteItemsAdapter.ViewHolder>{

    private List<NoteItem> notes;
    private SimpleCallback _deleteCallback;

    public NoteItemsAdapter(List<NoteItem> notes, SimpleCallback deleteCallback) {
        this.notes = notes;
        _deleteCallback = deleteCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View view = inflater.inflate(R.layout.note_item, parent, false);

        // Return a new holder instance
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteItem item = notes.get(position);
        holder.TitleTextView.setText(item.Title);
        holder.ContextTextView.setText(item.Content);
        holder.CategoryTextView.setText(item.CategoryName);
        holder.CategoryTextView.setTextColor(item.Color);

        holder.RemoveButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setMessage(holder.itemView.getContext().getString(R.string.areYouSure))
                    .setPositiveButton(R.string.yes, (dialog, id) -> {
                        new Thread(() -> {
                            DatabaseHelper.getNoteDao(holder.itemView.getContext()).deleteById(item.Id);
                            if(_deleteCallback != null) _deleteCallback.call();
                        }).start();
                        dialog.dismiss();
                    })
                    .setNegativeButton(R.string.no, (dialog, id) -> dialog.dismiss());
            // Create the AlertDialog object and return it
            builder.create().show();
        });

        holder.EditButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AddEditNoteActivity.class);
            intent.putExtra("id", item.Id);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView TitleTextView;
        public TextView ContextTextView;
        public TextView CategoryTextView;
        public Button EditButton;
        public Button RemoveButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TitleTextView = itemView.findViewById(R.id.noteItemTitle);
            ContextTextView = itemView.findViewById(R.id.noteItemContent);
            CategoryTextView = itemView.findViewById(R.id.noteItemCategory);
            EditButton = itemView.findViewById(R.id.btnEdit);
            RemoveButton = itemView.findViewById(R.id.btnDelete);
        }
    }
}
