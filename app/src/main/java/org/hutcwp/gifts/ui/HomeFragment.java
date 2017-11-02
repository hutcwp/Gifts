package org.hutcwp.gifts.ui;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.app.AppGlobal;
import org.hutcwp.gifts.databinding.FragmentHomeBinding;
import org.hutcwp.gifts.entity.bmob.Common;
import org.hutcwp.gifts.ui.base.BaseFragment;
import org.hutcwp.gifts.utils.WebUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


public class HomeFragment extends BaseFragment {


    final String url_movie = "http://m.iqiyi.com/dianying/";
    final String url_music = "http://music.163.com/m/";
    final String url_essay = "http://www.jianshu.com/";
    final String url_weibo = "http://ent.sina.com.cn/";


    Context mContetxt;


    FragmentHomeBinding binding;
    private Subscription subscription;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {

        binding = (FragmentHomeBinding) getBinding();

        mContetxt = getContext();

    }

    /**
     * 初始化功能区点击事件
     */
    @Override
    protected void initSetting() {

        binding.topTitle.setLeftBtnVisibility(View.INVISIBLE);
        binding.topTitle.setRightBtnVisibility(View.INVISIBLE);
        binding.topTitle.setTitle("推荐主页");

        binding.imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(mContetxt);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }
        });
        binding.imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(mContetxt,
                R.anim.zoom_in));
        binding.imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(mContetxt,
                R.anim.zoom_out));

        binding.itemMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WebUtils.openInternal(mContetxt, url_music, ContextCompat.getColor(getContext(), R.color.music));
            }
        });

        binding.itemEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebUtils.openInternal(mContetxt, url_essay, ContextCompat.getColor(getContext(), R.color.essay));
            }
        });

        binding.itemMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebUtils.openInternal(mContetxt, url_movie, ContextCompat.getColor(getContext(), R.color.movie));
            }
        });

        binding.itemWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebUtils.openInternal(mContetxt, url_weibo, ContextCompat.getColor(getContext(), R.color.blog));
            }
        });


    }

    @Override
    protected void lazyFetchData() {

        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {

        //初始化一次动态属性
        queryCommon();

        binding.imageSwitcher.post(new Runnable() {
            @Override
            public void run() {
                loadImage();
            }
        });
        subscription = Observable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        loadImage();
                    }
                });

    }


    /**
     * 加载图片
     */
    private void loadImage() {
        Glide.with(this).load(AppGlobal.IMGS_SPANNER[new Random().nextInt(5)]).into(
                new SimpleTarget<GlideDrawable>(binding.imageSwitcher.getWidth(), binding.imageSwitcher.getHeight()) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        binding.imageSwitcher.setImageDrawable(resource);
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!subscription.isUnsubscribed())
            subscription.unsubscribe();
    }


    /**
     * 获取轮播图的图片地址
     */
    private void queryCommon() {

        BmobQuery<Common> query = new BmobQuery<>();
        query.getObject("62c7db275d", new QueryListener<Common>() {

            @Override
            public void done(Common object, BmobException e) {
                if (e == null) {
                    object.getObjectId();
                    //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                    object.getCreatedAt();

                    String[] newImgs = object.getSpannerImg();
                    String mQQ = object.getQQNumber();
                    String phoneNumber = object.getPhoneNumber();
                    String signature = object.getSignature();
                    String dailyNotify = object.getDailyNotify();

                    if (!isNULL(newImgs)) {
                        AppGlobal.IMGS_SPANNER = newImgs;
                    }
                    if (!isNULL(mQQ)) {
                        AppGlobal.QQMyself = mQQ;
                    }
                    if (!isNULL(phoneNumber)) {
                        AppGlobal.PHONE_NUMBER = phoneNumber;
                    }
                    if (!isNULL(signature)) {
                        AppGlobal.SIGNATURE = signature;
                    }
                    if (!isNULL(dailyNotify)) {
                        AppGlobal.DailyNotify = dailyNotify;
                    }

                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }

            }
        });

    }


    //判空
    private boolean isNULL(Object obj) {

        if (obj == null) {
            return true;
        }
        return false;
    }


}
