package com.example.phonecontacts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.phonecontacts.activity.CreateActivity;
import com.example.phonecontacts.adapter.PeoAdapter;
import com.example.phonecontacts.bean.PeoBean;
import com.example.phonecontacts.dao.PeoDao;
import com.example.phonecontacts.until.DBUntil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText searchEditText;
    private ListView listView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        try (DBUntil dbUntil = new DBUntil(MainActivity.this)) {
//            DBUntil.db = dbUntil.getWritableDatabase();
//        } catch (Exception e) {
//            Log.e("MainActivity", "Failed to initialize DBUntil", e);
//        }

        DBUntil dbUntil = new DBUntil(MainActivity.this);
        DBUntil.db = dbUntil.getWritableDatabase();



        listView = findViewById(R.id.book_list);
        searchEditText = findViewById(R.id.search_id);
        FloatingActionButton floatingActionButton = findViewById(R.id.add);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        updateListView();

        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
            startActivity(intent);
        });

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 如果DBUntil.db需要关闭，确保在这里关闭
        DBUntil.db.close();
    }
}