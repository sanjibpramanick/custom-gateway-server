package com.ignitesol.CustomGatewayServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ignitesol.CustomGatewayServer.server.CustomTCPSocketServer;

/**
 * 
 * @author Sanjib Pramanick
 *
 */
@SpringBootApplication
public class CustomGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomGatewayServerApplication.class, args);
		CustomTCPSocketServer app;
		try {
			app = new CustomTCPSocketServer(args[0]);
			System.out.println("\r\nRunning Server: " + "Host=" + app.getSocketAddress().getHostAddress() + " Port="
					+ app.getPort());
			app.listen();
		} catch (Exception e) {
			System.out.println("Issue in running gateway server");
		}

	}

}
