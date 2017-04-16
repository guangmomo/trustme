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

//数据库访问
//该用户拥有的金额  String money
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
			// 设置对话框的图标
			builder.setIcon(R.drawable.dibu__application_draw);
			// 设置对话框的标题
			builder.setTitle("金额提现");
			// 0: 默认第一个单选按钮被选中
			builder.setSingleChoiceItems(R.array.money, 0, new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					j=which;
				}
			});
			
			// 添加一个确定按钮
			builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					if (j == 0) {
						//提现20
						checkMoney();
						
						
						//Intent intent1 = new Intent(getActivity(), null);//需要补充的！！
						//getActivity().startActivity(intent1);
					} else{
						//充值，假设每次充值5个虚拟币
						rechargeMoney();
						
						
//						Intent intent1 = new Intent(getActivity(), login.class);
//						getActivity().startActivity(intent1);//启动另一activity

					}
					listener.onClick();
					
				}
			}).setNegativeButton("取消", null);
			
			
			// 创建一个单选按钮对话框
			dialog = builder.create();

			return dialog;
		}
		
		/**
		 * 充值，假设每次充值5个虚拟币
		 */
		private void  rechargeMoney(){
			money=dao.findAmount(App.currentName)+5;//将用户的虚拟币加5
			dao.updateMoney(money, App.currentName);//更新数据库中虚拟币的数量
		}
		
		
		/**
		 * 提现20
		 */
		protected void checkMoney(){
			
			String SP_INFOS = null;
			int MODE_PRIVATE = 0;
//			SharedPreferences sp = getActivity().getSharedPreferences(SP_INFOS,
//					MODE_PRIVATE);
//			money = sp.getString("money",null);
			
			//访问数据库
			money=dao.findAmount(App.currentName);
			
			
			if(money<20){
				Toast.makeText(getActivity(), "提现金额需要达到20，你不符合情况！", Toast.LENGTH_LONG)
				.show();
			}else{
				money=money-20;//提现成功，虚拟币减20
				dao.updateMoney(money, App.currentName);//更新数据库中虚拟币的数量
				Toast.makeText(getActivity(), "提现成功", Toast.LENGTH_LONG)
				.show();
			}
		}

	}
