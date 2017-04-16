package fargement;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.fff.Kuangjia;
import com.example.fff.MainActivity;
import com.example.fff.New_project;
import com.example.fff.R;
import com.example.fff.login;
import com.example.fff.zhuce;

import data.DynamicInfo;
import data.TrustMeDao;
import data.UserInfo;
import fargement.FargementRecommend_shouye.MAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity.Header;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import app.App;
import app.Constant;
import app.ImageUtil;

//读取数据库
//String pro  打赏金额
//String phone 发布任务的号码尾数4位
//String another 外卖店名
//String dorstr 发布任务者的宿舍
//String Zhuangtai 当前的订单状态
public class UserFragment extends Fragment {
	private ListView lv;
	private LayoutInflater inflater1;
	private List<Map<String, Object>> data;
	private TrustMeDao dao=new TrustMeDao();
	private MAdapter adapter;
	private List<UserInfo> listUserInfo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_user, container,
				false);
		inflater1 = LayoutInflater.from(getActivity());
		lv = (ListView) view.findViewById(R.id.user_lv);
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
	 * 更新ListView的数据
	 */
	public void notifyDataSetChanged(){
		getData();
		adapter.notifyDataSetChanged();
	}

	public List<Map<String, Object>> getData() {
		listUserInfo=dao.findAllUser();
		data = new ArrayList<Map<String, Object>>();
		for(UserInfo userInfo: listUserInfo){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", userInfo.getNickName());
			map.put("img", R.drawable.img1);
			data.add(map);
		}
		Log.e("test",data.size()+"");
		return data;
	}
	


	class MAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			if (convertView == null) {
				convertView = inflater1.inflate(R.layout.user_item, parent,
						false);
				holder.relativeLayout=(RelativeLayout)convertView
						.findViewById(R.id.user_item);
				holder.img = (ImageView) convertView
						.findViewById(R.id.user_icon);
				holder.title = (TextView) convertView
						.findViewById(R.id.user_name);
				holder.btnDetails = (Button) convertView
						.findViewById(R.id.btn_details);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			String name=listUserInfo.get(position).getNickName();
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
			
			holder.title.setText("用户名: "+data.get(position).get("title").toString());
			holder.btnDetails.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					FragmentUserDetails pop = new FragmentUserDetails();
					pop.setInfo(listUserInfo.get(position));
					pop.show(getFragmentManager(), "用户详情");
				}
			});
			
			holder.relativeLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					FragmentUserDetails pop = new FragmentUserDetails();
					pop.setInfo(listUserInfo.get(position));
					pop.show(getFragmentManager(), "用户详情");
				}
			});
			return convertView;
		}
	}

	class ViewHolder {
		RelativeLayout relativeLayout;
		ImageView img;
		TextView title;
		Button btnDetails;
	}

}
