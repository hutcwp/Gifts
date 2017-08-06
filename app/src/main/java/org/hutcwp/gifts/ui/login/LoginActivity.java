package org.hutcwp.gifts.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.entity.bmob.User;
import org.hutcwp.gifts.ui.MainActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    EditText etUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("验证口令");

        etUserName = (EditText) findViewById(R.id.et_password);

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        if (BmobUser.getCurrentUser() == null) {
            login();
        } else {
            startMainActivity();
        }
        LoginActivity.this.finish();

    }


    /**
     * 注册的方法
     */
    public void register() {
        User bu = new User();
        bu.setUsername("hutcwp");
        bu.setNick("蔡文鹏");
        bu.setPassword("123456");
        //注意：不能用save方法进行注册
        bu.signUp(new SaveListener<User>() {
            @Override
            public void done(User s, BmobException e) {
                Log.d("test", "done");
                if (e == null) {
                    toast("注册成功");
                } else {
                    toast(e.getMessage());
                }
            }
        });
    }

    /**
     *
     */
    private void login() {

//        String userName = etUserName.getText().toString().trim();
        String userName = "hutcwp";

        BmobUser.loginByAccount(userName, "123456", new LogInListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if (user != null) {
                    Log.i("test", "用户登陆成功");
                    startMainActivity();
                } else {
                    toast("登录失败:原因->" + e.getMessage());
                }
            }
        });
    }

    private void startMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        LoginActivity.this.finish();
    }

    //弹出toast
    public void toast(String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
    }

}
