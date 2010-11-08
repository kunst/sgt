package cn.shengguantu;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author 
 * 
 */
public class SGTMainFrame extends JFrame implements ActionListener 
{
	private Game game;

	private JTextArea logBox;

	private JButton jButton0;

	private JButton jButton1;

	private JButton jButton2;

	private JButton jButton3;

	private JButton jButton4;

	private JButton jButton5;

	private JButton jButton6;

	private JButton jButton7;

	private JButton jButton8;

	private JButton jButton9;

	private JButton jButton10;

	private JButton jButton11;

	private JButton jButton12;

	private JButton jButton13;

	private JButton jButton14;

	private JButton jButton15;

	private JButton jButton16;

	private JButton jButton17;

	private JButton jButton18;

	private JButton jButton19;

	private JButton jButton20;

	private JButton jButtonJuanGuan;

	private JButton jButtonNewGame;

	private JButton jButtonGiveUp;

	private JButton jButtonOK;

	private JPanel playerPanel;

	private JLabel labelInfo[];

	private JComboBox weizhiChooser;

	private JLabel lebelGongzhu;

	public SGTMainFrame() {
		super("升官图 v0.1");
		logBox = new JTextArea();
		// logBox.append("-游戏开始:\n");
		logBox.setEditable(false);
		game = new Game(this);
		JPanel buttonPanel = buildCubePanel();
		playerPanel = buildPlayerPenal();

		this.setLayout(new GridLayout(0, 1));
		JScrollPane scrollPane = new JScrollPane(logBox);

		JPanel chooser = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel jLabel = new JLabel("将当前玩家的位置转换为:");
		weizhiChooser = new JComboBox(game.getWeizhis());
		jButtonOK = new JButton("确定");
		jButtonOK.addActionListener(this);

		chooser.add(jLabel);
		chooser.add(weizhiChooser);
		chooser.add(jButtonOK);

		this.add(playerPanel);
		this.add(scrollPane);
		this.add(buttonPanel);
		this.add(chooser);

		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		// this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JPanel buildCubePanel() {

		jButton0 = new JButton("德");
		jButton1 = new JButton("才");
		jButton2 = new JButton("功");
		jButton3 = new JButton("良");
		jButton4 = new JButton("柔");
		jButton5 = new JButton("赃");
		jButton6 = new JButton("穿花");
		jButton7 = new JButton("红二对");
		jButton8 = new JButton("素二对");
		jButtonNewGame = new JButton("新游戏");
		jButton9 = new JButton("聚四");
		jButton10 = new JButton("聚六");
		jButton11 = new JButton("聚五");
		jButton12 = new JButton("聚三");
		jButton13 = new JButton("聚二");
		jButton14 = new JButton("聚一");
		jButton15 = new JButton("全四");
		jButton16 = new JButton("全六");
		jButton17 = new JButton("全五");
		jButton18 = new JButton("全三");
		jButton19 = new JButton("全二");
		jButton20 = new JButton("全一");
		jButtonJuanGuan = new JButton("捐官");
		jButtonJuanGuan.setEnabled(false);
		jButtonGiveUp = new JButton("认输");

		JPanel buttonPanel = new JPanel(new GridLayout(0, 8));

		buttonPanel.add(jButton0);
		jButton0.addActionListener(this);
		buttonPanel.add(jButton1);
		jButton1.addActionListener(this);
		buttonPanel.add(jButton2);
		jButton2.addActionListener(this);
		buttonPanel.add(jButton3);
		jButton3.addActionListener(this);
		buttonPanel.add(jButton4);
		jButton4.addActionListener(this);
		buttonPanel.add(jButton5);
		jButton5.addActionListener(this);
		buttonPanel.add(jButton6);
		jButton6.addActionListener(this);
		buttonPanel.add(jButton7);
		jButton7.addActionListener(this);
		buttonPanel.add(jButton8);
		jButton8.addActionListener(this);
		buttonPanel.add(jButton9);
		jButton9.addActionListener(this);
		buttonPanel.add(jButton10);
		jButton10.addActionListener(this);
		buttonPanel.add(jButton11);
		jButton11.addActionListener(this);
		buttonPanel.add(jButton12);
		jButton12.addActionListener(this);
		buttonPanel.add(jButton13);
		jButton13.addActionListener(this);
		buttonPanel.add(jButton14);
		jButton14.addActionListener(this);
		buttonPanel.add(jButton15);
		jButton15.addActionListener(this);
		buttonPanel.add(jButton16);
		jButton16.addActionListener(this);
		buttonPanel.add(jButton17);
		jButton17.addActionListener(this);
		buttonPanel.add(jButton18);
		jButton18.addActionListener(this);
		buttonPanel.add(jButton19);
		jButton19.addActionListener(this);
		buttonPanel.add(jButton20);
		jButton20.addActionListener(this);
		buttonPanel.add(jButtonJuanGuan);
		jButtonJuanGuan.addActionListener(this);
		buttonPanel.add(jButtonNewGame);
		jButtonNewGame.addActionListener(this);
		buttonPanel.add(jButtonGiveUp);
		jButtonGiveUp.addActionListener(this);
		return buttonPanel;
	}

	private JPanel buildPlayerPenal() {
		lebelGongzhu = new JLabel("公注");
		JLabel label3 = new JLabel("玩家信息");

		JPanel panel = new JPanel(new GridLayout(0, 1, 10, 0));
		panel.add(lebelGongzhu);
		panel.add(label3);
		labelInfo = new JLabel[game.getPlayers().size()];
		for (int i = 0; i < game.getPlayers().size(); i++) {
			labelInfo[i] = new JLabel(game.getPlayers().get(i).getInfo());
			panel.add(labelInfo[i]);
		}
		updatePlayerInfos();
		return panel;
	}

	private void updatePlayerInfos() {
		String s;
		lebelGongzhu.setText("公注纹银" + game.getGongzhu()+"两");
		int active = game.getActivePlayerIndex();
		for (int i = 0; i < game.getPlayers().size(); i++) 
		{
			s = game.getPlayers().get(i).getInfo();
			if(i == active)
				s = ">> 当前 <<  " + s;
			else
				s = ">> 等待 <<  " + s;
			labelInfo[i].setText(s);
		}
	}

	public void appendLog(String s) {
		this.logBox.append(s);

	}

	public void actionPerformed(ActionEvent e) {
		if (!e.getSource().equals(jButtonNewGame)
				&& !e.getSource().equals(jButtonOK)
				&& !e.getSource().equals(jButtonGiveUp)
				&& !e.getSource().equals(jButtonJuanGuan)) {
			game.go(e.getActionCommand());
			updatePlayerInfos();
			this.logBox.selectAll();
			int i = this.game.getActivePlayer().getWeizhi().getPinji();
			if (!this.game.getActivePlayer().getWeizhi().isInCapital()) {
				if (i <= 11 && i > 0
						&& !this.game.getActivePlayer().getHasAextraWork()) {
					this.jButtonJuanGuan.setEnabled(true);
				} else {
					this.jButtonJuanGuan.setEnabled(false);
				}
			} else {
				if (i <= 10
						&& i > 0
						&& !this.game.getActivePlayer().getHasAextraWork()
						&& !this.game.getActivePlayer().getWeizhi().getYamen()
								.equals("太医院")
						&& !this.game.getActivePlayer().getWeizhi().getYamen()
								.equals("钦天监")) {
					this.jButtonJuanGuan.setEnabled(true);
				} else {
					this.jButtonJuanGuan.setEnabled(false);
				}
			}
		}
		if (e.getSource().equals(jButtonNewGame)) {
			int yORn = JOptionPane.showConfirmDialog(null,
					"要开始一个新游戏吗?现在的游戏将会丢失!", "Warning",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (yORn != JOptionPane.YES_OPTION) {
			} else {
				game.newGame();
				updatePlayerInfos();
				this.logBox.selectAll();
			}

		}
		if (e.getSource().equals(jButtonOK)) {

			Weizhi w = new Weizhi((String) weizhiChooser.getSelectedItem());
			game.getActivePlayer().setWeizhi(w);
			this.logBox.append("新职位: " + w.getGuanzhi() + "\n");
			//game.getActivePlayer()
			updatePlayerInfos();
			this.logBox.selectAll();
		}
		if (e.getSource().equals(jButtonJuanGuan)) {

			SelectJuanGuan sjg = new SelectJuanGuan(this, "捐官", true, this.game);
			this.logBox.append(sjg.getNewInfo());
			updatePlayerInfos();
			this.logBox.selectAll();
		}

		if (e.getSource().equals(jButtonGiveUp)) {
			int yORn = JOptionPane.showConfirmDialog(null,
					"退出游戏？！不用对品级，没有贺筹，也不能分公注！要认输吗?", "Warning",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (yORn != JOptionPane.YES_OPTION) {
			} else {
				this.game.giveUp(this.game.getActivePlayer());
				updatePlayerInfos();
				this.logBox.selectAll();
			}
			
		}
	}

	public static void main(String[] args) {

		new SGTMainFrame();

	}

	public int letUserChoose(Object[] items) {
		String s = (String)JOptionPane.showInputDialog(
		                    this,
		                    "人生的抉择",
		                    "人生剧场选人生",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    items,null
		                    );

		//If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
		    for(int i = 0; i<items.length; i++)
		    {
		    	if(s.equals((String)items[i]))
		    		return i;
		    }
		    
		}

		//If you're here, the return value was null/empty.
		return 0;
	}

}
