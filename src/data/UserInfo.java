package data;

/**
 * Created by xlw on 2017/3/3.
 * 用户的信息类
 */

public class UserInfo {


    private float    id;        //用户id
    private String nickName;  //用户昵称
    private String mobile;    //用户手机号
    private String password;  //用户密码
    private String school;    //用户学校
    private String dong;      //宿舍栋数
	private String lou;       //宿舍楼数
    private float    amount;	  //虚拟币的数量

    public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public float getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public String getLou() {
		return lou;
	}

	public void setLou(String lou) {
		this.lou = lou;
	}

}
