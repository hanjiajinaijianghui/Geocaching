package com.zhuoxin.hjj.geocaching.treasure;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zhuoxin.hjj.geocaching.R;
import com.zhuoxin.hjj.geocaching.user.UserPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    private ImageView ivIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        //Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            //不显示默认标题
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("");
        }

        //DrawerLayout的侧滑监听，展示控制开关的图标
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();//同步状态
        drawerLayout.addDrawerListener(toggle);

        //侧滑菜单的item的选择事件
        navigationView.setNavigationItemSelectedListener(this);

        //找到侧滑的头布局的头像控件
        ivIcon = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_usericon);
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/6/12 跳转到个人信息界面
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String photo = UserPrefs.getInstance().getPhoto();
        if (photo!=null){
            Picasso.with(this).load(photo).into(ivIcon);
        }
    }

    //侧滑的item 的某一项被选择的时候触发
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_hide:
                //埋藏宝藏
                break;
            case R.id.menu_logout:
                //退出
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
