package com.huangdali.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by HDL on 2017/3/20.
 */

public class BaseActivity extends AppCompatActivity {
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    public void setTitle(String title) {
        this.title = title;
    }
}
