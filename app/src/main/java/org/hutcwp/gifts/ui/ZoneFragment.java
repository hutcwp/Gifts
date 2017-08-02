package org.hutcwp.gifts.ui;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.adapter.DynamicAdapter;
import org.hutcwp.gifts.databinding.FragmentZoneBinding;
import org.hutcwp.gifts.entity.Dynamic;
import org.hutcwp.gifts.other.SpacesItemDecoration;
import org.hutcwp.gifts.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class ZoneFragment extends BaseFragment {


    FragmentZoneBinding binding;

    DynamicAdapter adapter;

    List<Dynamic> dynamicList = new ArrayList<>();



    int curPage = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zone;
    }

    @Override
    protected void initViews() {

        binding = (FragmentZoneBinding) getBinding();

    }

    @Override
    protected void initSetting() {

        binding.ivPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                publishDynamic();
            }
        });

        binding.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        //
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDynamic();

            }
        });

        binding.rvDynamic.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (!binding.rvDynamic.canScrollVertically(1)) {
                    loadMoreDynamic();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    @Override
    protected void lazyFetchData() {

//        Dynamic dynamic = new Dynamic();
//        dynamic.setPublisher("hutcwp");
//        dynamic.setPublishTime("今天 19:35");
//        dynamic.setComments("hutcwp:评论信息");
//        dynamic.setContent("矢量图免费下载电商素材,平面素材,APP/UI素材,H5素材.海报背景Banner素材专注png免抠素材帮助2000万设计师提升10倍工作效率,快速设计出精品作品升职加薪奥!");
//
//        dynamicList.add(dynamic);
//        dynamic.setPublisher("大户为名");
//        dynamicList.add(dynamic);

        adapter = new DynamicAdapter(dynamicList);
        binding.rvDynamic.setAdapter(adapter);
        binding.rvDynamic.addItemDecoration(new SpacesItemDecoration(getContext(), SpacesItemDecoration.VERTICAL_LIST));

    }


    /**
     * 发布动态
     */
    private void publishDynamic() {

        Dynamic newDynamic = new Dynamic();
        newDynamic.setPublisher("lucky");
        newDynamic.setContent("北京海淀");
        newDynamic.setPublishTime("上午 14:25");
        newDynamic.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    toast("添加数据成功，返回objectId为：" + objectId);
                } else {
                    toast("创建数据失败：" + e.getMessage());
                }
            }
        });
    }


    /**
     * 重新获取动态
     */
    private void getDynamic() {
        //还原，刷新数据
        curPage = 0;
        BmobQuery<Dynamic> query = new BmobQuery<>();
        query.setLimit(4).setSkip(curPage).order("-createdAt")
                .findObjects(new FindListener<Dynamic>() {
                    @Override
                    public void done(List<Dynamic> results, BmobException e) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                        if (e == null) {
                            for (Dynamic dynamic : results) {
                                Log.d("test", "dy:" + dynamic.getPublisher());
                            }
                            adapter.setNewData(results);
                        } else {
                            // ...
                            toast("查询失败"+e.getMessage());
                        }
                    }
                });

    }

    /**
     * 加载更多的动态
     */

    private void loadMoreDynamic() {

        BmobQuery<Dynamic> query = new BmobQuery<>();
        query.setLimit(4).setSkip(adapter.getItemCount()).order("-createdAt")
                .findObjects(new FindListener<Dynamic>() {
                    @Override
                    public void done(List<Dynamic> results, BmobException e) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                        if (e == null) {
                            for (Dynamic dynamic : results) {
                                Log.d("test", "dy:" + dynamic.getPublisher());
                            }
                            adapter.addDatas(results);
                        } else {
                            // ...
                            toast("查询失败"+e.getMessage());
                        }
                    }
                });
        //当前页增加
        curPage++;
    }


    /**
     * 更新动态
     */
    private void updateDynamic() {

    }


}
