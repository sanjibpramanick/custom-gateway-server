package com.ignitesol.CustomGatewayServer.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * 
 * @author Sanjib Pramanick
 *
 */
public class CustomTCPSocketClient {

	private Socket socket;
	private Scanner scanner;

	private CustomTCPSocketClient(InetAddress serverAddress, int serverPort) throws Exception {
		this.socket = new Socket(serverAddress, serverPort);
		this.scanner = new Scanner(System.in);
	}

	private void start() throws IOException {
		try (DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());) {
			while (true) {
				System.out.println(dis.readUTF());
				String tosend = scanner.nextLine();
				dos.writeUTF(tosend);

				if (tosend.equalsIgnoreCase("Exit")) {
					System.out.println("Closing this connection : " + socket);
					socket.close();
					System.out.println("Connection closed");
					break;
				}

			}
			scanner.close();
		} catch (Exception e) {
			System.out.println("Exception Occurred: " + e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		CustomTCPSocketClient client = new CustomTCPSocketClient(InetAddress.getByName(args[0]),
				Integer.parseInt(args[1]));

		System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
		client.start();
	}
}
