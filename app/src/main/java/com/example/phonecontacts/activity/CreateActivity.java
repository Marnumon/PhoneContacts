package com.example.phonecontacts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.phonecontacts.MainActivity;
import com.example.phonecontacts.R;
import com.example.phonecontacts.dao.PeoDao;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        TextView name = findViewById(R.id.create_name);
        TextView phone = findViewById(R.id.create_phone);
        RadioButton male = findViewById(R.id.create_male);
        RadioButton female = findViewById(R.id.create_female);
        male.setChecked(true);
        TextView remark = findViewById(R.id.create_remark);

        Button commit = findViewById(R.id.create_commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getText().toString().trim();
                String phone1 = phone.getText().toString().trim();
                String remark1 = remark.getText().toString().trim();
                if (name1.isEmpty()) {
                    Toast.makeText(CreateActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                } else if (phone1.isEmpty()) {
                    Toast.makeText(CreateActivity.this, "请输入电话", Toast.LENGTH_SHORT).show();
                } else if (remark1.isEmpty()) {
                    Toast.makeText(CreateActivity.this, "请输入备注", Toast.LENGTH_SHORT).show();
                } else {
                    String sex1 = "男";
                    if (female.isChecked()) {
                        sex1 = "女";
                    }
                    PeoDao.createPeo(name1, phone1, sex1, remark1);
                    Toast.makeText(CreateActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.create_toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CreateActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}