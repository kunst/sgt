package cn.shengguantu;

import java.util.Vector;

public class Weizhi {
	private String guanzhi = ""; // 官职（名称 唯一）
	private String yamen = ""; // 衙门
	private int pinji = 0; // 品级 0-18 对应无-壹品
	private int shangfa = 0; // 赏罚
	private int he = 0; // 贺
	private String tiaojian = "无"; // 条件
	private boolean isAWork = false; // 差事
	private String tioajianToBeSet = ""; // 设置条件
	private String zidongWeizhi = ""; // 自动跳转到别的位置
	private Vector<Rule> rules = new Vector<Rule>();// 走法

	public Weizhi(String guanzhi, String yamen, int pinji, int shangfa, int he,
			String tiaojian, boolean isAWork, Vector<Rule> rules) {
		super();
		this.guanzhi = guanzhi;
		this.yamen = yamen;
		this.pinji = pinji;
		this.shangfa = shangfa;
		this.he = he;
		this.tiaojian = tiaojian;
		this.isAWork = isAWork;
		this.rules = rules;
	}

	/**
	 * all 对应rules.txt中的一行 例如：
	 * 生员,出身,无,0,0,无,否,德=经魁;才=举人;功=拔贡;良=优贡;柔=增生;穿花=才;红二对=解元;
	 */
	public Weizhi(String all) {
		super();
		String[] str = all.split(",");
		if (str.length < 9) {
			System.out.println("初始化官职文本" + all + "发生错误，可能导致不可预知错误");
			return;
		} else {
			this.guanzhi = str[0];
			this.yamen = str[1];
			this.pinji = Integer.parseInt(str[2]);
			this.shangfa = Integer.parseInt(str[3]);
			this.he = Integer.parseInt(str[4]);
			this.tiaojian = str[5];
			this.isAWork = !str[6].equals("否");
			this.tioajianToBeSet = str[7];
			this.zidongWeizhi = str[8];
			if (str.length >= 10)
				for (int i = 9; i < str.length; i++)
					this.rules.add(new Rule(str[i])); // 从9开始之后的都是rule
		}

	}

	public String getGuanzhi() {
		return guanzhi;
	}

	public String getYamen() {
		return yamen;
	}

	public int getPinji() {
		return pinji;
	}

	public int getShangfa() {
		return shangfa;
	}

	public int getHe() {
		return he;
	}

	public String getTiaojian() {
		return tiaojian;
	}

	public boolean isAWork() {
		return isAWork;
	}

	public Vector<Rule> getRules() {
		return rules;
	}

	public String getTioajianToBeSet() {
		return tioajianToBeSet;
	}

	public String getZidongWeizhi() {
		return zidongWeizhi;
	}

	public boolean isInCapital() {
		if (this.yamen.equals("各部院") || this.yamen.equals("兵马司")
				|| this.yamen.equals("学院") || this.yamen.equals("京府")
				|| this.yamen.equals("外府") || this.yamen.equals("直隶厅")
				|| this.yamen.equals("直隶州") || this.yamen.equals("州")
				|| this.yamen.equals("县") || this.yamen.equals("漕院")
				|| this.yamen.equals("河院") || this.yamen.equals("盐院")
				|| this.yamen.equals("布政司") || this.yamen.equals("按察司")
				|| this.yamen.equals("各道")) {
			return false;
		}
		if (this.yamen.equals("礼部") || this.yamen.equals("吏部")
			|| this.yamen.equals("刑部") || this.yamen.equals("工部")
			|| this.yamen.equals("兵部") || this.yamen.equals("户部")
			|| this.yamen.equals("大理寺") || this.yamen.equals("太常寺")
			|| this.yamen.equals("太仆寺") || this.yamen.equals("鸿胪寺")
			|| this.yamen.equals("光禄寺") || this.yamen.equals("内廷")
			|| this.yamen.equals("通政司") || this.yamen.equals("都察院")
			|| this.yamen.equals("会同四驿馆")|| this.yamen.equals("殿阁")
			|| this.yamen.equals("国子监") || this.yamen.equals("翰林院")
			|| this.yamen.equals("内务府") || this.yamen.equals("詹事府")
			|| this.yamen.equals("中书科") || this.yamen.equals("六科给事中")
			|| this.yamen.equals("内阁") || this.yamen.equals("理藩院")
			|| this.yamen.equals("宗人府") || this.yamen.equals("銮仪卫")
			|| this.yamen.equals("宫衔"))	
		{
			return true;
		}
		return false;
	}
}