package com.ignitesol.CustomGatewayServer.handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * @author Sanjib Pramanick
 *
 */
public class CustomUDPDataPublisher implements Runnable {

	private BlockingQueue<String> dataQueue;

	public CustomUDPDataPublisher(BlockingQueue<String> dataQueue) {
		this.dataQueue = dataQueue;
	}

	@Override
	public void run() {
		try {
			String data = dataQueue.take();
			System.out.println("Publishing Data: " + data);

			DatagramSocket socket = new DatagramSocket();
			InetAddress group = InetAddress.getByName("230.0.0.0");
			byte[] buf = data.getBytes();

			DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
			socket.send(packet);
			socket.close();
		} catch (InterruptedException e) {
			System.out.println("Exception Occurred: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Exception Occurred: " + e.getMessage());
		}

	}

}
