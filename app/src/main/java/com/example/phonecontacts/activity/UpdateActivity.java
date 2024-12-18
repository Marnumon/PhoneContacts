package com.example.phonecontacts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.phonecontacts.MainActivity;
import com.example.phonecontacts.R;
import com.example.phonecontacts.bean.PeoBean;
import com.example.phonecontacts.dao.PeoDao;

public class UpdateActivity extends AppCompatActivity {
    // 定义视图组件的引用和联系人ID
    private String id;
    private TextView nameView;
    private TextView phoneView;
    private RadioButton maleButton;
    private RadioButton femaleButton;
    private TextView remarkView;

    // onCreate方法在活动创建时调用，用于初始化活动
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        // 初始化用户界面和获取传递的数据
        initUI();
        // 设置Toolbar
        setupToolbar();
        // 设置数据到视图
        setupData();
        // 设置提交按钮的点击事件
        setupCommitButton();
    }

    // initUI方法用于初始化视图组件和获取传递的数据
    private void initUI() {
        id = getIntent().getStringExtra("id"); // 从Intent中获取联系人ID
        nameView = findViewById(R.id.update_name);
        phoneView = findViewById(R.id.update_phone);
        maleButton = findViewById(R.id.update_male);
        femaleButton = findViewById(R.id.update_female);
        remarkView = findViewById(R.id.update_remark);
    }

    // setupToolbar方法用于设置Toolbar和返回按钮的点击事件
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.update_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> backToMainActivity());
    }

    // setupData方法用于将选中的联系人数据设置到视图
    private void setupData() {
        PeoBean peo = PeoDao.getOnePeo(id); // 根据ID获取联系人数据
        nameView.setText(peo.getName());
        phoneView.setText(peo.getPhone());
        remarkView.setText(peo.getRemark());
        // 根据联系人性别设置对应的单选按钮
        if (peo.getSex().equals("男")) {
            maleButton.setChecked(true);
        } else {
            femaleButton.setChecked(true);
        }
    }

    // setupCommitButton方法用于设置提交按钮的点击事件
    private void setupCommitButton() {
        Button commit = findViewById(R.id.update_commit);
        commit.setOnClickListener(v -> {
            String name = nameView.getText().toString().trim();
            String phone = phoneView.getText().toString().trim();
            String remark = remarkView.getText().toString().trim();
            // 验证输入是否为空
            if (name.isEmpty()) {
                Toast.makeText(UpdateActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
            } else if (phone.isEmpty()) {
                Toast.makeText(UpdateActivity.this, "请输入电话", Toast.LENGTH_SHORT).show();
            } else if (remark.isEmpty()) {
                Toast.makeText(UpdateActivity.this, "请输入备注", Toast.LENGTH_SHORT).show();
            } else {
                // 根据单选按钮选择性别
                String sex = "男";
                if (femaleButton.isChecked()) {
                    sex = "女";
                }
                // 调用PeoDao的updatePeo方法更新联系人信息
                PeoDao.updatePeo(id, name, phone, sex, remark);
                Toast.makeText(UpdateActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                backToMainActivity();
            }
        });
    }

    // backToMainActivity方法用于返回主页
    private void backToMainActivity() {
        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        startActivity(intent);
    }
}