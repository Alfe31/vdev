package com.vdev.lauda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.vdev.abstracts.VDeviceSimulationParam;
import com.vdev.basic.main.VDLaudaProtocol;

public class VLauda {
	private InfosLauda infosLauda;
	private LaudaView laudaView;
	private VDLaudaProtocol protocol;
	private VDeviceSimulationParam simParam;
	
	public VLauda() {
		simParam = new VDeviceSimulationParam(100);
		infosLauda = new InfosLauda();
		laudaView = new LaudaView(infosLauda,simParam);
		protocol = new VDLaudaProtocol(infosLauda);
	}
	
	LaudaView getLaudaView() {
		return laudaView;
	}
	
	InfosLauda getInfosLauda() {
		return infosLauda;
	}
	
	VDLaudaProtocol getVDLaudaProtocol() {
		return protocol;
	}
	
	VDeviceSimulationParam getSimulationParam() {
		return simParam;
	}
	
	static public void main(String[] args) {
		System.out.println("VLaudaMain started");
		try(
				ServerSocket serverSocket = new ServerSocket(49152);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		){
			VLauda vlauda = new VLauda();
			System.out.println("Starting listening on socket port 49152");
			String inputLine, outputLine;
			boolean endListening = false;
			
			outputLine = vlauda.getVDLaudaProtocol().processInput(null);
			if(outputLine!=null) out.println(outputLine);
			else System.out.println("outputLine == null !!!");
			out.print("Lauda started");
			out.flush();
			System.out.println("Entering loop ...");
			vlauda.getLaudaView().setVisible(true);
			while (!endListening && !vlauda.getSimulationParam().shouldExitSimulation()) {
				try {
					inputLine = in.readLine();
				} catch (IOException e) {
					endListening = true;
					break;
				}

				try {
					Thread.sleep(vlauda.getSimulationParam().getSimulationPeriod());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("A new TCP request was received : " + inputLine);
				outputLine = vlauda.getVDLaudaProtocol().processInput(inputLine);
				if (outputLine != null)
					System.out.println("The VLauda response is " + outputLine);
				else
					System.out.println("The VLauda response is null!!!!!!");
				out.print(outputLine);
				out.flush();
			}
			vlauda.getLaudaView().dispose();
			System.out.println("Listening loop ended");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
