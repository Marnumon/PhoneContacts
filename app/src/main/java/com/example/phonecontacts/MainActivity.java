package com.example.phonecontacts;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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

import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        DBUntil dbUntil = new DBUntil(MainActivity.this);
        dbUntil.db = dbUntil.getWritableDatabase();

        List<PeoBean> result = PeoDao.getAllPeo();
        ListView listView = findViewById(R.id.book_list);
        if (result.isEmpty()) {
            listView.setAdapter(null);
        } else {
            result.sort(new Comparator<PeoBean>() {
                @Override
                public int compare(PeoBean o1, PeoBean o2) {
                    if (o1.getBeginZ().equals("#") || o2.getBeginZ().equals("#")) {
                        return 1;
                    } else {
                        return o1.getBeginZ().compareTo(o2.getBeginZ());
                    }
                }
            });
            PeoAdapter peoAdapter = new PeoAdapter(MainActivity.this, result);
            listView.setAdapter(peoAdapter);
        }

        FloatingActionButton floatingActionButton = findViewById(R.id.add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

        EditText id = findViewById(R.id.search_id);
        id.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                listView.setAdapter(null);
                String title = id.getText().toString();
                List<PeoBean> searchResult = null;
                if (title.isEmpty()) {
                    searchResult = PeoDao.getAllPeo();
                } else {
                    searchResult = PeoDao.getAllPeo(title);
                }
                searchResult.sort(new Comparator<PeoBean>() {
                    @Override
                    public int compare(PeoBean o1, PeoBean o2) {
                        if (o1.getBeginZ().equals("#") || o2.getBeginZ().equals("#")) {
                            return 1;
                        } else {
                            return o1.getBeginZ().compareTo(o2.getBeginZ());
                        }
                    }
                });
                PeoAdapter peoAdapter = new PeoAdapter(MainActivity.this, searchResult);
                listView.setAdapter(peoAdapter);
                return false;
            }
        });
        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listView.setAdapter(null);
                String title = id.getText().toString();
                List<PeoBean> searchResult = null;
                if (title.isEmpty()) {
                    searchResult = PeoDao.getAllPeo();
                } else {
                    searchResult = PeoDao.getAllPeo(title);
                }
                searchResult.sort(new Comparator<PeoBean>() {
                    @Override
                    public int compare(PeoBean o1, PeoBean o2) {
                        if (o1.getBeginZ().equals("#") || o2.getBeginZ().equals("#")) {
                            return 1;
                        } else {
                            return o1.getBeginZ().compareTo(o2.getBeginZ());
                        }
                    }
                });
                PeoAdapter peoAdapter = new PeoAdapter(MainActivity.this, searchResult);
                listView.setAdapter(peoAdapter);
            }
        });
    }
}