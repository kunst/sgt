package cn.shengguantu;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SelectJuanGuan extends JDialog implements ActionListener 
{
	private JTable sele_List;
	private JButton b_select;
	private JButton b_cancel;
	private Weizhi selectedWeizhi;
	private String newInfo;
	private Game agame;
	private Vector<Vector<String>> data;

	public SelectJuanGuan(final JFrame jf, final String title, final boolean b, final Game g) {
		super(jf, title, b);
		init(g);
	}

	private void init(final Game game) {

		this.agame = game;

		this.setLayout(new BorderLayout());
		final JPanel buttonPanel = new JPanel();
		final JPanel labelPanel = new JPanel();
		final JLabel jLabel = new JLabel("请在表中选择一个要捐的官职：");
		labelPanel.add(jLabel);
		final Vector<String> columnNames = new Vector<String>();
		columnNames.add("可捐的官职");
		columnNames.add("品级");
		columnNames.add("需捐纳纹银");
		// this.toBeSelect = new Vector<String>();
		this.data = new Vector<Vector<String>>();
		if (!agame.getActivePlayer().getWeizhi().isInCapital()) {
			for (int i = 0; i < 3; i++) {

				if (this.agame.getActivePlayer().getWeizhi().getPinji() < agame
						.getjuanJingGuan()[i].getPinji()) {
					final Vector<String> s = new Vector<String>();

					s.add(agame.getjuanJingGuan()[i].getGuanzhi());
					s.add(Player.getPinjiText(agame.getjuanJingGuan()[i]
							.getPinji()));
					s.add(Integer.toString(agame.payforJuanGuan(this.agame
							.getActivePlayer(), agame.getjuanJingGuan()[i])));
					this.data.add(s);

				}
			}
			for (int j = 0; j < 4; j++) {

				if (this.agame.getActivePlayer().getWeizhi().getPinji() < agame
						.getjuanWaiGuan()[j].getPinji()) {
					final Vector<String> s = new Vector<String>();
					s.add(agame.getjuanWaiGuan()[j].getGuanzhi());
					s.add(Player.getPinjiText(agame.getjuanWaiGuan()[j]
							.getPinji()));
					s.add(Integer.toString(agame.payforJuanGuan(this.agame
							.getActivePlayer(), agame.getjuanWaiGuan()[j])));
					this.data.add(s);

				}
			}

		} else {
			for (int i = 0; i < 3; i++) {

				if (this.agame.getActivePlayer().getWeizhi().getPinji() < agame
						.getjuanJingGuan()[i].getPinji()) {
					final Vector<String> s = new Vector<String>();
					s.add(agame.getjuanJingGuan()[i].getGuanzhi());
					s.add(Player.getPinjiText(agame.getjuanJingGuan()[i]
							.getPinji()));
					s.add(Integer.toString(agame.payforJuanGuan(this.agame
							.getActivePlayer(), agame.getjuanJingGuan()[i])));
					this.data.add(s);

				}
			}
			for (int j = 0; j < 4; j++) {

				if (this.agame.getActivePlayer().getWeizhi().getPinji() + 1 < agame
						.getjuanWaiGuan()[j].getPinji()) {
					final Vector<String> s = new Vector<String>();
					s.add(agame.getjuanWaiGuan()[j].getGuanzhi());
					s.add(Player.getPinjiText(agame.getjuanWaiGuan()[j]
							.getPinji()));
					s.add(Integer.toString(agame.payforJuanGuan(this.agame
							.getActivePlayer(), agame.getjuanWaiGuan()[j])));
					this.data.add(s);

				}
			}
		}
		System.out.println(this.data);

		this.sele_List = new JTable(data, columnNames);
		// this.sele_List.addMouseListener(this);

		final JScrollPane js = new JScrollPane(sele_List);
		this.b_select = new JButton("确定");
		this.b_cancel = new JButton("放弃");
		buttonPanel.add(b_select);
		buttonPanel.add(b_cancel);
		b_select.addActionListener(this);
		b_cancel.addActionListener(this);

		this.add(labelPanel, BorderLayout.NORTH);
		this.add(js, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setSize(300, 240);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	public Weizhi getSelectedWeizhi() {
		if (sele_List.getSelectedRow() != -1) {
			for (int i = 0; i < 3; i++) {
				if (agame.getjuanJingGuan()[i].getGuanzhi().equals(
						sele_List.getValueAt(sele_List.getSelectedRow(), 0))) {
					selectedWeizhi = agame.getjuanJingGuan()[i];
				}
			}
			for (int j = 0; j < 4; j++) {
				if (agame.getjuanWaiGuan()[j].getGuanzhi().equals(
						sele_List.getValueAt(sele_List.getSelectedRow(), 0))) {
					selectedWeizhi = agame.getjuanWaiGuan()[j];
				}

			}
		}
		return selectedWeizhi;
	}

	public String getNewInfo() {
		return this.newInfo;
	}

	public void actionPerformed(final ActionEvent e) {

		if (e.getActionCommand() == "确定") {
			if (sele_List.getSelectedRow() != -1) {
				this.newInfo = agame.juanGuan(agame.getActivePlayer(), this
						.getSelectedWeizhi());
				this.dispose();

			}
		}
		if (e.getActionCommand() == "放弃") {

			this.dispose();

		}

	}


}
