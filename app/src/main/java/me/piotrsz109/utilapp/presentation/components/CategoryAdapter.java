package me.piotrsz109.utilapp.presentation.components;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import me.piotrsz109.utilapp.R;
import me.piotrsz109.utilapp.notes.Category;

public class CategoryAdapter implements SpinnerAdapter {

    private List<Category> categories;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        Context context = view.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View result = inflater.inflate(R.layout.category_item, viewGroup, false);
        ((TextView) result.findViewById(R.id.categoryName)).setText(categories.get(i).Name);
        return result;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return categories.get(i).Id;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return categories != null && categories.size() > 0;
    }
}
