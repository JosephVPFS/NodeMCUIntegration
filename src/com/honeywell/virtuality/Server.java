package com.honeywell.virtuality;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {

		ServerSocket listener = SocketProvider.getServerSocket();
		SocketReadWriteResolver resolver = new SocketReadWriteResolver();
		try {
			while (true) {
				Socket socket = listener.accept();
				socket.setKeepAlive(true);
				System.out.println("Client Connected with : " + socket.getRemoteSocketAddress().toString());
				try {
					resolver.resolveReadWriteMCUs(socket);
				} finally {
					socket.close();
				}
			}
		} finally {
			listener.close();
		}
	}
}