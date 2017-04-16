package com.example.fff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import app.App;

public class MainActivity extends Activity {

	 private final long SPLASH_LENGTH = 2000;    
     Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    handler.postDelayed(new Runnable() {  //ʹ��handler��postDelayedʵ����ʱ��ת  
            
            public void run() {  
            	Intent intent=null;
            	if(App.currentName!=null){//����û���¼�����Ͳ��ٵ�¼
            		intent= new Intent(MainActivity.this, Kuangjia.class); 
            	}else{
            		intent=new Intent(MainActivity.this,login.class);
            	} 
                startActivity(intent);    
                finish();       
            }    
        }, SPLASH_LENGTH);//2�����ת��Ӧ��������
          
    }  


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
