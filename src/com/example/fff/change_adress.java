package com.example.fff;

import fargement.FargementStudy;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import app.App;

//“推荐”中顶部地址更换弹出框；

//需要访问数据库：获取当前弹出框的楼号，更新进数据库中。
//int j 为获得点击的当前楼号；
public class change_adress extends DialogFragment {
    int j;
	private FargementStudy fragment;

	public void setFragment(FargementStudy fragment) {
		this.fragment = fragment;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = null;

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// 设置对话框的图标
		builder.setIcon(R.drawable.dibu__application_draw);
		// 设置对话框的标题
		builder.setTitle("学生代拿平台");
		// 0: 默认当前用户所在栋数的单选按钮被选中
		builder.setSingleChoiceItems(R.array.adress, Integer.valueOf(FargementStudy.dong), new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				j=which;
			}
		});
		
		// 添加一个确定按钮
		builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				FargementStudy.dong=j;
				fragment.notifyDataSetChanged();
			}
		}).setNegativeButton("取消", null);
		
		
		// 创建一个单选按钮对话框
		dialog = builder.create();

		return dialog;
	}

}
