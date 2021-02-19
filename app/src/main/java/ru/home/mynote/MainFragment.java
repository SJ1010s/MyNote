package ru.home.mynote;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_INDEX_MAIN = "arg_index_main";


    // -----
    private List<NoteStructure> notes = new ArrayList<>();



    public MainFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(int index) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX_MAIN, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArrayList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initNoteFragment(view);
        super.onViewCreated(view, savedInstanceState);

    }

    public void initArrayList(){
        notes.add(new NoteStructure("Заголовок 1", "Содержание 1", "2021.01.02"));
        notes.add(new NoteStructure("Заголовок 2", "Содержание 2", "2021.01.28"));
        notes.add(new NoteStructure("Заголовок 3", "Содержание 3", "2021.01.30"));
    }

    public void initNoteFragment(View view){
        TextView descr = view.findViewById(R.id.text_note);
        descr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NoteFragment noteFragment = NoteFragment.newInstance("note");
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("note")
                        .replace(R.id.activity_main, noteFragment)
                        .commit();
            }
        });
    }
}