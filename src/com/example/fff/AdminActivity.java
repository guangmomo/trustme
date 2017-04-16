package com.example.fff;


import adapter.FragmentAdapter;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import fargement.OrderFragment;
import fargement.UserFragment;

public class AdminActivity extends FragmentActivity {

    //Activity回调onActivityResult时候的监听器
    private OnActivityResultListener listener;

    /**
     * 顶部两个LinearLayout
     */
    private LinearLayout llTestAd;
    private LinearLayout llConfigCheck;

    /**
     * 顶部的两个TextView
     */
    private TextView tvTestAd;
    private TextView tvConfigCheck;

    /**
     * Tab的那个引导线
     */
    private ImageView mTabLine;

    /**
     * 屏幕的宽度
     */
    private int mScreenWidth;

    private ViewPager mViewPager;
    private FragmentAdapter mAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private Resources res;

    private long exitTime = 0;
    
    public interface OnActivityResultListener {
        void onActivityResult(Intent intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        res = getResources();
        initView();      //初始化控件
        initViewPager(); //初始化ViewPager
        initTabLine();   //初始化引导线
    }

    /**
     * 初始化控件
     */
    private void initView() {
        tvTestAd = (TextView) findViewById(R.id.tv_test_ad);
        tvConfigCheck = (TextView) findViewById(R.id.tv_config_check);
      
        llTestAd = (LinearLayout) findViewById(R.id.ll_test_ad);
        llConfigCheck = (LinearLayout) findViewById(R.id.ll_config_check);
     

        tvTestAd.setOnClickListener(new TabOnClickListener(0));
        tvConfigCheck.setOnClickListener(new TabOnClickListener(1));
      
        llTestAd.setOnClickListener(new TabOnClickListener(0));
        llConfigCheck.setOnClickListener(new TabOnClickListener(1));
      

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        fragments.add(new OrderFragment());
        fragments.add(new UserFragment());
      
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new TabOnPageChangeListener());
    }

    /**
     * 根据屏幕的宽度，初始化引导线的宽度
     */
    private void initTabLine() {
        mTabLine = (ImageView) findViewById(R.id.id_tab_line);

        //获取屏幕的宽度
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;

        //获取控件的LayoutParams参数(注意：一定要用父控件的LayoutParams写LinearLayout.LayoutParams)
        LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
        lp.width = mScreenWidth / 2;//设置该控件的layoutParams参数
        mTabLine.setLayoutParams(lp);//将修改好的layoutParams设置为该控件的layoutParams
    }


    /**
     * 重置颜色
     */
    private void resetTextView() {
        tvTestAd.setTextColor(res.getColor(R.color.black));
        tvConfigCheck.setTextColor(res.getColor(R.color.black));
      
    }


    /**
     * 功能：点击主页TAB事件
     */
    private class TabOnClickListener implements View.OnClickListener {
        private int index = 0;

        public TabOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            mViewPager.setCurrentItem(index);//选择某一页
        }

    }

    /**
     * 功能：Fragment页面改变事件
     */
    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {

        //当滑动状态改变时调用
        public void onPageScrollStateChanged(int state) {

        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
            //返回组件距离左侧组件的距离
            lp.leftMargin = (int) ((positionOffset + position) * mScreenWidth / 2);
            mTabLine.setLayoutParams(lp);
        }

        //当新的页面被选中时调用
        public void onPageSelected(int position) {
            //重置所有TextView的字体颜色
            resetTextView();
            switch (position) {
                case 0:
                    tvTestAd.setTextColor(res.getColor(R.color.green));
                    break;
                case 1:
                    tvConfigCheck.setTextColor(res.getColor(R.color.green));
               
            }
        }
    }


  

    public void setListener(OnActivityResultListener listener) {
        this.listener = listener;
    }


	
    /**
     * 捕捉返回事件按钮
     * 
     * 因为此 Activity 继承 TabActivity 用 onKeyDown 无响应，所以改用 dispatchKeyEvent
     * 一般的 Activity 用 onKeyDown 就可以了
     */

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
      if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
          this.exitApp();
        }
        return true;
      }
      return super.dispatchKeyEvent(event);
    }

    /**
     * 退出程序
     */
    private void exitApp() {
      // 判断2次点击事件时间
      if ((System.currentTimeMillis() - exitTime) > 2000) {
        Toast.makeText(this, "再按一次退出管理员登录", Toast.LENGTH_SHORT).show();
        exitTime = System.currentTimeMillis();
      } else {
        finish();
      }
    }

}

