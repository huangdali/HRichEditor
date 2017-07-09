package com.huangdali.bean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hdl.hricheditorview.R;
import com.huangdali.utils.ItemTouchHelperAdapter;
import com.huangdali.view.TXTEditorActivity;

import java.util.Collections;
import java.util.List;

import io.valuesfeng.picker.Picker;
import io.valuesfeng.picker.engine.GlideEngine;


/**
 * item适配器
 * Created by HDL on 2017/3/14.
 */

public class RichEditorAdapter extends RecyclerView.Adapter<RichEditorAdapter.MyViewHolder> implements ItemTouchHelperAdapter {
    private List<EContent> datas;
    private Activity context;
    private static final int REQUEST_CODE_CHOOSE_ITEM_IMG = 1002;//更改item图片
    private static final int REQUEST_CODE_EDIT_TXT = 1005;//编辑文本
    private int curClickItemIndex = 0;//当前点击的item
    private OnDownUpChangeListener onDownUpChangeListener;
    private OnChoiseVideoListener onChoiseVideoListener;
    private OnItemClickListener onItemClickListener;


    public RichEditorAdapter(Activity context, List<EContent> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final EContent eContent = datas.get(position);
        /**
         * 隐藏第一个item的上箭头和最后一个item的下箭头
         */
        if (position == 0) {
            holder.ivUp.setVisibility(View.GONE);
        } else if (position == datas.size() - 1) {
            holder.ivDown.setVisibility(View.GONE);
        }
        //设置内容
        holder.tvDesc.setText(TextUtils.isEmpty(eContent.getContent()) ? context.getString(R.string.rich_click_add_txt): eContent.getContent());
        /**
         * 根据类型显示item的图片
         */
        switch (eContent.getType()) {
            case ItemType.IMG:
                if (TextUtils.isEmpty(eContent.getUrl())) {
                    holder.ivPic.setImageResource(R.mipmap.img);
                } else {
                    Glide.with(context)
                            .load(eContent.getUrl())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.mipmap.img)
                            .error(R.mipmap.img)
                            .into(holder.ivPic);
                }
                break;
            case ItemType.TXT:
                holder.ivPic.setImageResource(R.mipmap.txt_item);
                break;
            case ItemType.VIDEO:
                holder.ivPic.setImageResource(R.mipmap.video_item);
                break;
        }
        /**
         * 选择item图片
         */
        holder.ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eContent.getType().equals(ItemType.IMG)) {
                    curClickItemIndex = position;
                    toChoiseItemPic();
                } else if (eContent.getType().equals(ItemType.VIDEO)) {
                    curClickItemIndex = position;
                    toChoiseItemVideo();
                }

            }
        });
        /**
         * 编辑文本
         */
        holder.tvDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curClickItemIndex = position;
                toTxtEditorPage(position);
            }
        });
        /**
         * 添加item监听
         */
        holder.ivAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemArea(holder);
            }
        });
        /**
         * 设置添加图片、文本、视频的监听
         */
        holder.ivAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAddArea(holder);
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(ItemType.IMG, position);
                }
            }
        });
        holder.ivAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAddArea(holder);
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(ItemType.VIDEO, position);
                }
            }
        });
        holder.ivAddTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAddArea(holder);
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(ItemType.TXT, position);
                }
            }
        });
        /**
         * 设置向下向上箭头、删除的单击事件监听
         */
        holder.ivDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onDownUpChangeListener.onDown(v, position);

            }
        });
        holder.ivUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDownUpChangeListener.onUp(v, position);
            }
        });
        holder.ivDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDownUpChangeListener.onDrop(v, position);
            }
        });
    }

    /**
     * 显示增加图片、文字、视频区域，隐藏添加按钮
     *
     * @param holder
     */
    private void showAddItemArea(MyViewHolder holder) {
        holder.ivAddItem.setVisibility(View.GONE);
        holder.rvAddItemArea.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏增加图片、文字、视频区域，显示添加按钮
     *
     * @param holder
     */
    private void hideAddArea(MyViewHolder holder) {
        holder.rvAddItemArea.setVisibility(View.GONE);
        holder.ivAddItem.setVisibility(View.VISIBLE);
    }

    /**
     * 跳转到文本编辑页面
     *
     * @param index
     */
    private void toTxtEditorPage(int index) {
        Intent intent = new Intent(context, TXTEditorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("eContent", datas.get(index));
        intent.putExtras(bundle);
        context.startActivityForResult(intent, REQUEST_CODE_EDIT_TXT);
    }

    /**
     * 更换item的图片
     */
    private void toChoiseItemPic() {
        Picker.from(context)
                .count(1)
                .enableCamera(true)
                .setEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE_ITEM_IMG);
    }

    /**
     * 获取item的高度
     *
     * @return
     */
    public int getItemHight(LinearLayoutManager linearLayoutManager) {
        return linearLayoutManager.getChildAt(0).getMeasuredHeight();
    }

    /**
     * 更换item的视频
     */
    private void toChoiseItemVideo() {
        onChoiseVideoListener.onStart();
    }


    /**
     * 设置向上向下箭头的监听事件
     *
     * @param onDownUpChangeListener
     */
    public void setOnDownUpChangeListener(OnDownUpChangeListener onDownUpChangeListener) {
        this.onDownUpChangeListener = onDownUpChangeListener;
    }

    /**
     * 设置item的单击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置选择视频的监听
     *
     * @param onChoiseVideoListener
     */
    public void setOnChoiseVideoListener(OnChoiseVideoListener onChoiseVideoListener) {
        this.onChoiseVideoListener = onChoiseVideoListener;
    }

    @Override
    public void onItemMove(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        int fromPosition = source.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (fromPosition < datas.size() && toPosition < datas.size()) {
            //交换数据位置
            Collections.swap(datas, fromPosition, toPosition);
            //刷新位置交换
            notifyItemMoved(fromPosition, toPosition);
        }
        //移动过程中移除view的放大效果
        onItemClear(source);
    }

    @Override
    public void onItemDissmiss(RecyclerView.ViewHolder source) {
    }

    @Override
    public void onItemSelect(RecyclerView.ViewHolder viewHolder) {
        //当拖拽选中时放大选中的view
        viewHolder.itemView.setScaleX(1.2f);
        viewHolder.itemView.setScaleY(1.2f);
    }

    @Override
    public void onItemClear(RecyclerView.ViewHolder viewHolder) {
        //拖拽结束后恢复view的状态
        viewHolder.itemView.setScaleX(1.0f);
        viewHolder.itemView.setScaleY(1.0f);

    }

    /**
     * 向上向下监听器对象
     */
    public interface OnDownUpChangeListener {
        void onDown(View view, int postion);

        void onUp(View view, int postion);

        void onDrop(View view, int postion);
    }

    /**
     * 选择视频
     */
    public interface OnChoiseVideoListener {
        void onStart();
    }

    public interface OnItemClickListener {
        void onClick(String itemType, int index);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 获取当前单击的item的下标
     *
     * @return
     */
    public int getCurClickItemIndex() {
        return curClickItemIndex;
    }

    /**
     * 创建viewholder类
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic, ivUp, ivDown, ivDrop, ivAddItem, ivAddTxt, ivAddImg, ivAddVideo;
        TextView tvDesc;
        RelativeLayout rvItem;
        LinearLayout rvAddItemArea;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_item_pic);
            rvItem = (RelativeLayout) itemView.findViewById(R.id.rl_item);
            ivAddItem = (ImageView) itemView.findViewById(R.id.iv_additem_add);
            ivAddTxt = (ImageView) itemView.findViewById(R.id.iv_additem_txt);
            ivAddImg = (ImageView) itemView.findViewById(R.id.iv_additem_img);
            ivAddVideo = (ImageView) itemView.findViewById(R.id.iv_additem_video);
            ivUp = (ImageView) itemView.findViewById(R.id.iv_item_up);
            ivDown = (ImageView) itemView.findViewById(R.id.iv_item_down);
            ivDrop = (ImageView) itemView.findViewById(R.id.iv_item_delete);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_item_desc);
            rvAddItemArea = (LinearLayout) itemView.findViewById(R.id.ll_additem_addarea);
        }
    }
}
