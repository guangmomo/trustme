package fargement;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.fff.FragmentOrderDetails;
import com.example.fff.R;


import com.example.fff.zhuangtai_other;

import data.DynamicInfo;
import data.TrustMeDao;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import app.Constant;
import app.ImageUtil;

//访问数据库
//String pro  打赏金额
//String phone 发布任务的号码尾数4位
//String another 外卖店名
//String dorstr 发布任务者的宿舍
//String Zhuangtai 当前的订单状态

//ListView的item解释
//title 茹同学 尾号8084
//name  3-604

public class FargementRecommend_shouye extends Fragment {

	private int currIndex = 0;
	private int bottomLineWidth;
	private int offset = 0;
	private int position_one;
	private int position_two;
	Resources resources;
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
	private ImageView ivBottomLine;
	private TextView tvTabone, tvTabtwo, tvTabthree;
	private ImageView point1, point2, point3;
	private ViewPager vp;
	private List<View> containers, containers1;
	private int e = 0;
	private ListView lv;
	
	final int MENU_GENDER_FREE = 0;
	final int MENU_GENDER_FINAL=1;
	final int MENU_GENDER = 2;
	final int GENDER_GROUP =0;
	final int MAIN_GROUP =2;
	MenuItem order = null;

	// 虚拟数据
	static private String[] title = new String[100];
	private String[] titles;
	static private int[] img = new int[100];
	private int[] imgs;
	static private String[] name = new String[100];
	private String[] names;

	private Button recommend_bt;// 状态修改键
	private TextView zhuangtai;// 状态情况
	private int get_zhuangtai;//获得用户修改状态；
	private LayoutInflater inflater1;
	private List<Map<String, Object>> data;

	String pro;
	int mon, date, hour, min, sec;
	String address, another;

	String get_pro, get_name;
	String uidstr;

	static int num = 1;
	
	private TrustMeDao dao=new TrustMeDao();
	private MAdapter adapter;
	private List<DynamicInfo> listDynamic;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_listview, container,
				false);
		inflater1 = LayoutInflater.from(getActivity());
		lv = (ListView) view.findViewById(R.id.lv);
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

	
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	public List<Map<String, Object>> getData() {
		listDynamic=dao.findMyTakeDynamic();
		data = new ArrayList<Map<String, Object>>();
		for(DynamicInfo dynamicInfo: listDynamic){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", dynamicInfo.getSponsor()+" 尾号"+dynamicInfo.getTailNumber());
			map.put("img", R.drawable.img1);
			map.put("name",dynamicInfo.getDong()+dynamicInfo.getLou());
			data.add(map);
		}
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
				convertView = inflater1.inflate(R.layout.dongtai_me, parent,
						false);
				holder.relativeLayout= (RelativeLayout) convertView
						.findViewById(R.id.item_sponsor);
				holder.img = (ImageView) convertView
						.findViewById(R.id.recommendedlv_img);
				holder.title = (TextView) convertView
						.findViewById(R.id.recommendedlv_text);
				holder.name = (TextView) convertView
						.findViewById(R.id.recommendedlv_name);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			zhuangtai = (TextView) convertView.findViewById(R.id.dongtai_me);

			recommend_bt = (Button) convertView.findViewById(R.id.recommend_bt);
		
			int state=listDynamic.get(position).getState();
			DynamicInfo info=listDynamic.get(position);
			String sponsor=info.getSponsor();
			if(state==Constant.HAVE_TAKE){
				zhuangtai.setText("你正在帮"+sponsor+"代拿外卖");
				recommend_bt.setText("结束订单");
			}else if(state==Constant.TAKE_COMPLETE){
				zhuangtai.setText("等待"+sponsor+"的打赏");
				recommend_bt.setText("订单已结束");
			}else if(state==Constant.RECEIVER_CANCEL_ORDER){
				zhuangtai.setText("订单被你自己取消了");
				recommend_bt.setText("订单已结束");
			}else if(state==Constant.CONFIRM_GOODS){
				float reward=info.getMoney();
				zhuangtai.setText("已获得"+sponsor+"奖励的"+reward+"赏金");
				recommend_bt.setText("订单已结束");
			}else if(state==Constant.ADMIN_CANCEL_ORDER){
				zhuangtai.setText("订单已被管理员取消");
				recommend_bt.setText("订单结束");
			}else{
				zhuangtai.setText("你正在帮"+sponsor+"代拿外卖");
				recommend_bt.setText("结束订单");
			}
			
			recommend_bt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (((Button)v).getText().equals("结束订单")) {
						zhuangtai_other dialog = new zhuangtai_other();
						dialog.show(getFragmentManager(), "结束订单");
						dialog.setFragment(FargementRecommend_shouye.this);
						dialog.setInfo(listDynamic.get(position));
					}else{
						Toast.makeText(v.getContext(), "订单已经结束啦...", Toast.LENGTH_SHORT).show();
					}
					
				}
			});

			
			 holder.relativeLayout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub
						FragmentOrderDetails pop = new FragmentOrderDetails();
						pop.setInfo(listDynamic.get(position));
						pop.show(getFragmentManager(), "订单详情");
					}
				});
			
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
				
			holder.title.setText(data.get(position).get("title").toString());
			holder.name.setText(data.get(position).get("name").toString());
			return convertView;
		}
	}

	class ViewHolder {
		RelativeLayout relativeLayout;
		ImageView img;
		TextView title;
		TextView times;
		TextView name;
	}

}
