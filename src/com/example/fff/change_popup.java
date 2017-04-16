package com.example.fff;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import app.App;

/**
 * 我的右上角的设置，弹出框可以选择“修改个人信息”和“退出登录”
 * @author xlw
 *
 */
public class change_popup  extends DialogFragment {
    int j=0;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = null;

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// 设置对话框的图标
		builder.setIcon(R.drawable.dibu__application_draw);
		// 设置对话框的标题
		builder.setTitle("学生代拿平台");
		// 0: 默认第一个单选按钮被选中
		builder.setSingleChoiceItems(R.array.msa, 0, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				j=which;
			}
		});
		
		// 添加一个确定按钮
		builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				if (j == 0) {//修改个人信息
					Intent intent1 = new Intent(getActivity(), change_inform.class);
					getActivity().startActivity(intent1);
				} else{//退出
					Intent intent1 = new Intent(getActivity(), login.class);
					getActivity().startActivity(intent1);//启动另一activity
					App.currentDong=null;
					App.currentName=null;
					getActivity().finish();

				}
				
			}
		}).setNegativeButton("取消", null);
		
		
		// 创建一个单选按钮对话框
		dialog = builder.create();

		return dialog;
	}

}
