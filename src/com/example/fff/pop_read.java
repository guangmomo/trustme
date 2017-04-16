package com.example.fff;

import data.DynamicInfo;
import data.TrustMeDao;
import fargement.FargementStudy;
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
import android.widget.Toast;
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

public class pop_read extends DialogFragment {

	TrustMeDao dao=new TrustMeDao();
	String pro,phone;
	String address, another;

	String get_pro, get_name;
	String uidstr;

	private TextView send_name;
	private TextView send_procession;
	private TextView send_time;
	private TextView send_address;
	private TextView send_another;
	private TextView tvReceiver;
	private DynamicInfo info;
	private FargementStudy currentFragment;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.list_xianxi, null);
		send_name = (TextView) view.findViewById(R.id.send_name);
		send_procession = (TextView) view.findViewById(R.id.send_procession);
		send_time = (TextView) view.findViewById(R.id.send_time);
		send_address = (TextView) view.findViewById(R.id.send_address);
		tvReceiver = (TextView) view.findViewById(R.id.tv_receiver);
		
			send_name.setText(info.getSponsor());
			send_procession.setText(info.getTailNumber());
			send_time.setText(info.getShop());
			send_address.setText(info.getMoney()+"");
			if(info.getReceiver()==null || info.getReceiver().equals("")){
				tvReceiver.setText("暂无");
			}else {
				tvReceiver.setText(info.getReceiver());
			}
		
       if(info.getState()==Constant.WAIT_TAKE || info.getState()==Constant.RECEIVER_CANCEL_ORDER){    	   
    	   builder.setView(view)
    		// Add action buttons
    				.setPositiveButton("领单", new DialogInterface.OnClickListener() {
    					@Override
    					public void onClick(DialogInterface dialog, int id) {
    						if(info.getSponsor().equals(App.currentName)){
    							Toast.makeText(App.getContext(), "不能帮自己代拿，你可以去取消订单O(∩_∩)O", Toast.LENGTH_SHORT).show();
    						}else{
    							 dao.updateDynamicState(Constant.HAVE_TAKE, App.currentName,info.getId());
    	                          currentFragment.notifyDataSetChanged();
    						}
                         
    					}
    				}).setNegativeButton("取消", null);
       }else{
    	   builder.setView(view);
       }
			
	
		return builder.create();
	}

	
	public void setInfo(DynamicInfo info){
		this.info=info;
	}
	
	public void setFragment(FargementStudy currentFragment){
		this.currentFragment=currentFragment;
	}
}