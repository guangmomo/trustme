package com.example.fff;

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
import android.widget.TextView;
import app.App;
import app.Constant;

//���Ƽ������б�ġ����顱
//��Ҫ�������ݿ⣺
//�������Ϣ��
//String phone ������Ϣ�ߵĵ绰����
//String another ������Ϣ����������
//String pro ���ͽ��
//�޸����ݿ⣺
//���鷳�����޸��Ƿ��쵥��״ֵ̬

public class FragmentUserDetails extends DialogFragment {

	TrustMeDao dao=new TrustMeDao();
	String address, another;

	String get_pro, get_name;
	String uidstr;

	private TextView userName;
	private TextView phone;
	private TextView dormitory;
	private TextView amount;
	private TextView school;
	private UserInfo info;
	private FargementStudy currentFragment;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.list_user, null);
		userName = (TextView) view.findViewById(R.id.user_name);
		phone = (TextView) view.findViewById(R.id.phone);
		dormitory = (TextView) view.findViewById(R.id.dormitory);
		amount = (TextView) view.findViewById(R.id.amount);
		school = (TextView) view.findViewById(R.id.school);
		
		if(info!=null){
			userName.setText(info.getNickName());
			phone.setText(info.getMobile());
			dormitory.setText(info.getDong()+info.getLou());
			amount.setText(info.getAmount()+"");
			school.setText(info.getSchool());
		}
	
			
		return builder.create();
	}

	
	public void setInfo(UserInfo info){
		this.info=info;
	}
	
	public void setFragment(FargementStudy currentFragment){
		this.currentFragment=currentFragment;
	}
}