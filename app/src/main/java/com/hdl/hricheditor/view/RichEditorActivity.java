package com.hdl.hricheditor.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hdl.hricheditor.R;
import com.hdl.hricheditor.bean.BaseView;
import com.hdl.hricheditor.bean.EContent;
import com.hdl.hricheditor.bean.ItemType;
import com.hdl.hricheditor.utils.ImageScaleUtils;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.LocalMediaLoader;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import io.valuesfeng.picker.Picker;
import io.valuesfeng.picker.engine.GlideEngine;
import io.valuesfeng.picker.utils.PicturePickerUtils;

import static com.luck.picture.lib.model.FunctionConfig.MODE_MULTIPLE;

public class RichEditorActivity extends AppCompatActivity implements BaseView {
    private List<EContent> contentList = new CopyOnWriteArrayList<>();//用这个才不会抛出异常
    private RecyclerView rvItemList;//item列表
    private static final String TAG = "RichEditorActivity";
    private static final int REQUEST_CODE_CHOOSE_BG = 908;//选择背景
    private static final int REQUEST_CODE_CHOOSE_ITEM_IMG = 909;//更改item图片
    private static final int REQUEST_CODE_SET_TITLE = 901;//设置标题
    private static final int REQUEST_CODE_CHOOSE_IMGS = 902;//多选图片
    private List<Uri> bgPic = new ArrayList<>();//图片存放的集合
    private ImageView ivBackgroudIMG;
    private int currentClickIndex = 0, currentVideoIndex = 0;
    private float translateDistance = 440;//位移的距离
    private String title;
    private String videoPath;
    private String bgParam = "";
    private TextView tvTitle;
    private CheckBox setBg;
    private View view;
    private ProgressDialog mProgressDialog;
    private Map<String, String> mapImgName = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.layout_richeditor, null);
        setContentView(view);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("正在同步到云...");
        initView();
//        if (meipian != null) {
//            String content = meipian.getContent();
//            contentList = new Gson().fromJson(content, new TypeToken<List<EContent>>() {
//            }.getType());
//            KLog.e("需要修改的数据" + contentList);
//            Glide.with(RichEditorActivity.this)
//                    .load(GlobalField.BASEURL + meipian.getImgurl())
//                    .placeholder(R.mipmap.default_adv)
//                    .error(R.mipmap.default_adv)
//                    .into(ivBackgroudIMG);
//            title = meipian.getTitle();
//            tvTitle.setText(title);
//            if ("Y".equals(meipian.getBgcolor())) {
//                setBg.setChecked(true);
//            }
//        } else {
//            Picker.from(this)
//                    .count(10)
//                    .enableCamera(true)
//                    .setEngine(new GlideEngine())
//                    .forResult(REQUEST_CODE_CHOOSE_IMGS);
//        }
        showData();
//        getToken();
    }

//    @Override
//    public void ivNext(View view) {
//        super.ivNext(view);
//        onSubmit();
//    }

//    private void getToken() {
//        String url = "/videoController/saveVideo.action";
//        MyHttpUtils.build()
//                .url(GlobalField.BASEURL + "actionToken/getToken.action?userid=" + userId + "&actionUrl=" + url)
//                .setJavaBean(TokenBean.class)
//                .onExecute(new CommCallback<TokenBean>() {
//                    @Override
//                    public void onSucceed(TokenBean tokenBean) {
//                        token = tokenBean.getToken();
//                    }
//
//                    @Override
//                    public void onFailed(Throwable throwable) {
//
//                    }
//                });
//    }

    /**
     * 将文件集转为请求体
     *
     * @return
     */
//    public Map<String, RequestBody> filesToMap() {
//        Map<String, RequestBody> paramsMap = new HashMap<>();
//        RequestBody fileBody;
//        for (EContent eContent : contentList) {
////            KLog.e(eContent.toString());
//            if (!TextUtils.isEmpty(eContent.getUrl()) && !eContent.getUrl().contains("upload/videoandimages/")) {
//                File file;
//                if (eContent.getType().equals(ItemType.IMG)) {//图片类型的文件
//                    String resultPath = ImageCompereUtils.compressImg(ImageScaleUtils.getRealPathFromURI(this, Uri.parse(eContent.getUrl())), 30);
//                    file = new File(resultPath);
//                    KLog.e("压缩之后的文件路径：" + resultPath);
//                    mapImgName.put(eContent.getUrl(), file.getName());
////                    KLog.e("图片地址：" + file.getAbsoluteFile());
//                    fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
////                    paramsMap.put(mapImgName.get(file.getName()) + "\";filename=\"" + mapImgName.get(file.getName()), fileBody);
//                    paramsMap.put(file.getName() + "\";filename=\"" + file.getName(), fileBody);
//                } else {
//                    file = new File(eContent.getUrl());
//                    fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
//                    paramsMap.put(file.getName() + "\";filename=\"" + file.getName(), fileBody);
//                }
//            }
//        }
//        return paramsMap;
//    }

//    public void onSubmit() {
//        if (TextUtils.isEmpty(title)) {
//            Snackbar.make(view, "标题不能为空", Snackbar.LENGTH_SHORT)
//                    .setAction("确定", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    }).show();
//            return;
//        }
//        if (contentList == null || contentList.size() == 0) {
//            Snackbar.make(view, "内容不为空", Snackbar.LENGTH_SHORT)
//                    .setAction("确定", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    }).show();
//            return;
//        }
//        Map<String, RequestBody> mapParams = filesToMap();
//        String content = getContent();
//
//        //------------开始构造封面请求参数------------
//        if (bgPic == null || bgPic.size() == 0) {
////            ToastUtils.showToast(this, "背景图片不能为空");
//            if (TextUtils.isEmpty(meipian.getImgurl())) {
//                Snackbar.make(view, "背景图片不能为空", Snackbar.LENGTH_SHORT)
//                        .setAction("确定", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                            }
//                        }).show();
//                return;
//            }
//        }
//
//        SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM");
//        String time = formart.format(new Date(System.currentTimeMillis()));
//
//
//        if (bgPic != null && bgPic.size() > 0) {
////            File file = new File(ImageScaleUtils.getRealPathFromURI(this, bgPic.get(0)));
////            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
////            String bgName = "IMG_" + System.currentTimeMillis() + "." + file.getName().substring(file.getName().lastIndexOf(".") + 1);
////            mapParams.put(bgName + "\";filename=\"" + bgName, fileBody);
////            bgParam = "upload/videoandimages/" + time + "/" + userId + "/" + bgName;
////            KLog.e("上传的图片地址：" + bgParam);
//            String path = ImageCompereUtils.compressImg(ImageScaleUtils.getRealPathFromURI(this, bgPic.get(0)), 10);
//            File file = new File(path);
//            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
////            String bgName = "IMG_" + System.currentTimeMillis() + "." + file.getName().substring(file.getName().lastIndexOf(".") + 1);
//            mapParams.put(file.getName() + "\";filename=\"" + file.getName(), fileBody);
//            bgParam = "upload/videoandimages/" + time + "/" + userId + "/" + file.getName();
//            KLog.e("上传的图片地址：" + bgParam);
//        } else {
//            KLog.e("图片地址没有改变哦：" + bgParam);
//            if (!TextUtils.isEmpty(meipian.getImgurl())) {
//                bgParam = meipian.getImgurl();
//            } else {
//                Snackbar.make(view, "背景图片不能为空", Snackbar.LENGTH_SHORT)
//                        .setAction("确定", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                            }
//                        }).show();
//                return;
//            }
//        }
//        //------------结束构造封面请求参数------------
//
//        showProgress();
//        if (meipian != null) {
//            mapParams.put("id", RequestBody.create(MediaType.parse("application/json"), meipian.getId()));
//        }
//        mapParams.put("title", RequestBody.create(MediaType.parse("application/json"), title));
//        mapParams.put("imgurl", RequestBody.create(MediaType.parse("application/json"), bgParam));
//        mapParams.put("userid", RequestBody.create(MediaType.parse("application/json"), userId));
//        mapParams.put("content", RequestBody.create(MediaType.parse("application/json"), content));
//        mapParams.put("bgcolor", RequestBody.create(MediaType.parse("application/json"), setBg.isChecked() ? "Y" : "N"));
//        mapParams.put("token", RequestBody.create(MediaType.parse("application/json"), token));
//        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
//        OkHttpClient client = httpBuilder
//                .readTimeout(100, TimeUnit.MINUTES)
//                .connectTimeout(100, TimeUnit.MINUTES)
//                .writeTimeout(100, TimeUnit.MINUTES) //设置超时.
//                .build();
//
//        ApiInterface apiInterface = new Retrofit
//                .Builder()
//                .client(client)
//                .baseUrl(GlobalField.API_PUBLISH_MEIPIAN)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(ApiInterface.class);
//        if (meipian == null) {//说明是发布
//            KLog.e("meipian---------发布");
//            apiInterface.upload(mapParams)
//                    .enqueue(new Callback<ResultBean>() {
//                        @Override
//                        public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
//                            hideProgress();
//                            KLog.e(response.toString());
//                            if (response.body() != null && "SUCCESS".equals(response.body().getStatus())) {
//                                toDetaild(response.body().getId());
//                            } else {
//                                showMsg("发布失败，请重试！");
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResultBean> call, Throwable t) {
//                            showMsg("发布失败，您发布的内容太多，请删除一些再上传");
//                            hideProgress();
//                        }
//
//                    });
//        } else {//修改
//            KLog.e("修改内容");
//            if (mapParams != null && mapParams.size() > 0) {
//                apiInterface.update(mapParams)
//                        .enqueue(new Callback<ResultBean>() {
//                            @Override
//                            public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
//                                KLog.e("请求成功了-----------------" + response.toString());
//                                hideProgress();
//                                toDetaild(meipian.getId());
//                            }
//
//                            @Override
//                            public void onFailure(Call<ResultBean> call, Throwable t) {
//                                KLog.e(t.getMessage());
//                                showMsg("修改失败");
//                            }
//                        });
//            } else {
//                showMsg("没有修改任何内容哦");
//            }
//        }
//    }

//    private void toDetaild(String id) {
//        String url = GlobalField.API_LOOK_MEIPIAN + "?id=" + id;
//
//        try {
//            title = URLDecoder.decode(title, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
////        Intent intent = new Intent(this, GPublicWebView.class)
////                .putExtra("title", title)
////                .putExtra("collectid", "1121")
////                .putExtra("url", url)
////                .putExtra("type", "NEW")
////                .putExtra("collection", "YES");
////        startActivity(intent);
//        String conStr = "";
//        if (contentList != null && contentList.size() > 0) {
//            for (EContent eContent : contentList) {
//                String text = eContent.getContent();
//                conStr += TextUtils.isEmpty(text) ? "" : text;
//            }
//        }
//        if (TextUtils.isEmpty(conStr)) {
//            conStr = title;
//        }
//        Intent intent = new Intent(this, MeiPianDetailActivity.class)
//                .putExtra("title", title)
//                .putExtra("content", conStr)
//                .putExtra("zanCount", "0")
//                .putExtra("comCount", "0")
//                .putExtra("views", "2")
//                .putExtra("mId", id)
//                .putExtra("isZan", "N")
//                .putExtra("imageurl", GlobalField.BASEURL + bgParam)
//                .putExtra("url", GlobalField.API_LOOK_MEIPIAN + "?id=" + id);//需要加上"file:///mnt/" 才可以哦
//        startActivity(intent);
//
//        new Thread() {//发布完成之后就清理图片缓存文件夹
//            @Override
//            public void run() {
//                DataCleanManager.cleanCustomCache("/sdcard/takephoto");//发布完成之后清除用于存放临时图片的文件夹
//            }
//        }.start();
//
//        finish();
//    }


    /**
     * 获取内容
     *
     * @return
     */
    private String getContent() {
//        SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM");
//        String time = formart.format(new Date(System.currentTimeMillis()));
//        for (EContent eContent : contentList) {
//            if (!TextUtils.isEmpty(eContent.getUrl()) && !eContent.getUrl().contains("upload/videoandimages/")) {
//                File file;
//                if (eContent.getType().equals(ItemType.IMG)) {
////                    file = new File(ImageScaleUtils.getRealPathFromURI(this, Uri.parse(eContent.getUrl())));
//                    eContent.setUrl("upload/videoandimages/" + time + "/" + userId + "/" + mapImgName.get(eContent.getUrl()));
////                    KLog.e("数据库里面存了啥" + eContent.getUrl());
//
//                } else {
//                    file = new File(eContent.getUrl());
//                    eContent.setUrl("upload/videoandimages/" + time + "/" + userId + "/" + file.getName());
//                }
//            }
//        }
////        return new Gson().toJson(contentList);
        return "";
    }

    /**
     * 视频回调方法
     */

    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {


        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            contentList.get(currentVideoIndex).setUrl(resultList.get(0).getPath());
            videoPath = resultList.get(0).getPath();
            showData();
        }
    };

    /**
     * 设置标题
     *
     * @param view
     */
    public void onSetTitle(View view) {
        startActivityForResult(new Intent(this, TitleEidtorActivity.class).putExtra("title", TextUtils.isEmpty(title) ? "" : title), REQUEST_CODE_SET_TITLE);
    }

    public void onChangeBG(View view) {
        Picker.from(this)
                .count(1)
                .enableCamera(true)
                .setEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE_BG);
    }

    private void initView() {
        rvItemList = (RecyclerView) findViewById(R.id.rv_itemlist);
        ivBackgroudIMG = (ImageView) findViewById(R.id.iv_richeditor_bg);
        tvTitle = (TextView) findViewById(R.id.tv_richeditor_title);
    }

    public void showData() {
//        rvItemList.removeAllViews();
        /**
         * 增加第一个
         */
        View addItem0 = View.inflate(RichEditorActivity.this, R.layout.view_add_item, null);
        ImageView ivAdd0 = (ImageView) addItem0.findViewById(R.id.iv_additem_add);
        final LinearLayout llAddItemArea0 = (LinearLayout) addItem0.findViewById(R.id.ll_additem_addarea);
        setAddAreaListener(0, addItem0, ivAdd0, llAddItemArea0);
        rvItemList.addView(addItem0);

        for (int i = 0; i < contentList.size(); i++) {
            final EContent curEContent = contentList.get(i);
            final View item = View.inflate(this, R.layout.recycleview_item, null);
            ImageView ivItemPic = (ImageView) item.findViewById(R.id.iv_item_pic);
            if (ItemType.TXT.equals(curEContent.getType())) {
                ivItemPic.setImageResource(R.mipmap.txt_item);
            } else if (ItemType.VIDEO.equals(curEContent.getType())) {
//                if (curEContent.getUrl().contains("upload/videoandimages/")) {
//                    Glide.with(RichEditorActivity.this)
//                            .load("http://api.g3box.com:8090/G3/" + curEContent.getUrl())
//                            .placeholder(R.mipmap.video_item)
//                            .error(R.mipmap.video_item)
//                            .into(ivItemPic);
//                    KLog.e("视频地址：http://api.g3box.com:8090/G3/" + curEContent.getUrl());
//                } else if (!TextUtils.isEmpty(curEContent.getUrl())) {
//                    Glide.with(RichEditorActivity.this)
//                            .load(Uri.fromFile( new File(curEContent.getUrl())))
//                            .placeholder(R.mipmap.video_item)
//                            .error(R.mipmap.video_item)
//                            .into(ivItemPic);
//                    KLog.e("视频地址："+curEContent.getUrl());
//                } else {
//                    KLog.e("没有视频可以显示");
                ivItemPic.setImageResource(R.mipmap.video_item);
//                }
            } else {
                if (!TextUtils.isEmpty(curEContent.getUrl())) {
//                    if (!curEContent.getUrl().contains("upload/videoandimages/")) {
//                        String imageAbsolutePath = ImageScaleUtils.getImageAbsolutePath(RichEditorActivity.this, Uri.parse(curEContent.getUrl()));
//                        Bitmap bitmap = ImageScaleUtils.compressBySize(imageAbsolutePath, 128, 128);
//                        ivItemPic.setImageBitmap(bitmap);
//                    } else {
//                        Glide.with(RichEditorActivity.this)
//                                .load("http://api.g3box.com:8090/G3/" + curEContent.getUrl())
//                                .placeholder(R.mipmap.img)
//                                .into(ivItemPic);
//                        KLog.e("http://api.g3box.com:8090/G3/" + curEContent.getUrl());
//                    }

                } else {
                    ivItemPic.setImageResource(R.mipmap.img);
                }
            }
            if (ItemType.IMG.equals(curEContent.getType())) {
                final int finalI1 = i;
                ivItemPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Picker.from(RichEditorActivity.this)
                                .count(1)
                                .enableCamera(true)
                                .setEngine(new GlideEngine())
                                .forResult(REQUEST_CODE_CHOOSE_ITEM_IMG);
                        currentClickIndex = finalI1;
                    }
                });

            } else if (ItemType.VIDEO.equals(curEContent.getType())) {
                final int finalI2 = i;
                ivItemPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentVideoIndex = finalI2;//记录当前点击的是哪个位置
                        getVideo();
                    }
                });
            }
            TextView tvItemDesc = (TextView) item.findViewById(R.id.tv_item_desc);
            ImageView ivItemDelete = (ImageView) item.findViewById(R.id.iv_item_delete);
            ImageView ivItemUp = (ImageView) item.findViewById(R.id.iv_item_up);
            ImageView ivItemDown = (ImageView) item.findViewById(R.id.iv_item_down);
            setItemListeners(i, curEContent, item, ivItemDelete, ivItemUp, ivItemDown);

            /**
             * 隐藏第一个item的up和最后一个item的down
             */
            if (i == 0) {
                ivItemUp.setVisibility(View.GONE);
            }
            if (i == contentList.size() - 1) {
                ivItemDown.setVisibility(View.GONE);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 10, 20, 10);
            item.setLayoutParams(params);
            tvItemDesc.setText(TextUtils.isEmpty(curEContent.getContent()) ? "点击添加文字" : curEContent.getContent());
            final int finalI = i;
            tvItemDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RichEditorActivity.this, TXTEditorActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("index", finalI);
                    bundle.putSerializable("eContent", contentList.get(finalI));
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1002);
                }
            });
            View addItem = View.inflate(RichEditorActivity.this, R.layout.view_add_item, null);
            ImageView ivAdd = (ImageView) addItem.findViewById(R.id.iv_additem_add);
            final LinearLayout llAddItemArea = (LinearLayout) addItem.findViewById(R.id.ll_additem_addarea);
            setAddAreaListener(i + 1, addItem, ivAdd, llAddItemArea);
            rvItemList.addView(item);
            rvItemList.addView(addItem);

        }
    }

    private void getVideo() {
        FunctionConfig config = new FunctionConfig();
        config.setType(LocalMediaLoader.TYPE_VIDEO);
        config.setCompress(true);
        config.setMaxSelectNum(1);
        config.setSelectMode(MODE_MULTIPLE);
        config.setShowCamera(true);
        config.setEnablePreview(true);
        config.setPreviewVideo(true);
        config.setRecordVideoSecond(60 * 60);// 视频秒数
        config.setCompressFlag(1);
        config.setCheckNumMode(true);
        PictureConfig.init(config);
//                config.setRecordVideoDefinition(Constants.HIGH);
        // 启动相册并设置回调函数

        PictureConfig.getPictureConfig().openPhoto(RichEditorActivity.this, resultCallback);
    }

    /**
     * 设置item的单击监听
     *
     * @param i
     * @param curEContent
     * @param ivItemDelete
     * @param ivItemUp
     * @param ivItemDown
     */
    private void setItemListeners(int i, final EContent curEContent, final View item, ImageView ivItemDelete, ImageView ivItemUp, ImageView ivItemDown) {
        ivItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentList.remove(curEContent);
                showData();
            }
        });
        final int index = i;
        ivItemDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapDown(item, index);

            }
        });
        ivItemUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapUp(item, index);
            }
        });
    }

    /**
     * 向上的交换动画
     *
     * @param item
     * @param index
     */
    private void swapUp(View item, final int index) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(item, "TranslationY", 0, -translateDistance);
        animator.setDuration(300);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                if (animatedValue == -translateDistance) {
                    Collections.swap(contentList, index, index - 1);
                    showData();
                }
            }
        });
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(rvItemList.getChildAt(rvItemList.indexOfChild(item) - 2), "TranslationY", 0, translateDistance);
        animator1.setDuration(300);
        animator1.start();
    }

    /**
     * 向下的交换动画
     *
     * @param item
     * @param index
     */
    private void swapDown(View item, final int index) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(item, "TranslationY", 0, translateDistance);
        animator.setDuration(300);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                if (animatedValue == translateDistance) {
                    Collections.swap(contentList, index, index + 1);
                    showData();
                }
            }
        });
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(rvItemList.getChildAt(rvItemList.indexOfChild(item) + 2), "TranslationY", 0, -translateDistance);
        animator1.setDuration(300);
        animator1.start();
    }

    /**
     * 设置添加区域的单击监听
     *
     * @param i
     * @param addItem
     * @param ivAdd
     * @param llAddItemArea
     */
    private void setAddAreaListener(final int i, View addItem, ImageView ivAdd, final LinearLayout llAddItemArea) {
        ImageView ivTxt = (ImageView) addItem.findViewById(R.id.iv_additem_txt);
        ImageView ivImg = (ImageView) addItem.findViewById(R.id.iv_additem_img);
        ImageView ivVideo = (ImageView) addItem.findViewById(R.id.iv_additem_video);
        final int index = i;
        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EContent eContent = new EContent();
                eContent.setType(ItemType.IMG);
                contentList.add(index, eContent);
                showData();

            }
        });
        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EContent eContent = new EContent();
                eContent.setType(ItemType.VIDEO);
                contentList.add(index, eContent);
                showData();
            }
        });
        ivTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EContent eContent = new EContent();
                eContent.setType(ItemType.TXT);
                contentList.add(index, eContent);
                showData();
            }
        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                llAddItemArea.setVisibility(View.VISIBLE);
            }
        });
    }


    /**
     * 获取txt文本编辑器输入的内容
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002 && resultCode == 2002) {
            EContent eContent = (EContent) data.getSerializableExtra("eContent");
            int index = data.getIntExtra("index", 0);
            updateData(index, eContent);
            showData();//重新显示数据
        } else if (requestCode == REQUEST_CODE_CHOOSE_BG && resultCode == RESULT_OK) {
            bgPic = PicturePickerUtils.obtainResult(data);
            Glide.with(RichEditorActivity.this)
                    .load(bgPic.get(0))
                    .placeholder(R.mipmap.default_adv)
                    .error(R.mipmap.default_adv)
                    .into(ivBackgroudIMG);
        } else if (requestCode == REQUEST_CODE_CHOOSE_ITEM_IMG && resultCode == RESULT_OK) {
            List<Uri> itemImg = PicturePickerUtils.obtainResult(data);
            contentList.get(currentClickIndex).setUrl(itemImg.get(0) + "");
            showData();
        } else if (requestCode == REQUEST_CODE_SET_TITLE && resultCode == 3002) {
            title = data.getStringExtra("title");
            tvTitle.setText(title);
        } else if (requestCode == REQUEST_CODE_CHOOSE_IMGS && resultCode == RESULT_OK) {
            List<Uri> itemImg = PicturePickerUtils.obtainResult(data);
            if (itemImg == null || itemImg.size() == 0) {
                Toast.makeText(RichEditorActivity.this, "至少要选择一张图片", Toast.LENGTH_SHORT).show();
                Picker.from(this)
                        .count(10)
                        .enableCamera(true)
                        .setEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE_IMGS);
                return;
            }
            bgPic.add(itemImg.get(0));
            String imageAbsolutePath = ImageScaleUtils.getImageAbsolutePath(RichEditorActivity.this, bgPic.get(0));
            Bitmap bitmap = ImageScaleUtils.compressBySize(imageAbsolutePath, 640, 640);
            ivBackgroudIMG.setImageBitmap(bitmap);
            for (Uri uri : itemImg) {
                contentList.add(new EContent(uri + "", ItemType.IMG));
            }
            showData();
        }
    }

    /**
     * 修改数据
     *
     * @param index
     * @param eContent
     */
    private void updateData(int index, EContent eContent) {
        for (int i = 0; i < contentList.size(); i++) {//修改
            if (i == index) {
                contentList.remove(index);
                contentList.add(index, eContent);
                showData();
                break;
            }
        }
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }
}
