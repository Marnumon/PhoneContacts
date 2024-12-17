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
import com.example.phonecontacts.bean.PeoBean;
import com.example.phonecontacts.dao.PeoDao;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        PeoBean peo = PeoDao.getOnePeo(id);
        TextView name = findViewById(R.id.update_name);
        TextView phone = findViewById(R.id.update_phone);
        RadioButton male = findViewById(R.id.update_male);
        RadioButton female = findViewById(R.id.update_female);
        TextView remark = findViewById(R.id.update_remark);

        name.setText(peo.getName());
        phone.setText(peo.getPhone());
        remark.setText(peo.getRemark());
        if (peo.getSex().equals("男")) {
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }

        Button commit = findViewById(R.id.update_commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getText().toString().trim();
                String phone1 = phone.getText().toString().trim();
                String remark1 = remark.getText().toString().trim();
                if (name1.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                } else if (phone1.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "请输入电话", Toast.LENGTH_SHORT).show();
                } else if (remark1.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "请输入备注", Toast.LENGTH_SHORT).show();
                } else {
                    String sex1 = "男";
                    if (female.isChecked()) {
                        sex1 = "女";
                    }
                    PeoDao.updatePeo(id, name1, phone1, sex1, remark1);
                    Toast.makeText(UpdateActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.update_toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}