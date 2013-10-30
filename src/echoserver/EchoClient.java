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
			
			OutputStream toServer = socket.getOutputStream();
			InputStream fromServer = socket.getInputStream();
			InputStream fromKeyboard = System.in;
			
			int currentByte;
			while ((currentByte = fromKeyboard.read()) != -1) {
				toServer.write(currentByte);
				System.out.write(fromServer.read());
			}
			System.out.flush();
			socket.close();
		} catch (IOException ioe) {
			System.out.println("We caught an exception");
			System.err.println(ioe);
		}
	}

}