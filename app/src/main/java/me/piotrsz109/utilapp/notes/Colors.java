package me.piotrsz109.utilapp.notes;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.piotrsz109.utilapp.R;

public class Colors {
    public String Name;
    public int Code;

    public Colors(String name, int code) {
        Name = name;
        Code = code;
    }

    public static List<Colors> getColors(Context context) {
        ArrayList<Colors> result = new ArrayList<Colors>(Arrays.asList(new Colors(context.getString(R.string.red), Color.RED),
                new Colors(context.getString(R.string.blue), Color.BLUE),
                new Colors(context.getString(R.string.yellow), Color.YELLOW),
                new Colors(context.getString(R.string.green), Color.GREEN)));

        return result;
    }

    @Override
    public String toString() {
        return Name;
    }
}
