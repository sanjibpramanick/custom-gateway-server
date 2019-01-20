package com.ignitesol.CustomGatewayServer.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * @author Sanjib Pramanick
 *
 */
public class ClientHandler implements Runnable {

	private final Socket s;
	private final DataInputStream dis;
	private final DataOutputStream dos;

	private BlockingQueue<String> dataQueue;

	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, BlockingQueue<String> dataQueue) {
		this.s = s;
		this.dis = dis;
		this.dos = dos;
		this.dataQueue = dataQueue;
	}

	@Override
	public void run() {
		String received;
		while (true) {
			try {

				dos.writeUTF("Write your message here..\n" + "Type Exit to terminate connection.");

				received = dis.readUTF();

				if (received.equals("Exit")) {
					System.out.println("Client " + this.s + " sends exit...");
					System.out.println("Closing this connection.");
					this.s.close();
					System.out.println("Connection closed");
					break;
				}

				multicastMessage(received);

			} catch (IOException e) {
				System.out.println("Exception Occurred: " + e.getMessage());
			} catch (InterruptedException e) {
				System.out.println("Exception Occurred: " + e.getMessage());
			}
		}

		try {
			this.dis.close();
			this.dos.close();

		} catch (IOException e) {
			System.out.println("Exception Occurred: " + e.getMessage());
		}

	}

	private void multicastMessage(String received) throws IOException, InterruptedException {
		System.out.println("Multicasting: " + received);
		dataQueue.put(received);
		
		Thread publisherThread = new Thread(new CustomUDPDataPublisher(dataQueue));
		publisherThread.start();

	}

}
