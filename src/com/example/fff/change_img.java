package com.example.fff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import app.App;
import app.ImageUtil;

//���ҡ���ͷ�����
//������Լ������ݿ⣬�޸�ͷ��ͷ����ֵ��String IMAGE_FILE_NAME;

public class change_img extends Activity {
	

	
	/* ���մ��ͷ����ļ� */
	private File file =new File(Environment.getExternalStorageDirectory(), App.currentName+".jpg");
	
	/* ͷ����ļ���ʱ�ļ������ս�ȡ��ͷ���ļ� */
	private File fileTemp =new File(Environment.getExternalStorageDirectory(), App.currentName+ "_temp.jpg");
	

	private Uri imageUri = Uri.fromFile(fileTemp);//The Uri to store the big bitmap

	/* ����ʶ���� */
	private static final int CODE_GALLERY_REQUEST = 0xa0;
	private static final int CODE_CAMERA_REQUEST = 0xa1;
	private static final int CODE_RESULT_REQUEST = 0xa2;

	// �ü���ͼƬ�Ŀ�(X)�͸�(Y),480 X 480�������Ρ�
	private static int output_X = 480;
	private static int output_Y = 480;

	private ImageView headImage = null;
	private TextView sure;
	private TextView toback;

	private Intent intent3;
	
	
	private Bitmap photo;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chang_img);

		headImage = (ImageView) findViewById(R.id.imageView);
		
		ViewTreeObserver observer=headImage.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				headImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
			   ImageUtil.getImageInstance().setImage(headImage, file);
			}
		});
		
		Button buttonLocal = (Button) findViewById(R.id.buttonLocal);
		buttonLocal.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 if(hasSdcard()){
	                    choseHeadImageFromGallery();
	                }else {
	                    Toast.makeText(getApplication(), "û��SDCard!", Toast.LENGTH_LONG)
	                            .show();
	                }
			}
		});

		Button buttonCamera = (Button) findViewById(R.id.buttonCamera);
		buttonCamera.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(hasSdcard()){
					choseHeadImageFromCameraCapture();
                }else {
                    Toast.makeText(getApplication(), "û��SDCard!", Toast.LENGTH_LONG)
                            .show();
                }
			}
		});

		sure = (TextView) findViewById(R.id.tosure);
		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(ImageUtil.getImageInstance().deleteImage(file)){//ɾ��ԭ���ļ�
					Toast.makeText(App.getContext(),App.currentName+".jpg"+"ɾ���ɹ�",Toast.LENGTH_SHORT).show();
				}else{
					 Toast.makeText(App.getContext(),App.currentName+".jpg"+"ɾ��ʧ��",Toast.LENGTH_SHORT).show();
				}
				
				if(ImageUtil.getImageInstance().changeImageName(fileTemp, App.currentName)){//������ͼƬ
					 Toast.makeText(App.getContext(),"�޸����ֳɹ�",Toast.LENGTH_SHORT).show();
				}else{
					  Toast.makeText(App.getContext(),"�޸�����ʧ��",Toast.LENGTH_SHORT).show();
				}
				
			     change_img.this.finish();
				}
		});

		toback = (TextView) findViewById(R.id.toback);
		toback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(change_img.this, Kuangjia.class);
				//startActivity(intent);
				
				if(ImageUtil.getImageInstance().deleteImage(fileTemp)){//ɾ���ļ�
					Toast.makeText(App.getContext(), App.currentName+ "_temp.jpg"+"ɾ���ɹ�",Toast.LENGTH_SHORT).show();
				}else{
					 Toast.makeText(App.getContext(), App.currentName+ "_temp.jpg"+"ɾ��ʧ��",Toast.LENGTH_SHORT).show();
				}
				finish();

			}
		});
	}

	// �ӱ������ѡȡͼƬ��Ϊͷ��
	private void choseHeadImageFromGallery() {
		  Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
	        intent.setType("image/*");
	        intent.putExtra("crop", "true");
	        intent.putExtra("aspectX", 1);
	        intent.putExtra("aspectY", 1);
	        intent.putExtra("outputX", output_X);
	        intent.putExtra("outputY", output_Y);
	        intent.putExtra("scale", true);
	        intent.putExtra("return-data", false);
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
	        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
	        intent.putExtra("noFaceDetection", true); // no face detection
	        startActivityForResult(intent, CODE_GALLERY_REQUEST);
	}

	// �����ֻ����������Ƭ��Ϊͷ��
	private void choseHeadImageFromCameraCapture() {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
					.fromFile(fileTemp));
		startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// �û�û�н�����Ч�����ò���������
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplication(), "ȡ��", Toast.LENGTH_LONG).show();
			return;
		}

		switch (requestCode) {
		case CODE_GALLERY_REQUEST:
			  if (imageUri != null) {
                  photo = decodeUriAsBitmap(imageUri);//decode bitmap
                  headImage.setImageBitmap(photo);
              }
			break;

		case CODE_CAMERA_REQUEST:
			if (hasSdcard()) {
				cropRawPhoto(Uri.fromFile(fileTemp));
			} else {
				Toast.makeText(getApplication(), "û��SDCard!", Toast.LENGTH_LONG)
						.show();
			}

			break;

		case CODE_RESULT_REQUEST:
			if (intent != null) {
				setImageToHeadView(intent);
			}

			break;
		}

		super.onActivityResult(requestCode, resultCode, intent);
	}
	
	
	private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
		try {
			inputStream = getContentResolver().openInputStream(uri);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        bitmap = BitmapFactory.decodeStream(inputStream);
        try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return bitmap;

    }


	/**
	 * �ü�ԭʼ��ͼƬ
	 */
	public void cropRawPhoto(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		// ���òü�
		intent.putExtra("crop", "true");

		// aspectX , aspectY :��ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX , outputY : �ü�ͼƬ���
		intent.putExtra("outputX", output_X);
		intent.putExtra("outputY", output_Y);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, CODE_RESULT_REQUEST);
	}

	/**
	 * ��ȡ����ü�֮���ͼƬ���ݣ�������ͷ�񲿷ֵ�View
	 */
	private void setImageToHeadView(Intent intent) {
		Bundle extras = intent.getExtras();
		intent3 = intent;
		if (extras != null) {
			photo = extras.getParcelable("data");
			headImage.setImageBitmap(photo);
		}
	}

	/**
	 * ����豸�Ƿ����SDCard�Ĺ��߷���
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// �д洢��SDCard
			return true;
		} else {
			return false;
		}
	}

}