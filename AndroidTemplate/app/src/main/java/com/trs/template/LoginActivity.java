package com.trs.template;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trs.template.net.LoginResponse;
import com.trs.template.net.NetUtils;

public class LoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    Button loginButton;
    EditText telEditText;
    EditText passwordEditText;


    NetUtils netUtils;
    MyApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_login);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.login_title);
        initCtrl();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


    private void initCtrl() {
        app = (MyApp) getApplication();
        netUtils = app.netUtils;
        loginButton = (Button) findViewById(R.id.activity_login_btn_login);
        loginButton.setOnClickListener(this);
        telEditText = (EditText) findViewById(R.id.activity_login_et_tel);
        passwordEditText = (EditText) findViewById(R.id.activity_login_et_pwd);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.activity_login_btn_login:
                String tel = telEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (tel.equals("")) {
                    showMessage("请输入手机号");
                    return;
                }
                if (password.equals("")) {
                    showMessage("请输入密码");
                    return;
                }
                loginPro(tel, password);
                break;
        }
    }

    private void loginPro(String tel, String password) {
        netUtils.requestLogin(tel, password, new NetUtils.OnLoginListener() {
            @Override
            public void onLogin(int status, LoginResponse loginResponse) {
                if (status == 0) {
                    Log.i(TAG, loginResponse.toString());
                } else {
                    showMessage("登录失败");
                }
            }
        });
    }

    private void showMessage(String message) {
//        Snackbar.make(v, message, Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
