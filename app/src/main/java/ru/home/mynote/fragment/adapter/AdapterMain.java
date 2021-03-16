package ru.home.mynote.fragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import ru.home.mynote.NoteStructure;
import ru.home.mynote.R;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.NotesViewHolder> implements NoteAdapterSetItem {

    private final List<NoteStructure> notes = new ArrayList<>();
    private final NotesAdapterCallback callback;
    private final Fragment contextFragment;
    private int menuPosition;

    public AdapterMain(NotesAdapterCallback callback, Fragment contextFragment) {
        this.callback = callback;
        this.contextFragment = contextFragment;
    }

    public void setItems(List<NoteStructure> notes) {
        this.notes.clear();
        this.notes.addAll(notes);
        notifyDataSetChanged();
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_main, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.onBind(notes.get(position), position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public void setItem(NoteStructure noteStructure, int position) {
        notes.set(position, noteStructure);
        notifyItemChanged(position);

    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView title;
        private final MaterialTextView date;
        private final MaterialTextView descr;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            date = itemView.findViewById(R.id.item_date);
            descr = itemView.findViewById(R.id.item_descr);

            registerContextMenu(itemView);

        }

        public void onBind(NoteStructure model, int position) {
            title.setText(model.getTitle());
            date.setText(model.getDate());
            descr.setText(model.getDescr());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        callback.onItemClick(getAdapterPosition());
                    }
                }
            });
        }

        public void registerContextMenu(@NonNull View itemView) {
            if (contextFragment != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        menuPosition = getLayoutPosition();
                        return false;
                    }
                });
                contextFragment.registerForContextMenu(itemView);
            }
        }

    }
}
