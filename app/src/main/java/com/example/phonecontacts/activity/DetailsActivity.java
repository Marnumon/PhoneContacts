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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String id = getIntent().getStringExtra("id");
        TextView phoneNumber = findViewById(R.id.details_phone);
        Button call = findViewById(R.id.details_phone_button);
        call.setOnClickListener(v -> {
            int permissionCode = ContextCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.CALL_PHONE);
            if (permissionCode == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(phoneNumber.getText().toString().trim());
            } else {
                ActivityCompat.requestPermissions(DetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        });

        Button text = findViewById(R.id.details_message_button);
        text.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            Uri uri = Uri.parse("smsto:" + Uri.encode(phoneNumber.getText().toString().trim()));
            intent.setData(uri);
            startActivity(intent);
        });

        Button back = findViewById(R.id.details_back_button);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button delete = findViewById(R.id.details_delete_button);
        delete.setOnClickListener(v -> {
            PeoDao.deletePeo(id);
            Toast.makeText(DetailsActivity.this, "数据已删除", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button edit = findViewById(R.id.details_edit_button);
        edit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, UpdateActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });

        PeoBean peo = PeoDao.getOnePeo(id);
        ImageView avatar = findViewById(R.id.details_avatar);
        if (peo.getSex().equals("男")) {
            avatar.setImageResource(R.drawable.peo_male);
        } else {
            avatar.setImageResource(R.drawable.peo_female);
        }
        TextView name = findViewById(R.id.details_name);
        TextView phone = findViewById(R.id.details_phone);
        TextView sex = findViewById(R.id.details_sex);
        TextView remark = findViewById(R.id.details_remark);
        name.setText(peo.getName());
        phone.setText(peo.getPhone());
        sex.setText(peo.getSex());
        remark.setText(peo.getRemark());

    }

    private void makePhoneCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }
}