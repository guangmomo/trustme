package data;

/**
 * Created by xlw on 2017/3/3.
 * �û�����Ϣ��
 */

public class UserInfo {


    private float    id;        //�û�id
    private String nickName;  //�û��ǳ�
    private String mobile;    //�û��ֻ���
    private String password;  //�û�����
    private String school;    //�û�ѧУ
    private String dong;      //���ᶰ��
	private String lou;       //����¥��
    private float    amount;	  //����ҵ�����

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
