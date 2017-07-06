package com.hdl.hricheditor.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hdl.hricheditor.R;
import com.hdl.hricheditor.bean.LinkContent;


public class LinkedActivity extends AppCompatActivity {

    private EditText etLinked, etDesc;
    private static final int REQUEST_CODE_EDIT_LINKED = 2001;//编辑文本

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked);
        etLinked = (EditText) findViewById(R.id.et_txteditor_linked);
        etDesc = (EditText) findViewById(R.id.et_txteditor_desc);
    }

    public void onOK(View view) {
        if (TextUtils.isEmpty(etLinked.getText().toString())) {
            Toast.makeText(this, "链接不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        LinkContent linkContent = new LinkContent();
        intent.putExtra("linkContent", linkContent);
        linkContent.setLink(TextUtils.isEmpty(etDesc.getText().toString()) ? "网页链接" : etDesc.getText().toString());
        linkContent.setLink(etLinked.getText().toString());

        this.setResult(REQUEST_CODE_EDIT_LINKED, intent);
        finish();
    }
}
