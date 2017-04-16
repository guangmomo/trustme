package com.example.fff;

import data.TrustMeDao;
import data.UserInfo;
import fargement.FargementStudy;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import app.App;

//访问数据库
//private String phone; 电话 
//private String password; 密码


public class login extends Activity {

	private EditText et_phone;
	private EditText et_password;

	private String phone;
	private String password;
	private String uidstr;
	private String pwdstr;

	private TextView findps_login;
	private TextView zhuce_login;
    private TrustMeDao  dao;//用于操作数据库的类
	private Button btn_login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		dao=new TrustMeDao();
		
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_password = (EditText) findViewById(R.id.et_password);

		zhuce_login = (TextView) findViewById(R.id.zhuce_login);
		zhuce_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(login.this, zhuce.class);
				startActivity(intent);
			}
		});

		String SP_INFOS = null;
		SharedPreferences sp = getSharedPreferences(SP_INFOS, MODE_PRIVATE);
		uidstr = sp.getString("phone", null);
		pwdstr = sp.getString("passwords", null);
        
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phone = et_phone.getText().toString().trim();
				password = et_password.getText().toString().trim();

				if(phone.equals(App.sAdminAccount) && password.equals(App.sAdminPassword)){
			        login.this.startActivity(new Intent(login.this,AdminActivity.class));
			        return;
		        }else if(phone.equals(App.sAdminAccount) && !password.equals("")){
		        	Toast.makeText(login.this, "密码不能为空！", Toast.LENGTH_LONG)
					.show();
		        	return;
		        }else if(phone.equals(App.sAdminAccount) && password.equals(App.sAdminPassword)){
		        	Toast.makeText(login.this, "密码错误", Toast.LENGTH_LONG)
					.show();
		        	return;
		        }
				
				
				if (phone.equals("")) {
					Toast.makeText(login.this, "手机号码不能为空！", Toast.LENGTH_LONG)
							.show();
				} else if (!dao.isMobileExist(phone)) {//查询数据库，判断手机号是否被注册过
					Toast.makeText(login.this, "此号码未被注册！", Toast.LENGTH_LONG)
							.show();
				} else if (password.equals("")) {
					Toast.makeText(login.this, "密码不能为空！", Toast.LENGTH_LONG)
							.show();
				} else if (dao.findPass(phone)==null || !dao.findPass(phone).equals(password)) {//如果数据库中存储的密码和用户输入的密码不相同
					Toast.makeText(login.this, "手机号码或密码不对，请重输！",
							Toast.LENGTH_LONG).show();
				} else {
					UserInfo info=dao.findUserByMobile(phone);
					App.currentName=info.getNickName();//设置当前的当前的登录用户
					App.currentDong=info.getDong();
					FargementStudy.dong=Integer.valueOf(App.currentDong);
					Intent intent = new Intent(login.this, Kuangjia.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | 
                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
					
				}

			}
		});

	}

}
