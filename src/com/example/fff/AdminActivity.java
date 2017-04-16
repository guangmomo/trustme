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

    //Activity�ص�onActivityResultʱ��ļ�����
    private OnActivityResultListener listener;

    /**
     * ��������LinearLayout
     */
    private LinearLayout llTestAd;
    private LinearLayout llConfigCheck;

    /**
     * ����������TextView
     */
    private TextView tvTestAd;
    private TextView tvConfigCheck;

    /**
     * Tab���Ǹ�������
     */
    private ImageView mTabLine;

    /**
     * ��Ļ�Ŀ��
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
        initView();      //��ʼ���ؼ�
        initViewPager(); //��ʼ��ViewPager
        initTabLine();   //��ʼ��������
    }

    /**
     * ��ʼ���ؼ�
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
     * ��ʼ��ViewPager
     */
    private void initViewPager() {
        fragments.add(new OrderFragment());
        fragments.add(new UserFragment());
      
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new TabOnPageChangeListener());
    }

    /**
     * ������Ļ�Ŀ�ȣ���ʼ�������ߵĿ��
     */
    private void initTabLine() {
        mTabLine = (ImageView) findViewById(R.id.id_tab_line);

        //��ȡ��Ļ�Ŀ��
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;

        //��ȡ�ؼ���LayoutParams����(ע�⣺һ��Ҫ�ø��ؼ���LayoutParamsдLinearLayout.LayoutParams)
        LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
        lp.width = mScreenWidth / 2;//���øÿؼ���layoutParams����
        mTabLine.setLayoutParams(lp);//���޸ĺõ�layoutParams����Ϊ�ÿؼ���layoutParams
    }


    /**
     * ������ɫ
     */
    private void resetTextView() {
        tvTestAd.setTextColor(res.getColor(R.color.black));
        tvConfigCheck.setTextColor(res.getColor(R.color.black));
      
    }


    /**
     * ���ܣ������ҳTAB�¼�
     */
    private class TabOnClickListener implements View.OnClickListener {
        private int index = 0;

        public TabOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            mViewPager.setCurrentItem(index);//ѡ��ĳһҳ
        }

    }

    /**
     * ���ܣ�Fragmentҳ��ı��¼�
     */
    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {

        //������״̬�ı�ʱ����
        public void onPageScrollStateChanged(int state) {

        }

        //��ǰҳ�汻����ʱ����
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
            //������������������ľ���
            lp.leftMargin = (int) ((positionOffset + position) * mScreenWidth / 2);
            mTabLine.setLayoutParams(lp);
        }

        //���µ�ҳ�汻ѡ��ʱ����
        public void onPageSelected(int position) {
            //��������TextView��������ɫ
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
     * ��׽�����¼���ť
     * 
     * ��Ϊ�� Activity �̳� TabActivity �� onKeyDown ����Ӧ�����Ը��� dispatchKeyEvent
     * һ��� Activity �� onKeyDown �Ϳ�����
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
     * �˳�����
     */
    private void exitApp() {
      // �ж�2�ε���¼�ʱ��
      if ((System.currentTimeMillis() - exitTime) > 2000) {
        Toast.makeText(this, "�ٰ�һ���˳�����Ա��¼", Toast.LENGTH_SHORT).show();
        exitTime = System.currentTimeMillis();
      } else {
        finish();
      }
    }

}

