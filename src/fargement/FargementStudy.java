package fargement;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.fff.New_project;
import com.example.fff.R;
import com.example.fff.change_adress;
//import com.example.fff.change_popup;
import com.example.fff.login;
import com.example.fff.pop_read;
import com.example.fff.pop_tozhuce;

import data.DynamicInfo;
import data.TrustMeDao;


import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import app.App;
import app.Constant;
import app.ImageUtil;

//��ȡ���ݿ�
//��û�и��µ�����
//String pro  ���ͽ��
//String phone ��������ĺ���β��4λ
//String another ��������
//String dorstr ���������ߵ�����

//title  �ص�
//s_name ��ͬѧ β��7864
//time   ʱ��

public class FargementStudy extends Fragment {

	private TrustMeDao dao=new TrustMeDao();
	private ImageView btn_add,img1;
	private ListView lv;
	static private String[] title = new String[100];
	static private int[] img = new int[100];
	private int[] imgs;
	static private String[] time = new String[100];
	static private String[] name = new String[100];
	private List<Map<String, Object>> data;
	private Button recommend_bt;
	private LayoutInflater inflater1;
	private String dorstr;
	private TextView title_adress;
	
	public static int dong;

	String pro,phone;
	int area;
	String another;

	String get_pro, get_name;
	String uidstr;

	static int num = 1;
	private MAdapter adapter;
	List<DynamicInfo> listDyInfos;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.study, container, false);
		
		img1 = (ImageView) view.findViewById(R.id.iv_data);
		img1.setBackgroundResource(R.anim.data_anim);
		final AnimationDrawable animationDrawable = (AnimationDrawable) img1
				.getBackground();
		img1.post(new Runnable() {
			@Override
			public void run() {
				animationDrawable.start();
			}
		});

		btn_add = (ImageView) view.findViewById(R.id.btn_add);
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				uidstr = App.currentName;
				if (uidstr != null) {
					New_project pop = new New_project();
					pop.setFragment(FargementStudy.this);
					pop.show(getFragmentManager(), "��������");
				} else {
					pop_tozhuce pop = new pop_tozhuce();
					pop.show(getFragmentManager(), "��������ƽ̨");
				}
			}
		});

		inflater1 = LayoutInflater.from(getActivity());

		initt();

		String SP_INFOS = null;
		int MODE_PRIVATE = 0;
		
		title_adress = (TextView) view.findViewById(R.id.new_title);
		title_adress.setText(dong+"��");
		title_adress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				change_adress e = new change_adress();
				e.setFragment(FargementStudy.this);
				e.show(getFragmentManager(), "����");
				
			}
		});

		getData();
		adapter=new MAdapter();
		
		lv = (ListView) view.findViewById(R.id.study_lv);
		lv.setAdapter(adapter);

		return view;
	}

	private String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(new Date());
	}

	public void initt() {
		title[0] = "19��32���ٳ��ܲ�";
		img[0] = R.drawable.img1;
		time[0] = "2016-01-17 13:55:23";
		name[0] = "�ҽ���GO�Ͳ˵����룺";
		
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

	public List<Map<String, Object>> getData() {
		data = new ArrayList<Map<String, Object>>();
		if(App.currentDong!=null){
		   listDyInfos=dao.findDynamic(String.valueOf(dong));
			for(DynamicInfo info : listDyInfos){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("title",info.getSponsor()+" β��"+info.getTailNumber());
				map.put("img", R.drawable.img1);
				map.put("time", info.getTime());
				map.put("thename",info.getDong()+info.getLou() );
				data.add(map);
			}
		}
		title_adress.setText(dong+"��");
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
				convertView = inflater1.inflate(R.layout.study_list, parent,
						false);
				holder.relativeLayout = (RelativeLayout)convertView
						.findViewById(R.id.item_study);
				holder.img = (ImageView) convertView
						.findViewById(R.id.studylv_img);
				holder.title = (TextView) convertView
						.findViewById(R.id.studylv_text);
				holder.times = (TextView) convertView
						.findViewById(R.id.studylv_time);
				holder.s_name = (TextView) convertView
						.findViewById(R.id.studylv_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			recommend_bt = (Button) convertView.findViewById(R.id.studylv_bt);
			int positionState=listDyInfos.get(position).getState();
			if(positionState==Constant.WAIT_TAKE || positionState==Constant.RECEIVER_CANCEL_ORDER){//����Ҫ���ݶ�����״̬ȥ���°�ť���ı�
				recommend_bt.setText("����");
			}else if(positionState==Constant.ADMIN_CANCEL_ORDER || positionState==Constant.SPONSOR_CANCEL_ORDER){
				recommend_bt.setText("��ȡ��");
			}else{
				recommend_bt.setText("�Ѵ���");
			}
			
			recommend_bt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(((Button)v).getText().equals("��ȡ��")){//������ȡ����
						Toast.makeText(v.getContext(), "�˶����Ѿ���ȡ����ȥ������Ķ�����O(��_��)O", Toast.LENGTH_SHORT).show();
					}else{//����û�б�ȡ��
						if(((Button)v).getText().equals("�Ѵ���")){//�����Ѿ���������
							Toast.makeText(v.getContext(), "�˶����Ѿ������ã�ȥ���ñ�Ķ�����O(��_��)O", Toast.LENGTH_SHORT).show();
						}else{//������û����������
							if(listDyInfos.get(position).getSponsor().equals(App.currentName)){//�����Լ������Ķ���
								Toast.makeText(v.getContext(), "���ܰ��Լ����ã������ȥȡ������O(��_��)O", Toast.LENGTH_SHORT).show();
							}else{//���ñ��˷����Ķ���
									pop_read dialog = new pop_read();
									dialog.setInfo(listDyInfos.get(position));
									dialog.setFragment(FargementStudy.this);
									dialog.show(getFragmentManager(), "��ϸ��Ϣ");
							}
						}
						
						
					}
				}
			});
			
			holder.relativeLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
						pop_read dialog = new pop_read();
						dialog.setInfo(listDyInfos.get(position));
						dialog.setFragment(FargementStudy.this);
						dialog.show(getFragmentManager(), "��ϸ��Ϣ");
				}
			});
             
				
			String name=listDyInfos.get(position).getSponsor();
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
			holder.times.setText(data.get(position).get("time").toString());
			holder.s_name.setText(data.get(position).get("thename").toString());
            
			return convertView;
		}

	}

	class ViewHolder {
		RelativeLayout relativeLayout;
		ImageView img;
		TextView title;
		TextView times;
		TextView s_name;
	}

}
