package com.huangdali.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hdl.hricheditorview.R;
import com.huangdali.utils.CToast;


/**
 * 设置文章标题的activity
 */
public class TitleEidtorActivity extends Activity {
    private EditText etTitle;
    private String title;
    private static final int REQUEST_CODE_SET_TITLE = 1003;//设置标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.activity_title_eidtor, null);
        setContentView(view);
        ((TextView) view.findViewById(R.id.tv_public_title)).setText(getString(R.string.title_edit_title));
        etTitle = (EditText) findViewById(R.id.et_titleeditor_title);
        title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            etTitle.setText(title);
            etTitle.setSelection(title.length());
        }

    }

    public void onSubmit(View view) {
        if (TextUtils.isEmpty(etTitle.getText().toString().trim())) {
            CToast.show(this, getString(R.string.title_dont_null));
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
