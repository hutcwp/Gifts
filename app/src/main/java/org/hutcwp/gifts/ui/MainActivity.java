package org.hutcwp.gifts.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.app.AppGlobal;
import org.hutcwp.gifts.databinding.ActivityMainBinding;
import org.hutcwp.gifts.ui.base.BaseActivity;
import org.hutcwp.gifts.view.BottomTiltle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends BaseActivity {


    private ActivityMainBinding binding;

    String TAG = "tag";

    private String currentFragmentTag;

    private FragmentManager fragmentManager;

    private static final String FRAGMENT_TAG_ZONE = "zone";
    private static final String FRAGMENT_TAG_HOME = "tab_home";
    private static final String FRAGMENT_TAG_USER = "user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        binding.lyTopTitle.setBtnLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "onclik", Toast.LENGTH_LONG).show();
            }
        });
        binding.lyTopTitle.serBtnRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "onclik", Toast.LENGTH_LONG).show();
            }
        });
        binding.lyTopTitle.setTitle("我的主页");

        binding.lyTopTitle.setVisibility(View.GONE);

        setPageChange();

        fragmentManager = getSupportFragmentManager();

        initFragment(savedInstanceState);

        checkVersion();
    }

    /**
     * 设置Page改变
     */
    private void setPageChange() {

        BottomTiltle bottomTiltle = (BottomTiltle) findViewById(R.id.ly_bottom);
        bottomTiltle.setItemClickListener(new BottomTiltle.OnItemClickListener() {
            @Override
            public void leftItemClick() {
                switchContent(FRAGMENT_TAG_ZONE);
            }

            @Override
            public void rightItemClick() {
                switchContent(FRAGMENT_TAG_USER);
            }

            @Override
            public void middleItemClick() {
                switchContent(FRAGMENT_TAG_HOME);
            }
        });

    }


    /**
     * 初始化Fragment
     *
     * @param savedInstanceState
     */
    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            switchContent(FRAGMENT_TAG_HOME);
        } else {
            currentFragmentTag = savedInstanceState.getString(AppGlobal.CURRENT_INDEX);
            switchContent(currentFragmentTag);
        }
    }

    /**
     * 重写的方法
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(AppGlobal.CURRENT_INDEX, currentFragmentTag);
    }

    /**
     * 选择当前的Fragment
     *
     * @param name Fragment的名字
     */
    public void switchContent(String name) {

        Log.d("error", "switchContent");
        if (currentFragmentTag != null && currentFragmentTag.equals(name))
            return;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        Fragment currentFragment = fragmentManager.findFragmentByTag(currentFragmentTag);
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }
        Fragment foundFragment = fragmentManager.findFragmentByTag(name);
        if (foundFragment == null) {
            switch (name) {
                case FRAGMENT_TAG_HOME:
                    foundFragment = new HomeFragment();
                    break;
                case FRAGMENT_TAG_ZONE:
                    foundFragment = new ZoneFragment();
                    break;
                case FRAGMENT_TAG_USER:
                    foundFragment = new UserFragment();
                    break;
            }
        }
        if (foundFragment == null) {

        } else if (foundFragment.isAdded()) {
            ft.show(foundFragment);
        } else {
            ft.add(R.id.content, foundFragment, name);
        }
        ft.commit();
        currentFragmentTag = name;
    }


    //检测本程序的版本，这里假设从服务器中获取到最新的版本号为3
    public void checkVersion() {
        //如果检测本程序的版本号小于服务器的版本号，那么提示用户更新
//        if (getVersionCode() < 3) {
//            showDialogUpdate();//弹出提示版本更新的对话框
//
//        }else{
//            //否则吐司，说现在是最新的版本
//            Toast.makeText(this,"当前已经是最新的版本",Toast.LENGTH_SHORT).show();
//
//        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else{
            showDialogUpdate();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            showDialogUpdate();
        }else{
            Log.d("getFileFromServer","没有写入内存大的权限");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 提示版本更新的对话框
     */
    private void showDialogUpdate() {
        // 这里的属性可以一直设置，因为每次设置后返回的是一个builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置提示框的标题
        builder.setTitle("版本升级").
                // 设置提示框的图标
                        setIcon(R.mipmap.ic_launcher).
                // 设置要显示的信息
                        setMessage("发现新版本！请及时更新").
                // 设置确定按钮
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "选择确定哦", 0).show();
                        loadNewVersionProgress();//下载最新的版本程序
                    }
                }).

                // 设置取消按钮,null是什么都不做，并关闭对话框
                        setNegativeButton("取消", null);

        // 生产对话框
        AlertDialog alertDialog = builder.create();
        // 显示对话框
        alertDialog.show();


    }


    /**
     * 下载新版本程序
     */
    private void loadNewVersionProgress() {
        final String uri = "http://www.imooc.com/mobile/mukewang.apk";
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        //启动子线程下载任务
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(uri, pd);
                    sleep(5000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框


                } catch (Exception e) {
                    //下载apk失败
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_LONG).show();
                        }
                    });
                    e.printStackTrace();
                    Log.d("getFileFromServer", e.getMessage());
                }
            }
        }.start();
    }

    /**
     * 安装apk
     */
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }


    /**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行哦）
     */
    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            long time = System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(Environment.getExternalStorageDirectory(), time + "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                Log.d("getFileFromServer", "total:" + total + "   cur:" + len);
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }


}
