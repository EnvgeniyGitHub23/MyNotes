package ru.evgenious.mynotes;


import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private List<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Кнопка FAB для добавления новой заметки
        findViewById(R.id.fabAdd).setOnClickListener(v -> {
            Intent intent = new Intent(this, EditNoteActivity.class);
            editNoteLauncher.launch(intent);
        });


        initializeData();

        adapter = new NotesAdapter(notesList);
        recyclerView.setAdapter(adapter);

        // кастомный декоратор для разделителей
        recyclerView.addItemDecoration(new SimpleDividerDecoration());


    }

    // Добавляем тестовые карточки
    private void initializeData() {
        notesList = new ArrayList<>();

        notesList.add(new Note("Первая заметка",
                "и важная",
                "Сегодня", true));

        notesList.add(new Note("Покупки",
                "Купить: молоко, хлеб, мандарины",
                "06.12.2025", false));

        notesList.add(new Note("НГ",
                "Купить подарки",
                "30.12.2025", false));

        notesList.add(new Note("Учеба",
                "Сделать приложение заметки",
                "21.12.2025", true));

        notesList.add(new Note("Учеба",
                "Сделать ДЗ по тестированию ПО",
                "20.12.2025", true));

        notesList.add(new Note("Фильмы",
                "Скачать сериалы на NAS",
                "28.11.2025", false));

        notesList.add(new Note("Гитара",
                "Отнести мастеру",
                "04.12.2025", false));
    }

    // декоратор для разделителей
    private class SimpleDividerDecoration extends RecyclerView.ItemDecoration {
        private final Paint paint;

        SimpleDividerDecoration() {
            paint = new Paint();
            paint.setColor(Color.LTGRAY);
            paint.setStrokeWidth(2f);
        }

        @Override
        public void getItemOffsets(Rect outRect, android.view.View view,
                                   RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            //  отступ снизу для всех элементов, кроме последнего
            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                outRect.bottom = 20;
            }
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);

            int left = parent.getPaddingLeft() + 50;
            int right = parent.getWidth() - parent.getPaddingRight() - 50;

            for (int i = 0; i < parent.getChildCount(); i++) {
                android.view.View child = parent.getChildAt(i);
                int position = parent.getChildAdapterPosition(child);

                // Не рисуем разделитель после последнего элемента
                if (position == parent.getAdapter().getItemCount() - 1) {
                    continue;
                }

                float top = child.getBottom() + child.getTranslationY();
                float bottom = top + 2;

                c.drawLine(left, top, right, bottom, paint);
            }
        }
    }

    private final ActivityResultLauncher<Intent> editNoteLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Note note = (Note) result.getData()
                                    .getSerializableExtra(EditNoteActivity.EXTRA_NOTE);

                            int position = result.getData()
                                    .getIntExtra(EditNoteActivity.EXTRA_POSITION, -1);

                            if (position == -1) {
                                notesList.add(note);
                                adapter.notifyItemInserted(notesList.size() - 1);
                            } else {
                                notesList.set(position, note);
                                adapter.notifyItemChanged(position);
                            }
                        }
                    });

}