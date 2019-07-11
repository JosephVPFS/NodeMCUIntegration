package com.honeywell.virtuality;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketReadWriteResolver {
	private Map<String, NodeMCUModule> nodeModules;
	private static final String SENSOR ="SENSOR";
	private static final String ACTUATOR ="ACTUATOR";
	private NodeMCUReader finder;
	Socket socket =null;
	private String command = null;
	public SocketReadWriteResolver() {
		nodeModules = new HashMap<>();
	}
	
	public void resolveReadWriteMCUs(Socket socket) throws IOException {
		String socketString = socket.getRemoteSocketAddress().toString();
		this.socket = socket;
		finder = new NodeMCUReader(socket);
		command = finder.doRead();
		NodeMCUModule module = null;
		if(command.trim().equalsIgnoreCase(SENSOR)) {
			module = getReader(socketString);
		}else if(command.trim().equalsIgnoreCase(ACTUATOR)) {
			module = getWriter(socketString);
		}else {
			module = nodeModules.get(socketString);
		}
		
		if(module != null) {
			module.doReadOrCommand(this);
		}
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	private NodeMCUWriter getWriter(String socketString) throws IOException {
		if(nodeModules.get(socketString) != null) {
			return (NodeMCUWriter)nodeModules.get(socketString);
		}
		NodeMCUWriter reader = new NodeMCUWriter(this.socket);
		nodeModules.put(socketString, reader);
		return reader;
	}

	private NodeMCUReader getReader(String socketString) throws IOException {
		if(nodeModules.get(socketString) != null) {
			return (NodeMCUReader)nodeModules.get(socketString);
		}
		NodeMCUReader reader = new NodeMCUReader(this.socket);
		nodeModules.put(socketString, reader);
		return reader;
	}
}
