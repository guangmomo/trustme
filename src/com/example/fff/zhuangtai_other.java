package com.example.fff;

import data.DynamicInfo;
import data.TrustMeDao;
import fargement.FargementRecommend_shouye;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import app.Constant;

//需要访问数据库
// int zhuangtai 当前该单的状态；
public class zhuangtai_other extends DialogFragment {
    int j=0;
    int zhuangtai;
    private FargementRecommend_shouye fragment;
    private DynamicInfo info;
    private TrustMeDao dao=new TrustMeDao();
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = null;

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// 设置对话框的图标
		builder.setIcon(R.drawable.dibu__application_draw);
		// 设置对话框的标题
		builder.setTitle("学生代拿平台");
		// 0: 默认第一个单选按钮被选中
		builder.setSingleChoiceItems(R.array.zhuangtai, 0, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				j=which;
			}
		});
		
		// 添加一个确定按钮
		builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				if (j == 0) {
					dao.updateDynamicState(Constant.RECEIVER_CANCEL_ORDER,info.getId());
					fragment.notifyDataSetChanged();
				} else{
					dao.updateDynamicState(Constant.TAKE_COMPLETE,info.getId());
					fragment.notifyDataSetChanged();

				}
				
			}
		}).setNegativeButton("取消", null);
		
		
		// 创建一个单选按钮对话框
		dialog = builder.create();

		return dialog;
	}
	
	
	public void setFragment(FargementRecommend_shouye fragment){
		this.fragment=fragment;
	}
	

	public void setInfo(DynamicInfo info){
		this.info=info;
	}

}
