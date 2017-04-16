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

//��Ҫ�������ݿ�
// int zhuangtai ��ǰ�õ���״̬��
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
		// ���öԻ����ͼ��
		builder.setIcon(R.drawable.dibu__application_draw);
		// ���öԻ���ı���
		builder.setTitle("ѧ������ƽ̨");
		// 0: Ĭ�ϵ�һ����ѡ��ť��ѡ��
		builder.setSingleChoiceItems(R.array.zhuangtai, 0, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				j=which;
			}
		});
		
		// ���һ��ȷ����ť
		builder.setPositiveButton("ȷ ��", new DialogInterface.OnClickListener() {
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
		}).setNegativeButton("ȡ��", null);
		
		
		// ����һ����ѡ��ť�Ի���
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
