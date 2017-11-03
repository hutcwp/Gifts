package org.hutcwp.gifts.ui.publish;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.databinding.ActivityPublishBinding;
import org.hutcwp.gifts.entity.bmob.Dynamic;
import org.hutcwp.gifts.entity.bmob.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class PublishActivity extends AppCompatActivity {

    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final int REQUEST_ALBUM = 2;
    private static final String TAG = "PublishActivity";

    private ActivityPublishBinding binding;
    private final int Max = 4;
    //最大为4
    private List<String> imgUrlList = new ArrayList<>();

    private LocalBroadcastManager localBroadcastManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_publish);

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("发布动态");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                binding.progressBar.setVisibility(View.VISIBLE);
                if (binding.lyContent.etContent.getText().toString().trim().length() == 0) {
                    toast("内容不能为空！");
                } else {
                    if (imgUrlList.size() > 0) {
                        uploadImgs(imgUrlList);
                    } else {
                        publishDynamic(null);
                    }
                }
            }
        });


        binding.lyContent.ivAddImg.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                selectAlbum();
            }
        });

    }


    /**
     * 上传图片
     */
    private void uploadImgs(final List<String> imgList) {

        String[] files = new String[imgList.size()];
        for (int i = 0; i < files.length; i++) {

            files[i] = imgList.get(i);
        }

        BmobFile.uploadBatch(files, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                if (urls.size() == files.size()) {//如果数量相等，则代表文件全部上传完成
                    //do something
                    toast("图片上传成功");
                    publishDynamic(files);
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                toast("图片上传失败");
                toast("错误码" + statuscode + ",错误描述：" + errormsg);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
            }
        });
    }


    /**
     * 发布动态
     */

    private void publishDynamic(final List<BmobFile> list) {

        Bmob.getServerTime(new QueryListener<Long>() {

            @Override
            public void done(Long time, BmobException e) {

                binding.progressBar.setVisibility(View.INVISIBLE);

                String publishTime = "";
                if (e == null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    publishTime = formatter.format(new Date(time * 1000L));
                    Log.i("bmob", "当前服务器时间为:" + publishTime);

                } else {
                    Log.i("bmob", "获取服务器时间失败:" + e.getMessage());

                    DateFormat df = new SimpleDateFormat("HH:mm:ss");
                    publishTime = df.format(new Date());
                }

                Dynamic dynamic = new Dynamic();
                User user = BmobUser.getCurrentUser(User.class);
                dynamic.setPublisher(user);
                dynamic.setPublishTime(publishTime);
                dynamic.setContent(binding.lyContent.etContent.getText().toString().trim());
                dynamic.setCommentCount(0);
                //图片不为0
                if (list != null) {
                    dynamic.setImgs(list);
                }

                dynamic.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            toast("发布动态成功！");
//                            toast("添加Dynamic成功，返回objectId为：" + objectId);
                            PublishActivity.this.finish();

                            Intent intent = new Intent("com.nyl.orderlybroadcast.AnotherBroadcastReceiver");
                            //发送本地广播
                            localBroadcastManager = LocalBroadcastManager.getInstance(PublishActivity.this);
                            localBroadcastManager.sendBroadcast(intent);
                        } else {
                            toast("发布动态失败！");
//                            toast("创建Dynamic失败：" + e.getMessage());
                            Log.d(TAG, "msg:" + e.getMessage());
                        }
                    }
                });
            }

        });


    }


    /**
     * 从系统中选择图片
     */
    private void selectAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        startActivityForResult(albumIntent, REQUEST_ALBUM);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_ALBUM == requestCode) {
            String imgPath = handleImage(data);

            imgUrlList.add(imgPath);
            ImageView imageView = new ImageView(PublishActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);

            imageView.setLayoutParams(params);

            //使用params,width 和params.heght 去加载图片
            Glide.with(PublishActivity.this)
                    .load(data.getData())
                    .override(240, 240)
                    .placeholder(R.drawable.app_logo)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);//加载网络图片

            binding.lyContent.lyImgs.addView(imageView);
        }

    }


    /***
     * 将图片uri转换为地址
     * @param data
     * @return 地址
     */
    private String handleImage(Intent data) {

        Uri uri = data.getData();
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    //弹出toast
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}
