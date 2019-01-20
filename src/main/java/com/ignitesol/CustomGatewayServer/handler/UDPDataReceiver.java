package com.ignitesol.CustomGatewayServer.handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * 
 * @author Sanjib Pramanick
 *
 */
public class UDPDataReceiver implements Runnable {

	private byte[] buf = new byte[256];

	@Override
	public void run() {
		try {
			MulticastSocket socket = new MulticastSocket(4446);

			InetAddress group = InetAddress.getByName("230.0.0.0");
			socket.joinGroup(group);
			while (true) {
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				String received = new String(packet.getData(), 0, packet.getLength());
				System.out.println("Multicast Message Received: " + received);
				if ("exit".equalsIgnoreCase(received)) {
					break;
				}
			}
			socket.leaveGroup(group);
			socket.close();
		} catch (IOException e) {
			System.out.println("Exception Occurred: " + e.getMessage());
		}
	}

}
