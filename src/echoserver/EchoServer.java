package echoserver;

import java.net.*;
import java.io.*;

public class EchoServer {

	public static void main(String[] args) {
		try {
			ServerSocket sock = new ServerSocket(6013);

			
			
			while (true) {
				System.out.println("Got a request!");
				Socket client = sock.accept();

				OutputStream toClient = client.getOutputStream();
				InputStream fromClient = client.getInputStream();

				int currentByte;
				while((currentByte=fromClient.read()) != -1){
						toClient.write(currentByte);
				}
				toClient.flush();
				client.close();
			}
			
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}

}