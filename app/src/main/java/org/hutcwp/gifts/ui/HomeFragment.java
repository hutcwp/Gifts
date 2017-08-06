package org.hutcwp.gifts.ui;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.databinding.FragmentHomeBinding;
import org.hutcwp.gifts.ui.base.BaseFragment;
import org.hutcwp.gifts.utils.WebUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


public class HomeFragment extends BaseFragment {


    final String  url_movie = "http://m.iqiyi.com/dianying/";
    final String  url_music = "http://music.163.com/m/";
    final String  url_essay = "http://www.jianshu.com/";
    final String  url_weibo = "http://ent.sina.com.cn/";


    private String[] imageUrls = {
            "http://7xp1a1.com1.z0.glb.clouddn.com/liyu01.png",
            "http://7xp1a1.com1.z0.glb.clouddn.com/liyu02.png",
            "http://7xp1a1.com1.z0.glb.clouddn.com/liyu03.png",
            "http://7xp1a1.com1.z0.glb.clouddn.com/liyu04.png",
            "http://7xp1a1.com1.z0.glb.clouddn.com/liyu05.png"};

    Context mContetxt ;


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
                WebUtils.openInternal(mContetxt,url_music);
            }
        });

        binding.itemEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebUtils.openInternal(mContetxt,url_essay);
            }
        });

        binding.itemMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebUtils.openInternal(mContetxt,url_movie);
            }
        });

        binding.itemWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebUtils.openInternal(mContetxt,url_weibo);
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
        Glide.with(this).load(imageUrls[new Random().nextInt(5)]).into(
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
}
