package com.example.fff;

import data.DynamicInfo;
import data.TrustMeDao;
import data.UserInfo;
import fargement.FargementStudy;
import fargement.OrderFragment;
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

public class FragmentOrderDetails extends DialogFragment {

	TrustMeDao dao=new TrustMeDao();

	private TextView tvSponsor;
	private TextView tvReceiver;
	private TextView tvDormitory;
	private TextView tvMoney;
	private TextView tvState;
	private TextView tvTime;
	private TextView tvShop;
	private TextView tvTailnum;
	
	private DynamicInfo info;
	private OrderFragment currentFragment;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.list_dynamic, null);
		tvSponsor = (TextView) view.findViewById(R.id.tv_sponsor);
		tvReceiver = (TextView) view.findViewById(R.id.tv_receiver);
		tvDormitory = (TextView) view.findViewById(R.id.tv_dormitory);
		tvMoney = (TextView) view.findViewById(R.id.tv_reward);
		tvState = (TextView) view.findViewById(R.id.tv_state);
		tvTime = (TextView) view.findViewById(R.id.tv_time);
		tvShop = (TextView) view.findViewById(R.id.tv_shop);
		tvTailnum = (TextView) view.findViewById(R.id.tv_tailnum);
		
	    if(info!=null){
	    	tvSponsor.setText(info.getSponsor());
	    	if(info.getReceiver()==null || info.getReceiver().equals("")){
	    		tvReceiver.setText("����");
	    	}else{
	    		tvReceiver.setText(info.getReceiver());
	    	}
	    	tvDormitory.setText(info.getDong()+info.getLou());
	    	tvMoney.setText(info.getMoney()+"");
	    	tvState.setText(getState(info.getState()));
	    	tvTime.setText(info.getTime());
	    	tvShop.setText(info.getShop());
	    	tvTailnum.setText(info.getTailNumber());
	    }
	    
	    if(currentFragment!=null){//��currentFragment!=null��ʱ��˵���ǹ���Ա������������
	    	if(info.getState()==Constant.CONFIRM_GOODS){//�����Ѿ�ȷ���ջ��ģ�����Ա����ֹͣ�ö�������Ϊ�ö����Ѿ������
	    		builder.setView(view);
	    	}else{//�û�������ת������
	    		builder.setView(view)
				// Add action buttons
						.setPositiveButton("ȷ��",null).setNegativeButton("ֹͣ�ö���",  new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
		                      dao.updateDynamicState(Constant.ADMIN_CANCEL_ORDER, info.getId());
		                      currentFragment.notifyDataSetChanged();
							}
						});
	    	}
	    	
	    }else{
	    	builder.setView(view);
	    }
	
		return builder.create();
			
	}

	
	public void setInfo(DynamicInfo info){
		this.info=info;
	}
	
	public void setFragment(OrderFragment currentFragment){
		this.currentFragment=currentFragment;
	}

    
	
	private String getState(int state){
		String stateStr="";
		if(state==Constant.WAIT_TAKE){
			stateStr="�ȴ�����";
		}else if(state==Constant.HAVE_TAKE){
			stateStr="�Ѵ���";
		}else if(state==Constant.SPONSOR_CANCEL_ORDER){
			stateStr="�������õ���ȡ������";
		}else if(state==Constant.TAKE_COMPLETE){
			stateStr="�����߱�ʾ�������";
		}else if(state==Constant.CONFIRM_GOODS){
			stateStr="��ȷ���ջ���������";
		}else if(state==Constant.RECEIVER_CANCEL_ORDER){
			stateStr="Ϊ���˴��õ���ȡ������";
		}else if(state==Constant.ADMIN_CANCEL_ORDER){
			stateStr="����������Ա��ͣ";
		}
		return stateStr;
	}
	
	
}