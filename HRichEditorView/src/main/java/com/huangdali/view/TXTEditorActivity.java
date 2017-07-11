package com.huangdali.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hdl.hricheditorview.R;
import com.huangdali.base.GlobalField;
import com.huangdali.bean.EContent;
import com.huangdali.bean.LinkContent;
import com.huangdali.custom.MyCheckBox;
import com.huangdali.custom.MyRadioButton;

/**
 * 文本编辑activity，HRichEditor核心类
 */
public class TXTEditorActivity extends Activity {
    //整形常量区
    private static final int REQUEST_CODE_EDIT_TXT = 1005;//编辑文本
    private static final int REQUEST_CODE_EDIT_LINKED = 2001;//编辑链接
    //字符常量区
    private static final String TAG = "TXTEditorActivity";

    private boolean isBold;//是否选中了加粗
    private boolean isInter;//是否选中了斜体

    private String style = "";
    private String content = "";

    //                    加粗按钮      字体大小按钮  字体对齐方式    字体颜色
    private MyRadioButton rbFontBold, rbFontSize, rbFontAlign, rbFontColor;
    // 字体加粗选项，多选      加粗         斜体          下划线
    private MyCheckBox cbFontBold, cbFontInter, cbFontLine;
    //                    字体大小区           加粗区           对齐方式区        颜色区
    private LinearLayout llFontSizeArea, llFontBoldArea, llFontAlignArea, llFontColorArea;
    //                    加粗区      字体大小区    颜色区        对齐方式区
    private RadioGroup rgFontBold, rgFontSize, rgFontColor, rgFontAlign;
    //底部字体控制栏
    private LinearLayout llFontControl;
    //内容区
    private EditText etContent;
    //添加链接
    private TextView tvAddLinked;
    //内容对象
    private EContent eContent;
    //链接对象
    private LinkContent linkContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        eContent = (EContent) getIntent().getSerializableExtra("eContent");
        echoStyle();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        View view = View.inflate(this, R.layout.activity_txteditor, null);
        setContentView(view);
        ((TextView) view.findViewById(R.id.tv_public_title)).setText(getString(R.string.txt_edit_content));
        tvAddLinked = (TextView) findViewById(R.id.tv_txteditor_addlinked);
        rbFontSize = (MyRadioButton) findViewById(R.id.iv_font_option_a);
        rbFontBold = (MyRadioButton) findViewById(R.id.iv_font_option_b);
        etContent = (EditText) findViewById(R.id.et_txteditor_content);
        rbFontAlign = (MyRadioButton) findViewById(R.id.iv_font_option_center);
        rbFontColor = (MyRadioButton) findViewById(R.id.iv_font_option_color);
        llFontSizeArea = (LinearLayout) findViewById(R.id.ll_font_option_a);
        llFontBoldArea = (LinearLayout) findViewById(R.id.ll_txteditor_style_area);
        llFontAlignArea = (LinearLayout) findViewById(R.id.ll_font_option_center);
        llFontColorArea = (LinearLayout) findViewById(R.id.ll_font_option_color);
        rgFontSize = (RadioGroup) findViewById(R.id.rg_font_option_a);
        rgFontBold = (RadioGroup) findViewById(R.id.rg_font_option_b);
        rgFontColor = (RadioGroup) findViewById(R.id.rg_font_option_color);
        rgFontAlign = (RadioGroup) findViewById(R.id.rg_font_option_center);
        llFontControl = (LinearLayout) findViewById(R.id.ll_font_option_area);
        cbFontBold = (MyCheckBox) findViewById(R.id.mcb_font_option_border);
        cbFontInter = (MyCheckBox) findViewById(R.id.mcb_font_option_inter);
        cbFontLine = (MyCheckBox) findViewById(R.id.mcb_font_option_line);
    }

    /**
     * 回显样式
     */
    private void echoStyle() {
        if (!TextUtils.isEmpty(eContent.getContent())) {
            String content = eContent.getContent();
            if (content.contains("<br/><a href=")) {//回显需要去掉链接
                content = content.substring(0, content.indexOf("<br/><a href="));
            }
            etContent.setText(content);
            etContent.setSelection(content.length());
        }
        if (!TextUtils.isEmpty(eContent.getStyle())) {
            String style = eContent.getStyle();
            setFontBold(style);//回显字体加粗方式
            setFontAlign(style);//回显字体对齐方式
            setFontSize(style);//回显字体大小
            setFontColor(style);//回显字体颜色
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


    /**
     * 获取数据
     */
    private void getData() {
        style = "";
        content = etContent.getText().toString().trim();
        Log.e(TAG, "getData: " + content);
        if (linkContent != null && !TextUtils.isEmpty(linkContent.getLink())) {
            content = content + "<br/><a href=\"" + linkContent.getLink() + "\">" + linkContent.getTitle() + "</a><br/>";
        }

        int checkedId = rgFontSize.getCheckedRadioButtonId();
        if (checkedId == R.id.mrb_font_option_add) {
            style += GlobalField.FontSize.KEY_SIZE_18;
        } else if (checkedId == R.id.mrb_font_option_normal) {
            style += GlobalField.FontSize.KEY_SIZE_16;
        } else if (checkedId == R.id.mrb_font_option_sub) {
            style += GlobalField.FontSize.KEY_SIZE_14;
        }
        int checkedIdCetner = rgFontAlign.getCheckedRadioButtonId();
        if (checkedIdCetner == R.id.mrb_font_option_center) {
            style += GlobalField.FontAlign.KEY_ALIGN_CENTER;
        } else if (checkedIdCetner == R.id.mrb_font_option_right) {
            style += GlobalField.FontAlign.KEY_ALIGN_RIGHT;
        } else if (checkedIdCetner == R.id.mrb_font_option_left) {
            style += GlobalField.FontAlign.KEY_ALIGN_LEFT;
        }
        if (cbFontBold.isChecked()) {
            style += GlobalField.FontBold.KEY_STYLE_BOLD;//加粗
        }
        if (cbFontInter.isChecked()) {
            style += GlobalField.FontBold.KEY_STYLE_ITALIC;//斜体
        }
        if (cbFontLine.isChecked()) {
            style += GlobalField.FontBold.KEY_STYLE_UNDERLINE;//下划线
        }
        int checkedIdColor = rgFontColor.getCheckedRadioButtonId();
        if (checkedIdColor == R.id.mrb_font_option_black) {
            style += GlobalField.FontColor.KEY_COLOR_BLACK;
        } else if (checkedIdColor == R.id.mrb_font_option_gray) {
            style += GlobalField.FontColor.KEY_COLOR_GRAY;
        } else if (checkedIdColor == R.id.mrb_font_option_blackgray) {
            style += GlobalField.FontColor.KEY_COLOR_BLACKGRAY;
        } else if (checkedIdColor == R.id.mrb_font_option_blue) {
            style += GlobalField.FontColor.KEY_COLOR_BLUE;
        } else if (checkedIdColor == R.id.mrb_font_option_green) {
            style += GlobalField.FontColor.KEY_COLOR_GREEN;
        } else if (checkedIdColor == R.id.mrb_font_option_yellow) {
            style += GlobalField.FontColor.KEY_COLOR_YELLOW;
        } else if (checkedIdColor == R.id.mrb_font_option_violet) {
            style += GlobalField.FontColor.KEY_COLOR_VOILET;
        } else if (checkedIdColor == R.id.mrb_font_option_white) {
            style += GlobalField.FontColor.KEY_COLOR_WHITE;
        } else if (checkedIdColor == R.id.mrb_font_option_red) {
            style += GlobalField.FontColor.KEY_COLOR_RED;
        }
        Log.e(TAG, "getData: " + content + "\n" + style);
        eContent.setContent(content);
        eContent.setStyle(style);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "startActivityForResult: -----------" + requestCode);
        if (requestCode == REQUEST_CODE_EDIT_LINKED && resultCode == REQUEST_CODE_EDIT_LINKED) {
            linkContent = (LinkContent) data.getSerializableExtra("linkContent");
            tvAddLinked.setText(linkContent.getTitle());
            Log.e(TAG, "startActivityForResult: " + linkContent);
        }
    }

    /**
     * 设置监听
     */
    private void setListener() {

        tvAddLinked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(TXTEditorActivity.this, LinkedActivity.class), REQUEST_CODE_EDIT_LINKED);
            }
        });
        rbFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llFontSizeArea.setVisibility(View.VISIBLE);
                llFontBoldArea.setVisibility(View.GONE);
                llFontAlignArea.setVisibility(View.GONE);
                llFontColorArea.setVisibility(View.GONE);
            }
        });
        rbFontBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llFontSizeArea.setVisibility(View.GONE);
                llFontBoldArea.setVisibility(View.VISIBLE);
                llFontAlignArea.setVisibility(View.GONE);
                llFontColorArea.setVisibility(View.GONE);
            }
        });
        rbFontAlign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llFontSizeArea.setVisibility(View.GONE);
                llFontBoldArea.setVisibility(View.GONE);
                llFontAlignArea.setVisibility(View.VISIBLE);
                llFontColorArea.setVisibility(View.GONE);
            }
        });
        rbFontColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llFontSizeArea.setVisibility(View.GONE);
                llFontBoldArea.setVisibility(View.GONE);
                llFontAlignArea.setVisibility(View.GONE);
                llFontColorArea.setVisibility(View.VISIBLE);
            }
        });
        llFontControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgFontBold.clearCheck();
                llFontSizeArea.setVisibility(View.GONE);
                llFontBoldArea.setVisibility(View.GONE);
                llFontAlignArea.setVisibility(View.GONE);
                llFontColorArea.setVisibility(View.GONE);
            }
        });
//
        setTextSizeListener();
        setTextAlginListener();
        setTextStyleListener();
        setTextColorListener();
    }

    /**
     * 设置字体颜色选择监听
     */
    private void setTextColorListener() {
        findViewById(R.id.mrb_font_option_black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(GlobalField.FontColor.COLOR_BLACK);
            }
        });
        findViewById(R.id.mrb_font_option_gray).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(GlobalField.FontColor.COLOR_GRAY);
            }
        });
        findViewById(R.id.mrb_font_option_blackgray).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(GlobalField.FontColor.COLOR_BLACKGRAY);
            }
        });
        findViewById(R.id.mrb_font_option_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(GlobalField.FontColor.COLOR_BLUE);
            }
        });
        findViewById(R.id.mrb_font_option_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(GlobalField.FontColor.COLOR_GREEN);
            }
        });
        findViewById(R.id.mrb_font_option_yellow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(GlobalField.FontColor.COLOR_YELLOW);
            }
        });
        findViewById(R.id.mrb_font_option_violet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(GlobalField.FontColor.COLOR_VOILET);
            }
        });
        findViewById(R.id.mrb_font_option_white).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(GlobalField.FontColor.COLOR_WHITE);
            }
        });
        findViewById(R.id.mrb_font_option_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setTextColor(GlobalField.FontColor.COLOR_RED);
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
                isBold = !isBold;
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
                    if (isBold) {
                        etContent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                    } else {
                        etContent.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    }
                } else {
                    if (isBold) {
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
                    etContent.setTextSize(GlobalField.FontSize.SIZE_18);
                } else {
                    etContent.setTextSize(GlobalField.FontSize.SIZE_16);
                }
            }
        });
        findViewById(R.id.mrb_font_option_normal).setOnClickListener(new View.OnClickListener() {//正常字体
            @Override
            public void onClick(View v) {
                etContent.setTextSize(GlobalField.FontSize.SIZE_16);
            }
        });
        findViewById(R.id.mrb_font_option_sub).setOnClickListener(new View.OnClickListener() {//小号字体
            @Override
            public void onClick(View v) {
                if (((MyRadioButton) v).isChecked()) {
                    etContent.setTextSize(GlobalField.FontSize.SIZE_14);
                } else {
                    etContent.setTextSize(GlobalField.FontSize.SIZE_16);
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

    /**
     * 回显字体加粗方式
     *
     * @param style
     */
    private void setFontBold(String style) {
        if (style.contains(GlobalField.FontBold.KEY_STYLE_BOLD) && style.contains(GlobalField.FontBold.KEY_STYLE_ITALIC)) {//加粗和斜体
            etContent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
            cbFontBold.setChecked(true);
            cbFontInter.setChecked(true);
            isBold = true;
            isInter = true;
        } else if (style.contains(GlobalField.FontBold.KEY_STYLE_BOLD)) {//只用加粗
            isBold = true;
            etContent.setTypeface(Typeface.DEFAULT_BOLD);
            cbFontBold.setChecked(true);
        } else if (style.contains(GlobalField.FontBold.KEY_STYLE_ITALIC)) {//只用斜体
            isInter = true;
            cbFontInter.setChecked(true);
            etContent.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }

        if (style.contains(GlobalField.FontBold.KEY_STYLE_UNDERLINE)) {
            cbFontLine.setChecked(true);
            etContent.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    /**
     * 回显字体对齐方式
     *
     * @param style
     */
    private void setFontAlign(String style) {
        if (style.contains(GlobalField.FontAlign.KEY_ALIGN_CENTER)) {
            etContent.setGravity(Gravity.CENTER_HORIZONTAL);
            rgFontAlign.check(R.id.mrb_font_option_center);
        } else if (style.contains(GlobalField.FontAlign.KEY_ALIGN_RIGHT)) {
            etContent.setGravity(Gravity.RIGHT);
            rgFontAlign.check(R.id.mrb_font_option_right);
        } else {
            rgFontAlign.check(R.id.mrb_font_option_left);
            etContent.setGravity(Gravity.LEFT);
        }
    }

    /**
     * 回显字体大小
     *
     * @param style
     */
    private void setFontSize(String style) {
        if (style.contains(GlobalField.FontSize.KEY_SIZE_18)) {
            etContent.setTextSize(GlobalField.FontSize.SIZE_18);
            rgFontSize.check(R.id.mrb_font_option_add);
        } else if (style.contains(GlobalField.FontSize.KEY_SIZE_14)) {
            etContent.setTextSize(GlobalField.FontSize.SIZE_14);
            rgFontSize.check(R.id.mrb_font_option_sub);
        } else {
            etContent.setTextSize(GlobalField.FontSize.SIZE_16);
            rgFontSize.check(R.id.mrb_font_option_normal);
        }
    }

    /**
     * 回显字体颜色
     *
     * @param style
     */
    private void setFontColor(String style) {
        if (style.contains(GlobalField.FontColor.KEY_COLOR_GRAY)) {//灰色
            etContent.setTextColor(GlobalField.FontColor.COLOR_GRAY);
            rgFontColor.check(R.id.mrb_font_option_gray);
        } else if (style.contains(GlobalField.FontColor.KEY_COLOR_BLACKGRAY)) {//深蓝色
            etContent.setTextColor(GlobalField.FontColor.COLOR_BLACKGRAY);
            rgFontColor.check(R.id.mrb_font_option_blackgray);
        } else if (style.contains(GlobalField.FontColor.KEY_COLOR_WHITE)) {//白色
            etContent.setTextColor(GlobalField.FontColor.COLOR_WHITE);
            rgFontColor.check(R.id.mrb_font_option_white);
        } else if (style.contains(GlobalField.FontColor.KEY_COLOR_BLUE)) {//蓝色
            etContent.setTextColor(GlobalField.FontColor.COLOR_BLUE);
            rgFontColor.check(R.id.mrb_font_option_blue);
        } else if (style.contains(GlobalField.FontColor.KEY_COLOR_GREEN)) {//绿色
            etContent.setTextColor(GlobalField.FontColor.COLOR_GREEN);
            rgFontColor.check(R.id.mrb_font_option_green);
        } else if (style.contains(GlobalField.FontColor.KEY_COLOR_YELLOW)) {//黄色
            etContent.setTextColor(GlobalField.FontColor.COLOR_YELLOW);
            rgFontColor.check(R.id.mrb_font_option_yellow);
        } else if (style.contains(GlobalField.FontColor.KEY_COLOR_VOILET)) {//紫色
            etContent.setTextColor(GlobalField.FontColor.COLOR_VOILET);
            rgFontColor.check(R.id.mrb_font_option_violet);
        } else if (style.contains(GlobalField.FontColor.KEY_COLOR_RED)) {//红色
            etContent.setTextColor(GlobalField.FontColor.COLOR_RED);
            rgFontColor.check(R.id.mrb_font_option_red);
        } else {
            etContent.setTextColor(GlobalField.FontColor.COLOR_BLACK);
            rgFontColor.check(R.id.mrb_font_option_black);//黑色
        }
    }

}
