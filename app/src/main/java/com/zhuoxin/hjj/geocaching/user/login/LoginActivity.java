package com.zhuoxin.hjj.geocaching.user.login;

import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.zhuoxin.hjj.geocaching.MainActivity;
import com.zhuoxin.hjj.geocaching.R;
import com.zhuoxin.hjj.geocaching.commons.ActivityUtils;
import com.zhuoxin.hjj.geocaching.commons.RegexUtils;
import com.zhuoxin.hjj.geocaching.fragment.AlertDialogFragment;
import com.zhuoxin.hjj.geocaching.treasure.HomeActivity;
import com.zhuoxin.hjj.geocaching.user.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登陆界面
 * 1.Toolbar作为Actionbar展示
 * 2.帐号和密码输入监听
 * 3.登录按钮，判断帐号和密码
 * 4.点击登录进行网路请求
 */
public class LoginActivity extends AppCompatActivity implements LoginView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_Username)
    EditText etUsername;
    @BindView(R.id.et_Password)
    EditText etPassword;
    @BindView(R.id.btn_Login)
    Button btnLogin;
    private String userName;
    private String passWord;
    private ProgressDialog progressDialog;
    private ActivityUtils mActivityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //触发onContentChanged()
        setContentView(R.layout.activity_login);



    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        mActivityUtils = new ActivityUtils(this);
        //Toolbar作为Actionbar展示
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            //设置返回键头，Android已经处理好，内部是选项菜单处理，id为：android.R.id.home
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回箭头
            //设置标题
            getSupportActionBar().setTitle(R.string.login);
        }
        //设置文本变化的监听
        etUsername.addTextChangedListener(mTextWatcher);
        etPassword.addTextChangedListener(mTextWatcher);
    }

    //文本变化监听
    TextWatcher mTextWatcher = new TextWatcher(){

        //文本变化前
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        //文本变化中
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        //文本变化后
        @Override
        public void afterTextChanged(Editable s) {
            userName = etUsername.getText().toString().trim();
            passWord = etPassword.getText().toString().trim();
            // 判断用户名和密码都不为空，按钮才可以点击
            boolean canLogin = !(TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord));
            btnLogin.setEnabled(canLogin);
        }
    };

    //用于处理选项菜单的选择事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_Login)
    public void onViewClicked() {
        //帐号不符合规则，弹出对话框提示提示错误
        if (RegexUtils.verifyUsername(userName)!=RegexUtils.VERIFY_SUCCESS){
            //弹出对话框提示,采用DialogFragment的方式
            AlertDialogFragment.getInstances(getString(R.string.username_error), getString(R.string.username_rules))
                    .show(getFragmentManager(), "usernameerror");//展示
            return;
        }
        //密码不符合规则，弹出对话框提示提示错误
        if (RegexUtils.verifyPassword(passWord)!=RegexUtils.VERIFY_SUCCESS){
            //弹出对话框提示
            AlertDialogFragment.getInstances(getString(R.string.password_error),getString(R.string.password_rules))
                    .show(getFragmentManager(),"passworderror");//展示
            return;
        }
        //验证成功将用户名和密码上传，进行网络请求，完成登录
        // TODO: 2017/6/7
        new LoginPresenter(this).login(new User(userName,passWord));

    }

    //-------------试图方法具体实现------------------
    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(this, "登录", "Login~~~~~");
    }

    @Override
    public void hideProgress() {
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String messgage) {
        mActivityUtils.showToast(messgage);
    }

    @Override
    public void navigateToHome() {
        mActivityUtils.startActivity(HomeActivity.class);
        finish();

        //发送本地广播到MainActivity，通知MainActivity关闭
        Intent intent = new Intent().setAction(MainActivity.MAIN_ACTOIN);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }
}
