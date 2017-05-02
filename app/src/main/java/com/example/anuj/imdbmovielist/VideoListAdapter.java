package com.example.anuj.imdbmovielist;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
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
import java.util.List;

/**
 * Created by anuj on 4/20/17.
 */
public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Results> values;
    private LayoutInflater mInflater;
    private Results movieItem;
    private String backDropUri;
    private ItemClickListener mClickListener;


    public VideoListAdapter(Context context, ArrayList<Results> values, String backDropUri) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.values = values;
        this.backDropUri = backDropUri;
    }

    public void setValues(ArrayList<Results> values, String backDropUri) {
        this.values = values;
        this.backDropUri = backDropUri;
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

        holder.videoNameTextView.setText(name);
        holder.youtubeKeyTextView.setText(key);
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185/" + backDropUri)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
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
    public Results getItem(int id) {
        return values.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView videoNameTextView, youtubeKeyTextView;
        public ImageView listMoviePoster;
        public RelativeLayout spinner;


        public ViewHolder(View row) {
            super(row);
            videoNameTextView = (TextView) row.findViewById(R.id.textview_listitem);
            youtubeKeyTextView = (TextView) row.findViewById(R.id.textview_year);
            listMoviePoster = (ImageView) row.findViewById(R.id.imageview_listposter);
            spinner = (RelativeLayout) row.findViewById(R.id.relativelayout_spinner);
            row.setOnClickListener(this);
//            row.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("https://www.youtube.com/watch?v="+ getItem(getAdapterPosition()).getKey()));
//                    intent.setPackage("com.google.android.youtube");
//                    context.startActivity(intent);
//                }
//            });
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
