package com.hdl.hricheditor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hdl.elog.ELog;
import com.hdl.hricheditor.runtimepermissions.PermissionsManager;
import com.hdl.hricheditor.runtimepermissions.PermissionsResultAction;
import com.huangdali.base.EditorResultBean;
import com.huangdali.bean.EContent;
import com.huangdali.bean.ItemType;
import com.huangdali.utils.ImageCompereUtils;
import com.huangdali.utils.ImageScaleUtils;
import com.huangdali.view.HRichEditorView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_EDIT = 192;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MainActivity", "onCreate(MainActivity.java:16)");
        requestPermission();
    }

    public void onStart(View view) {
        startActivityForResult(new Intent(this, HRichEditorView.class), REQUEST_CODE_EDIT);
    }

    /**
     * android6.0动态权限申请
     */
    private void requestPermission() {

        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied(String permission) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_EDIT) {
            //拿到编辑内容对象
            EditorResultBean resultBean = (EditorResultBean) data.getSerializableExtra("contents");
            ELog.e(resultBean);
            //上传图片、视频，并替换上传之后的服务器图片的url
            List<EContent> contents = resultBean.getContents();
            for (EContent content : contents) {
                if (ItemType.IMG.equals(content.getType()) || ItemType.VIDEO.equals(content.getType())) {
                    String fileUrl = uploadFile(content.getUrl());
                    content.setUrl(fileUrl);//反设置图片、视频的url（将URI转换为服务器中存放的地址）
                }
            }
            //拿到编辑内容对应的html body的字符串（已经包括样式啦）
            String htmlBody = "";
            for (EContent content : contents) {
                htmlBody += content.getHtml();
            }
            ELog.e("最终编辑的结果："+htmlBody);

        }
    }

    /**
     * 模拟文件上传，并且返回上传之后该文件所在的路径
     *
     * @param uri
     * @return
     */
    private String uploadFile(String uri) {
        String uploadResult;//用于记录文件上传之后的路径（建议使用相对路径，最好不好写死域名，拼接html的时候再加域名，避免域名更改导致更换麻烦的问题）
        String filePath = ImageCompereUtils.compressImg(ImageScaleUtils.getRealPathFromURI(this, Uri.parse(uri)), 30);//压缩文件
        //具体的文件上传逻辑。。。。。
        ELog.e(filePath);
        //模拟上传到服务器的地址
        uploadResult = "/upload/15519099928/IMG_" + System.currentTimeMillis() + "." + filePath.substring(filePath.lastIndexOf(".") + 1);
        return uploadResult;
    }


}
