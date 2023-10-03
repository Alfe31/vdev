package com.vdev.lauda;

import com.vdev.abstracts.VDeviceSimulationParam;

public class InfosLauda  {
	private float spTemperature;
	private float spCoolingFlow;
	
	public InfosLauda() {
		
	}
	
	public void setSpTemperature(float value) {
		spTemperature = value;
		System.out.println("setSpTemperature("+value+")");
	}
	
	public float getSpTemperature() {
		return spTemperature;
	}
	
	public void setSpCoolingFlow(float value) {
		spCoolingFlow = value;
		System.out.println("setSpCoolingFlow("+value+")");
	}
	
	public float getSpCoolingFlow() {
		return spCoolingFlow;
	}
	

}
