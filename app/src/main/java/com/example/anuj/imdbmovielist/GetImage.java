package com.example.anuj.imdbmovielist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by anuj on 4/25/17.
 */

public class GetImage extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    View spinner;

    public GetImage(ImageView imageView, View spinner) {
        this.imageView = imageView;
        this.spinner = spinner;
    }

    protected Bitmap doInBackground(String... urls) {
        String imageURL = urls[0];
        Bitmap bimage = null;
        try {
            InputStream in = new java.net.URL(imageURL).openStream();
            bimage = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimage;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
        spinner.setVisibility(View.GONE);

    }
}