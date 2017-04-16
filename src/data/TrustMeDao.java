package data;

/**
 * Created by xu on 17-1-10.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import app.App;
import app.Constant;



import java.util.ArrayList;
import java.util.List;


/**
 * 用于操作数据库的类
 */
public class TrustMeDao {

    private DBHelper dbHelper;

  

    public TrustMeDao() {
        dbHelper = new DBHelper(App.getContext());
    }


    /**
     * 向user表添加一条数据
     * @param
     */
    public void addUser(UserInfo info) {
        ContentValues values=new ContentValues();
        values.put(Constant.NICKNAME,info.getNickName());
        values.put(Constant.MOBILE,info.getMobile());
        values.put(Constant.PASSWORD,info.getPassword());
        values.put(Constant.SCHOOL,info.getSchool());
        values.put(Constant.DONG,info.getDong());
        values.put(Constant.LOU, info.getLou());
        values.put(Constant.AMOUNT, info.getAmount());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.insert("user",null,values);
            db.close();
        }
    }

    /**
     * 向dynamic表添加一条数据
     * @param
     */
    public void addDynamic(DynamicInfo info) {
        ContentValues values=new ContentValues();
        values.put(Constant.SPONSOR,info.getSponsor());
        values.put(Constant.RECEIVER,info.getReceiver());
        values.put(Constant.DONG,info.getDong());
        values.put(Constant.LOU,info.getLou());
        values.put(Constant.TAILNUMBER,info.getTailNumber());
        values.put(Constant.TIME,info.getTime());
        values.put(Constant.STATE,info.getState());
        values.put(Constant.RANGE,info.getRange());
        values.put(Constant.SHOP,info.getShop());
        values.put(Constant.MONEY,info.getMoney());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.insert("dynamic",null,values);
            db.close();
        }
    }

    /**
     * 根据用户输入的栋数筛选出对应的动态集合
     * @param dong
     * @return
     */
    public List<DynamicInfo> findDynamic(String dong){
    	 List<DynamicInfo> listDyamic=new ArrayList<>();
         SQLiteDatabase db = dbHelper.getReadableDatabase();
         if (db.isOpen()) {
        	 Cursor cursor = db.query("dynamic",null,null,null,null,null,null);
             while (cursor.moveToNext()){
                 DynamicInfo info=new DynamicInfo();
                 String dongTemp=cursor.getString(cursor.getColumnIndex(Constant.DONG));        //取出来的这条数据的dong数
                 int rangeTemp=cursor.getInt(cursor.getColumnIndex(Constant.RANGE));      //取出来的这条数据的range数
                 if((App.currentDong.equals(dong) && dongTemp.equals(dong))                            //同一栋的，只要栋数相同的动态即可
                		 || (rangeTemp==Constant.SCHOOL_RANGE && dongTemp.equals(dong))){ //不同栋的，需要信息是本校可见的，且栋数相同的动态
                     info.setState(cursor.getInt(cursor.getColumnIndex(Constant.STATE)));
                     info.setTime(cursor.getString(cursor.getColumnIndex(Constant.TIME)));
                     info.setSponsor(cursor.getString(cursor.getColumnIndex(Constant.SPONSOR)));
                     info.setTailNumber(cursor.getString(cursor.getColumnIndex(Constant.TAILNUMBER)));
                     info.setId(cursor.getInt(cursor.getColumnIndex(Constant._ID)));
                     info.setReceiver(cursor.getString(cursor.getColumnIndex(Constant.RECEIVER)));
                     info.setDong(cursor.getString(cursor.getColumnIndex(Constant.DONG)));
                     info.setLou(cursor.getString(cursor.getColumnIndex(Constant.LOU)));
                     info.setRange(cursor.getInt(cursor.getColumnIndex(Constant.RANGE)));
                     info.setShop(cursor.getString(cursor.getColumnIndex(Constant.SHOP)));
                     info.setMoney(cursor.getFloat(cursor.getColumnIndex(Constant.MONEY)));
                     listDyamic.add(info);
                 }
             }
          cursor.close();
          db.close();
         }
         return listDyamic;
    }
    
    
    /**
     * 获取我代拿的动态
     * @param dong
     * @return
     */
    public List<DynamicInfo> findMyTakeDynamic(){
    	 List<DynamicInfo> listDyamic=new ArrayList<>();
         SQLiteDatabase db = dbHelper.getReadableDatabase();
         if (db.isOpen()) {
        	 Cursor cursor = db.query("dynamic",null,null,null,null,null,null);
             while (cursor.moveToNext()){
                 DynamicInfo info=new DynamicInfo();
                 String receiver=cursor.getString(cursor.getColumnIndex(Constant.RECEIVER));        
                 if((App.currentName.equals(receiver))){ //receiver和当前的用户相同，说明是当前用户代拿的动态
                     info.setState(cursor.getInt(cursor.getColumnIndex(Constant.STATE)));
                     info.setTime(cursor.getString(cursor.getColumnIndex(Constant.TIME)));
                     info.setSponsor(cursor.getString(cursor.getColumnIndex(Constant.SPONSOR)));
                     info.setTailNumber(cursor.getString(cursor.getColumnIndex(Constant.TAILNUMBER)));
                     info.setId(cursor.getInt(cursor.getColumnIndex(Constant._ID)));
                     info.setReceiver(cursor.getString(cursor.getColumnIndex(Constant.RECEIVER)));
                     info.setDong(cursor.getString(cursor.getColumnIndex(Constant.DONG)));
                     info.setLou(cursor.getString(cursor.getColumnIndex(Constant.LOU)));
                     info.setRange(cursor.getInt(cursor.getColumnIndex(Constant.RANGE)));
                     info.setShop(cursor.getString(cursor.getColumnIndex(Constant.SHOP)));
                     info.setMoney(cursor.getFloat(cursor.getColumnIndex(Constant.MONEY)));
                     listDyamic.add(info);
                 }
             }
          cursor.close();
          db.close();
         }
         return listDyamic;
    }
    
    
    /**
     * 获取我发出的动态
     * @param dong
     * @return
     */
    public List<DynamicInfo> findMySendDynamic(){
    	 List<DynamicInfo> listDyamic=new ArrayList<>();
         SQLiteDatabase db = dbHelper.getReadableDatabase();
         if (db.isOpen()) {
        	 Cursor cursor = db.query("dynamic",null,null,null,null,null,null);
             while (cursor.moveToNext()){
                 DynamicInfo info=new DynamicInfo();
                 String sponsor=cursor.getString(cursor.getColumnIndex(Constant.SPONSOR));        
                 if((App.currentName.equals(sponsor))){ //sponsor和当前的用户相同，说明是当前用户发出的代拿动态
                     info.setState(cursor.getInt(cursor.getColumnIndex(Constant.STATE)));
                     info.setTime(cursor.getString(cursor.getColumnIndex(Constant.TIME)));
                     info.setSponsor(cursor.getString(cursor.getColumnIndex(Constant.SPONSOR)));
                     info.setTailNumber(cursor.getString(cursor.getColumnIndex(Constant.TAILNUMBER)));
                     info.setId(cursor.getInt(cursor.getColumnIndex(Constant._ID)));
                     info.setReceiver(cursor.getString(cursor.getColumnIndex(Constant.RECEIVER)));
                     info.setDong(cursor.getString(cursor.getColumnIndex(Constant.DONG)));
                     info.setLou(cursor.getString(cursor.getColumnIndex(Constant.LOU)));
                     info.setRange(cursor.getInt(cursor.getColumnIndex(Constant.RANGE)));
                     info.setShop(cursor.getString(cursor.getColumnIndex(Constant.SHOP)));
                     info.setMoney(cursor.getFloat(cursor.getColumnIndex(Constant.MONEY)));
                     listDyamic.add(info);
                 }
             }
          cursor.close();
          db.close();
         }
         return listDyamic;
    }
    
    
    /**
     * 修改个人信息，目前只修改昵称和密码
     * @param name
     * @param password
     */
    public void updateUserInfo(String name, String password){
    	SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            db.execSQL("update user set nickname= ?,password=? where nickname= ?",new Object[]{name,password,App.currentName});
            db.close();
        }
    }
    
    /**
     * 修改个人信息，更新发出代拿的人
     * @param name
     * @param password
     */
    public void updateSponsor(String oldSponsor, String newSponsor){
    	SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            db.execSQL("update dynamic set sponsor= ? where sponsor= ?",new String[]{newSponsor , oldSponsor});
            db.close();
        }
    }
    
    
    /**
     * 修改个人信息，更新帮他人代拿的人
     * @param name
     * @param password
     */
    public void updateReceiver(String oldReceiver, String newReceiver){
    	SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            db.execSQL("update dynamic set receiver= ? where receiver= ?",new String[]{newReceiver , oldReceiver});
            db.close();
        }
    }
    
  

    /**
     * 更新用户的虚拟币
     * @param amount 
     * @param name
     */
    public void updateMoney(float amount,String name){
    	  SQLiteDatabase db = dbHelper.getReadableDatabase();
          if (db.isOpen()) {
              db.execSQL("update user set amount= ? where nickname= ?",new Object[]{amount,name});
              db.close();
          }
    }
    

    
    

    /**
     * 获取mobile的密码,,如果没有查找到，就返回null
     * @param mobile
     * @return
     */
    public String findPass(String mobile){
        String password=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select password from user where mobile = ? ", new String[]{mobile});
            if(cursor.moveToFirst()){
                password=cursor.getString(cursor.getColumnIndex("password"));
            }else{
                Log.e("test","数据库中没有找到密码");
                return null;
            }
            cursor.close();
            db.close();
        }
        return password;
    }

    /**
     * 根据昵称去查找用户的信息
     * @param nickname
     * @return  符合条件的数据组成的List
     */
    public UserInfo findUserByNickname(String nickname){
        UserInfo userInfo=new UserInfo();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from user where nickname = ? ", new String[]{nickname});
            if(!cursor.moveToFirst()) {
                Log.e("test", "数据库中没有该昵称的用户数据");
            }else{
                    userInfo.setId(cursor.getInt(cursor.getColumnIndex(Constant._ID)));
                    userInfo.setDong(cursor.getString(cursor.getColumnIndex(Constant.DONG)));
                    userInfo.setLou(cursor.getString(cursor.getColumnIndex(Constant.LOU)));
                    userInfo.setMobile(cursor.getString(cursor.getColumnIndex(Constant.MOBILE)));
                    userInfo.setNickName(cursor.getString(cursor.getColumnIndex(Constant.NICKNAME)));
                    userInfo.setPassword(cursor.getString(cursor.getColumnIndex(Constant.PASSWORD)));
                    userInfo.setSchool(cursor.getString(cursor.getColumnIndex(Constant.SCHOOL)));
                    userInfo.setAmount(cursor.getFloat(cursor.getColumnIndex(Constant.AMOUNT)));
            }
            cursor.close();
            db.close();
        }
        return userInfo;
    }


    /**
     * 根据手机号码去查找用户的信息
     * @param mobile
     * @return
     */
    public UserInfo findUserByMobile(String mobile){
        UserInfo userInfo=new UserInfo();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from user where mobile = ? ", new String[]{mobile});
            if(!cursor.moveToFirst()) {
                Log.e("test", "数据库中没有该手机号的用户数据");
            }else{
                    userInfo.setId(cursor.getInt(cursor.getColumnIndex(Constant._ID)));
                    userInfo.setDong(cursor.getString(cursor.getColumnIndex(Constant.DONG)));
                    userInfo.setLou(cursor.getString(cursor.getColumnIndex(Constant.LOU)));
                    userInfo.setMobile(cursor.getString(cursor.getColumnIndex(Constant.MOBILE)));
                    userInfo.setNickName(cursor.getString(cursor.getColumnIndex(Constant.NICKNAME)));
                    userInfo.setPassword(cursor.getString(cursor.getColumnIndex(Constant.PASSWORD)));
                    userInfo.setSchool(cursor.getString(cursor.getColumnIndex(Constant.SCHOOL)));
                    userInfo.setAmount(cursor.getFloat(cursor.getColumnIndex(Constant.AMOUNT)));
            }
            cursor.close();
            db.close();
        }
        return userInfo;
    }



    /**
     * 判断昵称已经被注册过了
     * @param nickname
     * @return
     */
    public boolean isNicknameExist(String nickname){
        boolean result=false;
        Cursor cursor=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            cursor = db.rawQuery("select * from user where nickname = ? ", new String[]{nickname});
            if(cursor.moveToFirst()) {
               result=true;
            }
            cursor.close();
            db.close();
        }
        return result;
    }





    /**
     * 判断手机号是否已经被注册过了
     * @param mobile
     * @return
     */
    public boolean isMobileExist(String mobile){
        boolean result=false;
        Cursor cursor=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            cursor = db.rawQuery("select * from user where mobile = ? ", new String[]{mobile});
            if(cursor.moveToFirst()) {
               result=true;
            }
            cursor.close();
            db.close();
        }
        return result;
    }

    /**
     * 更新指定id的动态的state,代拿者
     * @param id
     * @param dynamicState
     * @return
     */
    public boolean updateDynamicState( int dynamicState,String receiver,int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            db.execSQL("update dynamic set state= ?, receiver=? where _id= ?",new Object[]{dynamicState,receiver,id});
            db.close();
        }
        return true;
    }

    
    
    /**
     * 更新指定id的动态的state
     * @param id
     * @param dynamicState
     * @return
     */
    public boolean updateDynamicState(int dynamicState,int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            db.execSQL("update dynamic set state= ? where _id= ?",new Object[]{dynamicState,id});
            db.close();
        }
        return true;
    }

    
    /**
     * 根据当前的用户名查询用户的虚拟币有多少
     * @param nickname
     * @return
     */
    public float findAmount(String nickname){
        float amount=-1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select amount from user where nickname = ? ", new String[]{nickname});
            if(cursor.moveToFirst()){
                amount=cursor.getFloat(cursor.getColumnIndex("amount"));
            }else{
                Log.e("test","数据库中没有找到指定昵称的money amount");
            }
            cursor.close();
            db.close();
        }
        return amount;
    }

    /**
     * 获取所有用户
     * @param dong
     * @return
     */
    public List<UserInfo> findAllUser(){
    	 List<UserInfo> listUser=new ArrayList<>();
         SQLiteDatabase db = dbHelper.getReadableDatabase();
         if (db.isOpen()) {
        	 Cursor cursor = db.query("user",null,null,null,null,null,null);
             while (cursor.moveToNext()){
            	  UserInfo userInfo=new UserInfo();
            	  userInfo.setId(cursor.getInt(cursor.getColumnIndex(Constant._ID)));
                  userInfo.setDong(cursor.getString(cursor.getColumnIndex(Constant.DONG)));
                  userInfo.setLou(cursor.getString(cursor.getColumnIndex(Constant.LOU)));
                  userInfo.setMobile(cursor.getString(cursor.getColumnIndex(Constant.MOBILE)));
                  userInfo.setNickName(cursor.getString(cursor.getColumnIndex(Constant.NICKNAME)));
                  userInfo.setPassword(cursor.getString(cursor.getColumnIndex(Constant.PASSWORD)));
                  userInfo.setSchool(cursor.getString(cursor.getColumnIndex(Constant.SCHOOL)));
                  userInfo.setAmount(cursor.getFloat(cursor.getColumnIndex(Constant.AMOUNT)));
                  listUser.add(userInfo);
             }
          cursor.close();
          db.close();
         }
         return listUser;
    }
    
    

    
    /**
     * 获取所有动态
     * @param dong
     * @return
     */
    public List<DynamicInfo> findAllDynatic(){
    	 List<DynamicInfo> listDyamic=new ArrayList<>();
         SQLiteDatabase db = dbHelper.getReadableDatabase();
         if (db.isOpen()) {
        	 Cursor cursor = db.query("dynamic",null,null,null,null,null,null);
             while (cursor.moveToNext()){
            	 DynamicInfo info=new DynamicInfo();
            	 info.setState(cursor.getInt(cursor.getColumnIndex(Constant.STATE)));
                 info.setTime(cursor.getString(cursor.getColumnIndex(Constant.TIME)));
                 info.setSponsor(cursor.getString(cursor.getColumnIndex(Constant.SPONSOR)));
                 info.setTailNumber(cursor.getString(cursor.getColumnIndex(Constant.TAILNUMBER)));
                 info.setId(cursor.getInt(cursor.getColumnIndex(Constant._ID)));
                 info.setReceiver(cursor.getString(cursor.getColumnIndex(Constant.RECEIVER)));
                 info.setDong(cursor.getString(cursor.getColumnIndex(Constant.DONG)));
                 info.setLou(cursor.getString(cursor.getColumnIndex(Constant.LOU)));
                 info.setRange(cursor.getInt(cursor.getColumnIndex(Constant.RANGE)));
                 info.setShop(cursor.getString(cursor.getColumnIndex(Constant.SHOP)));
                 info.setMoney(cursor.getFloat(cursor.getColumnIndex(Constant.MONEY)));
                 listDyamic.add(info);
             }
          cursor.close();
          db.close();
         }
         return listDyamic;
    }
    
    


}
