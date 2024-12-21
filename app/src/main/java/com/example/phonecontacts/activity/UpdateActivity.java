// UpdateActivity类继承自AppCompatActivity，用于更新联系人信息
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
import com.example.phonecontacts.contact.Contact;
import com.example.phonecontacts.dao.ContactDAO;

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
        initUI();
        setupToolbar();
        setupData();
        setupCommitButton();
    }

    // initUI方法用于初始化视图组件和获取传递的数据
    private void initUI() {
        id = getIntent().getStringExtra("id");
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
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    // setupData方法用于将选中的联系人数据设置到视图
    private void setupData() {
        Contact contact = ContactDAO.getOneContact(id);
        nameView.setText(contact.getName());
        phoneView.setText(contact.getPhone());
        remarkView.setText(contact.getRemark());
        // 根据联系人性别设置对应的单选按钮
        if (contact.getSex().equals("男")) {
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
            } else if (!phone.matches("\\d+")) {
                Toast.makeText(UpdateActivity.this, "电话必须是纯数字", Toast.LENGTH_SHORT).show();
            } else if (remark.isEmpty()) {
                Toast.makeText(UpdateActivity.this, "请输入备注", Toast.LENGTH_SHORT).show();
            } else {
                // 根据单选按钮选择性别
                String sex = "男";
                if (femaleButton.isChecked()) {
                    sex = "女";
                }

                // 调用ContactDAO的updateContact方法更新联系人信息
                ContactDAO.updateContact(id, name, phone, sex, remark);
                Toast.makeText(UpdateActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                // 更新成功后跳转到MainActivity
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}