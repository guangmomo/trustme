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

//�������ݿ�
//private String phone; �绰 
//private String password; ����


public class login extends Activity {

	private EditText et_phone;
	private EditText et_password;

	private String phone;
	private String password;
	private String uidstr;
	private String pwdstr;

	private TextView findps_login;
	private TextView zhuce_login;
    private TrustMeDao  dao;//���ڲ������ݿ����
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
		        	Toast.makeText(login.this, "���벻��Ϊ�գ�", Toast.LENGTH_LONG)
					.show();
		        	return;
		        }else if(phone.equals(App.sAdminAccount) && password.equals(App.sAdminPassword)){
		        	Toast.makeText(login.this, "�������", Toast.LENGTH_LONG)
					.show();
		        	return;
		        }
				
				
				if (phone.equals("")) {
					Toast.makeText(login.this, "�ֻ����벻��Ϊ�գ�", Toast.LENGTH_LONG)
							.show();
				} else if (!dao.isMobileExist(phone)) {//��ѯ���ݿ⣬�ж��ֻ����Ƿ�ע���
					Toast.makeText(login.this, "�˺���δ��ע�ᣡ", Toast.LENGTH_LONG)
							.show();
				} else if (password.equals("")) {
					Toast.makeText(login.this, "���벻��Ϊ�գ�", Toast.LENGTH_LONG)
							.show();
				} else if (dao.findPass(phone)==null || !dao.findPass(phone).equals(password)) {//������ݿ��д洢��������û���������벻��ͬ
					Toast.makeText(login.this, "�ֻ���������벻�ԣ������䣡",
							Toast.LENGTH_LONG).show();
				} else {
					UserInfo info=dao.findUserByMobile(phone);
					App.currentName=info.getNickName();//���õ�ǰ�ĵ�ǰ�ĵ�¼�û�
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
