package cn.shengguantu;

import java.util.Vector;
import cn.shengguantu.io.RulesIO;

/**
 * Game.java
 * core of the game
 * 中文
 */

/**
 * @author rwf
 * 
 */
public class Game {
	SGTMainFrame frame;

	private static String log; // 用于内部测试

	private int gongzhu; // 公注

	private static Weizhi[] juanJingGuan = {
			new Weizhi(
					"额外郎中,捐班候补,10,0,0,无,否,,,德=户部郎中;才=刑部郎中;功=工部郎中;良=试用/学习;柔=试用/学习;赃=停;穿花=才;"),
			new Weizhi(
					"额外员外,捐班候补,9,0,0,无,否,,,德=户部员外;才=刑部员外;功=工部员外;良=试用/学习;柔=试用/学习;赃=停;穿花=才;"),
			new Weizhi(
					"额外主事,捐班候补,8,0,0,无,否,,,德=户部主事;才=刑部主事;功=工部主事;良=试用/学习;柔=试用/学习;赃=停;穿花=才;"), };
	private static Weizhi[] juanWaiGuan = {
			new Weizhi(
					"侯补道,捐班候补,12,0,0,无,否,,,德=粮道;才=盐道;功=巡道;良=试用/学习;柔=试用/学习;赃=停;穿花=才;"),
			new Weizhi(
					"侯补府,捐班候补,11,0,0,无,否,,,德=烦府;才=烦府;功=简府;良=试用/学习;柔=试用/学习;赃=停补;穿花=才;"),
			new Weizhi(
					"侯补州,捐班候补,9,0,0,无,否,,,德=直隶州知州;才=直隶州知州;功=知州;良=试用/学习;柔=试用/学习;赃=停;穿花=才;"),
			new Weizhi(
					"侯补县,捐班候补,6,0,0,无,否,,,德=京县;才=烦县;功=简县;良=试用/学习;柔=试用/学习;赃=停;穿花=才;"), };

	public Weizhi[] getjuanJingGuan() {
		return juanJingGuan;
	}
	public Weizhi[] getjuanWaiGuan() {
		return juanWaiGuan;
	}

	private Vector<Player> players;

	public Vector<Player> getPlayers() {
		return players;
	}

	private Vector<String> weizhis; // all the posible positions

	public Vector<String> getWeizhis() {
		return weizhis;
	}

	private int activePlayerIndex = 0;

	public int getActivePlayerIndex() {
		return activePlayerIndex;
	}

	public Player getActivePlayer() {
		return players.elementAt(activePlayerIndex);
	}

	private Vector<String> cubeValues;

	public GameConfig config;

	private boolean allOver;

	public Game(SGTMainFrame mainFrame) {
		frame = mainFrame;
		weizhis = initWeizhis();
		config = new GameConfig();
		newGame();
	}

	private Vector<String> initWeizhis() {
		return RulesIO.load("./Allrules.txt");
	}

	private Vector<Player> initPlayers() {
		// TODO a GUI to enter user name.
		String str = weizhis.get(0);
		Weizhi w = new Weizhi(str);
		Vector<Player> players = new Vector<Player>();
		Player player1 = new Player("甲");
		Player player2 = new Player("乙");
		// Player player3 = new Player("丙");
		// Player player4 = new Player("丁");
		players.add(player1);
		players.add(player2);

		// 每人交纳100个筹码作为公注
		for (int i = 0; i < players.size(); i++) {
			this.playerPayGongzhu(players.get(i), 100);
		}
		// players.add(player3);
		// players.add(player4);
		player1.setWeizhi(w);
		player2.setWeizhi(w);
		return players;
	}

	public String go(String cubeValue) {
		if (allOver) {
			return "朋友，做人要厚道，都说游戏结束了，你还按个死人头！开个新游戏吧！\n";
		}
		String message = "";
		if (!isPosibleCubeValue(cubeValue)) {
			message("能掷出来" + cubeValue + "？\n"); // do nothing if cubeValue
													// not valid.
		} else {
			Player p = players.get(activePlayerIndex);
			message(p.getName());
			message(message + "掷了个" + cubeValue + "\n");

			cubeValues.add(cubeValue);

			Weizhi w;
			while (cubeValues.size() != 0) {
				if (p.getExtraWork() == null) // 这里先觉得是用普通规则还是用差事的规则-A
				{
					w = p.getWeizhi();
				} else {
					w = p.getExtraWork();
					message(p.getName() + "当前正在 "
							+ p.getExtraWork().getGuanzhi()+ " " + cubeValue
							+ " 行: ");
					p.setExtraWork(null);
				}

				String cube = cubeValues.remove(0); // use the first cubeValue
													// and remove it from
													// vector.
				String zoufa;
				Rule rule;
				
				/*以下是条件的部分，大概流程是
				 * 1 条件为选择 -> 看骰子，不中的就rule1，否则选择
				 * 2 条件为1种 -> 看骰子，不中的就rule1，否则rule2
				 * 3 条件为2种 -> 看骰子，不中的就rule1，否则分别判断条件
				*/
				if(w.getTiaojian().equals("选择"))
				{//选择
					rule = w.getRules().get(1);
					if(!rule.isCubeValuePosible(cube))
					{//中了
						Vector<String> items = new Vector<String>();
						items.add(w.getRules().get(0).useRule(cube));
						items.add(w.getRules().get(1).useRule(cube));
						int choose = letUserChoose(items);
						if(choose>=0 && choose<items.size())
						{
							rule = w.getRules().get(choose);
						}
					}
				}
				else if(!w.getTiaojian().isEmpty())
				{//没条件
					rule = w.getRules().firstElement();
				}
				else
				{ // 条件
					if(!w.getRules().get(1).isCubeValuePosible(cube))
					{//不中
						rule = w.getRules().firstElement();
					}
					else
					{//中了一个
						String str[] = w.getTiaojian().split(";");
						rule = w.getRules().firstElement();
						for(int i = 0; i < str.length; i++)
						{
							if(!p.canFullFillCondition(str[i]) && cube.equals(w.getRules().get(1).getCubeValues().get(i)))
							{//其中一个中，但是不符合条件用rule2
								rule = w.getRules().get(1);	
								break;
							}
						}
					
						//rule = w.getRules().get(1)
						//p.canFullFillCondition(w.getTiaojian())) 
						//rule = w.getRules().firstElement();
					}

				}
				/*条件选择rule到这里结束*/

				zoufa = rule.useRule(cube); // 终于得到该怎么走了-下一个位置的名称或者一个新的骰子-B
				if (isASetOfCubeValue(zoufa)) {// 算出来是一个或者几个骰子点数，加到数组里面
					message(zoufa + "\n");
					for (int i = 0; i < zoufa.length(); i++) {
						cubeValues.add(zoufa.substring(i, i + 1));
					}
				} 
				else if (zoufa.isEmpty()) {// 走法为空，初步认为是不走， //TODO 是否有其他情况
					message("留任，仍为" + p.getWeizhi().getGuanzhi() + "\n");
					break;
				 
				} 
				else if (zoufa.equals("回")) {// 玩家在考核或其他不支公注的特殊位置，回原位
					message("回原位，仍为" + p.getWeizhi().getGuanzhi() + "\n");
					break;
				} 
				else if (zoufa.equals("罚回")) {// 玩家在支公注的特殊位置，回原位，追还公注并加罚5筹
					this.playerPayGongzhu(p, 10);
					message("因贪赃销去差事，追还公注5筹并加罚5筹 \n 仍为" + p.getWeizhi().getGuanzhi() + "\n" );
						break;
				} 
				else if (zoufa.equals("倍罚回")) {// 玩家在支公注的特殊位置，回原位，追还公注并加罚10筹
					this.playerPayGongzhu(p, 15);
					message("因贪赃销去差事，追还公注5筹并重罚10筹 \n 仍为" + p.getWeizhi().getGuanzhi() + "\n") ;
						break;
				} 
				else if (zoufa.equals("销去")) {// 玩家在支公注的特殊位置，回原位，追还公注
					this.playerPayGongzhu(p, 5);
					message("因贪赃销去差事，追还公注5筹\n仍为" + p.getWeizhi().getGuanzhi() + "\n");
						break;
				} 
				else if (zoufa.equals("支公注5")) {// 玩家在普通位置，奖励公注5筹
					this.playerPayGongzhu(p, -5);
					message("奉差勤勉，奖励公注5筹\n 仍为" + p.getWeizhi().getGuanzhi() + "\n" );
						break;
				} 
				else if (zoufa.equals("罚5")) {// 玩家在普通位置，罚5筹
					this.playerPayGongzhu(p, 5);
					message("身为胥吏贪渎无状，姑罚纹银5两以观后效\n"+"留任，仍为" + p.getWeizhi().getGuanzhi());
						break;
				} 
				else if(zoufa.equals("大贺"))
				{//一个游戏者游戏结束的情况
					message ( "恭喜恭喜，玩家" + p.getName() + "" +zoufa +"结束了。\n"); 
					this.moneyMoney(TypeOfMoneyCalculation.ONEPLAYER_DAHE);
					this.duiPinJi(p,0);
					setCurrentUserOver();
					break;
				}
				else if(zoufa.equals("贺"))// 未结束玩家各贺10筹
				{
					for (int i = 0; i < this.players.size(); i++) {
						if (!players.elementAt(i).getName().equals(p.getName())&& !players.elementAt(i).getGameover()){
							this.playerPayPlayer(players.elementAt(i), p, 10);
						}
					}
					message ( "未结束玩家各贺10筹。\n");   
					break;
				} 
				else if(zoufa.equals("罚俸复任")) //   每级捐1筹复任
				{
					int fayin = p.getWeizhi().getPinji();
					this.playerPayGongzhu(p, fayin);
						
					message ( "玩家" + p.getName() + "被罚俸纹银" + fayin +"两\n 官复原职："+p.getWeizhi().getGuanzhi()+"\n");   
					
					break;
				} 
				else if(zoufa.equals("捐复"))// 每级捐2筹复任
				{
					int fayin = p.getWeizhi().getPinji()*2;
					this.playerPayGongzhu(p, fayin);
					
					message ( "玩家" + p.getName() + "报效纹银" + fayin +"两\n 官复原职："+p.getWeizhi().getGuanzhi()+"\n");   
					
					break;
				} 
				else if(zoufa.equals("赎罪复任"))// 未结束玩家各贺10筹
				{
					int fayin = p.getWeizhi().getPinji()*3;
					this.playerPayGongzhu(p, fayin);
					
					message ( "玩家" + p.getName() + "缴纳赎罪银" + fayin +"两\n 官复原职："+p.getWeizhi().getGuanzhi()+"\n");   
					
					break;
				} 
				else if(zoufa.equals("加倍赎罪复任"))// 未结束玩家各贺10筹
				{
					int fayin = p.getWeizhi().getPinji()*6;
					this.playerPayGongzhu(p, fayin);
					
					message ( "玩家" + p.getName() + "缴纳赎罪银" + fayin +"两\n 官复原职："+p.getWeizhi().getGuanzhi()+"\n");   
					
					break;
					
					
				} 
				else if(zoufa.equals("予告荣归"))
				{//一个游戏者游戏结束的情况
					message ( "恭喜恭喜，玩家" + p.getName() + "" +zoufa +"结束了。\n");   
					this.moneyMoney(TypeOfMoneyCalculation.ONEPLAYER_YUGAORONGGUI);
					this.duiPinJi(p,1);
					setCurrentUserOver();
					break;
				}
				else if(zoufa.equals("原品休致"))
				{//一个游戏者游戏结束的情况
					message ( "玩家" + p.getName() +zoufa +"结束了。\n"); 
					this.duiPinJi(p,0);
					setCurrentUserOver();
					break;
				}
				else if(zoufa.equals("长戍"))
				{//一个游戏者游戏结束的情况
					String str = findAPosition(zoufa);
					Weizhi weizhiToGoTo = new Weizhi(str);
					p.setWeizhi(weizhiToGoTo);
					message ( "玩家" + p.getName() + "被流放，" +zoufa +"边远军州。\n 结束了。\n");
					setCurrentUserOver();
					break;
				}
				else if(zoufa.equals("出局"))
				{//一个游戏者游戏结束的情况
					String str = findAPosition(zoufa);
					Weizhi weizhiToGoTo = new Weizhi(str);
					p.setWeizhi(weizhiToGoTo);
					message ( "玩家" + p.getName() + "被贬为庶民，永不叙用。 \n 结束了。\n");  
					setCurrentUserOver();
					break;
				}
				else if(zoufa.equals("认输"))
				{//一个游戏者游戏结束的情况
					p.setKefenGongzhu(false);
					message ( "玩家" + p.getName() + zoufa +"\n 结束了。\n");  
					setCurrentUserOver();
					break;
				}
				else if(zoufa.equals("停"))
				{//停一次
					p.setTingYiLun(true);
					message ( "玩家" + p.getName() + zoufa +"一轮，下轮将会被跳过。\n");  
					
					break;
				}
				else 
				{// 算出来是个官职或者办差
					String str = findAPosition(zoufa);
					Weizhi weizhiToGoTo = null;
					if (!str.equals("找不到")) {// 能找到这个职位或者办差
						weizhiToGoTo = new Weizhi(str);
					} else {// 没定义这个职位，
						message("<错误>---------" + zoufa + " 这个职位无法找到，"
								+ p.getName() + "游戏结束。\n");
						setCurrentUserOver();
						return "";
					}

					if (weizhiToGoTo != null) {
						if (weizhiToGoTo.isAWork()) {// 办差
							p.setHasAextraWork(true);
							p.setExtraWork(weizhiToGoTo);
							message(p.getWeizhi().getGuanzhi() + " 得到差事: "
									+ weizhiToGoTo.getYamen());
							message(" " + weizhiToGoTo.getGuanzhi()
									+ " 将在下轮产生效果。\n");
							if(weizhiToGoTo.getShangfa()!=0)
							{
								this.playerPayGongzhu(p, 0-weizhiToGoTo.getShangfa());
								
							}
							if(weizhiToGoTo.getHe()!=0)
							{
								this.allPayHim(weizhiToGoTo.getHe());
							}
						} else {// 普通职位
							p.setHasAextraWork(false);
							p.setWeizhi(weizhiToGoTo);
							message("新职位: " + zoufa + "\n");
							
							if(weizhiToGoTo.getShangfa()!=0)
							{
								this.playerPayGongzhu(p, 0-weizhiToGoTo.getShangfa());
							}
							if(weizhiToGoTo.getHe()!=0)
							{
								this.allPayHim(weizhiToGoTo.getHe());
							}
							
							while (!p.getWeizhi().getZidongWeizhi().isEmpty()) {
								String tmp = p.getWeizhi().getZidongWeizhi();
								str = findAPosition(tmp);
								weizhiToGoTo = new Weizhi(str);
								p.setWeizhi(weizhiToGoTo);
								message("自动获得新职位: " + tmp + "\n");
								if(weizhiToGoTo.getShangfa()!=0)
								{
									this.playerPayGongzhu(p, 0-weizhiToGoTo.getShangfa());
								}
								if(weizhiToGoTo.getHe()!=0)
								{
									this.allPayHim(weizhiToGoTo.getHe());
								}
							}
							this.moneyMoney(TypeOfMoneyCalculation.NEW_WEIZHI);
						}
						//执行新得到的位置的
						p.setConditions(weizhiToGoTo.getTioajianToBeSet());
					}
				}
			}

		}
		nextPlayer();

		return "";

	}
	public String giveUp(Player p){
	
	Weizhi weizhiToGoTo = new Weizhi("认输,结束,0,0,0,无,否,,,德=;才=;功=;良=;柔=;赃=;穿花=;红二对=;素二对=;聚四=;聚六=;聚五=;聚三=;聚二=;聚一=;全四=;全六=;全五=;全三=;全二=;全一=,德=;才=;功=;良=;柔=;赃=;穿花=;红二对=;素二对=;聚四=;聚六=;聚五=;聚三=;聚二=;聚一=;全四=;全六=;全五=;全三=;全二=;全一=");
	p.setWeizhi(weizhiToGoTo);
	p.setKefenGongzhu(false);
	message ( "玩家" + p.getName() + "认输\n 结束了。\n");  
	
	setCurrentUserOver();
	nextPlayer();
	return "";
	}
	private void message(String s) {
		this.sayInMainframe(s);
		log += s;
	}

	private enum TypeOfMoneyCalculation {ONEPLAYER_DAHE, ALLPLAYER_OVER, NEW_WEIZHI,ONEPLAYER_YUGAORONGGUI; }
	
	private void moneyMoney(TypeOfMoneyCalculation type)
	{
		Player cp = this.getActivePlayer();
		switch (type)
		{
		case NEW_WEIZHI: //给上级钱
			String yamen = cp.getWeizhi().getYamen();

			for (int i = 0; i < this.players.size(); i++) {
				Player p = players.get(i);
				if (i != this.getActivePlayerIndex()
						&& yamen.equals(p.getWeizhi().getYamen())) {
					if (cp.getWeizhi().getPinji() > p.getWeizhi().getPinji()) {
						this.playerPayPlayer(p, cp, 5);
						message("下级" + p.getName() + "诚意孝敬五大注！\n");
						message("$$$$$" + cp.getName()
								+ "大人辛苦，下官特备下~厚~礼，不成敬意。请笑纳！\n$$$$$呵呵呵呵");
						message(p.getName() + "大人客气，同朝为官，何必如此～～来人啊，还不赶紧点收去？\n");
					} else if (cp.getWeizhi().getPinji() < p.getWeizhi()
							.getPinji()) {
						this.playerPayPlayer(cp, p, 5);
						message("孝敬上级" + p.getName() + "五大注！\n");
						message("$$$$$" + p.getName()
								+ "大人辛苦，下官特备下~厚~礼，不成敬意。请笑纳！\n$$$$$呵呵呵呵,");
						message(cp.getName() + "大人客气，以后就我罩你了，就冲你这点薄礼。\n");
					} else {// 同级
						message("能与" + cp.getName() + "大人同僚，幸甚幸甚。\n");
					}
				}
			}
			break;
		case ONEPLAYER_DAHE:
			message("未结束的玩家");
			message("各贺30筹。\n");
			allPayHim(30);
			
			break;
		case ONEPLAYER_YUGAORONGGUI:
			message("未结束的玩家");
			message("各贺10筹。\n");
			allPayHim(10);
			break;
		case ALLPLAYER_OVER:
			break;
		}
	}
	/**
	 * 各贺 没结束的给当前玩家钱
	 * @param money 钱数
	 */
	private void allPayHim(int money)
	{
		Player cp = this.getActivePlayer();
		for(int i = 0; i<players.size(); i++)
		{
			Player p = players.get(i);
			if(i!=this.getActivePlayerIndex() && !p.getGameover())
			{
				this.playerPayPlayer(p, cp, money);
			}
		}		
	}
	
	public String juanGuan(Player actual, Weizhi toBeJuan) {
		int i = payforJuanGuan(actual, toBeJuan);
		this.playerPayGongzhu(actual, i);
		
		actual.setWeizhi(toBeJuan);
		message ("缴纳纹银" + i + "两,捐得职位: " + actual.getWeizhi().getGuanzhi() + "\n");
		
		this.nextPlayer();
		return "";
	}

	public int payforJuanGuan(Player p, Weizhi tbj) {
		 int juan = 0;

		int x = p.getWeizhi().getPinji();
		int y = tbj.getPinji();
		if (!p.getWeizhi().isInCapital()) {

			juan = (y - x) * 5;
		} else {
			for (int i = 0; i < 3; i++) {
				if (this.getjuanJingGuan()[i].getGuanzhi().equals(tbj.getGuanzhi())) {
					juan = (y - x) * 5;
				}
			}
			for (int j = 0; j < 4; j++) {
				if (this.getjuanWaiGuan()[j].getGuanzhi().equals(tbj.getGuanzhi())) {
					juan = (y - x - 1) * 5;
				}

			}
		}
		
		return juan;
	}



	private String nextPlayer() {
		allOver = true;
		int howManyPlayersStillAlive = 0;
		for (int i = 0; i < getPlayers().size(); i++) {
			if (players.get(i).getGameover() != true) {
				howManyPlayersStillAlive++;
			}	
		}
		if(howManyPlayersStillAlive > 1)
			allOver = false;
		
		if (allOver) {
			message( "全部玩家结束。游戏结束。\n");
			fenGongZhu(); //计算等级并且互相给钱
			return "";
		} 
		else 
		{//游戏还没结束，一定有2个或以上玩家没有结束，找下一个没结束的玩家，如果他罚停，设为不停，再找下一个。
			boolean found = false;
			while(!found)
			{
				activePlayerIndex = (activePlayerIndex + 1) % getPlayers().size();
				if (!players.get(activePlayerIndex).getGameover()
					&& !players.get(activePlayerIndex).isTingYiLun())
				{
					found = true;
				}
				else if(players.get(activePlayerIndex).isTingYiLun())
				{
					players.get(activePlayerIndex).setTingYiLun(false);
					message(getPlayers().get(activePlayerIndex).getName() + "被跳过");
				}	
			}			
			message( "************************\n" + "-轮到玩家 "
					+ getPlayers().get(activePlayerIndex).getName() + "\n");
			return "";
		}

	}

	public void setCurrentUserOver() {
		this.players.get(activePlayerIndex).setGameover(true);
		this.cubeValues.clear();
	}

	private String findAPosition(String value) {
		String str = "";
		boolean found = false;
		for (int i = 0; i < weizhis.size(); i++) {
			str = weizhis.get(i);
			String tmpstr = str.split(",")[0];

			if (tmpstr.equals(value)) {
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("出现错误，找不到这个官职: " + value + " CANNOT be found!");
			return "找不到";
		} else
			return str;
	}

	public void newGame() {
		allOver = false;
		gongzhu = 0;
		players = initPlayers();
		cubeValues = new Vector<String>();
		activePlayerIndex = 0;
		sayInMainframe("+++++++++++++++++++++++++++++++++++\n-新游戏开始" + "\n");
		sayInMainframe("-轮到玩家 " + getPlayers().get(activePlayerIndex).getName()
				+ "\n");
	}

	private void sayInMainframe(String s) {
		frame.appendLog(s);
	}
	private int letUserChoose(Vector<String> items) {
		return frame.letUserChoose(items.toArray());
		
	}

	static private boolean isASetOfCubeValue(String value) {
		boolean result = true;
		if (value.length() == 0)
			return false;
		for (int i = 0; i < value.length() && result == true; i++) {
			result = result
					&& isPosibleReturnCubeValue(value.substring(i, i + 1));
		}
		return result;
	}

	static private boolean isPosibleCubeValue(String value) {

		return (value.equals("德") || value.equals("才") || value.equals("功")
				|| value.equals("良") || value.equals("柔") || value.equals("赃")
				|| value.equals("穿花") || value.equals("红二对")
				|| value.equals("素二对") || value.equals("聚四")
				|| value.equals("聚六") || value.equals("聚五")
				|| value.equals("聚三") || value.equals("聚二")
				|| value.equals("聚一") || value.equals("全四")
				|| value.equals("全六") || value.equals("全五")
				|| value.equals("全三") || value.equals("全二") || value
				.equals("全一"));
	}

	static private boolean isPosibleReturnCubeValue(String value) {

		return (value.equals("德") || value.equals("才") || value.equals("功")
				|| value.equals("良") || value.equals("柔") || value.equals("赃")
				|| value.equals("穿花"));

	}
	
	/**
	 * 分公注
	 * 是用作游戏结束以后结算，分两步，首先对品级互结，然后分剩下的公注
	 */
	private void fenGongZhu()
	{
		//对品级，低级给高级付钱
		for(int i = 0; i <this.players.size()-1; i++)
		{
			if(players.get(i).getWeizhi().getGuanzhi()!="长戍" 
				&& players.get(i).getWeizhi().getGuanzhi()!="出局"
				&& players.get(i).getWeizhi().getGuanzhi()!="认输")
			{//三种情况不对等级
				for(int j = i+1; j<this.players.size(); j++)
				{
					{
						int pjj = players.get(j).getWeizhi().getPinji();
						int pji = players.get(i).getWeizhi().getPinji();
						if(pji<pjj)
						{
							message(players.get(i).getName()+" 低 " + players.get(j).getName()+ (pjj-pji) + "级\n");
							playerPayPlayer(players.get(i), players.get(j), 5*(pjj-pji));
						}
						else if(pji == pjj)
						{
							message(players.get(i).getName() +" 跟 " + players.get(j).getName()+ "同级\n");
						}
						else 
						{
							message(players.get(j).getName()+" 低 " + players.get(i).getName()+ -(pjj-pji) + "级\n");
							playerPayPlayer(players.get(i), players.get(j), 5*(pjj-pji));
						}						
					}
				}
			}
		}
		//分公注
		Vector<Player> fenQianPlayer =new Vector<Player>();
		for(int i = 0; i <this.players.size(); i++)
		{
			if(players.get(i).isKefenGongzhu())
			{
				fenQianPlayer.add(players.get(i));
			}
		}
		int money = this.getGongzhu()/fenQianPlayer.size();
		money = 0 - money;
		while(!fenQianPlayer.isEmpty())
		{
			this.playerPayGongzhu(fenQianPlayer.remove(0), money);
		}
	}
	
	/**
	 * 方法用于玩家退休后放到等级考上面等
	 * @param p 就是那个玩家
	 * @param bonus  是不是先+多少级才对
	 */
	private void duiPinJi(Player p, int bonus)
	{
		int pinji = p.getWeizhi().getPinji()+bonus;
		String zoufa ="";
		switch(pinji)
		{
		case 18:
			zoufa = "光禄大夫";
			break;
		case 17:
			zoufa = "荣禄大夫";
			break;
		case 16:
			zoufa = "资政大夫";
			break;
		case 15:
			zoufa = "通奉大夫";
			break;
		case 14:
			zoufa = "通议大夫";
			break;
		case 13:
			zoufa = "中议大夫";
			break;
		case 12:
			zoufa = "中宪大夫";
			break;
		case 11:
			zoufa = "朝议大夫";
			break;
		case 10:
			zoufa = "奉政大夫";
			break;
		case 9:
			zoufa = "奉直大夫";
			break;
		case 8:
			zoufa = "承德郎";
			break;
		case 7:
			zoufa = "儒林郎";
			break;
		case 6:
			zoufa = "文林郎";
			break;
		case 5:
			zoufa = "征仕郎";
			break;
		case 4:
			zoufa = "修职郎";
			break;
		case 3:
			zoufa = "修职佐郎";
			break;
		case 2:
			zoufa = "登仕郎";
			break;
		case 1:
			zoufa = "登仕佐郎";
			break;
		case 0:
			
		}
		if(!zoufa.isEmpty())
		{
			String str = this.findAPosition(zoufa);
			Weizhi weizhi = new Weizhi(str); 
			p.setWeizhi(weizhi);
			message("退休了领个虚衔" + zoufa + "，混吃等死中...\n");
		}
		else
		{
			//不可能到这里，除非你连品级都没有就挂了。
			System.out.println("没成功退休");
		}
	}

	public int getGongzhu() {
		return gongzhu;
	}

	private void addGongzhu(int gongzhu) {
		this.gongzhu += gongzhu;
	}

	/**
	 * 筹码变化发生在玩家和公注之间
	 * 
	 * @param p
	 *            玩家
	 * @param money
	 *            玩家给的钱 负值的话是支公注
	 */
	public void playerPayGongzhu(Player p, int money) {
		p.addChouma(0 - money);
		this.addGongzhu(money);
		if(money>0)
			message(p.getName()+ "给公注付 "+money+"两。\n");
		else
			message("支公注 "+ (-money) +"两。\n");
			
	}

	/**
	 * 筹码变化发生在玩家之间
	 * 
	 * @param payer
	 *            付款人
	 * @param bePaider
	 *            收款人
	 * @param money
	 *            交易数额
	 */
	public void playerPayPlayer(Player payer, Player bePaider, int money) {
		payer.addChouma(0 - money);
		bePaider.addChouma(money);
		if(money>0)
			message(payer.getName() + " 给 " + bePaider.getName() + money + "两。\n");
		else
			message(bePaider.getName() + " 给 " + payer.getName() + (-money) + "两。\n");
			
	}
	
}
