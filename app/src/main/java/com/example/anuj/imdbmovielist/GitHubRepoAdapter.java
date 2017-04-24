package com.example.anuj.imdbmovielist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuj on 4/20/17.
 */
public class GitHubRepoAdapter extends ArrayAdapter<Search> {

    private Context context;
    private ArrayList<Search> values;

    public GitHubRepoAdapter(Context context, ArrayList<Search> values) {
        super(context, R.layout.list_item, values);

        this.context = context;
        this.values = values;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView textView = (TextView) row.findViewById(R.id.textview_listitem);
        TextView year = (TextView) row.findViewById(R.id.textview_year);

        final Search item = values.get(position);
        String message = item.getTitle();
        String yearReleased = item.getYear();

        textView.setText(message);
        year.setText(yearReleased);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetail.class);
                intent.putExtra("MovieDetail", item);
                context.startActivity(intent);
            }
        });
        return row;
    }
}
