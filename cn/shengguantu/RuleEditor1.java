package cn.shengguantu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Editor.java
 */

/**
 * @author rwf
 *
 */
public class RuleEditor1 extends JFrame implements ActionListener, KeyListener, ListSelectionListener 
{
	private final Vector<String> rulesInOneString = new Vector<String>();;
	private JLabel[] j;

	private JTextField[] tfCube1;

	private JTextField[] tfCube2;

	private JLabel[] j1;

	private JTextField[] weizhi;

	private JTextField suchField;
	
	private final JLabel info = new JLabel("/0个职位");
	private final JTextField tfCurrentNr = new JTextField("0",3);

	private JButton btSuch;

	private JList ruleList;

	private final JButton btAdd = new JButton("新增规则");

	private final JButton btDel = new JButton("删除规则");

	private final JButton btLoad = new JButton("打开文件");

	private final JButton btExport = new JButton("导出");

	JPanel editorPanel;

	JPanel printPanel;
	private int oldIndex = -1;
	private boolean edited = false;
	
	private final JTextField input1;
	private final JButton toRule;
	

	private JPanel buildRulePanel() 
	{

		tfCube1 = new JTextField[21];
		for (int i = 0; i < tfCube1.length; i++) {
			tfCube1[i] = new JTextField("", 8);
			tfCube1[i].addKeyListener(this);
		}
		tfCube2 = new JTextField[21];
		for (int i = 0; i < tfCube2.length; i++) {
			tfCube2[i] = new JTextField("", 8);
			tfCube2[i].addKeyListener(this);
		}
		j = new JLabel[21];
		j[0] = new JLabel("德=");
		j[1] = new JLabel("才=");
		j[2] = new JLabel("功=");
		j[3] = new JLabel("良=");
		j[4] = new JLabel("柔=");
		j[5] = new JLabel("赃=");
		j[6] = new JLabel("穿花=");
		j[7] = new JLabel("红二对=");
		j[8] = new JLabel("素二对=");
		j[9] = new JLabel("聚四=");
		j[10] = new JLabel("聚六=");
		j[11] = new JLabel("聚五=");
		j[12] = new JLabel("聚三=");
		j[13] = new JLabel("聚二=");
		j[14] = new JLabel("聚一=");
		j[15] = new JLabel("全四=");
		j[16] = new JLabel("全六=");
		j[17] = new JLabel("全五=");
		j[18] = new JLabel("全三=");
		j[19] = new JLabel("全二=");
		j[20] = new JLabel("全一=");

		final JPanel panel = new JPanel(new GridLayout(0, 3, 2, 1));
		panel.add(new JLabel(""));
		panel.add(new JLabel("默认规则"));
		panel.add(new JLabel("条件规则"));
		for (int i = 0; i < j.length; i++) {
			panel.add(j[i]);
			panel.add(tfCube1[i]);
			panel.add(tfCube2[i]);
		}

		return panel;
	}

	private JPanel buildWeizhiPanel() 
	{
		final JPanel panel = new JPanel(new GridLayout(0, 2, 2, 1));
		j1 = new JLabel[9];
		j1[0] = new JLabel("官职");
		j1[1] = new JLabel("衙门");
		j1[2] = new JLabel("品级");
		j1[3] = new JLabel("赏罚=");
		j1[4] = new JLabel("贺=");
		j1[5] = new JLabel("条件=");
		j1[6] = new JLabel("差事=");
		j1[7] = new JLabel("设置条件=");
		j1[8] = new JLabel("自动跳转=");

		for (int i = 0; i < 2; i++) {
			panel.add(new JLabel(""));
		}
		weizhi = new JTextField[9];
		for (int i = 0; i < weizhi.length; i++) {
			panel.add(j1[i]);
			weizhi[i] = new JTextField("", 8);
			weizhi[i].addKeyListener(this);
			panel.add(weizhi[i]);
		}
		for (int i = 0; i < 24; i++) {
			panel.add(new JLabel(""));
		}

		weizhi[6].setText("否");
		weizhi[5].setText("无");
		weizhi[4].setText("0");
		weizhi[3].setText("0");
		
		return panel;
	}

	private JPanel buildLeftPanel()
	{
		JPanel p2;
		suchField = new JTextField("", 10);
		btSuch = new JButton("搜索");
		btSuch.addActionListener(this);
		final JPanel p1 = new JPanel(new FlowLayout());
		tfCurrentNr.addKeyListener(this);
		p1.add(tfCurrentNr);
		p1.add(info);
		p1.add(suchField);
		p1.add(btSuch);
		ruleList = new JList(new Vector<String>());
		ruleList.addListSelectionListener(this);
		final JScrollPane ruleScroll = new JScrollPane(ruleList);
		p2 = new JPanel(new BorderLayout());
		p2.add(p1, BorderLayout.NORTH);
		p2.add(ruleScroll, BorderLayout.CENTER);
		final JPanel p3 = new JPanel(new FlowLayout());
		btAdd.addActionListener(this);
		btDel.addActionListener(this);
		btLoad.addActionListener(this);
		btExport.addActionListener(this);
		//btSave.addActionListener(this);
		p3.add(btAdd);
		p3.add(btDel);
		p3.add(btLoad);
		//p3.add(btSave);
		p3.add(btExport);
		p2.add(p3, BorderLayout.SOUTH);
		return p2;
	}
	
	public RuleEditor1() 
	{
		super("规则编辑器v0.11");
		setLayout(new BorderLayout());
		
//		**********************************************************************
		final JPanel jPanel = new JPanel();
		final JLabel l1 = new JLabel("文本转换");
		this.input1 = new JTextField(50);
		this.toRule = new JButton("确定");
		toRule.addActionListener(this);
		jPanel.add(l1);
		jPanel.add(input1);
		jPanel.add(toRule);
		
//		**********************************************************************
		
		final JPanel editorPanel = new JPanel(new BorderLayout(10, 15));
		editorPanel.add(buildLeftPanel(), BorderLayout.WEST);
		editorPanel.add(buildWeizhiPanel(), BorderLayout.CENTER);
		editorPanel.add(buildRulePanel(), BorderLayout.EAST);
		
		editorPanel.add(jPanel, BorderLayout.NORTH);
		
		add(editorPanel, BorderLayout.CENTER);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		this.load();
	}
	
	public String readFromFile(final FileInputStream fistream)
    {
        String returnString = null;
        final InputStream is =  fistream;
        if (is != null)
        {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int ch = 0;
            try
            {
                while ((ch = is.read()) != -1)
                {
                    baos.write(ch);
                }
                final byte[] data = baos.toByteArray();
                returnString = new String(data, "UTF-8");
               
                is.close();
               baos.flush();
                baos.close(); 
            } catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
        return returnString;
    }
	public void savetoFile(final String s, final File f)
    {
		try {
			final String output = s;
			final FileOutputStream fos = new FileOutputStream(f);
			final byte[] data = output.getBytes("UTF-8");
			final ByteArrayInputStream bais = new ByteArrayInputStream(data);
			if (bais != null){
				int ch = 0;
				try
				{
					while ((ch = bais.read()) != -1)
        			{
        				fos.write(ch);
        			}
       			} catch (final IOException e)	{
        		e.printStackTrace();
				}
			}
			bais.close();
			fos.flush();
			fos.close(); 
		}
		
		catch (final IOException e)
    	{
    		e.printStackTrace();
    	} 
    }

	public void actionPerformed(final ActionEvent e) 
	{
		if (e.getActionCommand() == "打开文件") 
		{
			load();
		}
		else if (e.getActionCommand() == "导出") 
		{
			save();
		}
		else if (e.getActionCommand() == "新增规则") 		
		{
			add();
		}
		else if (e.getActionCommand() == "删除规则") 
		{
			del();
		}
		else if (e.getActionCommand() == "搜索") 
		{
			searsh();
		}else if (e.getActionCommand() == "确定") 
		{
			texttoRule();
		}
	}
	private void texttoRule() 
	{
		//*********************************************************************
		final String[] rule= {",,",",0,0,无,否,,,德=",";才=",";功=",";良=",";柔=",";赃=",";穿花=",";红二对=",";素二对=;"};
		final String[] split = this.input1.getText().split("@");
		
		final String[] tmp = split[0].split("  ");
		final String[] tmp1 = split[1].split("  ");
		for(int i = 0; i<tmp1.length; i++){
			if (tmp1[i].equals("大计")||tmp1[i].contains("处分")||tmp1[i].contains("钦差")||tmp1[i].contains("兼衔")||tmp1[i].contains("大考")||tmp1[i].contains("京察")||tmp1[i].contains("宫衔")||tmp1[i].contains("特恩 ")) {
				tmp1[i] = "";
			}
		}
		if (tmp.length >=2) {
			if (tmp[1].contains("未入")) {
				tmp[1] = "无";
			}
		}
		if (tmp.length >=9) {
			if (tmp[8].contains("穿花: 军功")) {
				if (tmp[1].contains("一") || tmp[1].contains("二")) {
					tmp[8] = "军功一品二品";
				} else if (tmp[1].contains("三") || tmp[1].contains("四")
						|| tmp[1].contains("五")) {
					tmp[8] = "军功三至五品";
				} else {
					System.out.println(tmp[1]);
					tmp[8] = "军功六品以下";
				}
			}
		}
		String test = "";
		String input = "";
		input += (tmp[0]+rule[0]);
		input += (tmp[1]+rule[1]);
		for(int i = 0; i<tmp1.length; i++){
			input += (tmp1[i]+tmp[i+2]+rule[i+2]);
			test += tmp1[i]+" ";
		}
		System.out.println(test);
		for(int i = tmp1.length+2; i<tmp.length; i++){
			input += (tmp[i]+rule[i]);
		}
		input += (";");
		this.input1.setText("");
		
		//**********************************************************************
		
		if (!input.equals("")) 
		{
			add(input);
			ruleList.setSelectedIndex(rulesInOneString.size()-1);
			ruleList.ensureIndexIsVisible(rulesInOneString.size()-1);
		}

	}

	private void searsh() 
	{
		
		final int index = ruleList.getSelectedIndex();

		final String s = suchField.getText();
		if (!s.equals("")) 
		{
			for(int i = index+1; i< rulesInOneString.size(); i++)
			{
				if(rulesInOneString.elementAt(i).contains(s))
				{
					
					ruleList.setSelectedIndex(i);
					ruleList.ensureIndexIsVisible(i);
					return;
				}
			}
			for(int i = 0; i <= index; i++)
			{
				if(rulesInOneString.elementAt(i).contains(s))
				{
					ruleList.setSelectedIndex(i);
					ruleList.ensureIndexIsVisible(i);
					return;
				}
			}
			//not found
			add(s);
			ruleList.setSelectedIndex(rulesInOneString.size()-1);
			ruleList.ensureIndexIsVisible(rulesInOneString.size()-1);
		}

	}
	private void replace() 
	{
		
		final int index = ruleList.getSelectedIndex();

		final String s = suchField.getText();
		if (!s.equals("")) 
		{
			for(int i = index+1; i< rulesInOneString.size(); i++)
			{
				if(rulesInOneString.elementAt(i).contains(s))
				{
					
					ruleList.setSelectedIndex(i);
					ruleList.ensureIndexIsVisible(i);
					return;
				}
			}
			for(int i = 0; i <= index; i++)
			{
				if(rulesInOneString.elementAt(i).contains(s))
				{
					ruleList.setSelectedIndex(i);
					ruleList.ensureIndexIsVisible(i);
					return;
				}
			}
			//not found
			add(s);
			ruleList.setSelectedIndex(rulesInOneString.size()-1);
			ruleList.ensureIndexIsVisible(rulesInOneString.size()-1);
		}

	}

	private void del() 
	{
		final int index = ruleList.getSelectedIndex();
		if (index >= 0)
		{
			final int yORn = JOptionPane.showConfirmDialog(null, "你确定要删除规则 '"
					+ ruleList.getSelectedValue() + "' ?", "警告",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (yORn == JOptionPane.YES_OPTION) 
			{
				rulesInOneString.remove(index);
				refreshRuleList();
				edited = false;
			}
		}
	}

	private void add() {
		//先保存当前的改动
		if(edited && oldIndex >= 0)
			writeRulesInOneString(oldIndex);
		
		final String inputValue = getStringFromDialog("新官职");
		if(inputValue == null)
			return;
		boolean isalreadyexist = false;
		String aRule = "";
		final Iterator<String> iter = rulesInOneString.iterator();
		while(iter.hasNext())
		{
			aRule = iter.next();
			final String[] s = aRule.split(",");
			if(inputValue.equals(s[0])) 
			{
				isalreadyexist = true;
				break;
			}
		}
		if(isalreadyexist)
		{
			final int index = rulesInOneString.indexOf(aRule);
			if(index>=0)
			{
				ruleList.setSelectedIndex(index);
				ruleList.ensureIndexIsVisible(index);
				edited = false;
				//showARule(index);
			}
		}
		else
		{
			add(inputValue);
			ruleList.setSelectedIndex(rulesInOneString.size()-1);
			ruleList.ensureIndexIsVisible(rulesInOneString.size()-1);
			
		}
	}
	private void add(final String s)
	{
		rulesInOneString.add(s);
		refreshRuleList();
		edited = false;
	}
	private String getStringFromDialog(final String initValue) {
		String inputValue = "";
		inputValue = JOptionPane.showInputDialog("请输入新规则名称：", initValue);
		if (inputValue == null)
		{
			return null;
		}
		while (inputValue.equals("")) 
		{
			inputValue = JOptionPane.showInputDialog("请输入新规则名称：", inputValue);
			if (inputValue == null) 
			{
				return null;
			}
			if (inputValue.equals("")) 
			{
				JOptionPane.showMessageDialog(null, "请输入一个规则名称 !", "提示",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		return inputValue;
	}

	private void showARule(final int i)
	{
		String[] tmp = {"","","","","","","","","","",""};
		if(i >= 0) 
		{
			tmp = rulesInOneString.get(i).split(",");
		}
		for(int j = 0; j < tmp.length; j++)
		{
			if(j<9)
			{
				weizhi[j].setText(tmp[j]);
			}
			else if(j == 9)
			{
				showCube(1, tmp[j]);
			}
			else if(j==10)
			{
				showCube(2, tmp[j]);
			}
		}
		
	}
	
	private void showCube(final int cubeNR, final String string) {
		JTextField[] textfield;
		if(cubeNR == 1)
			textfield = tfCube1;
		else if(cubeNR == 2)
			textfield = tfCube2;
		else
			return;
		
		final String[] s = string.split(";");
		final String[] j = new String[21];
		for(int i = 0; i<j.length; i++)
		{
			j[i]="";
		}
		for(int i = 0; i<s.length; i++)
		{
			if(s[i].contains("德="))
				j[0] = s[i];
			else if(s[i].contains("才="))
				j[1] = s[i];
			else if(s[i].contains("功="))
				j[2] = s[i];
			else if(s[i].contains("良="))
				j[3] = s[i];
			else if(s[i].contains("柔="))
				j[4] = s[i];
			else if(s[i].contains("赃="))
				j[5] = s[i];
			else if(s[i].contains("穿花="))
				j[6] = s[i];
			else if(s[i].contains("红二对="))
				j[7] = s[i];
			else if(s[i].contains("素二对="))
				j[8] = s[i];
			else if(s[i].contains("聚四="))
				j[9] = s[i];
			else if(s[i].contains("聚六="))
				j[10] = s[i];
			else if(s[i].contains("聚五="))
				j[11] = s[i];
			else if(s[i].contains("聚三="))
				j[12] = s[i];
			else if(s[i].contains("聚二="))
				j[13] = s[i];
			else if(s[i].contains("聚一="))
				j[14] = s[i];
			else if(s[i].contains("全四="))
				j[15] = s[i];
			else if(s[i].contains("全六="))
				j[16] = s[i];
			else if(s[i].contains("全五="))
				j[17] = s[i];
			else if(s[i].contains("全三="))
				j[18] = s[i];
			else if(s[i].contains("全二="))
				j[19] = s[i];
			else if(s[i].contains("全一="))
				j[20] = s[i];
			
		}
		for(int i = 0; i<j.length; i++)
		{
			final String str = j[i].substring(j[i].indexOf('=')+1);
			boolean found = false;
			for(int k=0; k< this.rulesInOneString.size();k++){
				if(rulesInOneString.get(k).split(",")[0].equals(str))
				{
					found = true;
					break;
				}	
			}
			textfield[i].setForeground(found?Color.black:Color.red);
			textfield[i].setText(str);
		}
			
	}

	private void save() 
	{
		//先保存当前的改动
		if(edited && oldIndex >= 0)
			writeRulesInOneString(oldIndex);
		
		//准备字符串
		String out = "";
		for (int i = 0; i < rulesInOneString.size(); i++) 
		{
			out += rulesInOneString.get(i)+ "\r\n";
		}

		//System.out.println(out);
		
		//获得文件名并写入文件
		File file;
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();
		 
		//In response to a button click:
		final FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"SGT rule files(*.txt,*.sgt)", "txt", "sgt");
		fc.setFileFilter(filter);
		final int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		{
			file = fc.getSelectedFile();
			this.savetoFile(out, file);
			System.out.println("saved.");
		}
	}

	private void load() {
		File file;
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();
		 
		//In response to a button click:
		final FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"SGT rule files(*.txt,*.sgt)", "txt", "sgt");
		fc.setFileFilter(filter);
		final int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		{
			file = fc.getSelectedFile();
			//This is where a real application would open the file.
			String content = "";
			try 
			{
				final FileInputStream fistream = new FileInputStream(file);
				content = readFromFile(fistream);
			} 
			catch (final Exception e1) 
			{
				System.out.println("打开文件时有错误 open file error");
				//return;
			}
			//System.out.println(content);
			final String[] s = content.split("\r\n");
			rulesInOneString.clear();
			for(int i = 0; i<s.length; i++)
			{
				rulesInOneString.add(s[i]);
			}

			refreshRuleList();	
			edited = false;
			oldIndex = -1;
			ruleList.clearSelection();
		}
	}
		
	private void refreshRuleList() 
	{
		ruleList.setListData(buildIndexList());
		info.setText("/" +rulesInOneString.size()+"官职");
	}
	private String[] buildIndexList()
	{
		final int nr = rulesInOneString.size();
		final String[] s = new String[nr];
		for(int i = 0; i < nr; i++)
		{
			final String[] tmp = rulesInOneString.get(i).split(",");
			s[i] = tmp[0];
		}
		return s;
	}
	private void writeRulesInOneString(final int index) 
	{
		String tmp = "";
		for(int i=0; i<9; i++)
		{
			tmp += "" + weizhi[i].getText() + ",";
		}
		final String cube1 = makeCube(1);
		final String cube2 = makeCube(2);
		if(!cube1.isEmpty())
		{//有规则才存，没有不存
			tmp += cube1;
			if(!cube2.isEmpty())
				tmp += "," + makeCube(2);
		}
	
		
		rulesInOneString.remove(index);
		rulesInOneString.insertElementAt(tmp, index);
		edited = false;
	}
	private String makeCube(final int cubeNR)
	{
		String output = "";
		JTextField[] textfield;
		if(cubeNR == 1)
			textfield = tfCube1;
		else if(cubeNR == 2)
			textfield = tfCube2;
		else
			return null;
		for(int i = 0; i < textfield.length; i++)
		{
			final String sami = (i!=0)?";":"";
			if(!textfield[i].getText().isEmpty())
				output += sami + j[i].getText() + textfield[i].getText();
		}
		return output;
	}
	
	
	
	//@Override
	public void keyPressed(final KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void keyReleased(final KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void keyTyped(final KeyEvent e) {
		if(e.getSource() != tfCurrentNr && e.getSource() != suchField)
		{
			edited = true;
		}
		else
		{
			ruleList.setSelectedIndex(Integer.parseInt(tfCurrentNr.getText()));
		}
	}

	//@Override
	public void valueChanged(final ListSelectionEvent e) 
	{
		if(edited && oldIndex >= 0)
			writeRulesInOneString(oldIndex);
		showARule(ruleList.getSelectedIndex());
		oldIndex = ruleList.getSelectedIndex();
		edited = false;
		tfCurrentNr.setText(""+ oldIndex);
	}		




	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new RuleEditor1();
	}



}

	/*private String aRule;
穿花=军功三至五品
	private int lastSelect;
穿花=
	protected Vector<String[]> tempRules;//store all the rules from file
穿花=军功一品二品
	private JLabel[] j;

	private JTextField[] t;

	private JTextField[] t2;

	private JLabel[] j1;

	private JTextField[] weizhi;

	private JTextField suchField;
	
	private JLabel info = new JLabel("共有职位0个");
	//TODO private JButton btAdd = new JButton("去");

	private JButton btSuch;

	private JList ruleList;

	private JButton btAdd = new JButton("新增规则");

	private JButton btDel = new JButton("删除规则");

	private JButton btLoad = new JButton("打开文件");

	private JButton btExport = new JButton("导出");

	JPanel editorPanel;

	JPanel printPanel;

	private JButton co;

	private JButton ok;

	private Result r;

	private Vector<String> rules;

	private JPanel p2;

	private JPanel buildRulePanel() {

		t = new JTextField[21];
		for (int i = 0; i < t.length; i++) {
			t[i] = new JTextField("", 8);
			t[i].setSize(8, 12);
		}
		t2 = new JTextField[21];
		for (int i = 0; i < t2.length; i++) {
			t2[i] = new JTextField("", 8);
			t2[i].setSize(8, 12);
		}
		j = new JLabel[21];
		j[0] = new JLabel("德=");
		j[1] = new JLabel("才=");
		j[2] = new JLabel("功=");
		j[3] = new JLabel("良=");
		j[4] = new JLabel("柔=");
		j[5] = new JLabel("赃=");
		j[6] = new JLabel("穿花=");
		j[7] = new JLabel("红二对=");
		j[8] = new JLabel("素二对=");
		j[9] = new JLabel("聚四=");
		j[10] = new JLabel("聚六=");
		j[11] = new JLabel("聚五=");
		j[12] = new JLabel("聚三=");
		j[13] = new JLabel("聚二=");
		j[14] = new JLabel("聚一=");
		j[15] = new JLabel("全四=");
		j[16] = new JLabel("全六=");
		j[17] = new JLabel("全五=");
		j[18] = new JLabel("全三=");
		j[19] = new JLabel("全二=");
		j[20] = new JLabel("全一=");

		JPanel panel = new JPanel(new GridLayout(0, 3, 2, 1));
		panel.add(new JLabel(""));
		panel.add(new JLabel("默认规则"));
		panel.add(new JLabel("条件规则"));
		for (int i = 0; i < j.length; i++) {
			panel.add(j[i]);
			panel.add(t[i]);
			panel.add(t2[i]);
		}

		return panel;
	}

	private JPanel buildWeizhiPanel() {
		JPanel panel = new JPanel(new GridLayout(0, 2, 2, 1));
		j1 = new JLabel[7];
		j1[0] = new JLabel("官职");
		j1[1] = new JLabel("衙门");
		j1[2] = new JLabel("品级");
		j1[3] = new JLabel("赏罚=");
		j1[4] = new JLabel("贺=");
		j1[5] = new JLabel("条件=");
		j1[6] = new JLabel("差事=");

		for (int i = 0; i < 2; i++) {
			panel.add(new JLabel(""));
		}
		weizhi = new JTextField[7];
		for (int i = 0; i < weizhi.length; i++) {
			panel.add(j1[i]);
			weizhi[i] = new JTextField("", 10);
			//weizhi[i].setSize(8,12);
			panel.add(weizhi[i]);
		}
		for (int i = 0; i < 28; i++) {
			panel.add(new JLabel(""));
		}

		weizhi[6].setText("否");
		weizhi[5].setText("无");
		weizhi[4].setText("0");
		weizhi[3].setText("0");

		return panel;
	}

	public RuleEditor1(Vector<String[]> v) {
		super("规则编辑器");
		this.aRule = "";

		this.tempRules = this.copy(v);
		this.lastSelect = -1;

		this.r = new Result();
		this.setLayout(new BorderLayout());

		JPanel editorPanel = new JPanel(new BorderLayout(10, 15));

		suchField = new JTextField("", 10);
		btSuch = new JButton("搜索");
		btSuch.addActionListener(this);
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(info);
		p1.add(suchField);
		p1.add(btSuch);
		this.rules = new Vector<String>();

		for (int i = 0; i < v.size(); i++) {

			rules.add(v.elementAt(0)[0]);
		}
		ruleList = new JList(rules);

		JScrollPane ruleScroll = new JScrollPane(ruleList);
		this.ruleList.addMouseListener(this);
		this.p2 = new JPanel(new BorderLayout());
		p2.add(p1, BorderLayout.NORTH);
		p2.add(ruleScroll, BorderLayout.CENTER);
		JPanel p3 = new JPanel(new FlowLayout());
		btAdd.addActionListener(this);
		btDel.addActionListener(this);
		btLoad.addActionListener(this);
		btExport.addActionListener(this);
		//btSave.addActionListener(this);
		p3.add(btAdd);
		p3.add(btDel);
		p3.add(btLoad);
		//p3.add(btSave);
		p3.add(btExport);
		if (rules.size() == 0) {

			btDel.setEnabled(false);
		}
		p2.add(p3, BorderLayout.SOUTH);
		editorPanel.add(p2, BorderLayout.WEST);
		editorPanel.add(buildWeizhiPanel(), BorderLayout.CENTER);
		editorPanel.add(buildRulePanel(), BorderLayout.EAST);
		this.add(editorPanel, BorderLayout.CENTER);

		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

	}
	public String readFromFile(FileInputStream fistream)
    {
        String returnString = null;
        InputStream is =  fistream;
        if (is != null)
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int ch = 0;
            try
            {
                while ((ch = is.read()) != -1)
                {
                    baos.write(ch);
                }
                byte[] data = baos.toByteArray();
                returnString = new String(data, "UTF-8");
                is.close();
                baos.close(); 
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return returnString;
    }


	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "打开文件") 
		{
			File file;
			//Create a file chooser
			final JFileChooser fc = new JFileChooser();
			 
			//In response to a button click:
						 FileNameExtensionFilter filter = new FileNameExtensionFilter(
						 "SGT rule files(*.txt,*.sgt)", "txt", "sgt");
						 fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) 
			{
				file = fc.getSelectedFile();
				//This is where a real application would open the file.
				
				try 
				{
					FileInputStream fistream = new FileInputStream(file);
					String content = readFromFile(fistream);
					

					String[] s = content.split("\r\n");
					tempRules.clear();
					rules.removeAllElements();
					for(int i = 0; i<s.length; i++)
					{
						if(s[i].charAt(0)!='#')
						{
							tempRules.add(addARule(s[i]));
							String str = tempRules.elementAt(i)[0];
							rules.add(str);							
						}
					}
				} 
				catch (Exception e1) 
				{
					System.out.println("打开文件时有错误 open file error");
					//return;
				}
				ruleList.setListData(this.rules);

				this.ruleList.setSelectedIndex(rules.size() - 1);
				ruleList.ensureIndexIsVisible(rules.size() - 1);
				this.refreshTextField(this.tempRules.elementAt(rules.size() - 1));
				this.lastSelect = rules.size() - 1;
				if (rules.size() > 0) {

					btDel.setEnabled(true);
				}
			
				//log.append("Opening: " + file.getName() + "." + newline);
			} 
			else 
			{
				System.out.println("Open command cancelled by user.");
			}
			this.refreshTextField(this.tempRules.elementAt(0));	 
		}
		if (e.getActionCommand() == "导出") {
			if (this.ruleList.getSelectedValue() != null
					&& this.lastSelect > -1) {
				this.SaveText(this.tempRules.elementAt(lastSelect));
			}
			String out = "";
			for (int i = 0; i < this.tempRules.size(); i++) {
				
				
				for (int k = 0; k < 7; k++) {
					out += this.tempRules.elementAt(i)[k] + ",";
				}
				for (int k = 7; k < 28; k++) {
					if (this.tempRules.elementAt(i)[k].equals("")) {
						out += "";
					} else {
						out += j[k - 7].getText()
								+ this.tempRules.elementAt(i)[k] + ";";
					}
				}
				if (this.tempRules.elementAt(i)[5].equals("无")) {
				} else {
					for (int k = 28; k < 49; k++) {
						if (this.tempRules.elementAt(i)[k].equals("")) {
							out += "";
						} else {
							out += j[k - 28].getText()
									+ this.tempRules.elementAt(i)[k] + ";";
						}
					}
				}
				out += "\r\n";
				
				
			}
			int yORn = JOptionPane.showConfirmDialog(this,
					"已编辑的规则将被输出到文本文件！", "提示",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (yORn == JOptionPane.YES_OPTION) {
				File file;
				//Create a file chooser
				final JFileChooser fc = new JFileChooser();
				 
				//In response to a button click:
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"SGT rule files(*.txt,*.sgt)", "txt", "sgt");
				fc.setFileFilter(filter);
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) 
				{
					file = fc.getSelectedFile();
					this.savetoFile(out, file);
				}
			}
		}

		if (e.getActionCommand() == "保存") {
			int yORn = JOptionPane.showConfirmDialog(this, "当前被编辑的文本框将被清空并输出！",
					"提示", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);

			if (yORn == JOptionPane.YES_OPTION) {
				System.out.println(aRule);
			}
			for (int i = 0; i < 27; i++) {
				if (i < 3 || i > 24) {
					j[i].setEnabled(true);
					t[i].setEnabled(true);
				}
				t[i].setText("");
			}
			this.ok.setEnabled(false);
			this.co.setEnabled(true);
			for (int k = 0; k < 7; k++) {
			 s[k] = t[k].getText() + ",";
			 }
			 for (int m = 7; m < 28; m++) {
			 if(t[m].getText().equals("")){
			 s[m] = "";
			 }
			 else
			 s[m] = t[m].getText() + ";";
			 }
			 for (int l = 28; l < 49; l++) {
			 if(t2[l].getText().equals("")){
			 s[l] = "";
			 }
			 else
			 s[l] = t2[l].getText() + ";";
			 
			 }

		}
		if (e.getActionCommand() == "输出") {
			int yORn = JOptionPane.showConfirmDialog(this,
					"已编辑的规则将被输出到文本文件'D:/rules.txt'！", "提示",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (yORn == JOptionPane.YES_OPTION) {
				r.finish();
			}

		}
		if (e.getActionCommand() == "新增规则") {
			if (this.ruleList.getSelectedValue() != null
					&& this.lastSelect > -1) {
				this.SaveText(this.tempRules.elementAt(lastSelect));
			}
			String inputValue = "";
			while (inputValue.equals("")) {
				inputValue = JOptionPane.showInputDialog("请输入新规则名称：");
				if (inputValue == null) {
					return;
				}
				if (inputValue.equals("")) {
					JOptionPane.showMessageDialog(null, "请输入一个规则名称 !", "提示",
							JOptionPane.ERROR_MESSAGE);
				}

			}

			boolean isalreadyexist = false;
			for (int i = 0; i < this.rules.size(); i++) {
				if (inputValue.equals(this.rules.elementAt(i))) {
					JOptionPane.showMessageDialog(this, "此规则已存在，请编辑!", "提示",
							JOptionPane.WARNING_MESSAGE);
					this.ruleList.setSelectedIndex(i);
					ruleList.ensureIndexIsVisible(i);
					this.refreshTextField(this.tempRules.elementAt(i));
					this.lastSelect = i;
					isalreadyexist = true;
					break;
				}
			}
			if (isalreadyexist) {
			} else {
				String[] s = new String[49];
				s[0] = inputValue;
				s[1] = "";
				s[2] = "";

				s[6] = "否";
				s[5] = "无";
				s[4] = "0";
				s[3] = "0";

				for (int i = 7; i < 49; i++) {
					s[i] = "";
				}
				this.tempRules.add(s);
				this.rules.add(inputValue);
				ruleList.setListData(this.rules);

				this.ruleList.setSelectedIndex(rules.size() - 1);
				ruleList.ensureIndexIsVisible(rules.size() - 1);
				this.refreshTextField(this.tempRules
						.elementAt(rules.size() - 1));
				this.lastSelect = rules.size() - 1;
			}
			if (rules.size() > 0) {

				btDel.setEnabled(true);
			}
			
		}
		if (e.getActionCommand() == "删除规则") {
			if (ruleList.getSelectedValue() != null) {

				int yORn = JOptionPane.showConfirmDialog(null, "你确定要删除规则 '"
						+ ruleList.getSelectedValue() + "' ?", "警告",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (yORn == JOptionPane.YES_OPTION) {
					for (int i = 0; i < this.tempRules.size(); i++) {
						if (ruleList.getSelectedValue().equals(
								this.tempRules.elementAt(i)[0])) {
							this.tempRules.remove(i);
							break;
						}
					}
					this.rules.remove(ruleList.getSelectedValue());

					ruleList.setListData(this.rules);
					this.lastSelect = -1;
					this.refreshTextField(new String[49]);

				}
			}
			if (rules.size() == 0) {

				btDel.setEnabled(false);
			}
		}
		if (e.getActionCommand() == "搜索") {
			if (this.ruleList.getSelectedValue() != null
					&& this.lastSelect > -1) {
				this.SaveText(this.tempRules.elementAt(lastSelect));
			}
			String s = this.suchField.getText();
			if (s.equals("")) {

			} else {
				boolean isalreadyexist = false;
				for (int i = 0; i < this.rules.size(); i++) {
					if (this.rules.elementAt(i).contains(s)) {
						//JOptionPane.showMessageDialog(this, "此规则已存在，请编辑!", "提示",
						//		JOptionPane.WARNING_MESSAGE);
						this.ruleList.setSelectedIndex(i);
						ruleList.ensureIndexIsVisible(i);
						this.refreshTextField(this.tempRules.elementAt(i));
						isalreadyexist = true;
						break;
					}
				}
				if (isalreadyexist) {
				} else {
					int yORn = JOptionPane.showConfirmDialog(null, "规则 '" + s + "' 不存在,是否创建此规则?", "提示",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					if (yORn == JOptionPane.YES_OPTION) {
						String[] ss = new String[49];
						ss[0] = s;
						ss[1] = "";
						ss[2] = "";

						ss[6] = "否";
						ss[5] = "无";
						ss[4] = "0";
						ss[3] = "0";

						for (int i = 7; i < 49; i++) {
							ss[i] = "";
						}
						this.tempRules.add(ss);
						this.rules.add(s);
						ruleList.setListData(this.rules);

						this.ruleList.setSelectedIndex(rules.size() - 1);
						ruleList.ensureIndexIsVisible(rules.size() - 1);
						this.refreshTextField(this.tempRules.elementAt(rules.size() - 1));
						this.lastSelect = rules.size() - 1;
				
						if (rules.size() > 0) {

							btDel.setEnabled(true);
						}
					}
				}
			}
		}
	}

	private String[] addARule(String string) {
		String[] s = string.split(",");
		String[] output = new String[49];
		if(s.length >= 7)
		{
			for(int i = 0; i< 7; i++)
			{
				output[i] = s[i];
			}
		}
		if(s.length >= 8)
		{
			String[] sCube = getCubeRule(s[7]);
			for(int i = 0; i< 21; i++)
			{
				output[i+7] = sCube[i];
			}
		}
		if(s.length >= 9)
		{
			String[] sCube1 = getCubeRule(s[8]);
			for(int i = 0; i< 21; i++)
			{
				output[i+28] = sCube1[i];
			}	
		}
		return output;
	}

	private String[] getCubeRule(String string) {
		String[] s = string.split(";");
		String[] j = new String[21];
		for(int i = 0; i<j.length; i++)
		{
			j[i]="";
		}
		for(int i = 0; i<s.length; i++)
		{
			if(s[i].contains("德="))
				j[0] = s[i];
			else if(s[i].contains("才="))
				j[1] = s[i];
			else if(s[i].contains("功="))
				j[2] = s[i];
			else if(s[i].contains("良="))
				j[3] = s[i];
			else if(s[i].contains("柔="))
				j[4] = s[i];
			else if(s[i].contains("赃="))
				j[5] = s[i];
			else if(s[i].contains("穿花="))
				j[6] = s[i];
			else if(s[i].contains("红二对="))
				j[7] = s[i];
			else if(s[i].contains("素二对="))
				j[8] = s[i];
			else if(s[i].contains("聚四="))
				j[9] = s[i];
			else if(s[i].contains("聚六="))
				j[10] = s[i];
			else if(s[i].contains("聚五="))
				j[11] = s[i];
			else if(s[i].contains("聚三="))
				j[12] = s[i];
			else if(s[i].contains("聚二="))
				j[13] = s[i];
			else if(s[i].contains("聚一="))
				j[14] = s[i];
			else if(s[i].contains("全四="))
				j[15] = s[i];
			else if(s[i].contains("全六="))
				j[16] = s[i];
			else if(s[i].contains("全五="))
				j[17] = s[i];
			else if(s[i].contains("全三="))
				j[18] = s[i];
			else if(s[i].contains("全二="))
				j[19] = s[i];
			else if(s[i].contains("全一="))
				j[20] = s[i];
			
		}
		for(int i = 0; i<j.length; i++)
		{
			j[i]=j[i].substring(j[i].indexOf('=')+1);
		}

		
		return j;
	}

	public class Result {

		private FileWriter fWriter;
		
		private PrintWriter pw;

		public Result() {
			try {
				fWriter = new FileWriter("D:/result.txt");  

				pw = new PrintWriter(fWriter);
			} catch (IOException iox) {
				System.err.println(iox);
			}
  			
  		}

		public void aLine(String in) { //写入一行
			pw.println(in);
		}

		public void finish() { //关闭输入流，将文字从缓存写入文件

			try {
				pw.flush();
				fWriter.close();
			} catch (IOException iox) {
				System.err.println(iox);
			}

		}

	}
	
	public void savetoFile(String s, File f)
    {
		try {
			String output = s;
			FileOutputStream fos = new FileOutputStream(f);
			byte[] data = output.getBytes("UTF-8");
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			if (bais != null){
				int ch = 0;
				try
				{
					while ((ch = bais.read()) != -1)
        			{
        				fos.write(ch);
        			}
       			} catch (IOException e)	{
        		e.printStackTrace();
				}
			}
			bais.close();
			fos.close(); 
		}
		
		catch (IOException e)
    	{
    		e.printStackTrace();
    	} 
    }
	public Vector<String[]> copy(Vector<String[]> vs) {
		Vector<String[]> copyed_Vector = new Vector<String[]>();
		try {
			// write currentdata to stream
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(vs);
			// read data from stream
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);

			copyed_Vector = (Vector<String[]>) (oi.readObject());

		} catch (Exception e) {
			System.out.println("Exception during serialization:" + e);
		}
		return copyed_Vector;
	}

	public void mousePressed(MouseEvent e) {
		if (e.getSource() != null) {

			if (this.ruleList.getSelectedValue() != null
					&& this.lastSelect > -1) {
				this.SaveText(this.tempRules.elementAt(lastSelect));
			}
		}
		if (e.getClickCount() == 1) {

			if (this.ruleList.getSelectedValue() != null) {
				int index = ruleList.locationToIndex(e.getPoint());
				this.refreshTextField(this.tempRules.elementAt(index));
				this.lastSelect = index;
			}
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void refreshTextField(String[] ss) {

		for (int i = 0; i < 7; i++) {

			weizhi[i].setText(ss[i]);

		}
		for (int i = 7; i < 28; i++) {

			t[i - 7].setText(ss[i]);
			t2[i - 7].setText(ss[i + 21]);
		}
		info.setText("共有职位"+tempRules.size()+"个");
		if(tempRules.size()>0)
			btDel.setEnabled(true);
		else
			btDel.setEnabled(false);
	}

	public void SaveText(String[] st) {

		for (int i = 0; i < 7; i++) {

			st[i] = weizhi[i].getText();

		}
		for (int i = 7; i < 28; i++) {

			st[i] = t[i - 7].getText();

		}
		for (int i = 28; i < 49; i++) {

			st[i] = t2[i - 28].getText();

		}
	}

	public static void main(String[] args) {
		Vector<String[]> allRules = new Vector<String[]>();
		RuleEditor1 test = new RuleEditor1(allRules);
		test.pack();
	}

}*/

