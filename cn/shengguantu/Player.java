package cn.shengguantu;

public class Player {

	private String name;

	private String chushen;

	private boolean xieban;
	private boolean xiangshi;
	private boolean huishi;
	private boolean juren;
	
	//三元
	private boolean zhuangyuan;
	private boolean xieyuan;
	private boolean huiyuan;

	private Weizhi weizhi;
	private Weizhi extraWork = null;
	private boolean hasAextraWork;
	private int chouma;
	private boolean kefenGongzhu =true;
	private boolean tingYiLun = false;

	private boolean gameover;

	public Player(String n) {
		this.name = n;
		this.chushen = null;
		this.xieban = false;
		this.xiangshi = false;
		this.huishi = false;
		this.weizhi = null;
		this.extraWork = null;
		this.hasAextraWork = false;
		this.chouma = 500;
		//this.xingdongjiesu = false;
		//this.zuofa = null;
		this.gameover = false;
		//this.guanwei = null;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String s) {
		this.name = s;
	}

	public int getChouma() {
		return this.chouma;
	}

	public void addChouma(int i) {
		this.chouma += i;
	}
	
	public void reduceChouma(int i) {
		this.chouma -= i;
	}

	
	public String getChushen() {
		return this.chushen;
	}

	public void setchushen(String s) {
		this.chushen = s;
	}

	public boolean getGameover() {
		return this.gameover;
	}

	public void setGameover(boolean b) {
		this.gameover = b;
	}

	public boolean getHasAextraWork() {
		return this.hasAextraWork;
	}

	public void setHasAextraWork(boolean b) {
		this.hasAextraWork = b;
	}



	public void setHuishi(boolean b) {
		this.huishi = b;
	}

	


	public void setXiangshi(boolean b) {
		this.xiangshi = b;
	}


	public void setXieban(boolean b) {
		this.xieban = b;
	}

	public Weizhi getWeizhi() {
		return this.weizhi;
	}

	public void setZhisi(Weizhi wz) {
		this.weizhi = wz;
	}

	public String getInfo() {
		if(getWeizhi() == null)
			return "n/a";
		else
		{
			String info = "";
			info += this.getName();
			info += "  -  ";
			info += Player.getPinjiText(getWeizhi().getPinji());
			info += "  ";
			info += this.getWeizhi().getGuanzhi();
			info += "  ";
			info += this.getWeizhi().getYamen();
			info += "  家财: 纹银";
			info += this.getChouma() + "两";
			if(this.getExtraWork()!=null)
			{
				info += " + " + this.getExtraWork().getGuanzhi();
			}
			return info;
		}
	}

	public boolean canFullFillCondition(String tiaojian) {
		//TODO add conditions
		if(tiaojian == "会试")
			return this.isHuishi();
		else if(tiaojian == "乡试")
			return this.isXiangshi();
		else if(tiaojian == "协办")
			return this.isXieban();
		else if(tiaojian == "举人")
			return this.isJuren();
		else if(tiaojian == "连中三元")
			return (this.isHuiyuan()&&this.isXieyuan()&&this.isZhuangyuan());
		else
			return false;
	}
	
	public void setConditions(String tiaojian){
		if(tiaojian.contains("会试"))
			setHuishi(true);
		if(tiaojian.contains("乡试"))
			setXiangshi(true);
		if(tiaojian.contains("协办"))
			setXieban(true);
		if(tiaojian.contains("举人"))
			setJuren(true);
		if(tiaojian.contains("会元"))
			setHuiyuan(true);
		if(tiaojian.contains("解元"))
			setXieyuan(true);
		if(tiaojian.contains("状元"))
			setZhuangyuan(true);
	
	}

	public Weizhi getExtraWork() {
		return extraWork;
	}

	public void setExtraWork(Weizhi extraWork) {
		this.extraWork = extraWork;
	}

	public void setWeizhi(Weizhi weizhi) {
		this.weizhi = weizhi;
	}
	
	public static String getPinjiText(int value)
	{
		String retValue = "";
		switch(value)
		{
			case 18:
				retValue = "正一品";
				break;
			case 17:
				retValue = "从一品";
				break;
			case 16:
				retValue = "正二品";
				break;
			case 15:
				retValue = "从二品";
				break;
			case 14:
				retValue = "正三品";
				break;
			case 13:
				retValue = "从三品";
				break;
			case 12:
				retValue = "正四品";
				break;
			case 11:
				retValue = "从四品";
				break;
			case 10:
				retValue = "正五品";
				break;
			case 9:
				retValue = "从五品";
				break;
			case 8:
				retValue = "正六品";
				break;
			case 7:
				retValue = "从六品";
				break;
			case 6:
				retValue = "正七品";
				break;
			case 5:
				retValue = "从七品";
				break;
			case 4:
				retValue = "正八品";
				break;
			case 3:
				retValue = "从八品";
				break;
			case 2:
				retValue = "正九品";
				break;
			case 1:
				retValue = "从九品";
				break;
			case 0:
				retValue = "";
				break;
					
		}
		return retValue;
	}

	public boolean isKefenGongzhu() {
		return kefenGongzhu;
	}

	public void setKefenGongzhu(boolean kefenGongzhu) {
		this.kefenGongzhu = kefenGongzhu;
	}

	public boolean isTingYiLun() {
		return tingYiLun;
	}

	public void setTingYiLun(boolean tingYiLun) {
		this.tingYiLun = tingYiLun;
	}

	public boolean isJuren() {
		return juren;
	}

	public void setJuren(boolean juren) {
		this.juren = juren;
	}

	public boolean isZhuangyuan() {
		return zhuangyuan;
	}

	public void setZhuangyuan(boolean zhuangyuan) {
		this.zhuangyuan = zhuangyuan;
	}

	public boolean isXieyuan() {
		return xieyuan;
	}

	public void setXieyuan(boolean xieyuan) {
		this.xieyuan = xieyuan;
	}

	public boolean isHuiyuan() {
		return huiyuan;
	}

	public void setHuiyuan(boolean huiyuan) {
		this.huiyuan = huiyuan;
	}

	public boolean isXieban() {
		return xieban;
	}

	public boolean isXiangshi() {
		return xiangshi;
	}

	public boolean isHuishi() {
		return huishi;
	}
}
