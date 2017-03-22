package com.example.alex.museumfunds;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alex.museumfunds.model.BaseColumns;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<BaseColumns> {

    private LayoutInflater inflater;
    private List objects;

    public CustomAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
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

        final BaseColumns baseColumns = (BaseColumns) this.objects.get(position);
        viewHolder.label.setText(baseColumns.getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        final View row = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        final TextView label = (TextView) row.findViewById(android.R.id.text1);
        final BaseColumns baseColumns = (BaseColumns) this.objects.get(position);
        label.setText(baseColumns.getName());
        return row;
    }
}
