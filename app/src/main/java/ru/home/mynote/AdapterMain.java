package ru.home.mynote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.NotesViewHolder> {

    private final List<NoteStructure> notes = new ArrayList<>();
    private final NotesAdapterCallback callback;

    public AdapterMain(NotesAdapterCallback callback) {
        this.callback = callback;
    }

    public void setItems(List<NoteStructure> notes){
        this.notes.clear();
        this.notes.addAll(notes);
        notifyDataSetChanged();
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

    class NotesViewHolder extends RecyclerView.ViewHolder{
        private final MaterialTextView title;
        private final MaterialTextView date;
        private final MaterialTextView descr;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            date = itemView.findViewById(R.id.item_date);
            descr = itemView.findViewById(R.id.item_descr);
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

    }
}
