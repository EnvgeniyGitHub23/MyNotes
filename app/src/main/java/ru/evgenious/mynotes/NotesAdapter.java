package ru.evgenious.mynotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private final List<Note> notesList;

    public interface OnNoteLongClickListener {
        void onNoteLongClick(int position, View view);
    }

    private OnNoteLongClickListener longClickListener;

    public void setOnNoteLongClickListener(OnNoteLongClickListener listener) {
        this.longClickListener = listener;
    }

    // цвета для карточек
    private final int[] cardColors = {
            0xFFE3F2FD, // голубой
            0xFFF3E5F5, // фиолетовый
            0xFFE8F5E9, // зеленый
            0xFFFFF3E0  // оранжевый
    };

    public NotesAdapter(List<Note> notesList) {
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notesList.get(position);

        //  Декораторы из Note
        holder.titleTextView.setText(note.getFormattedTitle());
        holder.contentTextView.setText(note.getFormattedContent(true));

        // Декоратор для цвета карточки
        holder.cardView.setCardBackgroundColor(
                cardColors[position % cardColors.length]
        );

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                int pos = holder.getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    longClickListener.onNoteLongClick(pos, v);
                }
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleTextView;
        TextView contentTextView;

        NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
        }
    }
}
