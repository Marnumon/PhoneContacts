// DetailsActivity类继承自AppCompatActivity，用于展示单个联系人的详细信息
package com.example.phonecontacts.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.phonecontacts.MainActivity;
import com.example.phonecontacts.R;
import com.example.phonecontacts.contact.Contact;
import com.example.phonecontacts.dao.ContactDAO;

public class DetailsActivity extends AppCompatActivity {
    // 定义视图组件的引用和联系人ID
    private String id;
    private TextView phoneView;
    private Button callButton;
    private Button textButton;
    private Button deleteButton;
    private Button editButton;

    // onCreate方法在活动创建时调用，用于初始化活动
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        id = getIntent().getStringExtra("id"); // 从Intent中获取联系人ID
        initUI(); // 初始化视图组件
        setupToolbar(); // 设置Toolbar
        setupButtonClickListeners(); // 设置按钮点击事件监听器
        setupData(); // 设置数据显示
    }

    // initUI方法用于初始化视图组件
    private void initUI() {
        phoneView = findViewById(R.id.details_phone);
        callButton = findViewById(R.id.details_phone_button);
        textButton = findViewById(R.id.details_message_button);
        deleteButton = findViewById(R.id.details_delete_button);
        editButton = findViewById(R.id.details_edit_button);
    }

    // setupToolbar方法用于设置Toolbar和返回按钮的点击事件
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    // setupButtonClickListeners方法用于设置按钮点击事件监听器
    private void setupButtonClickListeners() {
        // 拨打电话按钮的点击事件
        callButton.setOnClickListener(v -> {
            int permissionCode = ContextCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.CALL_PHONE);
            if (permissionCode == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(phoneView.getText().toString().trim());
            } else {
                ActivityCompat.requestPermissions(DetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        });

        // 发送短信按钮的点击事件
        textButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            Uri uri = Uri.parse("smsto:" + Uri.encode(phoneView.getText().toString().trim()));
            intent.setData(uri);
            startActivity(intent);
        });

        // 删除联系人按钮的点击事件
        deleteButton.setOnClickListener(v -> {
            ContactDAO.deleteContact(id); // 调用ContactDAO删除联系人
            Toast.makeText(DetailsActivity.this, "联系人已删除", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // 编辑联系人按钮的点击事件
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, UpdateActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });
    }

    // setupData方法用于设置数据显示
    private void setupData() {
        Contact contact = ContactDAO.getOneContact(id); // 根据ID获取联系人信息
        ImageView avatar = findViewById(R.id.details_avatar);
        // 根据联系人性别设置头像
        if (contact.getSex().equals("男")) {
            avatar.setImageResource(R.drawable.male);
        } else {
            avatar.setImageResource(R.drawable.female);
        }
        TextView nameView = findViewById(R.id.details_name);
        TextView phoneView = findViewById(R.id.details_phone);
        TextView remarkView = findViewById(R.id.details_remark);
        // 设置联系人信息显示
        nameView.setText(contact.getName());
        phoneView.setText(contact.getPhone());
        remarkView.setText(contact.getRemark());
    }

    // makePhoneCall方法用于拨打电话
    private void makePhoneCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }
}