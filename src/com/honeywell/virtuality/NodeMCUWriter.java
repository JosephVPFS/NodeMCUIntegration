package com.honeywell.virtuality;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class NodeMCUWriter implements NodeMCUModule{
	private BufferedWriter out;
	public NodeMCUWriter(Socket socket) throws IOException {
		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	
	@Override
	public String doRead() {
		return null;
	}

	@Override
	public void doCommand(String command) {
		try {
			System.out.println("Command Fired : " + command);
			out.write(command);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doReadOrCommand(SocketReadWriteResolver resolver) {
		doCommand(resolver.getCommand());
	}
}
