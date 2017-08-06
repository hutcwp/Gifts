package org.hutcwp.gifts.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.databinding.FragmentUserBinding;
import org.hutcwp.gifts.ui.base.BaseFragment;


public class UserFragment extends BaseFragment {

    FragmentUserBinding binding;


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

        //隐藏左边
        binding.lyTitle.setBtnLeftVisibility(View.INVISIBLE);
        binding.lyTitle.setTitle("个人中心");

    }


    private void startIm() {

        String url="mqqwpa://im/chat?chat_type=wpa&uin=1146751867";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

    }

    private void callPhone() {

        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "18373372430");
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    protected void lazyFetchData() {

    }


}
