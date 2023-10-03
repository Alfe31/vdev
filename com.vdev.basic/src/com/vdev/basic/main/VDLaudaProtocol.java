package com.vdev.basic.main;

import com.vdev.lauda.InfosLauda;

public class VDLaudaProtocol {
	
	private InfosLauda infosLauda;

	public VDLaudaProtocol(InfosLauda infosLauda) {
		this.infosLauda = infosLauda;
	}

	public String processInput(String inputLine) {
		String result = null;
		if(inputLine == null) return null;
		switch(inputLine) {
		case "IN_SP_00":
			result = processGetTemperatureSetpoint();
			break;
		case "IN_PV_07":
			result = processGetCoolingFlowSetpoint();
			break;
		default:
			result = sendErrorMessage();
		}
		return result;
	}

	private String sendErrorMessage() {
		return "ERR_3";
		
	}

	private String processGetCoolingFlowSetpoint() {
		return ""+infosLauda.getSpCoolingFlow()+"\r\n";
		
	}

	private String processGetTemperatureSetpoint() {
		return ""+infosLauda.getSpTemperature()+"\r\n";
		
	}

}
