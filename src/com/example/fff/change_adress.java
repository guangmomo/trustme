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

//���Ƽ����ж�����ַ����������

//��Ҫ�������ݿ⣺��ȡ��ǰ�������¥�ţ����½����ݿ��С�
//int j Ϊ��õ���ĵ�ǰ¥�ţ�
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
		// ���öԻ����ͼ��
		builder.setIcon(R.drawable.dibu__application_draw);
		// ���öԻ���ı���
		builder.setTitle("ѧ������ƽ̨");
		// 0: Ĭ�ϵ�ǰ�û����ڶ����ĵ�ѡ��ť��ѡ��
		builder.setSingleChoiceItems(R.array.adress, Integer.valueOf(FargementStudy.dong), new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				j=which;
			}
		});
		
		// ���һ��ȷ����ť
		builder.setPositiveButton("ȷ ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				FargementStudy.dong=j;
				fragment.notifyDataSetChanged();
			}
		}).setNegativeButton("ȡ��", null);
		
		
		// ����һ����ѡ��ť�Ի���
		dialog = builder.create();

		return dialog;
	}

}
