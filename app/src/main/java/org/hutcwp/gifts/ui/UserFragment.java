package org.hutcwp.gifts.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.app.AppGlobal;
import org.hutcwp.gifts.databinding.FragmentUserBinding;
import org.hutcwp.gifts.ui.base.BaseFragment;
import org.hutcwp.gifts.utils.Utils;


public class UserFragment extends BaseFragment {

    private FragmentUserBinding binding;

    private String TAG = "UserFragment";


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initViews() {

        binding = (FragmentUserBinding) getBinding();

    }

    @Override
    protected void initSetting() {

        binding.lyTag.tagOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callPhone();
            }
        });

        binding.lyTag.tagTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startIm();
            }
        });

        binding.lyTag.tagThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserFragment.this.getActivity(), AlbumActivity.class);
                startActivity(intent);
            }
        });

        binding.lyTag.tagFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "对方接受了你的撩，并向你抛了一个媚眼！", Toast.LENGTH_SHORT).show();
            }
        });

        //隐藏左边
        binding.lyTitle.setLeftBtnVisibility(View.INVISIBLE);
        binding.lyTitle.setTitle("个人中心");
        binding.lyTitle.setRightBtnVisibility(View.INVISIBLE);
        binding.tvSignature.setText(AppGlobal.SIGNATURE);

        //获取头像
        Utils.getUserImgWithGlide(getContext(), binding.ivUserPhoto);

    }


    /**
     * 开始QQ聊天
     */
    private void startIm() {

        String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + AppGlobal.QQMyself;
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    /**
     * 打电话
     */
    private void callPhone() {

        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + AppGlobal.PHONE_NUMBER);
        intent.setData(data);
        startActivity(intent);
    }


    @Override
    protected void lazyFetchData() {

    }


}
