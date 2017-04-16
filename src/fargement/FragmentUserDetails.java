package fargement;

import com.example.fff.R;

import data.DynamicInfo;
import data.TrustMeDao;
import data.UserInfo;
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
import app.App;
import app.Constant;

//锟斤拷锟狡硷拷锟斤拷锟斤拷锟叫憋拷摹锟斤拷锟斤拷椤�
//锟斤拷要锟斤拷锟斤拷锟斤拷锟捷库：
//锟斤拷锟斤拷锟斤拷锟较拷锟�
//String phone 锟斤拷锟斤拷锟斤拷息锟竭的电话锟斤拷锟斤拷
//String another 锟斤拷锟斤拷锟斤拷息锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
//String pro 锟斤拷锟酵斤拷锟�
//锟睫革拷锟斤拷锟捷库：
//锟斤拷锟介烦锟斤拷锟斤拷锟睫革拷锟角凤拷锟届单锟斤拷状态值

public class FragmentUserDetails extends DialogFragment {

	TrustMeDao dao=new TrustMeDao();
	String pro,phone;
	String address, another;

	String get_pro, get_name;
	String uidstr;

	private TextView tvUserName;
	private TextView tvSchool;
	private TextView tvPhone;
	private TextView tvDormitory;
	private TextView tvAmount;
	private UserInfo info;
	private FargementStudy currentFragment;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.list_user, null);
		tvUserName=(TextView) view.findViewById(R.id.user_name);
		tvSchool=(TextView) view.findViewById(R.id.school);
		tvPhone=(TextView) view.findViewById(R.id.phone);
		tvDormitory=(TextView) view.findViewById(R.id.dormitory);
		tvAmount=(TextView) view.findViewById(R.id.amount);
	     
		if(info!=null){
			tvUserName.setText(info.getNickName());
			tvSchool.setText(info.getSchool());
			tvPhone.setText(info.getMobile());
			tvDormitory.setText(info.getDong()+info.getLou());
			tvAmount.setText(info.getAmount()+"");
			
		}
		
		
		builder.setView(view);
		return builder.create();
	}

	
	public void setInfo(UserInfo info){
		this.info=info;
	}
	
	public void setFragment(FargementStudy currentFragment){
		this.currentFragment=currentFragment;
	}
}