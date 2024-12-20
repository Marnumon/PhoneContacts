// ContactAdapter类继承自ArrayAdapter，用于为联系人列表提供视图和数据
package com.example.phonecontacts.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.phonecontacts.R;
import com.example.phonecontacts.activity.DetailsActivity;
import com.example.phonecontacts.contact.Contact;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    // 成员变量，用于存储联系人列表数据
    List<Contact> items;

    // ContactAdapter的构造函数，接收上下文和联系人列表数据，并调用父类的构造函数
    public ContactAdapter(Context context, List<Contact> items) {
        super(context, R.layout.book_view, items);
        this.items = items;
    }

    // getView方法用于为每个列表项提供视图
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.book_view, parent, false);
        }

        // 通过findViewById获取布局中的姓名TextView
        TextView name = convertView.findViewById(R.id.name);
        // 通过findViewById获取布局中的首字母TextView
        TextView letter = convertView.findViewById(R.id.letter);
        // 获取当前位置的联系人对象
        Contact contact = items.get(position);
        // 设置姓名TextView的文本为联系人的姓名
        name.setText(contact.getName());

        // 通过findViewById获取布局中的性别ImageView
        ImageView imageView = convertView.findViewById(R.id.image);
        // 根据联系人性别设置对应的图片资源
        if (contact.getSex().equals("男")) {
            imageView.setImageResource(R.drawable.male);
        } else if (contact.getSex().equals("女")) {
            imageView.setImageResource(R.drawable.female);
        }

        // 如果是列表的第一个项，设置首字母TextView的文本为联系人的首字母
        if (position == 0) {
            letter.setText(contact.getFirstLetter());
        } else {
            // 如果当前项的首字母与前一项不同，则更新首字母TextView的文本
            Contact temp = items.get(position - 1);
            if (!temp.getFirstLetter().equals(contact.getFirstLetter())) {
                letter.setText(contact.getFirstLetter());
            } else {
                // 如果首字母相同，则将首字母TextView的高度设置为0，使其不可见
                letter.setHeight(0);
            }
        }

        // 为convertView设置点击事件监听器，点击时跳转到详情页面
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putExtra("id", contact.getId());
            getContext().startActivity(intent);
        });

        // 返回convertView，即当前项的视图
        return convertView;
    }
}