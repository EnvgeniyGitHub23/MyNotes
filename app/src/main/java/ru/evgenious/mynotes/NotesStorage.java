package ru.evgenious.mynotes;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NotesStorage {

    private static final String PREFS_NAME = "notes_prefs";
    private static final String KEY_NOTES = "notes_list";

    private final SharedPreferences prefs;
    private final Gson gson = new Gson();

    public NotesStorage(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // сохранение
    public void saveNotes(List<Note> notes) {
        String json = gson.toJson(notes);
        prefs.edit().putString(KEY_NOTES, json).apply();
    }

    // загрузка
    public List<Note> loadNotes() {
        String json = prefs.getString(KEY_NOTES, null);
        if (json == null) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<List<Note>>() {}.getType();
        return gson.fromJson(json, type);
    }

    // ===== CLEAR (опционально) =====
    public void clear() {
        prefs.edit().clear().apply();
    }
}
