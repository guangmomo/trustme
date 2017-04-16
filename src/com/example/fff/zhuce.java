package com.example.fff;

import java.util.ArrayList;
import java.util.List;
import data.TrustMeDao;
import data.UserInfo;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import app.Constant;

//访问数据库
//String name; 用户名
//String phone; 用户号码
//String posswords; 密码
//String school; 学校
//String dormatory; 宿舍号
public class zhuce extends Activity {
	
	private List<Integer> num1 = new ArrayList<Integer>();
	private List<Integer> num2 = new ArrayList<Integer>();
	private List<Integer> num3 = new ArrayList<Integer>();
	private List<Integer> num4 = new ArrayList<Integer>();


	private EditText zhuce_name;
	private EditText zhuce_phone;
	private EditText zhuce_posswords;
	private EditText zhuce_reposswords;
	private EditText zhuce_school;
	private Spinner zhuce_num1;
	private Spinner zhuce_num2;
	private Spinner zhuce_num3;
	private Spinner zhuce_num4;
	private EditText zhuce_dormatory;
	private EditText zhuce_yanzheng;
	private Button zhuce_yanzhengbt;
	private Button btn_zhuce;
	
	private ArrayAdapter<Integer> adapter1, adapter2, adapter3, adapter4;
	Integer dormatory_num1, dormatory_num2, dormatory_num3, dormatory_num4;

	String name;
	String phone;
	String posswords;
	String reposswords;
	String school;
	String dormatory;
	String yanzheng;
	String dong;
	String lou;
	
	private TrustMeDao dao;

	private int[] yz = new int[4];
	private String[] yzm = new String[4];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuce);

		dao=new TrustMeDao();
		
		zhuce_name = (EditText) findViewById(R.id.zhuce_name);
		zhuce_phone = (EditText) findViewById(R.id.zhuce_phone);
		zhuce_posswords = (EditText) findViewById(R.id.zhuce_password);
		zhuce_reposswords = (EditText) findViewById(R.id.zhuce_repassword);
		zhuce_school = (EditText) findViewById(R.id.zhuce_school);
		zhuce_num1 = (Spinner) findViewById(R.id.zhuce_num1);
		zhuce_num2 = (Spinner) findViewById(R.id.zhuce_num2);
		zhuce_num3 = (Spinner) findViewById(R.id.zhuce_num3);
		zhuce_num4 = (Spinner) findViewById(R.id.zhuce_num4);
	//	zhuce_dormatory = (EditText) findViewById(R.id.zhuce_dormatory);
		zhuce_yanzheng = (EditText) findViewById(R.id.zhuce_yanzhen);
		
		for (int i = 0; i < 10; i++) {
			num1.add(i);
			num2.add(i);
			num3.add(i);
			num4.add(i);
		}
		
		adapter1 = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, num1);
		adapter2 = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, num2);
		adapter3 = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, num3);
		adapter4 = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, num4);

		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		zhuce_num1.setAdapter(adapter1);
		zhuce_num2.setAdapter(adapter2);
		zhuce_num3.setAdapter(adapter3);
		zhuce_num4.setAdapter(adapter4);

		zhuce_num1.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				dormatory_num1 = adapter1.getItem(arg2);
				arg0.setVisibility(View.VISIBLE);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		zhuce_num2.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				dormatory_num2 = adapter2.getItem(arg2);
				arg0.setVisibility(View.VISIBLE);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		zhuce_num3.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				dormatory_num3 = adapter3.getItem(arg2);
				arg0.setVisibility(View.VISIBLE);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		zhuce_num4.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				dormatory_num4 = adapter4.getItem(arg2);
				arg0.setVisibility(View.VISIBLE);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		zhuce_yanzhengbt = (Button) findViewById(R.id.zhuce_yanzhenbt);
		zhuce_yanzhengbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int j = 0; j < 4; j++) {
					yz[j] = (int) (Math.random() * 10);
				}
				for (int j = 0; j < 4; j++) {
					yzm[j] = Integer.toString(yz[j]);
				}
				Toast.makeText(zhuce.this,
						"输入验证码：" + yzm[0] + yzm[1] + yzm[2] + yzm[3],
						Toast.LENGTH_LONG).show();
			}
		});

		btn_zhuce = (Button) findViewById(R.id.btn_zhuce);
		btn_zhuce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name = zhuce_name.getText().toString().trim();
				phone = zhuce_phone.getText().toString().trim();
				posswords = zhuce_posswords.getText().toString().trim();
				reposswords = zhuce_reposswords.getText().toString().trim();
				school = zhuce_school.getText().toString().trim();
				yanzheng = zhuce_yanzheng.getText().toString().trim();
				dormatory = ""+dormatory_num1+"-"+dormatory_num2+dormatory_num3+dormatory_num4;
                dong=dormatory_num1+"";
                lou="-"+dormatory_num2+dormatory_num3+dormatory_num4;
				if (name.equals("")) {
					Toast.makeText(zhuce.this, "昵称不能为空！", Toast.LENGTH_LONG)
							.show();
				} else if (phone.equals("")) {
					Toast.makeText(zhuce.this, "手机号码不能为空！", Toast.LENGTH_LONG)
							.show();
				} else if (phone.length() != 11) {
					Toast.makeText(zhuce.this, "手机号码格式不对！", Toast.LENGTH_LONG)
							.show();
				} else if (posswords.equals("")) {
					Toast.makeText(zhuce.this, "密码不能为空！", Toast.LENGTH_LONG)
							.show();
				} else if (reposswords.equals("")) {
					Toast.makeText(zhuce.this, "密码重输不能为空！", Toast.LENGTH_LONG)
							.show();
				} else if (!posswords.equals(reposswords)) {
					Toast.makeText(zhuce.this, "两次密码不同，请重输！", Toast.LENGTH_LONG)
							.show();
				} else if (school.equals("")) {
					Toast.makeText(zhuce.this, "学校不能为空！", Toast.LENGTH_LONG)
							.show();
				}else if(dormatory.equals("")){
					Toast.makeText(zhuce.this, "宿舍不能为空！", Toast.LENGTH_LONG)
					.show();
				} else if(dao.isNicknameExist(name)){
					Toast.makeText(zhuce.this, "已经存在此昵称，请您修改您的昵称！", Toast.LENGTH_LONG)
					.show();
		        }else if(dao.isMobileExist(phone)){
		        	Toast.makeText(zhuce.this, "该手机号已经注册过！", Toast.LENGTH_LONG)
					.show();
		        }else if (!yanzheng.equals(yzm[0] + yzm[1] + yzm[2] + yzm[3])) {
					Toast.makeText(zhuce.this, "验证码不正确，请重输！", Toast.LENGTH_LONG)
							.show();
				} else {
					String SP_INFOS = null;
//					SharedPreferences sp = getSharedPreferences(SP_INFOS,
//							MODE_PRIVATE);
//					SharedPreferences.Editor editor = sp.edit();
//					editor.putString("name", name);
//					editor.putString("phone", phone);
//					editor.putString("passwords", posswords);
//					editor.putString("school", school);
//					editor.putString("doromatoy", dormatory);
//					editor.putString("money", "5");
//					editor.commit();
					
				    //数据库操作,将用户的信息添加到数据库
					  UserInfo info=new UserInfo();
				         info.setNickName(name);
				         info.setMobile(phone);
				         info.setPassword(posswords);
				         info.setSchool(school);
				         info.setDong(dong);
				         info.setLou(lou);
				         info.setAmount(5);//用户注册，奖励5个虚拟币
				         dao.addUser(info);
				       
					Intent intent = new Intent(zhuce.this, login.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | 
                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
					zhuce.this.finish();
				}
			}
		});
	}
	
	
	

}
