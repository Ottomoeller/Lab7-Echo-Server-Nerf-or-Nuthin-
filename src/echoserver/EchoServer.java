package echoserver;

import java.net.*;
import java.io.*;

public class EchoServer {

	public static void main(String[] args) {
		try {
			ServerSocket sock = new ServerSocket(6016);

			
			
			while (true) {
				System.out.println("Got a request!");
				Socket client = sock.accept();

				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				
				PrintWriter writer = new PrintWriter(client.getOutputStream(), true);

				String currentLine;
				
				boolean stayConnected = true;
				while((currentLine=reader.readLine()) != null && stayConnected){
					if(currentLine.compareTo("command:Off") == 0){
						sock.close();
						System.exit(0);
					} else if(currentLine.compareTo("command:Disconnect") == 0){
						stayConnected = false;
					} else {
						writer.println(currentLine);
					}
				}
				client.close();
			}
			
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}

}