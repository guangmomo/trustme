package com.example.fff;

import data.TrustMeDao;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import app.App;

//���ݿ����
//���û�ӵ�еĽ��  String money
public class Me_money extends DialogFragment {

	 int j=0;
	 private float money;
	 private TrustMeDao dao=new TrustMeDao();
	 
	 private OnItemClickListener listener;
	 
	 public void setItemClickListener(OnItemClickListener listener)
	 {
		 this.listener=listener;
	 }
	 
	 public interface OnItemClickListener{
		 void onClick();
	 }
		
		@Override	
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			Dialog dialog = null;
		
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			// ���öԻ����ͼ��
			builder.setIcon(R.drawable.dibu__application_draw);
			// ���öԻ���ı���
			builder.setTitle("�������");
			// 0: Ĭ�ϵ�һ����ѡ��ť��ѡ��
			builder.setSingleChoiceItems(R.array.money, 0, new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					j=which;
				}
			});
			
			// ���һ��ȷ����ť
			builder.setPositiveButton("ȷ ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					if (j == 0) {
						//����20
						checkMoney();
						
						
						//Intent intent1 = new Intent(getActivity(), null);//��Ҫ����ģ���
						//getActivity().startActivity(intent1);
					} else{
						//��ֵ������ÿ�γ�ֵ5�������
						rechargeMoney();
						
						
//						Intent intent1 = new Intent(getActivity(), login.class);
//						getActivity().startActivity(intent1);//������һactivity

					}
					listener.onClick();
					
				}
			}).setNegativeButton("ȡ��", null);
			
			
			// ����һ����ѡ��ť�Ի���
			dialog = builder.create();

			return dialog;
		}
		
		/**
		 * ��ֵ������ÿ�γ�ֵ5�������
		 */
		private void  rechargeMoney(){
			money=dao.findAmount(App.currentName)+5;//���û�������Ҽ�5
			dao.updateMoney(money, App.currentName);//�������ݿ�������ҵ�����
		}
		
		
		/**
		 * ����20
		 */
		protected void checkMoney(){
			
			String SP_INFOS = null;
			int MODE_PRIVATE = 0;
//			SharedPreferences sp = getActivity().getSharedPreferences(SP_INFOS,
//					MODE_PRIVATE);
//			money = sp.getString("money",null);
			
			//�������ݿ�
			money=dao.findAmount(App.currentName);
			
			
			if(money<20){
				Toast.makeText(getActivity(), "���ֽ����Ҫ�ﵽ20���㲻���������", Toast.LENGTH_LONG)
				.show();
			}else{
				money=money-20;//���ֳɹ�������Ҽ�20
				dao.updateMoney(money, App.currentName);//�������ݿ�������ҵ�����
				Toast.makeText(getActivity(), "���ֳɹ�", Toast.LENGTH_LONG)
				.show();
			}
		}

	}
