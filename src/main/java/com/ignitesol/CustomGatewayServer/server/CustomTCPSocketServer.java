package com.ignitesol.CustomGatewayServer.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.ignitesol.CustomGatewayServer.handler.ClientHandler;
import com.ignitesol.CustomGatewayServer.handler.CustomUDPDataPublisher;
import com.ignitesol.CustomGatewayServer.handler.UDPDataReceiver;

/**
 * 
 * @author Sanjib Pramanick
 *
 */
public class CustomTCPSocketServer {

	private ServerSocket server;

	public CustomTCPSocketServer(String ipAddress) throws Exception {
		if (ipAddress != null && !ipAddress.isEmpty())
			this.server = new ServerSocket(5056, 1, InetAddress.getByName(ipAddress));
		else
			this.server = new ServerSocket(5056, 1, InetAddress.getLocalHost());
	}

	public void listen() throws Exception {
		try {

			Socket client = this.server.accept();

			DataInputStream dis = new DataInputStream(client.getInputStream());
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());

			BlockingQueue<String> dataQueue = new ArrayBlockingQueue<String>(5);

			Thread serverThread = new Thread(new ClientHandler(client, dis, dos, dataQueue));
			serverThread.start();
			Thread publisherThread = new Thread(new CustomUDPDataPublisher(dataQueue));
			publisherThread.start();
			
			new Thread(new UDPDataReceiver()).start();
		} catch (Exception e) {
			System.out.println("Exception Occurred: " + e.getMessage());
		}
	}

	public InetAddress getSocketAddress() {
		return this.server.getInetAddress();
	}

	public int getPort() {
		return this.server.getLocalPort();
	}

}
