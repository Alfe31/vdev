package com.vdev.lauda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.vdev.abstracts.VDeviceSimulationParam;
import com.vdev.abstracts.VDeviceView;




class LaudaView extends VDeviceView implements ActionListener, FocusListener {
	private static final long serialVersionUID = -6018295356853922101L;
	private JCheckBox cb_RunStop;
	private JRadioButton rb_modeManu, rb_modeAuto;
	private JLabel lb_spTemperature, lb_spCoolingFlow;
	private JTextField tf_spTemperature,tf_spCoolingFlow;
	private InfosLauda laudaData;


	public LaudaView(InfosLauda laudaData, VDeviceSimulationParam simParam) {
		super("Virtual Lauda", simParam);
		this.laudaData = laudaData;
		
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

		super.actionPerformed(e);
		
		if(e.getSource() == cb_RunStop) {
			if(cb_RunStop.isSelected() == true) System.out.println("Mode = RUNNING");
			else System.out.println("Mode = STOPPED");
		}
		if(e.getSource() == rb_modeAuto || e.getSource() == rb_modeManu) {
			System.out.println("rb_modeAuto = "+rb_modeAuto.isSelected()+
					" , rb_modeManu = "+ rb_modeManu.isSelected() );
		}
		if(e.getSource() == tf_spTemperature) {
			updateTemperatureSetpoint();
		}
		
		if(e.getSource() == tf_spCoolingFlow) {
			updateCoolingFlowSetpoint();
		}
	}

	private void updateCoolingFlowSetpoint() {
		System.out.println("New Cooling Flow setpoint = "+tf_spCoolingFlow.getText());
		float newSp = toFloat(tf_spCoolingFlow.getText());
		if(newSp != Float.NaN) laudaData.setSpCoolingFlow(newSp);
	}

	private void updateTemperatureSetpoint() {
		System.out.println("New temperature setpoint = "+tf_spTemperature.getText());
		float newSp = toFloat(tf_spTemperature.getText());
		if(newSp != Float.NaN) laudaData.setSpTemperature(newSp);
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource() == tf_spTemperature) {
			updateTemperatureSetpoint();
		}
		
		if(e.getSource() == tf_spCoolingFlow) {
			updateCoolingFlowSetpoint();
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