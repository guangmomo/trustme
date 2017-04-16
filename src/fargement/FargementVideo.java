package fargement;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.fff.FragmentOrderDetails;
import com.example.fff.Kuangjia;
import com.example.fff.MainActivity;
import com.example.fff.R;
import com.example.fff.login;
import com.example.fff.zhuce;

import data.DynamicInfo;
import data.TrustMeDao;
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
public class FargementVideo extends Fragment {

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

	// 虚拟数据
	static private String[] title = new String[100];
	private String[] titles;
	static private int[] img = new int[100];
	private int[] imgs;
	static private String[] name = new String[100];
	private String[] names;

	private Button recommend_bt;// 状态修改键
	private TextView zhuangtai;//状态情况
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
		listDynamic=dao.findMySendDynamic();
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
			String receiver=info.getReceiver();
			if(state==Constant.WAIT_TAKE || state==Constant.RECEIVER_CANCEL_ORDER){
				zhuangtai.setText("等待代拿");
				recommend_bt.setText("取消订单");
			}else if(state==Constant.HAVE_TAKE){
				zhuangtai.setText(receiver+"正在帮您配送外卖..");
				recommend_bt.setText("确认收货");
			}else if(state==Constant.TAKE_COMPLETE){
				zhuangtai.setText(receiver+"已将您的外卖送达");
				recommend_bt.setText("确认收货");
			}else if(state==Constant.SPONSOR_CANCEL_ORDER){
				zhuangtai.setText("订单被你自己取消了");
				recommend_bt.setText("订单结束");
			}else if(state==Constant.CONFIRM_GOODS){
				float reward=info.getMoney();
				zhuangtai.setText(receiver+"收到了"+"您的"+reward+"赏金");
				recommend_bt.setText("订单完成");
			}else if(state==Constant.ADMIN_CANCEL_ORDER){
				zhuangtai.setText("订单已被管理员取消");
				recommend_bt.setText("订单结束");
			}
			
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
			
			recommend_bt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
                    
					DynamicInfo info=listDynamic.get(position);
					int id=info.getId();//动态的id
					float reward=info.getMoney();//赏金
					if (((Button)v).getText().toString().equals("取消订单")) {
						dao.updateDynamicState(Constant.SPONSOR_CANCEL_ORDER,id);
						float sponsorMoney=dao.findAmount(App.currentName);//用户当前的虚拟币
					    dao.updateMoney(sponsorMoney+reward, App.currentName);//取消订单后将虚拟币返回给发出代拿的人
					    Toast.makeText(v.getContext(), "赏金已退回到你的账户！", Toast.LENGTH_SHORT).show();
					} else if (((Button)v).getText().toString().equals("确认收货")) {
						dao.updateDynamicState(Constant.CONFIRM_GOODS, id);
						String receiver=listDynamic.get(position).getReceiver();//这个订单的代拿者
						float receiverMoney=dao.findAmount(receiver);//代拿者当前的虚拟币
					    dao.updateMoney(receiverMoney+reward,receiver);//确认订单后将虚拟币奖励给代拿者
					    Toast.makeText(v.getContext(),info.getReceiver()+"已经收到你的赏金啦~~", Toast.LENGTH_SHORT).show();
					} 
					FargementVideo.this.notifyDataSetChanged();
				}
			});

			String name=listDynamic.get(position).getSponsor();
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
				
				/*holder.img.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						ImageUtil.getImageInstance().setImage(imageView, file);
					}
				});*/
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
