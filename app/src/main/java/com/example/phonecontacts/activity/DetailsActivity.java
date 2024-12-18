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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.phonecontacts.MainActivity;
import com.example.phonecontacts.R;
import com.example.phonecontacts.bean.PeoBean;
import com.example.phonecontacts.dao.PeoDao;

public class DetailsActivity extends AppCompatActivity {
    // 定义视图组件的引用和联系人ID
    private String id;
    private TextView phoneView;
    private Button callButton;
    private Button textButton;
    private Button backButton;
    private Button deleteButton;
    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        id = getIntent().getStringExtra("id");
        // 初始化用户界面元素
        initUI();
        // 设置按钮点击事件
        setupButtonClickListeners();
        // 设置数据显示到用户界面
        setupData();
    }

    private void initUI() {
        // 声明并初始化显示电话号码的TextView
        phoneView = findViewById(R.id.details_phone);
        // 通过findViewById获取界面上的按钮，并初始化
        callButton = findViewById(R.id.details_phone_button);
        textButton = findViewById(R.id.details_message_button);
        backButton = findViewById(R.id.details_back_button);
        deleteButton = findViewById(R.id.details_delete_button);
        editButton = findViewById(R.id.details_edit_button);
    }

    private void setupButtonClickListeners() {
        // 设置拨打电话按钮的点击事件
        callButton.setOnClickListener(v -> {
            int permissionCode = ContextCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.CALL_PHONE);
            if (permissionCode == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(phoneView.getText().toString().trim());
            } else {
                ActivityCompat.requestPermissions(DetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        });

        // 设置发送短信按钮的点击事件
        textButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            Uri uri = Uri.parse("smsto:" + Uri.encode(phoneView.getText().toString().trim()));
            intent.setData(uri);
            startActivity(intent);
        });

        // 设置返回按钮的点击事件
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // 设置删除联系人按钮的点击事件
        deleteButton.setOnClickListener(v -> {
            PeoDao.deletePeo(id);
            Toast.makeText(DetailsActivity.this, "数据已删除", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // 设置编辑联系人按钮的点击事件
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, UpdateActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });
    }

    private void setupData() {
        // 根据ID从数据库获取单个联系人的信息
        PeoBean peo = PeoDao.getOnePeo(id);
        // 获取显示头像的ImageView，并根据性别设置不同的图片资源
        ImageView avatar = findViewById(R.id.details_avatar);
        if (peo.getSex().equals("男")) {
            avatar.setImageResource(R.drawable.peo_male);
        } else {
            avatar.setImageResource(R.drawable.peo_female);
        }
        // 获取界面上的TextView，并设置联系人的姓名、性别和备注
        TextView nameView = findViewById(R.id.details_name);
        TextView phoneView = findViewById(R.id.details_phone);
        TextView sexView = findViewById(R.id.details_sex);
        TextView remarkView = findViewById(R.id.details_remark);
        nameView.setText(peo.getName());
        phoneView.setText(peo.getPhone());
        sexView.setText(peo.getSex());
        remarkView.setText(peo.getRemark());
    }

    // 拨打电话的方法
    private void makePhoneCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }
}