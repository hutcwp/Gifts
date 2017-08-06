package org.hutcwp.gifts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.hutcwp.gifts.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by hutcwp on 2017/8/3.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;

    private List<BmobFile> imgUrls = new ArrayList<>();

    public ImageAdapter(List<BmobFile> imgUrls) {
        if (imgUrls != null) {
            this.imgUrls = imgUrls;
        }
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_imgs, null, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        //使用params,width 和params.heght 去加载图片
        Glide.with(mContext)
                .load(imgUrls.get(position).getUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivImg);//加载网络图片

    }

    @Override
    public int getItemCount() {
        return imgUrls.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImg;

        public ImageViewHolder(View itemView) {
            super(itemView);

            ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
