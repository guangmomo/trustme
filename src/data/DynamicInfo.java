package data;

/**
 * Created by xlw on 2017/3/3.
 */

public class DynamicInfo {


    private int id;
    private String sponsor;    //������
    private String receiver;   //������
    private String dong;      //���ᶰ��
 	private String lou;       //����¥��
    private String tailNumber; //�ֻ�β��
    private String time;       //����ʱ��
    private int state;         //����״̬�������Constant���е�ע��
    private int range;         //��Χ  1��ʾ��¥��2��ʾ��У
	private String shop;       //����С����
    private float money;      //���ͽ��


    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }


    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
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

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}
}
