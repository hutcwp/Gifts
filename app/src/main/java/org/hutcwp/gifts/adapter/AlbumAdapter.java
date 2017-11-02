package org.hutcwp.gifts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.hutcwp.gifts.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hutcwp on 2017/11/2.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.GirlViewHolder> {

    private List<String> imgsList = new ArrayList<>();

    private Context mContext;


    public AlbumAdapter(Context context, List<String> girlList) {

        mContext = context;
        this.imgsList = girlList;

    }

    /**
     * 添加数据
     *
     * @param datas 新增的数据
     */
    public void addDatas(List<String> datas) {

        imgsList.addAll(datas);

        notifyItemChanged(getItemCount());
    }

    /**
     * 设置新内容
     *
     * @param data 新内容
     */
    public void setNewData(List<String> data) {
        this.imgsList = data;
        notifyDataSetChanged();
    }


    @Override
    public GirlViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_photo, parent,
                false);//这个布局就是一个imageview用来显示图片
        GirlViewHolder holder = new GirlViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(GirlViewHolder holder, final int position) {

        ViewGroup.LayoutParams params = holder.iv.getLayoutParams();
        params.width = 520;
        params.height = (new Random().nextInt(100) + 600);
        holder.iv.setLayoutParams(params);

//        holder.name.setText(imgsList.get(position).getName());
//        holder.date.setText(imgsList.get(position).getDate());

//        holder.iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = PicDetailActivity.newIntent(mContext, imgsList.get(position).getUrl(), "");
//                mContext.startActivity(intent);
//            }
//        });

        //使用params,width 和params.heght 去加载图片
        Glide.with(mContext)
                .load(imgsList.get(position))
                .override(params.width, params.height) //设置加载尺寸
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((holder).iv);//加载网络图片

        Log.d("AlbumAdapter", "onBindViewHolder: "+"加载图片 "+position);

    }


    @Override
    public int getItemCount() {
        return imgsList == null ? 0 : imgsList.size();
    }


    //自定义ViewHolder，用于加载图片
    class GirlViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView name;
        TextView date;

        GirlViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.img);
//            name = (TextView) view.findViewById(R.id.name);
//            date = (TextView) view.findViewById(R.id.date);
        }
    }


}