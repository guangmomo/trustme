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

//“我”中头像更换
//如果可以加入数据库，修改头像，头像数值：String IMAGE_FILE_NAME;

public class change_img extends Activity {
	

	
	/* 最终存放头像的文件 */
	private File file =new File(Environment.getExternalStorageDirectory(), App.currentName+".jpg");
	
	/* 头像的文件临时文件，即刚截取的头像文件 */
	private File fileTemp =new File(Environment.getExternalStorageDirectory(), App.currentName+ "_temp.jpg");
	

	private Uri imageUri = Uri.fromFile(fileTemp);//The Uri to store the big bitmap

	/* 请求识别码 */
	private static final int CODE_GALLERY_REQUEST = 0xa0;
	private static final int CODE_CAMERA_REQUEST = 0xa1;
	private static final int CODE_RESULT_REQUEST = 0xa2;

	// 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
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
	                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
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
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }
			}
		});

		sure = (TextView) findViewById(R.id.tosure);
		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(ImageUtil.getImageInstance().deleteImage(file)){//删除原有文件
					Toast.makeText(App.getContext(),App.currentName+".jpg"+"删除成功",Toast.LENGTH_SHORT).show();
				}else{
					 Toast.makeText(App.getContext(),App.currentName+".jpg"+"删除失败",Toast.LENGTH_SHORT).show();
				}
				
				if(ImageUtil.getImageInstance().changeImageName(fileTemp, App.currentName)){//重命名图片
					 Toast.makeText(App.getContext(),"修改名字成功",Toast.LENGTH_SHORT).show();
				}else{
					  Toast.makeText(App.getContext(),"修改名字失败",Toast.LENGTH_SHORT).show();
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
				
				if(ImageUtil.getImageInstance().deleteImage(fileTemp)){//删除文件
					Toast.makeText(App.getContext(), App.currentName+ "_temp.jpg"+"删除成功",Toast.LENGTH_SHORT).show();
				}else{
					 Toast.makeText(App.getContext(), App.currentName+ "_temp.jpg"+"删除失败",Toast.LENGTH_SHORT).show();
				}
				finish();

			}
		});
	}

	// 从本地相册选取图片作为头像
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

	// 启动手机相机拍摄照片作为头像
	private void choseHeadImageFromCameraCapture() {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
					.fromFile(fileTemp));
		startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// 用户没有进行有效的设置操作，返回
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
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
				Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
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
	 * 裁剪原始的图片
	 */
	public void cropRawPhoto(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		// 设置裁剪
		intent.putExtra("crop", "true");

		// aspectX , aspectY :宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX , outputY : 裁剪图片宽高
		intent.putExtra("outputX", output_X);
		intent.putExtra("outputY", output_Y);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, CODE_RESULT_REQUEST);
	}

	/**
	 * 提取保存裁剪之后的图片数据，并设置头像部分的View
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
	 * 检查设备是否存在SDCard的工具方法
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// 有存储的SDCard
			return true;
		} else {
			return false;
		}
	}

}