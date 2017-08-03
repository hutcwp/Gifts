package org.hutcwp.gifts.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.entity.bmob.Dynamic;
import org.hutcwp.gifts.entity.bmob.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hutcwp on 2017/7/30.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public abstract class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.DynimacViewHolder> {

    private List<Dynamic> dynamicList = new ArrayList<>();

    public DynamicAdapter(List<Dynamic> dynamicList) {
        this.dynamicList = dynamicList;
    }

    @Override
    public DynimacViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_dynamic, parent, false);
        return new DynimacViewHolder(view);
    }

    public List<Dynamic> getDynamicList() {
        return dynamicList;
    }

    @Override
    public void onBindViewHolder(DynimacViewHolder holder, final int position) {

        Dynamic dynamic = dynamicList.get(position);
        User user = dynamic.getPublisher();
        holder.tvUserName.setText(user.getUsername());
        holder.tvPublishTime.setText(dynamic.getPublishTime());
        holder.tvCommets.setText(dynamic.getCommentCount()+"");
        holder.tvContent.setText(dynamic.getContent());

        holder.ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comment(position);
            }
        });
    }

    public abstract void comment(int position);

    @Override
    public int getItemCount() {
        return dynamicList.size();
    }

    /**
     * 添加数据
     *
     * @param newData 新增的数据
     */
    public void addDatas(List<Dynamic> newData) {

        dynamicList.addAll(newData);
        notifyItemChanged(getItemCount());
    }

    /**
     * 设置新内容
     *
     * @param newData 新内容
     */
    public void setNewData(List<Dynamic> newData) {
        this.dynamicList = newData;
        notifyDataSetChanged();
    }


    class DynimacViewHolder extends RecyclerView.ViewHolder {

        ImageView ivUserPhoto;
        ImageView ivFavour;
        ImageView ivComment;
        TextView tvUserName;
        TextView tvPublishTime;
        TextView tvCommets;
        TextView tvContent;

        public DynimacViewHolder(View itemView) {
            super(itemView);

            ivUserPhoto = (ImageView) itemView.findViewById(R.id.iv_user_photo);
            ivFavour = (ImageView) itemView.findViewById(R.id.iv_favour);
            ivComment = (ImageView) itemView.findViewById(R.id.iv_comment);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_username);
            tvPublishTime = (TextView) itemView.findViewById(R.id.tv_publish_time);
            tvCommets = (TextView) itemView.findViewById(R.id.tv_comments);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }


    }

}
