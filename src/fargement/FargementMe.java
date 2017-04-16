package fargement;

import java.io.File;

import com.example.fff.Me_money;
import com.example.fff.Me_money.OnItemClickListener;
import com.example.fff.R;
import com.example.fff.about_soft;
import com.example.fff.change_img;
import com.example.fff.change_popup;
import com.example.fff.login;
import com.example.fff.pop_problem;

import data.TrustMeDao;
import data.UserInfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import app.App;
import app.ImageUtil;

//需要访问数据库
//private String uidstr; 用户名
//private String phostr; 电话号码
//private String schstr; 学校
//private String dorstr; 宿舍
//private String money;  零钱

public class FargementMe extends Fragment {

	private ImageView me_change;
	private ImageView change_touxian;
	private RelativeLayout aboutsoft;
	private RelativeLayout about_problem;
	private String img;

	private String uidstr;
	private String phostr;
	private String schstr;
	private String dorstr;
	private String dongstr;
	private String loustr;
	private String money;
	private TextView me_name;
	private TextView me_myphone;
	private TextView me_school;
	private TextView me_dormitory;
	private TextView me_Momey;//零钱
	private LinearLayout me_momey;
	
	private TrustMeDao dao;
	
	private OnItemClickListener listener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.me, container, false);

		String SP_INFOS = null;
		int MODE_PRIVATE = 0;
		dao=new TrustMeDao();   
		me_name = (TextView) view.findViewById(R.id.me_name);
		me_myphone = (TextView) view.findViewById(R.id.me_myphone);
		me_school = (TextView) view.findViewById(R.id.me_school);
		me_dormitory = (TextView) view.findViewById(R.id.me_dormitory);
		me_Momey = (TextView) view.findViewById(R.id.me_momey);//零钱
		me_momey = (LinearLayout) view.findViewById(R.id.me_Money);

		
		//初始化listener
		
		listener=new OnItemClickListener() {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				UserInfo info=dao.findUserByNickname(App.currentName);
				me_Momey.setText(String.valueOf(info.getAmount()));
			}
		};
		
	
		
		me_momey.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Me_money e = new Me_money();
			    e.setItemClickListener(listener);
				e.show(getFragmentManager(), "代拿");
			}
		});

		me_change = (ImageView) view.findViewById(R.id.me_change);
		me_change.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				change_popup e = new change_popup();
				e.show(getFragmentManager(), "代拿");
			}
		});

		change_touxian = (ImageView) view.findViewById(R.id.me_changeimg);
		change_touxian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(getActivity(), change_img.class);
				getActivity().startActivity(intent1);// 启动另一activity

			}
		});

		aboutsoft = (RelativeLayout) view.findViewById(R.id.aboutme);
		aboutsoft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(getActivity(), about_soft.class);
				getActivity().startActivity(intent1);
			}
		});

		about_problem = (RelativeLayout) view.findViewById(R.id.yijianfankui);
		about_problem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop_problem dialog = new pop_problem();
				dialog.show(getFragmentManager(), "意见反馈");
			}
		});

		return view;
	}
     
	@Override
    public void onResume(){
		
		super.onResume();
		
		//访问数据库
				uidstr=App.currentName;
				UserInfo info=dao.findUserByNickname(uidstr);
		        phostr=info.getMobile();
		        schstr=info.getSchool();
		        dongstr=info.getDong();
		        loustr=info.getLou();
		        dorstr=dongstr+loustr;
		        money =String.valueOf(info.getAmount());
		        
		        
		  //更新界面上的信息  	
				if (uidstr != null && phostr != null && schstr != null
						&& dorstr != null) {
					me_name.setText(uidstr);
					me_myphone.setText(phostr);
					me_school.setText(schstr);
					me_dormitory.setText(dorstr);
					me_Momey.setText(money);
				}
		  
		    	final File file =new File(Environment.getExternalStorageDirectory(), App.currentName+".jpg");
		    	
		    	
		    	ViewTreeObserver observer=change_touxian.getViewTreeObserver();
				observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					
					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						// TODO Auto-generated method stub
						Log.e("test","layout");
						change_touxian.getViewTreeObserver().removeOnGlobalLayoutListener(this);
					   ImageUtil.getImageInstance().setImage(change_touxian, file);
					}
				});
				
			
		    	
		    	
		    	
			
	    			
    }
   
}
