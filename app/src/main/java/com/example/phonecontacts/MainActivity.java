// MainActivity类继承自AppCompatActivity，作为应用的主界面
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
import com.example.phonecontacts.adapter.ContactAdapter;
import com.example.phonecontacts.contact.Contact;
import com.example.phonecontacts.dao.ContactDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // 定义UI组件的引用
    private EditText searchEditText;
    private ListView listView;
    private FloatingActionButton floatingActionButton;

    // onCreate方法在活动创建时调用，用于初始化活动
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化数据库帮助类
        ContactDAO.setDbHelper(this);
        // 初始化用户界面
        initUI();
        // 设置监听器
        setupListeners();
        // 更新列表视图
        updateListView();
    }

    // initUI方法用于初始化视图组件
    private void initUI(){
        searchEditText = findViewById(R.id.search_id); // 获取搜索框
        listView = findViewById(R.id.book_list); // 获取列表视图
        floatingActionButton = findViewById(R.id.add); // 获取浮动操作按钮
        Toolbar toolbar = findViewById(R.id.toolbar); // 获取工具栏
        setSupportActionBar(toolbar); // 设置工具栏
    }

    // setupListeners方法用于设置视图组件的监听器
    private void setupListeners(){
        // 为搜索框添加文本变化监听器，当文本变化后更新列表视图
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

        // 为浮动操作按钮设置点击监听器，点击后跳转到创建联系人的活动
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
            startActivity(intent);
        });
    }

    // updateListView方法用于根据搜索框的文本更新列表视图
    public void updateListView() {
        String searchText = searchEditText.getText().toString(); // 获取搜索框文本
        // 根据搜索框的文本获取搜索结果
        List<Contact> searchResult = searchText.isEmpty() ? ContactDAO.getAllContacts() : ContactDAO.getAllContacts(searchText);
        // 如果搜索结果为空，则清空列表视图的适配器
        if (searchResult.isEmpty()) {
            listView.setAdapter(null);
            return;
        } else {
            // 对搜索结果进行排序
            searchResult.sort((o1, o2) -> {
                if (o1.getFirstLetter().equals("#") || o2.getFirstLetter().equals("#")) {
                    return 1;
                } else {
                    return o1.getFirstLetter().compareTo(o2.getFirstLetter());
                }
            });
        }
        // 创建联系人适配器并设置到列表视图
        ContactAdapter contactAdapter = new ContactAdapter(MainActivity.this, searchResult);
        listView.setAdapter(contactAdapter);
    }
}