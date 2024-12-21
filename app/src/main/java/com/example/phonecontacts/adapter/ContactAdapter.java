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
    private final List<Contact> items;

    // ContactAdapter的构造函数，接收上下文和联系人列表数据，并调用父类的构造函数
    public ContactAdapter(Context context, List<Contact> items) {
        super(context, R.layout.book_view, items);
        this.items = items;
    }

    // getView方法用于为每个列表项提供视图
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // 如果convertView为null，则通过LayoutInflater创建新的视图
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_view, parent, false);
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

        // 如果是列表的第一个项或当前项的首字母与前一项不同，则显示首字母
        if (position == 0 || !items.get(position - 1).getFirstLetter().equals(contact.getFirstLetter())) {
            letter.setVisibility(View.VISIBLE);
            letter.setText(contact.getFirstLetter());
        } else {
            letter.setVisibility(View.GONE);
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