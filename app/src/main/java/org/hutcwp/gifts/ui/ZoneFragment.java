package org.hutcwp.gifts.ui;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.adapter.DynamicAdapter;
import org.hutcwp.gifts.databinding.FragmentZoneBinding;
import org.hutcwp.gifts.entity.bmob.Comment;
import org.hutcwp.gifts.entity.bmob.Dynamic;
import org.hutcwp.gifts.entity.bmob.User;
import org.hutcwp.gifts.other.SpacesItemDecoration;
import org.hutcwp.gifts.ui.base.BaseFragment;
import org.hutcwp.gifts.ui.publish.PublishActivity;
import org.hutcwp.gifts.view.SmartScrollView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


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


        binding.topTitle.setRightBtnVisibility(View.INVISIBLE);
        binding.topTitle.setLeftBtnVisibility(View.INVISIBLE);
        binding.topTitle.setTitle("动态列表");

        binding.ivPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), PublishActivity.class));

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

        binding.swipeRefreshLayout.setProgressViewOffset(true, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics()));


        //解决recyclerView的滑动不连贯的问题
        binding.rvDynamic.setNestedScrollingEnabled(false);

        binding.scrollView.requestDisallowInterceptTouchEvent(true);

        binding.scrollView.setScanScrollChangedListener(new SmartScrollView.ISmartScrollChangedListener() {
            @Override
            public void onScrolledToBottom() {

                loadMoreDynamic();
            }

            @Override
            public void onScrolledToTop() {
//                toast("滑动到顶了");
            }
        });

    }

    @Override
    protected void lazyFetchData() {

        adapter = new DynamicAdapter(dynamicList) {
            @Override
            public void comment(int position) {
                publishComment(position);
            }
        };
        binding.rvDynamic.setAdapter(adapter);
        binding.rvDynamic.addItemDecoration(new SpacesItemDecoration(getContext(), SpacesItemDecoration.VERTICAL_LIST));

        getDynamic();
    }


    /**
     * 发表评论
     */
    private void publishComment(int position) {

        Dynamic curDynamic = adapter.getDynamicList().get(position);

        Comment comment = new Comment();
        User user = BmobUser.getCurrentUser(User.class);
        comment.setUser(user);
        comment.setContent("pinglun");
        comment.setDynamic(curDynamic);
        comment.setAuthor("cwp");
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    toast("添加Comment成功，返回objectId为：" + objectId);
                } else {
                    toast("创建Comment失败：" + e.getMessage());
                }
            }
        });

        int count = curDynamic.getCommentCount() + 1;
        Dynamic newDynamic = new Dynamic();
        newDynamic.setCommentCount(count);
        newDynamic.update(curDynamic.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("test", "更新评论数量成功");
                } else {
                    Log.i("test", "更新评论数量失败：" + e.getMessage() + "," + e.getErrorCode());
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
        query.addWhereEqualTo("publisher", BmobUser.getCurrentUser());
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
                            toast("查询失败" + e.getMessage());
                        }
                    }
                });

    }


    /**
     * 获取评论
     */
    private void getComment() {

        BmobQuery<Comment> query = new BmobQuery<Comment>();

        query.setLimit(4).order("-createdAt").findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> object, BmobException e) {
                if (e == null) {
                    Log.e("test", "查询comment个数：" + object.size() + "comment:" + object.get(0).getContent());
                    if (object.size() != 0) {
//                        comment = object.get(0);
                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
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
                            toast("查询失败" + e.getMessage());
                        }
                    }
                });
        //当前页增加
        curPage++;
    }


}
