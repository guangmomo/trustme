package com.example.fff;

import data.TrustMeDao;
import fargement.FargementMe;
import fargement.FargementShouye;
import fargement.FargementStudy;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import app.App;


public class Kuangjia extends FragmentActivity {

	private ImageView img_me, img_application, img_Study, img_recommend;
	private FargementMe peason_fragment = new FargementMe();//���ҡ�ҳ��
	private FargementStudy recommended = new FargementStudy();//���Ƽ���ҳ��
	private FargementShouye study = new FargementShouye();//����̬��ҳ��

	private String uidstr;
	private TrustMeDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kuangjia);

		img_me = (ImageView) findViewById(R.id.img_me);
		img_Study = (ImageView) findViewById(R.id.img_Study);
		img_recommend = (ImageView) findViewById(R.id.img_recommend);

		// �õ�fragment �Ĺ�����
		FragmentManager fm = getSupportFragmentManager();
		// ����һ������
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.fragment_content, recommended, "content_fragment");


		img_me.setImageResource(R.drawable.dibu__me_draw);
		img_Study.setImageResource(R.drawable.dibu_study_draw);
		img_recommend.setImageResource(R.drawable.dibu__recommend_light);
		ft.commit();

	}

	public void clickMe(View view) {

		FragmentManager fm = getSupportFragmentManager();
		// ����һ������
		FragmentTransaction ft = fm.beginTransaction();

		switch (view.getId()) {
	
		case R.id.img_recommend:
			ft.replace(R.id.fragment_content, recommended);
			img_me.setImageResource(R.drawable.dibu__me_draw);
			img_Study.setImageResource(R.drawable.dibu_study_draw);
			img_recommend.setImageResource(R.drawable.dibu__recommend_light);
			break;


		case R.id.img_Study:
			ft.replace(R.id.fragment_content, study);
			img_me.setImageResource(R.drawable.dibu__me_draw);
			img_Study.setImageResource(R.drawable.dibu__study_light);
			img_recommend.setImageResource(R.drawable.dibu__recommend_draw);
			break;


		case R.id.img_me:
			String SP_INFOS = null;
//			SharedPreferences sp = getSharedPreferences(SP_INFOS, MODE_PRIVATE);
//			uidstr = sp.getString("name", null);
			uidstr=App.currentName;
			if (uidstr != null) {
				ft.replace(R.id.fragment_content, peason_fragment);

			} else {

				dialog();
			}
			img_me.setImageResource(R.drawable.dibu__me_light);
			img_Study.setImageResource(R.drawable.dibu_study_draw);
			img_recommend.setImageResource(R.drawable.dibu__recommend_draw);
			break;

		default:
			break;
		}
		ft.commit();
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(Kuangjia.this);
		builder.setMessage("��δ��¼����ǰ����¼��");

		builder.setTitle("��ʾ");

		builder.setPositiveButton("ȷ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

				Intent intent = new Intent(Kuangjia.this, login.class);
				startActivity(intent);
			}
		});

		builder.setNegativeButton("ȡ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

}
