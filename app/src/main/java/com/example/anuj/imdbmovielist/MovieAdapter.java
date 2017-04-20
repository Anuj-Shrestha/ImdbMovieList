package com.example.anuj.imdbmovielist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by anuj on 4/20/17.
 */
public class MovieAdapter extends ArrayAdapter<GitHubRepo> {

    private Context context;
    private List<GitHubRepo> values;

    public MovieAdapter(Context context, List<GitHubRepo> values) {
        super(context, R.layout.list_item, values);

        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView textView = (TextView) row.findViewById(R.id.textview_listitem);

        GitHubRepo item = values.get(position);
        String message = item.getName();
        textView.setText(message);

        return row;
    }
}
