package com.example.anuj.imdbmovielist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class LifecycleExampleActivity extends AppCompatActivity {

    int sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_example);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("onstart ", String.valueOf(sum++));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume ", String.valueOf(sum++));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause ", String.valueOf(sum++));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop ", String.valueOf(sum++));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy ", String.valueOf(sum++));
    }
}
