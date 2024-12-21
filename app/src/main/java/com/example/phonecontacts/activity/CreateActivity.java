// CreateActivity类继承自AppCompatActivity，用于创建新的联系人信息
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
import com.example.phonecontacts.dao.ContactDAO;

public class CreateActivity extends AppCompatActivity {
    // 定义视图组件的引用
    private TextView nameView;
    private TextView phoneView;
    private TextView remarkView;
    private RadioButton femaleButton;

    // onCreate方法在活动创建时调用，用于初始化活动
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initUI(); // 初始化视图组件
        setupToolbar(); // 设置Toolbar
        setupCommitButton(); // 设置提交按钮的点击事件
    }

    // initUI方法用于初始化视图组件
    private void initUI() {
        nameView = findViewById(R.id.create_name);
        phoneView = findViewById(R.id.create_phone);
        remarkView = findViewById(R.id.create_remark);
        femaleButton = findViewById(R.id.create_female);
        RadioButton maleButton = findViewById(R.id.create_male);
        maleButton.setChecked(true); // 默认选中男性单选按钮
    }

    // setupToolbar方法用于设置Toolbar和返回按钮的点击事件
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.create_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(CreateActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    // setupCommitButton方法用于设置提交按钮的点击事件
    private void setupCommitButton() {
        Button commitButton = findViewById(R.id.create_commit);
        commitButton.setOnClickListener(v -> {
            String name = nameView.getText().toString().trim();
            String phone = phoneView.getText().toString().trim();
            String remark = remarkView.getText().toString().trim();

            // 验证输入是否为空
            if (name.isEmpty()) {
                Toast.makeText(CreateActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
            } else if (phone.isEmpty()) {
                Toast.makeText(CreateActivity.this, "请输入电话", Toast.LENGTH_SHORT).show();
            } else if (!phone.matches("\\d+")) {
                Toast.makeText(CreateActivity.this, "电话必须是纯数字", Toast.LENGTH_SHORT).show();
            } else if (remark.isEmpty()) {
                Toast.makeText(CreateActivity.this, "请输入备注", Toast.LENGTH_SHORT).show();
            } else {
                // 根据单选按钮选择性别
                String sex = "男";
                if (femaleButton.isChecked()) {
                    sex = "女";
                }

                // 调用ContactDAO的createContact方法创建新的联系人
                ContactDAO.createContact(name, phone, sex, remark);
                Toast.makeText(CreateActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                // 添加成功后跳转到MainActivity
                Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}