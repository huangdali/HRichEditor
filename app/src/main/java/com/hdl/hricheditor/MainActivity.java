package com.hdl.hricheditor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.huangdali.view.HRichEditorView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            Log.e("MainActivity","onCreate(MainActivity.java:16)");
    }

    public void onStart(View view) {
        startActivity(new Intent(this, HRichEditorView.class));
    }


}
