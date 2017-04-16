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
 * �ҵ����Ͻǵ����ã����������ѡ���޸ĸ�����Ϣ���͡��˳���¼��
 * @author xlw
 *
 */
public class change_popup  extends DialogFragment {
    int j=0;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = null;

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// ���öԻ����ͼ��
		builder.setIcon(R.drawable.dibu__application_draw);
		// ���öԻ���ı���
		builder.setTitle("ѧ������ƽ̨");
		// 0: Ĭ�ϵ�һ����ѡ��ť��ѡ��
		builder.setSingleChoiceItems(R.array.msa, 0, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				j=which;
			}
		});
		
		// ���һ��ȷ����ť
		builder.setPositiveButton("ȷ ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				if (j == 0) {//�޸ĸ�����Ϣ
					Intent intent1 = new Intent(getActivity(), change_inform.class);
					getActivity().startActivity(intent1);
				} else{//�˳�
					Intent intent1 = new Intent(getActivity(), login.class);
					getActivity().startActivity(intent1);//������һactivity
					App.currentDong=null;
					App.currentName=null;
					getActivity().finish();

				}
				
			}
		}).setNegativeButton("ȡ��", null);
		
		
		// ����һ����ѡ��ť�Ի���
		dialog = builder.create();

		return dialog;
	}

}
