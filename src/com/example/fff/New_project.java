package com.example.fff;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import data.DynamicInfo;
import data.TrustMeDao;
import data.UserInfo;
import fargement.FargementStudy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import app.App;
import app.Constant;

//���Ƽ��������ϽǷ�������
//��Ҫ�������ݿ⣺
//String area �����ɼ��ص㣨����ֻ����ͬһ�����˿ɼ�����ȫУ�ɼ���
//String pro ���ͽ�
//String phone �ֻ�β��4λ
//String another ��������

public class New_project extends DialogFragment {

	private TrustMeDao dao=new TrustMeDao();
	private List<String> list = new ArrayList<String>();
	private List<Integer> num1 = new ArrayList<Integer>();
	private List<Integer> num2 = new ArrayList<Integer>();
	private List<Integer> num3 = new ArrayList<Integer>();
	private List<Integer> num4 = new ArrayList<Integer>();

	private Spinner new_pro;
	private ArrayAdapter<String> adapter;

	private EditText new_address;
	private EditText new_another;

	private Button new_sure;
	private Button new_no;
	
	private TextView tvPhone;
	private UserInfo info;

	RadioButton pro1, pro2;
	String pro;//�ͽ�
	String phone;
	Integer phone_num1, phone_num2, phone_num3, phone_num4;
	int mon;
	int date;
	int hour;
	int min;
	float reward;//�ͽ�

	String another;
	
	public FargementStudy fragment;

	public void setFragment(FargementStudy fragment) {
		this.fragment = fragment;
	}

	int area;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.new_projects, null);

		list.add("0.5");
		list.add("1.0");
		list.add("1.5");
		list.add("2.0");
		list.add("2.5");
		list.add("3.0");

	

		pro1 = (RadioButton) view.findViewById(R.id.new_pro);

		pro2 = (RadioButton) view.findViewById(R.id.new_pro1);

		pro1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				area = 0;
				Toast.makeText(v.getContext(), "00", Toast.LENGTH_SHORT).show();
			}
		});
		
          pro2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				area = 1;
				Toast.makeText(v.getContext(), "11", Toast.LENGTH_SHORT).show();
			}
		});
		

		tvPhone=(TextView) view.findViewById(R.id.tv_phone);
	     info=dao.findUserByNickname(App.currentName);
		 String mobile=info.getMobile();
	     String tailNumber=mobile.substring(mobile.length()-4,mobile.length());
		tvPhone.setText(tailNumber);
		new_pro = (Spinner) view.findViewById(R.id.new_money);

		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, list);
		
		
		// Ϊ���������������б�����ʱ�Ĳ˵���ʽ��
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ����������ӵ������б���
		new_pro.setAdapter(adapter);
		// Ϊ�����б����ø����¼�����Ӧ���������Ӧ�˵���ѡ��
		new_pro.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				pro = adapter.getItem(arg2);
				reward=Float.valueOf(pro);
				arg0.setVisibility(View.VISIBLE);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		
	
		/* �����˵�����������ѡ���ı��¼����� */
		new_pro.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
			}
		});

		new_another = (EditText) view.findViewById(R.id.new_another);
		new_sure = (Button) view.findViewById(R.id.new_sure);
		new_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				another = new_another.getText().toString().trim();
				phone = "" + phone_num1 + phone_num2 + phone_num3 + phone_num4;

				if (another.equals("")) {
					Toast.makeText(getActivity(), "�ص㲻��Ϊ�գ�", Toast.LENGTH_LONG)
							.show();
				} else {
					if(checkMoney()){//����û���������Ƿ��㹻֧���ͽ�
						updateMoney();//�����û�������ң��������Ҫ��ȥ�ͽ�
						addDynamic();//���һ����̬�����ݿ�
						fragment.notifyDataSetChanged();
						New_project.this.dismiss();
					}
				}

			}
		});

		builder.setView(view);

		return builder.create();
	}


	/**
	 * ����û���������Ƿ��㹻֧���ͽ�
	 * @return
	 */
	public boolean checkMoney(){
		UserInfo userInfo=dao.findUserByNickname(App.currentName);
		if(userInfo.getAmount()<reward){
			Toast.makeText(App.getContext(), "�������Ҳ���", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	/**
	 * �����û��������
	 */
	public void updateMoney(){
		UserInfo userInfo=dao.findUserByNickname(App.currentName);
		dao.updateMoney(userInfo.getAmount()-reward, App.currentName);//���û�������Ҽ�ȥ�ͽ�
	}
	
	/**
	 * ����̬��ŵ����ݿ�
	 */
	public void addDynamic(){
		DynamicInfo dynamic =new DynamicInfo();
		dynamic.setSponsor(App.currentName);
		dynamic.setDong(info.getDong());
		dynamic.setLou(info.getLou());
		dynamic.setTailNumber(tvPhone.getText().toString());
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//�������ڸ�ʽ
		 dynamic.setTime(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
	     dynamic.setState(Constant.WAIT_TAKE);
	     if(area==0){
	    	 dynamic.setRange(Constant.FLOOR_RANGE);
	     }else if(area==1){
	    	 dynamic.setRange(Constant.SCHOOL_RANGE);
	     }
	     dynamic.setShop(another);
	     dynamic.setMoney(reward);
	     dao.addDynamic(dynamic);
	}
	
	private String getMon() {
		SimpleDateFormat format = new SimpleDateFormat("MM");
		return format.format(new Date());
	}

	private String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd");
		return format.format(new Date());
	}

	private String getHour() {
		SimpleDateFormat format = new SimpleDateFormat("HH");
		return format.format(new Date());
	}

	private String getMin() {
		SimpleDateFormat format = new SimpleDateFormat("mm");
		return format.format(new Date());
	}

	private String getSec() {
		SimpleDateFormat format = new SimpleDateFormat("ss");
		return format.format(new Date());
	}

}
