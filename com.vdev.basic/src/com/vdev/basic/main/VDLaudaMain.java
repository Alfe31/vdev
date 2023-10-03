package com.vdev.basic.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.vdev.lauda.InfosLauda;

public class VDLaudaMain {
	static public void main(String[] args) {
		System.out.println("VLaudaMain started");
		try(
				ServerSocket serverSocket = new ServerSocket(49152);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		){
			System.out.println("Starting listening on socket port 49152");
			String inputLine, outputLine;
			boolean endListening = false;
			VDLaudaProtocol laudaProtocol = new VDLaudaProtocol(new InfosLauda());
			outputLine = laudaProtocol.processInput(null);
			out.println(outputLine);
			System.out.println("Entering loop ...");
			while(!endListening) {
				try {
					inputLine = in.readLine();
				} catch(IOException e) {
					endListening = true;
					break;
				}
				System.out.println("A new TCP request was received : "+inputLine);
				outputLine = laudaProtocol.processInput(inputLine);
				out.print(outputLine);
			}
			System.out.println("Listening loop ended");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
