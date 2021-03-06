package ru.home.mynote;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private NoteStructure note;
    private static final String ARG_NOTE_FRAG = "note_fragment";
    private MaterialToolbar noteToolbar;
    private EditText title;
    private EditText date;
    private EditText descr;
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }


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
        title = view.findViewById(R.id.note_title);
        date = view.findViewById(R.id.note_date);
        descr = view.findViewById(R.id.note_descr);
        title.setText(note.getTitle());
        date.setText(note.getDate());
        descr.setText(note.getDescr());
        noteToolbar = view.findViewById(R.id.note_details_toolbar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        noteToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null){
                    Map<String, Object> noteMap = new HashMap<>();
                    noteMap.put("id", note.getId());
                    noteMap.put("title", title.getText().toString());
                    noteMap.put("descr", descr.getText().toString());
                    noteMap.put("date", date.getText().toString());
                    firebaseFirestore
                            .collection("notes")
                            .document(note.getId())
                            .set(noteMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    note.setTitle(title.getText().toString());
                                    note.setDate(date.getText().toString());
                                    note.setDescr(descr.getText().toString());
                                    getActivity().onBackPressed();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                }
            }
        });
    }

    public int getPosition() {
        return position;
    }
}