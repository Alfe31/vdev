package com.vdev.basic.poc.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class VDLaudaMain {
	public void main(String[] args) {
		try(
				ServerSocket serverSocket = new ServerSocket(49152);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		){
			String inputLine, outputLine;
			VDLaudaProtocol laudaProtocol = new VDLaudaProtocol();
			outputLine = laudaProtocol.processInput(null);
			out.println(outputLine);
			
			while((inputLine = in.readLine()) != null) {
				outputLine = laudaProtocol.processInput(inputLine);
				out.print(outputLine);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
