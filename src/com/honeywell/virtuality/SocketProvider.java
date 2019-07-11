package com.honeywell.virtuality;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketProvider {
	private static ServerSocket listener;
	public static ServerSocket getServerSocket() {
		if(listener == null) {
			try {
				listener = new ServerSocket(9090);
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
		return listener;
	}
}
