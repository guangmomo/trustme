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

//�������ݿ�
//String pro  ���ͽ��
//String phone ��������ĺ���β��4λ
//String another ��������
//String dorstr ���������ߵ�����
//String Zhuangtai ��ǰ�Ķ���״̬

//ListView��item����
//title ��ͬѧ β��8084
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

	// ��������
	static private String[] title = new String[100];
	private String[] titles;
	static private int[] img = new int[100];
	private int[] imgs;
	static private String[] name = new String[100];
	private String[] names;

	private Button recommend_bt;// ״̬�޸ļ�
	private TextView zhuangtai;// ״̬���
	private int get_zhuangtai;//����û��޸�״̬��
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
	 * ����ListView������
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
			map.put("title", dynamicInfo.getSponsor()+" β��"+dynamicInfo.getTailNumber());
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
				zhuangtai.setText("�����ڰ�"+sponsor+"��������");
				recommend_bt.setText("��������");
			}else if(state==Constant.TAKE_COMPLETE){
				zhuangtai.setText("�ȴ�"+sponsor+"�Ĵ���");
				recommend_bt.setText("�����ѽ���");
			}else if(state==Constant.RECEIVER_CANCEL_ORDER){
				zhuangtai.setText("���������Լ�ȡ����");
				recommend_bt.setText("�����ѽ���");
			}else if(state==Constant.CONFIRM_GOODS){
				float reward=info.getMoney();
				zhuangtai.setText("�ѻ��"+sponsor+"������"+reward+"�ͽ�");
				recommend_bt.setText("�����ѽ���");
			}else if(state==Constant.ADMIN_CANCEL_ORDER){
				zhuangtai.setText("�����ѱ�����Աȡ��");
				recommend_bt.setText("��������");
			}else{
				zhuangtai.setText("�����ڰ�"+sponsor+"��������");
				recommend_bt.setText("��������");
			}
			
			recommend_bt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (((Button)v).getText().equals("��������")) {
						zhuangtai_other dialog = new zhuangtai_other();
						dialog.show(getFragmentManager(), "��������");
						dialog.setFragment(FargementRecommend_shouye.this);
						dialog.setInfo(listDynamic.get(position));
					}else{
						Toast.makeText(v.getContext(), "�����Ѿ�������...", Toast.LENGTH_SHORT).show();
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
						pop.show(getFragmentManager(), "��������");
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
