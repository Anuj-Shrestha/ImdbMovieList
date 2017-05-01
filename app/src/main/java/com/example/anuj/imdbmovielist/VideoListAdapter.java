package com.example.anuj.imdbmovielist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

/**
 * Created by anuj on 4/20/17.
 */
public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Results> values;
    private LayoutInflater mInflater;
    private Results movieItem;


    public VideoListAdapter(Context context, ArrayList<Results> values) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.values = values;
    }

    public void setValues(ArrayList<Results> values) {
        this.values = values;
        notifyDataSetChanged();
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.video_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Results item = values.get(position);
        movieItem = item;
        String name = item.getName();
        String key = item.getKey();

        holder.textView.setText(name);
        holder.year.setText(key);
//        Glide.with(context)
//                .load("http://image.tmdb.org/t/p/w185/" + item.getPoster())
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        holder.spinnerRelativeLayout.setVisibility(View.GONE);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        holder.spinnerRelativeLayout.setVisibility(View.GONE);
//                        return false;
//                    }
//                })
//                .skipMemoryCache( true )
//                .into(holder.listMoviePoster);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    // convenience method for getting data at click position
    public Results getItem(int id) {
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
//            row.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, MovieDetailActivity.class);
//                    intent.putExtra("MovieDetailActivity", getItem(getAdapterPosition()));
//                    context.startActivity(intent);
//                }
//            });
        }
    }
}
