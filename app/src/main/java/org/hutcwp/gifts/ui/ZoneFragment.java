package org.hutcwp.gifts.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.adapter.DynamicAdapter;
import org.hutcwp.gifts.app.AppGlobal;
import org.hutcwp.gifts.databinding.FragmentZoneBinding;
import org.hutcwp.gifts.entity.bmob.Comment;
import org.hutcwp.gifts.entity.bmob.Dynamic;
import org.hutcwp.gifts.entity.bmob.User;
import org.hutcwp.gifts.other.SpacesItemDecoration;
import org.hutcwp.gifts.ui.base.BaseFragment;
import org.hutcwp.gifts.ui.publish.PublishActivity;
import org.hutcwp.gifts.view.SmartScrollView;
import org.hutcwp.gifts.weather.bean.WeatherBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ZoneFragment extends BaseFragment {


    private FragmentZoneBinding binding;

    private DynamicAdapter adapter;

    private List<Dynamic> dynamicList = new ArrayList<>();


    private int curPage = 0;
    private String TAG = "ZoneFragment";

    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    private static String BACK_IMG_URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509378001273&di=2dc9001aa2bb28116b9f05c7651ce130&imgtype=0&src=http%3A%2F%2Fcp.cw1.tw%2Ffiles%2Fmd5%2Fbe%2F8f%2Fbe8fbeed5d025bebb1ccdc2b9e702bc9-18426.jpg";


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
        binding.topTitle.setTitle("心情动态");
        binding.tvNotify.setText(AppGlobal.DailyNotify);

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
                toast("滑动到顶了");
            }
        });

    }

    @Override
    protected void lazyFetchData() {

        //初始化本地广播接收器
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.nyl.orderlybroadcast.AnotherBroadcastReceiver");
        localReceiver = new LocalReceiver();
        //注册本地接收器
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);


        adapter = new DynamicAdapter(dynamicList) {
            @Override
            public void comment(int position) {
//                publishComment(position);
            }
        };
        binding.rvDynamic.setAdapter(adapter);
        binding.rvDynamic.addItemDecoration(new SpacesItemDecoration(getContext(), SpacesItemDecoration.VERTICAL_LIST));

        getDynamic();
        getWeather();
        getIvBack(BACK_IMG_URL);

    }

    /**
     * 获取背景图片
     */
    private void getIvBack(String url) {
        Glide.with(getContext())
                .load(url)
                .centerCrop()
                .into(binding.ivBack);
    }

    /**
     * 获取天气信息
     */
    private void getWeather() {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url(AppGlobal.WEATHER_CHENZHOU)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String weatherInfo = response.body().string();
                WeatherBean weather = new Gson().fromJson(weatherInfo, WeatherBean.class);

                Log.d("test", "response:" + weatherInfo);
                Log.d("test", "now==null?" + (weather.getHeWeather().get(0).getNow() == null));

                if (weather.getHeWeather().get(0).getNow() != null) {
                    final String tmp = weather.getHeWeather().get(0).getNow().getTmp() + "℃";//气温
                    final String cond = weather.getHeWeather().get(0).getNow().getCond().getTxt();//天气状况
                    final String city = weather.getHeWeather().get(0).getBasic().getCity();//天气状况
                    ZoneFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.tvWeaTmp.setText(tmp);
                            binding.tvWeaCond.setText(cond);
                            binding.tvWeaCity.setText(city);
                        }
                    });

                }
            }

        });


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
                    Log.i(TAG, "更新评论数量成功");
                } else {
                    Log.i(TAG, "更新评论数量失败：" + e.getMessage() + "," + e.getErrorCode());
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
                                Log.d(TAG, "dy:" + dynamic.getPublisher());

                            }
                            adapter.setNewData(results);
                            toast("刷新成功");

                            //清除原来的数据
                            adapter.clearData();
                            for (final Dynamic dynamic : results) {
                                Log.d("test", "dy:" + dynamic.getPublisher());
                                BmobQuery<User> userBmobQuery = new BmobQuery<>();
                                userBmobQuery.getObject(dynamic.getPublisher().getObjectId(), new QueryListener<User>() {
                                    @Override
                                    public void done(User user, BmobException e) {
                                        Log.d("test","done");
                                        if (e == null) {
                                            dynamic.setPublisher(user);
                                            adapter.addData(dynamic);
                                            Log.d("test","添加成功！");
                                        } else {
                                            Log.d("test", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                            Toast.makeText(getContext(), "查询失败！", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }


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
                    Log.e(TAG, "查询comment个数：" + object.size() + "comment:" + object.get(0).getContent());
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
                    public void done(final List<Dynamic> results, BmobException e) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                        if (e == null) {
                            for (  final Dynamic dynamic : results) {
                                Log.d("test", "dy:" + dynamic.getPublisher());
                                BmobQuery<User> userBmobQuery = new BmobQuery<>();
                                userBmobQuery.getObject(dynamic.getPublisher().getObjectId(), new QueryListener<User>() {
                                    @Override
                                    public void done(User user, BmobException e) {
                                        Log.d("test", "done");
                                        if (e == null) {
                                            dynamic.setPublisher(user);
                                            adapter.addData(dynamic);
                                            Log.d("test", "添加成功！");
                                        } else {
                                            Log.d("test", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                            Toast.makeText(getContext(), "查询失败！", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                });
                            }
                        } else {
                            // ...
//                            toast("查询失败" + e.getMessage());
                            Log.d(TAG, e.getMessage());
                            if (e.getErrorCode() == 9016) {
                                toast("网络异常，请检查网络了连接！");
                            }
                            if (e.getMessage().equals("Qps beyond the limit: 10,12,218.75.197.116")) {
                                toast("没有更多的内容了！");
                            }
                        }
                    }
                });
        //当前页增加
        curPage++;
    }


    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "更新最新动态", Toast.LENGTH_SHORT).show();
            getDynamic();
        }
    }

}