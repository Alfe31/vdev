package com.vdev.swing;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class VDevMain {

	public static void main(String[] args) {
		VLaudaData laudaData = new VLaudaData(1000);
		JFrame fen = new VDevView(laudaData);
		fen.setVisible(true);
		while(laudaData.shouldExitSimulation()==false) {

			System.out.println("Lauda temperature setpoint = "+laudaData.getSpTemperature()+" ,"+
					"Lauda Cooling Flow setpoint = "+laudaData.getSpCoolingFlow());
			try {
				Thread.sleep(laudaData.getSimulationPeriod());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		fen.dispose();
	}
}

class VLaudaData {
	private boolean exitSimulation = false;
	private int simulationPeriod;
	private float spTemperature;
	private float spCoolingFlow;
	
	public VLaudaData(int per) {
		simulationPeriod = per;
	}
	
	public void setSpTemperature(float value) {
		spTemperature = value;
	}
	
	public float getSpTemperature() {
		return spTemperature;
	}
	
	public void setSpCoolingFlow(float value) {
		spCoolingFlow = value;
	}
	
	public float getSpCoolingFlow() {
		return spCoolingFlow;
	}
	
	public void setExitSimulation(boolean value) {
		exitSimulation = value;
	}
	
	public boolean shouldExitSimulation() {
		return exitSimulation;
	}
	
	public void setPeriodSimulation(int value) {
		simulationPeriod = value;
	}
	
	public int getSimulationPeriod() {
		return simulationPeriod;
	}
}

class VDevView extends JFrame implements ActionListener, FocusListener {
	private static final long serialVersionUID = -6018295356853922101L;
	private JCheckBox cb_RunStop;
	private JRadioButton rb_modeManu, rb_modeAuto;
	private JLabel lb_spTemperature, lb_spCoolingFlow;
	private JTextField tf_spTemperature,tf_spCoolingFlow;
	private VLaudaData laudaData;
	private JMenuItem exitMenuItem;
	private JMenuItem configMenuItem;

	public VDevView(VLaudaData laudaData) {
		this.laudaData = laudaData;
		setTitle("VDev");
		setBounds(50,100,600,600);
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(this);
		menuFile.add(exitMenuItem);
		menuBar.add(menuFile);
		getContentPane().add(menuBar);
		JMenu menuConfig = new JMenu("Config");
		configMenuItem = new JMenuItem("Simulation Parameters");
		menuBar.add(menuConfig);
		menuConfig.add(configMenuItem);
		configMenuItem.addActionListener(this);
		getContentPane().setLayout(new FlowLayout());
		
		//Temperature Setpoint
		lb_spTemperature = new JLabel("Setpoint Temperature");
		getContentPane().add(lb_spTemperature);
		tf_spTemperature = new JTextField(6);
		tf_spTemperature.addActionListener(this);
		tf_spTemperature.addFocusListener(this);
		getContentPane().add(tf_spTemperature);

		
		// Cooling Flow setpoint
		lb_spCoolingFlow = new JLabel("Setpoint Cooling Flow");
		getContentPane().add(lb_spCoolingFlow);
		tf_spCoolingFlow = new JTextField(6);
		tf_spCoolingFlow.addActionListener(this);
		tf_spCoolingFlow.addFocusListener(this);
		getContentPane().add(tf_spCoolingFlow);
		
		// Run/Stop mode
		cb_RunStop = new JCheckBox("Run/Stop");
		getContentPane().add(cb_RunStop);
		cb_RunStop.addActionListener(this);
		
		// Manu/Auto mode
		ButtonGroup rb_grpMode = new ButtonGroup();
		rb_modeManu = new JRadioButton("Mode Manu",true);
		rb_grpMode.add(rb_modeManu);
		getContentPane().add(rb_modeManu);
		rb_modeManu.addActionListener(this);
		rb_modeAuto = new JRadioButton("Mode Auto");
		rb_grpMode.add(rb_modeAuto);
		rb_grpMode.add(rb_modeAuto);
		getContentPane().add(rb_modeAuto);
		rb_modeAuto.addActionListener(this);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				System.out.println("clic dans la fenêtre aux coordonnées : " + x + "," + y);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == exitMenuItem) {
			laudaData.setExitSimulation(true);
			setVisible(false);
		}
		if(e.getSource() == configMenuItem) {
			String stringValue = JOptionPane.showInputDialog(
					null,"Enter Simulation Period (ms):","Simulation Parameters",
					JOptionPane.QUESTION_MESSAGE);
			try {
				laudaData.setPeriodSimulation(Integer.parseInt(stringValue));
			} catch(NumberFormatException exc) {
				// Invalid value is simply ignored
			}
		}
		if(e.getSource() == cb_RunStop) {
			if(cb_RunStop.isSelected() == true) System.out.println("Mode = RUNNING");
			else System.out.println("Mode = STOPPED");
		}
		if(e.getSource() == rb_modeAuto || e.getSource() == rb_modeManu) {
			System.out.println("rb_modeAuto = "+rb_modeAuto.isSelected()+
					" , rb_modeManu = "+ rb_modeManu.isSelected() );
		}
		if(e.getSource() == tf_spTemperature) {
			//System.out.println("New temperature setpoint = "+tf_spTemperature.getText());
			float newSp = toFloat(tf_spTemperature.getText());
			if(newSp != Float.NaN) laudaData.setSpTemperature(newSp);
		}
		
		if(e.getSource() == tf_spCoolingFlow) {
			//System.out.println("New Cooling Flow setpoint = "+tf_spCoolingFlow.getText());
			float newSp = toFloat(tf_spCoolingFlow.getText());
			if(newSp != Float.NaN) laudaData.setSpCoolingFlow(newSp);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource() == tf_spTemperature) {
			System.out.println("New temperature setpoint = "+tf_spTemperature.getText());
		}
		
		if(e.getSource() == tf_spCoolingFlow) {
			System.out.println("New Cooling Flow setpoint = "+tf_spCoolingFlow.getText());
		}
		
	}
	
	
	public float toFloat(String svalue) {
		try {
			float result = Float.parseFloat(svalue);
			return result;
		} catch(NumberFormatException e) {
			return Float.NaN;
		}
	}

}
