package ru.home.mynote.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.SearchView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.home.mynote.firebase.FirebaseHandler;
import ru.home.mynote.fragment.adapter.AdapterMain;
import ru.home.mynote.NoteStructure;
import ru.home.mynote.fragment.adapter.NotesAdapterCallback;
import ru.home.mynote.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements NotesAdapterCallback {

    public static final String ARG_INDEX_MAIN = "arg_index_main";

    private FirebaseHandler firebase = new FirebaseHandler();
    private List<NoteStructure> notes = new ArrayList<>();
    private final AdapterMain adapterMain = new AdapterMain(this, this);
    private NoteFragment noteFragment;


    public MainFragment() {
        // Required empty public constructor
    }


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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initNotes();
    }

    private void initNotes(){
        notes.clear();
        firebase.setFirebaseItemsInNotes(notes, adapterMain);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initToolbar(view);
        initView(view);
        initButtonAdd(view);
        super.onViewCreated(view, savedInstanceState);
    }

    public void onSuccessNoteSet(List<NoteStructure> items) {
        notes.clear();
    }

    public void initToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        setHasOptionsMenu(true);
        setMenuVisibility(true);
    }

    public void initButtonAdd(View view) {
        FloatingActionButton floatingActionButton = view.findViewById(R.id.main_note_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = UUID.randomUUID().toString();
                NoteStructure objectNote = new NoteStructure(id, "", "", "");
                notes.add(objectNote);
                onItemClick(notes.indexOf(objectNote));
            }
        });
    }

    public void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapterMain);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        NoteStructure note = notes.get(position);
        noteFragment = NoteFragment.newInstance("note");
        noteFragment.setPosition(position);
        noteFragment.setNote(note);
        noteFragment.setFirebaseHandler(firebase);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("note")
                .replace(R.id.activity_main, noteFragment)
                .commit();
    }

    @Override
    public void onCreateContextMenu(
            @NonNull ContextMenu menu,
            @NonNull View v,
            @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_notes, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapterMain.getMenuPosition();
        String id = notes.get(position).getId();
        switch (item.getItemId()){
            case R.id.action_delete_note:
                firebase.deleteNote(id, notes, adapterMain);
                initNotes();

                return true;
        }
        return super.onContextItemSelected(item);

    }
}