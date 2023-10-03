package com.vdev.abstracts;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class VDeviceView extends JFrame implements ActionListener {
	private static final long serialVersionUID = -1225611751576199257L;
	protected VDeviceSimulationParam simParam;
	protected JMenuItem exitMenuItem;
	protected JMenuItem configMenuItem;
	protected JMenuBar menuBar;
	protected JMenu menuFile, menuConfig;
	
	public VDeviceView(String windowTitle, VDeviceSimulationParam param) {
		this.simParam = param;
		setTitle(windowTitle);
		setBounds(50,100,600,600);
		menuBar = new JMenuBar();
		menuFile = new JMenu("File");
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(this);
		menuFile.add(exitMenuItem);
		menuBar.add(menuFile);
		getContentPane().add(menuBar);
		menuConfig = new JMenu("Config");
		configMenuItem = new JMenuItem("Simulation Parameters");
		menuBar.add(menuConfig);
		menuConfig.add(configMenuItem);
		configMenuItem.addActionListener(this);
		getContentPane().setLayout(new FlowLayout());
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e)  {
		if(e.getSource() == exitMenuItem) {
			simParam.setExitSimulation(true);
			setVisible(false);
		}
		if(e.getSource() == configMenuItem) {
			String stringValue = JOptionPane.showInputDialog(
					null,"Enter Simulation Period (ms):","Simulation Parameters",
					JOptionPane.QUESTION_MESSAGE);
			try {
				simParam.setPeriodSimulation(Integer.parseInt(stringValue));
			} catch(NumberFormatException exc) {
				// Invalid value is simply ignored
			}
		}
	}
}
