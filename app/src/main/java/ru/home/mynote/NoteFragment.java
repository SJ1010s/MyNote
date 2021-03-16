package ru.home.mynote;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    private NoteStructure note;
    private static final String ARG_NOTE_FRAG = "note_fragment";


    public NoteFragment() {
        // Required empty public constructor
    }

    public void setNote(NoteStructure note){
        this.note = note;
    }

    public NoteStructure getNote() {
        return note;
    }

    public static NoteFragment newInstance(String param1) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOTE_FRAG, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNote(view);
    }

    public void initNote(View view){
        EditText title = view.findViewById(R.id.note_title);
        EditText date = view.findViewById(R.id.note_date);
        EditText descr = view.findViewById(R.id.note_descr);
        title.setText(note.getTitle());
        date.setText(note.getDate());
        descr.setText(note.getDescr());
    }
}