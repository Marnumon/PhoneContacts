package com.example.phonecontacts;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.phonecontacts.activity.CreateActivity;
import com.example.phonecontacts.adapter.PeoAdapter;
import com.example.phonecontacts.bean.PeoBean;
import com.example.phonecontacts.dao.PeoDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText searchEditText;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeoDao.setDbHelper(this);
        searchEditText = findViewById(R.id.search_id);
        listView = findViewById(R.id.book_list);
        FloatingActionButton floatingActionButton = findViewById(R.id.add);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        updateListView();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateListView();
            }
        });

        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
            startActivity(intent);
        });
    }

    private void updateListView() {
        String searchText = searchEditText.getText().toString();
        List<PeoBean> searchResult = searchText.isEmpty() ? PeoDao.getAllPeo() : PeoDao.getAllPeo(searchText);
        if (searchResult.isEmpty()) {
            listView.setAdapter(null);
            return;
        } else {
            searchResult.sort((o1, o2) -> {
                if (o1.getBeginZ().equals("#") || o2.getBeginZ().equals("#")) {
                    return 1;
                } else {
                    return o1.getBeginZ().compareTo(o2.getBeginZ());
                }
            });
        }
        PeoAdapter peoAdapter = new PeoAdapter(MainActivity.this, searchResult);
        listView.setAdapter(peoAdapter);
    }
}