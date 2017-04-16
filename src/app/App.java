package app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

/**
 * Created by xlw on 2017/3/3.
 */

public class App extends Application {
    public static String currentName; //当前登录的用户的昵称

    public static String currentDong;//当前用户所在宿舍的栋数
    
    public static String sAdminAccount="1";
    
    public static String sAdminPassword="1";
    
   
    
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this.getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
    
  
}
