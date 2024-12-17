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
import com.example.phonecontacts.bean.PeoBean;

import java.util.List;

public class PeoAdapter extends ArrayAdapter<PeoBean> {
    List<PeoBean> items;

    public PeoAdapter(Context context, List<PeoBean> items) {
        super(context, R.layout.book_view, items);
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.book_view, parent, false);
        }
        TextView name = convertView.findViewById(R.id.name);
        TextView letter = convertView.findViewById(R.id.letter);
        PeoBean peo = items.get(position);
        name.setText(peo.getName());
        ImageView imageView = convertView.findViewById(R.id.image);
        if (peo.getSex().equals("男")) {
            imageView.setImageResource(R.drawable.male);
        } else if (peo.getSex().equals("女")){
            imageView.setImageResource(R.drawable.female);
        }
        if (position == 0) {
            letter.setText(peo.getBeginZ());
        } else {
            PeoBean temp = items.get(position - 1);
            if (!temp.getBeginZ().equals(peo.getBeginZ())) {
                letter.setText(peo.getBeginZ());
            } else {
                letter.setHeight(0);
            }
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("id", peo.getId());
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
