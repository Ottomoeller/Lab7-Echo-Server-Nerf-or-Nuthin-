package echoserver;

import java.net.*;
import java.io.*;

public class EchoClient {

	public static void main(String[] args) {
		try {
			String serverIP;
			if(args.length < 1){
				serverIP = "localhost";
			} else {
				serverIP = args[0];
			}
			Socket socket = new Socket(serverIP, 6013);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader serverReturn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			
			String currentLine;
			boolean stillConnected = true;
			while ((currentLine = reader.readLine()) != null && stillConnected) {
				writer.println(currentLine);
				System.out.println(serverReturn.readLine());
				if((currentLine.compareTo("command:Disconnect") == 0) || (currentLine.compareTo("command:Off") == 0)){
					System.exit(0);
				}
			}
		} catch (IOException ioe) {
			System.out.println("We caught an exception");
			System.err.println(ioe);
		}
	}

}