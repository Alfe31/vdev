package com.vdev.abstracts;

public class VDeviceSimulationParam {

	public VDeviceSimulationParam(int period) {
		simulationPeriod = period;
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
	private boolean exitSimulation = false;
	private int simulationPeriod;

}
