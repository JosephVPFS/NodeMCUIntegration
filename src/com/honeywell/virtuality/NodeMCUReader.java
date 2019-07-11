package com.honeywell.virtuality;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class NodeMCUReader implements NodeMCUModule{
	private BufferedReader in;
	private static final String HIGH = "HIGH";
	private static final String LOW = "LOW";
	public NodeMCUReader(Socket socket) throws IOException {
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	@Override
	public String doRead() {
		String inputRead = null;
		try {
			inputRead = in.readLine()!=null? in.readLine().trim() : "0";
			System.out.println("Input Read : " + inputRead);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputRead;
	}
	@Override
	public void doCommand(String command) {
		
	}

	@Override
	public void doReadOrCommand(SocketReadWriteResolver resolver) {
		String input = doRead();
		int value = Integer.parseInt(input);
		if(value < 10) {
			resolver.setCommand(HIGH);
		}else
			resolver.setCommand(LOW);
	}
}
