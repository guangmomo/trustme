package fargement;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.fff.FragmentOrderDetails;
import com.example.fff.Me_money;
import com.example.fff.Me_money.OnItemClickListener;
import com.example.fff.R;
import com.example.fff.about_soft;
import com.example.fff.change_img;
import com.example.fff.change_popup;
import com.example.fff.login;
import com.example.fff.pop_problem;

import data.DynamicInfo;
import data.TrustMeDao;
import data.UserInfo;
import fargement.UserFragment.MAdapter;
import fargement.UserFragment.ViewHolder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import app.App;
import app.Constant;
import app.ImageUtil;

//��Ҫ�������ݿ�
//private String uidstr; �û���
//private String phostr; �绰����
//private String schstr; ѧУ
//private String dorstr; ����
//private String money;  ��Ǯ

public class OrderFragment extends Fragment {

	private ListView lv;
	private LayoutInflater inflater1;
	private List<Map<String, Object>> data;
	private TrustMeDao dao=new TrustMeDao();
	private MAdapter adapter;
	private List<DynamicInfo> listDynamicInfo=new ArrayList<>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_oder, container,
				false);
		inflater1 = LayoutInflater.from(getActivity());
		lv = (ListView) view.findViewById(R.id.order_lv);
		getData();
		adapter=new MAdapter();
		lv.setAdapter(adapter);
		return view;
	}

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		notifyDataSetChanged();
	}
	
	/**
	 * ����ListView������
	 */
	public void notifyDataSetChanged(){
		getData();
		adapter.notifyDataSetChanged();
	}

	public void getData() {
		List<DynamicInfo> list=dao.findAllDynatic();
		if(list!=null){
			listDynamicInfo=list;
		}
	}
	


	class MAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listDynamicInfo.size();
		}

		@Override
		public Object getItem(int position) {
			return listDynamicInfo.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			if (convertView == null) {
				convertView = inflater1.inflate(R.layout.item_order, parent,
						false);
				holder.relativeLayout= (RelativeLayout)convertView
						.findViewById(R.id.order_item);
				holder.img = (ImageView) convertView
						.findViewById(R.id.user_icon);
				holder.userName = (TextView) convertView
						.findViewById(R.id.user_name);
				holder.dormitory=(TextView) convertView
						.findViewById(R.id.dormitory);
				holder.state=(TextView) convertView
						.findViewById(R.id.state);
				holder.shopName=(TextView) convertView
						.findViewById(R.id.shop_name);
				holder.btnDetails = (Button) convertView
						.findViewById(R.id.btn_detail);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			DynamicInfo info=listDynamicInfo.get(position);
			
			String name=info.getSponsor();
			
			final File file =new File(Environment.getExternalStorageDirectory(), name+".jpg");
			final ImageView imageView=holder.img;
			ViewTreeObserver observer=imageView.getViewTreeObserver();
			observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				
				@SuppressWarnings("deprecation")
				@Override
				public void onGlobalLayout() {
					// TODO Auto-generated method stub
					imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				   ImageUtil.getImageInstance().setImage(imageView, file);
				}
			});
		
			
			holder.userName.setText("�û���:"+info.getSponsor());
			holder.shopName.setText("����С������"+info.getShop());
			holder.dormitory.setText("�����:"+info.getDong()+info.getLou());
			//holder.state.setText("״̬");
			
			holder.btnDetails.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					FragmentOrderDetails pop = new FragmentOrderDetails();
					pop.setInfo(listDynamicInfo.get(position));
					pop.setFragment(OrderFragment.this);
					pop.show(getFragmentManager(), "��������");
				}
			});
			
	holder.relativeLayout.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			FragmentOrderDetails pop = new FragmentOrderDetails();
			pop.setInfo(listDynamicInfo.get(position));
			pop.setFragment(OrderFragment.this);
			pop.show(getFragmentManager(), "��������");
		}
	});
			
			return convertView;
		}
	}

	
	
	class ViewHolder {
		RelativeLayout relativeLayout;
		ImageView img;
		TextView userName;
		TextView state;
		TextView shopName;
		TextView dormitory;
		Button btnDetails;
	}

}

