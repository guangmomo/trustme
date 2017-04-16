package app;

import java.net.PortUnreachableException;

/**
 * Created by xlw on 2017/3/3.
 */

public class Constant {
	//------------数据库的user表的每个属性名-------------
	
    public static final String _ID="_id";
    public static final String NICKNAME="nickname";
    public static final String MOBILE="mobile";
    public static final String PASSWORD="password";
    public static final String SCHOOL="school";
    public static final String AMOUNT="amount";
    public static final String DONG="dong";
    public static final String LOU="lou";
    

    
    //------------数据库的dynamic表的每个属性名-----------
    public static final String SPONSOR="sponsor";
    public static final String RECEIVER="receiver";
    public static final String TAILNUMBER="tailnumber";
    public static final String TIME="time";
    public static final String STATE="state";
    public static final String RANGE="range";
    public static final String SHOP="shop";
    public static final String MONEY="money";

    

    //注册后，固定奖励50个虚拟币
    public static final int INIT_AMOUTN=50;



    //------------------下面是代拿订单的状态-------------------------------

    //等待代拿
    public static final int WAIT_TAKE=1;

    //已代拿
    public static final int HAVE_TAKE=2;

    //发出代拿的人取消订单
    public static final int SPONSOR_CANCEL_ORDER=3;

    //代拿的人点击代拿完成
    public static final int TAKE_COMPLETE=4;

    //确认收货
    public static final int CONFIRM_GOODS=5;

    //已打赏
    public static final int PAY=6;
    
    //为他人代拿的人取消订单
    public static final int RECEIVER_CANCEL_ORDER=7;
    
    //订单被管理员取消
    public static final int ADMIN_CANCEL_ORDER=8;
    
    
    
    
    
    
    //-------------------以下是信息接收区域----------------
    
    //信息接收区域：本校
    public static final int SCHOOL_RANGE=1;
    
    //信息接收区域：本楼
    public static final int FLOOR_RANGE=2;


}
