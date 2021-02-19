package ru.home.mynote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            int index = intent.getIntExtra(MainFragment.ARG_INDEX_MAIN, 0);
            MainFragment mainFragment = MainFragment.newInstance(index);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_main, mainFragment)
                    .commit();

        }
    }
}