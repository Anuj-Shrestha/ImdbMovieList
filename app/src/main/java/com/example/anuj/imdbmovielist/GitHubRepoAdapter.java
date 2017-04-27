package com.example.anuj.imdbmovielist;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuj on 4/20/17.
 */
public class GitHubRepoAdapter extends RecyclerView.Adapter<GitHubRepoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Search> values;
    private LayoutInflater mInflater;
    private Search movieItem;


    public GitHubRepoAdapter(Context context, ArrayList<Search> values) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.values = values;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Search item = values.get(position);
        movieItem = item;
        String message = item.getTitle();
        String yearReleased = item.getYear();

        holder.textView.setText(message);
        holder.year.setText(yearReleased);
        Glide.with(context)
                .load(item.getUri())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.spinner.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.spinner.setVisibility(View.GONE);
                        return false;
                    }
                })
                .skipMemoryCache( true )
                .into(holder.listMoviePoster);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    // convenience method for getting data at click position
    public Search getItem(int id) {
        return values.get(id);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView, year;
        public ImageView listMoviePoster;
        public RelativeLayout spinner;

        public ViewHolder(View row) {
            super(row);
            textView = (TextView) row.findViewById(R.id.textview_listitem);
            year = (TextView) row.findViewById(R.id.textview_year);
            listMoviePoster = (ImageView) row.findViewById(R.id.imageview_listposter);
            spinner = (RelativeLayout) row.findViewById(R.id.relativelayout_spinner);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MovieDetail.class);
                    intent.putExtra("MovieDetail", getItem(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
