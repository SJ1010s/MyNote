package ru.home.mynote;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.SearchView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements NotesAdapterCallback {

    public static final String ARG_INDEX_MAIN = "arg_index_main";

    private final List<NoteStructure> notes = new ArrayList<>();
    private final AdapterMain adapterMain = new AdapterMain(this);

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
        initArrayList();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapterMain.setItems(notes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment]

        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initToolbar(view);
        initView(view);
        super.onViewCreated(view, savedInstanceState);

    }

    public void initArrayList() {
        notes.add(new NoteStructure("Заголовок 1", "Содержание 1", "2021.01.02"));
        notes.add(new NoteStructure("Заголовок 2", "Содержание 2", "2021.01.28"));
        notes.add(new NoteStructure("Заголовок 3", "Содержание 3", "2021.01.30"));
    }

    public void initToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        setHasOptionsMenu(true);
        setMenuVisibility(true);
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
                Toast.makeText( getContext(), query, Toast.LENGTH_SHORT).show();
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
        NoteFragment noteFragment = NoteFragment.newInstance("note");
        noteFragment.setNote(notes.get(position));
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("note")
                .replace(R.id.activity_main, noteFragment)
                .commit();
    }
}