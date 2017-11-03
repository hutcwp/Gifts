package org.hutcwp.gifts.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.entity.bmob.User;
import org.hutcwp.gifts.ui.MainActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserName;
    private Button btnLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                login(etUserName.getText().toString().trim());
            }
        });

//        boolean isRememberPwd = Utils.getSharedPrefrence(LoginActivity.this, AppGlobal.REMEMBER_PWD);
//        if (isRememberPwd) {
        if (BmobUser.getCurrentUser() != null) {
            startMainActivity();
        }
//        }

    }


    /**
     * 登录
     */
    private void login(String pwd) {

        String userName = "zl";

        BmobUser.loginByAccount(userName, pwd, new LogInListener<User>() {

            @Override
            public void done(User user, BmobException e) {

                progressBar.setVisibility(View.INVISIBLE);
                if (user != null) {
                    Log.d("test", "用户登陆成功");
                    startMainActivity();
                } else {
                    Log.i("test", "登录失败:原因->" + e.getMessage());
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
