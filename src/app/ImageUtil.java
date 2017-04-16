package app;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.fff.R;

/**
 * Created by xlw on 2017/3/31.
 */

public class ImageUtil {

    private volatile static ImageUtil imageInstance;

    private ImageUtil(){}

    public static ImageUtil getImageInstance(){
        if(imageInstance==null){
            synchronized (ImageUtil.class){
                if(imageInstance==null){
                    imageInstance = new ImageUtil();
                }
            }
        }
        return imageInstance;
    }

   //��ѯͼƬ�Ƿ����
    public boolean isImageExists(File file){
        return file.exists();
    }

  //�޸�ͼƬ������	
    public boolean changeImageName(File file,String newName){
        String parent=file.getParent();
        File newFile = new File(parent + File.separatorChar+newName+".jpg");
        return file.renameTo(newFile);

    }

    //��ȡͼƬ
    public Bitmap getImage(ImageView imageView, File file){
        Bitmap bitmap= decodeSampledBitmapFromFile(file,imageView.getWidth(),imageView.getHeight());
        return bitmap;
    }
    
    
    /**
     * ����豸�Ƿ����SDCard�Ĺ��߷���
     */
    public  boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // �д洢��SDCard
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * ɾ��һ��ͼƬ
     * @param file
     * @return
     */
    public boolean deleteImage(File file){
        if(file.isFile() &&  file.exists()&& file.delete()){
            	  Log.e("fff","ɾ���ɹ�");
                  return true;
        }else{
        	   Log.e("fff","ɾ��ʧ��");
               return false;
        }
    }
    
    
    public void setImage(ImageView imageView,File file){
    	Bitmap bitmap=null;
    	if(hasSdcard()){
	    	bitmap=getImage(imageView,file);
	    	if(bitmap!=null){
	    		imageView.setImageBitmap(bitmap);
	    	}else{
	    		bitmap=decodeSampledBitmapFromResource(App.getContext().getResources(), R.drawable.img1,
	    	            imageView.getWidth(), imageView.getHeight());
	    		imageView.setImageBitmap(bitmap);
	    	}
	    }else{
	    	bitmap=decodeSampledBitmapFromResource(App.getContext().getResources(), R.drawable.img1,
    	            imageView.getWidth(), imageView.getHeight());
    		imageView.setImageBitmap(bitmap);
	    }
    }
    
    /**
     * 从文件流中解析bitmap
     * @param fis
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public  Bitmap decodeSampledBitmapFromFile(File file,
            int reqWidth, int reqHeight) {
    	// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
       // BitmapFactory.decodeResource(res, resId, options);
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return   BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }
    
    /**
     * 从R资源中解析bitmap
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public  Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
            int reqWidth, int reqHeight) {
    	// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 计算bitmap的inSampleSize
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public  int calculateInSampleSize(BitmapFactory.Options options,
    		int reqWidth, int reqHeight) {
    	// 源图片的高度和宽度
    	final int height = options.outHeight;
    	final int width = options.outWidth;
    	int inSampleSize = 1;
    	if (height > reqHeight || width > reqWidth) {
    		// 计算出实际宽高和目标宽高的比率
    		final int heightRatio = Math.round((float) height / (float) reqHeight);
    		final int widthRatio = Math.round((float) width / (float) reqWidth);
    		// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
    		// 一定都会大于等于目标的宽和高。
    		inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    	}
    	return inSampleSize;
    }

    

}
