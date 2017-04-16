package com.example.fff;

import java.io.File;

import data.TrustMeDao;
import data.UserInfo;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import app.App;
import app.ImageUtil;

//�޸���Ϣ
//�������ݿ�
//String name; �û���
//String phone; �绰����
//String posswords; ����
//String reposswords; ����
//String school; ѧУ
//String dormatory; ����

public class change_inform extends Activity {

	private TrustMeDao dao=new TrustMeDao();
	private EditText zhuce_name;
	private TextView zhuce_phone;
	private EditText zhuce_posswords;
	private EditText zhuce_reposswords;
	private EditText zhuce_school;
	private TextView zhuce_dormatory;
	private Button btn_zhuce;

	private ImageView change_back;

	String name;
	String phone;
	String posswords;
	String reposswords;
	String school;
	String dormatory;
	String yanzheng;
	UserInfo info;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_infor);
        
		String SP_INFOS = null;
		zhuce_name = (EditText) findViewById(R.id.change_name);
		zhuce_posswords = (EditText) findViewById(R.id.change_password);
		zhuce_reposswords = (EditText) findViewById(R.id.change_repassword);

		info=dao.findUserByNickname(App.currentName);
		
		change_back = (ImageView) findViewById(R.id.change_back);
		change_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		btn_zhuce = (Button) findViewById(R.id.btn_change);
		btn_zhuce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				name = zhuce_name.getText().toString().trim();
				posswords = zhuce_posswords.getText().toString().trim();
				reposswords = zhuce_reposswords.getText().toString().trim();
				

				if (name.equals("")) {
					Toast.makeText(change_inform.this, "�ǳƲ���Ϊ�գ�",
							Toast.LENGTH_LONG).show();
				}  else if (posswords.equals("")) {
					Toast.makeText(change_inform.this, "���벻��Ϊ�գ�",
							Toast.LENGTH_LONG).show();
				} else if (reposswords.equals("")) {
					Toast.makeText(change_inform.this, "�������䲻��Ϊ�գ�",
							Toast.LENGTH_LONG).show();
				} else if (!posswords.equals(reposswords)) {
					Toast.makeText(change_inform.this, "�������벻ͬ�������䣡",
							Toast.LENGTH_LONG).show();
				}else if(dao.isNicknameExist(name) && !name.equals(App.currentName)){
					Toast.makeText(change_inform.this, "���ǳ��Ѿ��������û�ʹ�ã��뻻һ���ǳ�",
							Toast.LENGTH_LONG).show();
				}
				else {
					 String oldName=App.currentName;
		             dao.updateUserInfo(name, posswords);//�������ݿ��е��û���Ϣ	
		             dao.updateReceiver(oldName, name);
		             dao.updateSponsor(oldName, name);
		             File imageFile=new File(Environment.getExternalStorageDirectory(), oldName+".jpg");
		             ImageUtil.getImageInstance().changeImageName(imageFile, name);
		             App.currentName=name;//���µ�ǰ��¼�û����ǳ�
					 finish();
				}
			}
		});

	}

}
