package com.example.alex.museumfunds;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alex.museumfunds.model.BaseEntity;

import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    private final LayoutInflater inflater;
    private final List objects;

    public CustomAdapter(Context context, List objects) {
        super(context, android.R.layout.simple_spinner_item, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.objects = objects;
    }

    private static class ViewHolder {
        TextView label;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.label = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        final BaseEntity baseEntity = (BaseEntity) objects.get(position);
        viewHolder.label.setText(baseEntity.getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        final View row = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        final TextView label = (TextView) row.findViewById(android.R.id.text1);
        final BaseEntity baseEntity = (BaseEntity) objects.get(position);
        label.setText(baseEntity.getName());
        return row;
    }
}
