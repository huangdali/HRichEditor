package com.hdl.hricheditor.view;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hdl.hricheditor.R;
import com.hdl.hricheditor.bean.EContent;

public class TXTEditorActivity extends AppCompatActivity {
    private static final String TAG = "TXTEditorActivity";
    private MyRadioButton ivFontB, ivFontA, ivFontCenter, ivFontColor;
    private LinearLayout llFontA, llFontB, llFontCenter, llFontColor;
    private RadioGroup rgFontB, rgFontA, rgFontColor, rgFontCenter;
    private LinearLayout llFontArea;
    private String style = "";
    private String content = "";
    private EditText etContent;
    private MyCheckBox ivFontBorder, ivFontInter, ivFontLine;
    private boolean isBorder, isInter;
    private String desc, linked;
    private TextView tvAddLinked;
    private EContent eContent;
    private static final int REQUEST_CODE_EDIT_TXT = 1005;//编辑文本
    private static final int REQUEST_CODE_EDIT_LINKED = 2001;//编辑文本

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        eContent = (EContent) getIntent().getSerializableExtra("eContent");
        echoStyle();
    }

    /**
     * 回显样式
     */
    private void echoStyle() {
        if (!TextUtils.isEmpty(eContent.getContent())) {
            String content = eContent.getContent();
            if (content.contains("<br/><a href=")) {
                content = content.substring(0, content.indexOf("<br/><a href="));
            }
            etContent.setText(content);
            etContent.setSelection(content.length());
        }
        if (!TextUtils.isEmpty(eContent.getStyle())) {
            String style = eContent.getStyle();
            if (style.contains("font-weight:bold") && style.contains("font-style:italic")) {//加粗和斜体
                etContent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                ivFontBorder.setChecked(true);
                ivFontInter.setChecked(true);
                isBorder = true;
                isInter = true;
            } else if (style.contains("font-weight:bold")) {//只用加粗
                isBorder = true;
                etContent.setTypeface(Typeface.DEFAULT_BOLD);
                ivFontBorder.setChecked(true);
            } else if (style.contains("font-style:italic")) {//只用斜体
                isInter = true;
                ivFontInter.setChecked(true);
                etContent.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            }
            if (style.contains("text-decoration:underline")) {
                ivFontLine.setChecked(true);
                etContent.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            }
            if (style.contains("text-align:center")) {
                etContent.setGravity(Gravity.CENTER_HORIZONTAL);
                rgFontCenter.check(R.id.mrb_font_option_center);
            } else if (style.contains("text-align:right")) {
                etContent.setGravity(Gravity.RIGHT);
                rgFontCenter.check(R.id.mrb_font_option_right);
            } else {
                rgFontCenter.check(R.id.mrb_font_option_left);
                etContent.setGravity(Gravity.LEFT);
            }
            if (style.contains("font-size:18px")) {
                etContent.setTextSize(18);
                rgFontA.check(R.id.mrb_font_option_add);
            } else if (style.contains("font-size:14px")) {
                etContent.setTextSize(14);
                rgFontA.check(R.id.mrb_font_option_sub);
            } else {
                etContent.setTextSize(16);
                rgFontA.check(R.id.mrb_font_option_normal);
            }
            if (style.contains("color:#a9b7b7")) {
                etContent.setTextColor(0xffa9b7b7);
                rgFontColor.check(R.id.mrb_font_option_gray);
            } else if (style.contains("color:#33475f")) {
                etContent.setTextColor(0xff33475f);
                rgFontColor.check(R.id.mrb_font_option_blackgray);
            } else if (style.contains("color:#ecf0f1")) {
                etContent.setTextColor(0xffecf0f1);
                rgFontColor.check(R.id.mrb_font_option_whete);
            } else if (style.contains("color:#56abe4")) {
                etContent.setTextColor(0xff56abe4);
                rgFontColor.check(R.id.mrb_font_option_blue);
            } else if (style.contains("color:#11cd6e")) {
                etContent.setTextColor(0xff11cd6e);
                rgFontColor.check(R.id.mrb_font_option_green);
            } else if (style.contains("color:#f4c600")) {
                etContent.setTextColor(0xfff4c600);
                rgFontColor.check(R.id.mrb_font_option_yellow);
            } else if (style.contains("color:#9d55b8")) {
                etContent.setTextColor(0xff9d55b8);
                rgFontColor.check(R.id.mrb_font_option_violet);
            } else if (style.contains("color:#eb4f38")) {
                etContent.setTextColor(0xffeb4f38);
                rgFontColor.check(R.id.mrb_font_option_red);
            } else {
                etContent.setTextColor(0xff272636);
                rgFontColor.check(R.id.mrb_font_option_black);
            }
        }

    }


    /**
     * 确定按钮
     *
     * @param view
     */
    public void onSubmit(View view) {
        getData();
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("eContent", eContent);
        data.putExtras(bundle);
        this.setResult(REQUEST_CODE_EDIT_TXT, data);
        this.finish();
    }

    private void initView() {
        View view = View.inflate(this, R.layout.activity_txteditor, null);
        setContentView(view);
        ((TextView) view.findViewById(R.id.tv_public_title)).setText("编辑内容");
        tvAddLinked = (TextView) findViewById(R.id.tv_txteditor_addlinked);
        ivFontA = (MyRadioButton) findViewById(R.id.iv_font_option_a);
        ivFontB = (MyRadioButton) findViewById(R.id.iv_font_option_b);
        etContent = (EditText) findViewById(R.id.et_txteditor_content);
        ivFontCenter = (MyRadioButton) findViewById(R.id.iv_font_option_center);
        ivFontColor = (MyRadioButton) findViewById(R.id.iv_font_option_color);
        llFontA = (LinearLayout) findViewById(R.id.ll_font_option_a);
        llFontB = (LinearLayout) findViewById(R.id.ll_txteditor_style_area);
        llFontCenter = (LinearLayout) findViewById(R.id.ll_font_option_center);
        llFontColor = (LinearLayout) findViewById(R.id.ll_font_option_color);
        rgFontA = (RadioGroup) findViewById(R.id.rg_font_option_a);
        rgFontB = (RadioGroup) findViewById(R.id.rg_font_option_b);
        rgFontColor = (RadioGroup) findViewById(R.id.rg_font_option_color);
        rgFontCenter = (RadioGroup) findViewById(R.id.rg_font_option_center);
        llFontArea = (LinearLayout) findViewById(R.id.ll_font_option_area);
        ivFontBorder = (MyCheckBox) findViewById(R.id.mcb_font_option_border);
        ivFontInter = (MyCheckBox) findViewById(R.id.mcb_font_option_inter);
        ivFontLine = (MyCheckBox) findViewById(R.id.mcb_font_option_line);
    }

    /**
     * 获取数据
     */
    private void getData() {
        style = "";
        content = etContent.getText().toString().trim();
        Log.e(TAG, "getData: " + content);
        if (!TextUtils.isEmpty(linked)) {
            content = content + "<br/><a href=\"" + linked + "\">" + desc + "</a><br/>";
        }

        int checkedId = rgFontA.getCheckedRadioButtonId();
        if (checkedId == R.id.mrb_font_option_add) {
            style += "font-size:18px;";
        } else if (checkedId == R.id.mrb_font_option_normal) {
            style += "font-size:16px;";
        } else if (checkedId == R.id.mrb_font_option_sub) {
            style += "font-size:14px;";
        }
        int checkedIdCetner = rgFontCenter.getCheckedRadioButtonId();
        if (checkedIdCetner == R.id.mrb_font_option_center) {
            style += "text-align:center;";
        } else if (checkedIdCetner == R.id.mrb_font_option_right) {
            style += "text-align:right;";
        } else if (checkedIdCetner == R.id.mrb_font_option_left) {
            style += "text-align:left;";
        }
        if (ivFontBorder.isChecked()) {
            style += "font-weight:bold;";//加粗
        }
        if (ivFontInter.isChecked()) {
            style += "font-style:italic;";//斜体
        }
        if (ivFontLine.isChecked()) {
            style += "text-decoration:underline;";//下划线
        }
        int checkedIdColor = rgFontColor.getCheckedRadioButtonId();
        if (checkedIdColor == R.id.mrb_font_option_black) {
            style += "color:#272636";
        } else if (checkedIdColor == R.id.mrb_font_option_gray) {
            style += "color:#a9b7b7";
        } else if (checkedIdColor == R.id.mrb_font_option_blackgray) {
            style += "color:#33475f";
        } else if (checkedIdColor == R.id.mrb_font_option_blue) {
            style += "color:#56abe4";
        } else if (checkedIdColor == R.id.mrb_font_option_green) {
            style += "color:#11cd6e";
        } else if (checkedIdColor == R.id.mrb_font_option_yellow) {
            style += "color:#f4c600";
        } else if (checkedIdColor == R.id.mrb_font_option_violet) {
            style += "color:#9d55b8";
        } else if (checkedIdColor == R.id.mrb_font_option_whete) {
            style += "color:#ecf0f1";
        } else if (checkedIdColor == R.id.mrb_font_option_red) {
            style += "color:#eb4f38";
        }
        Log.e(TAG, "getData: " + content + "\n" + style);
        if (eContent == null) {
            Log.e(TAG, "getData: 我是空了");
        }
        eContent.setContent(content);
        eContent.setStyle(style);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "startActivityForResult: -----------" + requestCode);
        if (requestCode == REQUEST_CODE_EDIT_LINKED && resultCode == REQUEST_CODE_EDIT_LINKED) {
            linked = data.getStringExtra("linked");
            desc = data.getStringExtra("desc");
            tvAddLinked.setText(desc);
            Log.e(TAG, "startActivityForResult: " + linked + "\n" + desc);
        }
    }

    private void setListener() {

        tvAddLinked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(TXTEditorActivity.this, LinkedActivity.class), REQUEST_CODE_EDIT_LINKED);
            }
        });
        ivFontA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llFontA.setVisibility(View.VISIBLE);
                llFontB.setVisibility(View.GONE);
                llFontCenter.setVisibility(View.GONE);
                llFontColor.setVisibility(View.GONE);
            }
        });
        ivFontB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llFontA.setVisibility(View.GONE);
                llFontB.setVisibility(View.VISIBLE);
                llFontCenter.setVisibility(View.GONE);
                llFontColor.setVisibility(View.GONE);
            }
        });
        ivFontCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llFontA.setVisibility(View.GONE);
                llFontB.setVisibility(View.GONE);
                llFontCenter.setVisibility(View.VISIBLE);
                llFontColor.setVisibility(View.GONE);
            }
        });
        ivFontColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llFontA.setVisibility(View.GONE);
                llFontB.setVisibility(View.GONE);
                llFontCenter.setVisibility(View.GONE);
                llFontColor.setVisibility(View.VISIBLE);
            }
        });
        llFontArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgFontB.clearCheck();
                llFontA.setVisibility(View.GONE);
                llFontB.setVisibility(View.GONE);
                llFontCenter.setVisibility(View.GONE);
                llFontColor.setVisibility(View.GONE);
            }
        });
//
        setTextSizeListener();
        setTextAlginListener();
        setTextStyleListener();
        setTextColorListener();
    }

    private void setTextColorListener() {
        findViewById(R.id.mrb_font_option_black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(0xff272636);
            }
        });
        findViewById(R.id.mrb_font_option_gray).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(0xffa9b7b7);
            }
        });
        findViewById(R.id.mrb_font_option_blackgray).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(0xff33475f);
            }
        });
        findViewById(R.id.mrb_font_option_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(0xff56abe4);
            }
        });
        findViewById(R.id.mrb_font_option_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(0xff11cd6e);
            }
        });
        findViewById(R.id.mrb_font_option_yellow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(0xfff4c600);
            }
        });
        findViewById(R.id.mrb_font_option_violet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(0xff9d55b8);
            }
        });
        findViewById(R.id.mrb_font_option_whete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(0xffecf0f1);
            }
        });
        findViewById(R.id.mrb_font_option_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(0xffeb4f38);
            }
        });
    }

    /**
     * 设置字体对齐监听
     */
    private void setTextAlginListener() {
        findViewById(R.id.mrb_font_option_left).setOnClickListener(new View.OnClickListener() {//居左显示
            @Override
            public void onClick(View v) {
                etContent.setGravity(Gravity.START);
            }
        });
        findViewById(R.id.mrb_font_option_center).setOnClickListener(new View.OnClickListener() {//居中显示
            @Override
            public void onClick(View v) {
                etContent.setGravity(Gravity.CENTER_HORIZONTAL);
            }
        });
        findViewById(R.id.mrb_font_option_right).setOnClickListener(new View.OnClickListener() {//居右显示
            @Override
            public void onClick(View v) {
                etContent.setGravity(Gravity.RIGHT);
            }
        });
    }

    /**
     * 设置字体加粗、斜线、下划线监听
     */
    private void setTextStyleListener() {
        findViewById(R.id.mcb_font_option_border).setOnClickListener(new View.OnClickListener() {//加粗
            @Override
            public void onClick(View v) {
                isBorder = !isBorder;
                if (((MyCheckBox) v).isChecked()) {
                    if (isInter) {
                        etContent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                    } else {
                        etContent.setTypeface(Typeface.DEFAULT_BOLD);
                    }
                } else {
                    if (isInter) {
                        etContent.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    } else {
                        etContent.setTypeface(Typeface.DEFAULT);
                    }
                }
            }
        });
        findViewById(R.id.mcb_font_option_inter).setOnClickListener(new View.OnClickListener() {//斜体
            @Override
            public void onClick(View v) {
                isInter = !isInter;
                if (((MyCheckBox) v).isChecked()) {
                    if (isBorder) {
                        etContent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                    } else {
                        etContent.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    }
                } else {
                    if (isBorder) {
                        etContent.setTypeface(Typeface.DEFAULT_BOLD);
                    } else {
                        etContent.setTypeface(Typeface.DEFAULT);
                    }
                }
            }
        });
        findViewById(R.id.mcb_font_option_line).setOnClickListener(new View.OnClickListener() {//下划线
            @Override
            public void onClick(View v) {
                if (((MyCheckBox) v).isChecked()) {
                    etContent.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                } else {
                    etContent.getPaint().setFlags(0);
//                    etContent.setTypeface(Typeface.DEFAULT);
                }
                etContent.setText(etContent.getText().toString());
                etContent.setSelection(etContent.getText().toString().length());
            }
        });
    }

    /**
     * 设置字体大小的监听
     */
    private void setTextSizeListener() {
        findViewById(R.id.mrb_font_option_add).setOnClickListener(new View.OnClickListener() {//字体增大
            @Override
            public void onClick(View v) {
                if (((MyRadioButton) v).isChecked()) {
                    etContent.setTextSize(18);
                } else {
                    etContent.setTextSize(16);
                }
            }
        });
        findViewById(R.id.mrb_font_option_normal).setOnClickListener(new View.OnClickListener() {//正常字体
            @Override
            public void onClick(View v) {
                etContent.setTextSize(16);
            }
        });
        findViewById(R.id.mrb_font_option_sub).setOnClickListener(new View.OnClickListener() {//小号字体
            @Override
            public void onClick(View v) {
                if (((MyRadioButton) v).isChecked()) {
                    etContent.setTextSize(14);
                } else {
                    etContent.setTextSize(16);
                }
            }
        });
    }

    /**
     * 返回按钮事件
     *
     * @param view
     */
    public void onBack(View view) {
        this.finish();
    }

}
