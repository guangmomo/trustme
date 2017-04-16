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

//“推荐”中列表的“详情”
//需要访问数据库：
//该项的信息：
//String phone 发布信息者的电话号码
//String another 发布信息中外卖店名
//String pro 悬赏金额
//修改数据库：
//请麻烦给个修改是否领单的状态值

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
	    		tvReceiver.setText("暂无");
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
	    
	    if(currentFragment!=null){//当currentFragment!=null的时候，说明是管理员界面跳过来的
	    	if(info.getState()==Constant.CONFIRM_GOODS){//订单已经确认收货的，管理员不能停止该订单，因为该订单已经完成了
	    		builder.setView(view);
	    	}else{//用户界面跳转过来的
	    		builder.setView(view)
				// Add action buttons
						.setPositiveButton("确定",null).setNegativeButton("停止该订单",  new DialogInterface.OnClickListener() {
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
			stateStr="等待代拿";
		}else if(state==Constant.HAVE_TAKE){
			stateStr="已代拿";
		}else if(state==Constant.SPONSOR_CANCEL_ORDER){
			stateStr="发出代拿的人取消订单";
		}else if(state==Constant.TAKE_COMPLETE){
			stateStr="代拿者表示代拿完成";
		}else if(state==Constant.CONFIRM_GOODS){
			stateStr="已确认收货，并打赏";
		}else if(state==Constant.RECEIVER_CANCEL_ORDER){
			stateStr="为他人代拿的人取消订单";
		}else if(state==Constant.ADMIN_CANCEL_ORDER){
			stateStr="订单被管理员暂停";
		}
		return stateStr;
	}
	
	
}