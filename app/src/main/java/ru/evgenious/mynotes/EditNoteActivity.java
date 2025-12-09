package ru.evgenious.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE = "extra_note";
    public static final String EXTRA_POSITION = "extra_position";

    private EditText etTitle, etContent, etDate;
    private CheckBox cbImportant;
    private int notePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        etDate = findViewById(R.id.etDate);
        cbImportant = findViewById(R.id.cbImportant);
        Button btnSave = findViewById(R.id.btnSave);

        // Если редактирование
        if (getIntent().hasExtra(EXTRA_NOTE)) {
            Note note = (Note) getIntent().getSerializableExtra(EXTRA_NOTE);
            notePosition = getIntent().getIntExtra(EXTRA_POSITION, -1);

            etTitle.setText(note.getTitle());
            etContent.setText(note.getContent());
            etDate.setText(note.getDate());
            cbImportant.setChecked(note.getIsImportant());
        }

        btnSave.setOnClickListener(v -> {
            Note resultNote = new Note(
                    etTitle.getText().toString(),
                    etContent.getText().toString(),
                    etDate.getText().toString(),
                    cbImportant.isChecked()
            );

            Intent result = new Intent();
            result.putExtra(EXTRA_NOTE, resultNote);
            result.putExtra(EXTRA_POSITION, notePosition);
            setResult(RESULT_OK, result);
            finish();
        });
    }
}
