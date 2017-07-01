package com.hdl.hricheditor.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hdl.hricheditor.R;

/**
 * 设置文章标题的activity
 */
public class TitleEidtorActivity extends AppCompatActivity {
    private EditText etTitle;
    private String title;
    private static final int REQUEST_CODE_SET_TITLE = 1003;//设置标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.activity_title_eidtor, null);
        setContentView(view);
        ((TextView) view.findViewById(R.id.tv_public_title)).setText("编辑标题");
        etTitle = (EditText) findViewById(R.id.et_titleeditor_title);
        title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            etTitle.setText(title);
            etTitle.setSelection(title.length());
        }

    }

    public void onSubmit(View view) {
        if (TextUtils.isEmpty(etTitle.getText().toString().trim())) {
            Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra("title", etTitle.getText().toString().trim());
        this.setResult(REQUEST_CODE_SET_TITLE, data);
        finish();
    }
    public void onBack(View view) {
        this.finish();
    }

}
