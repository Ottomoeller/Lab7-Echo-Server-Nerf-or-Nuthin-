package echoserver;

import java.net.*;
import java.io.*;

public class EchoServer implements Runnable {

        Socket client;
   
        public EchoServer() {
           
        }
       
        private EchoServer(Socket client){
            this.client = client;
        }
       
    public static void main(String[] args){
        try {
            ServerSocket sock = new ServerSocket(6013);
           
            while (true) {
                Socket client = sock.accept();
                                System.out.println("Got a request!");

                Thread connection = new Thread(new EchoServer(client));
                connection.start();
            }
           
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    @Override
    public void run() {
        OutputStream toClient;
        try {
            toClient = client.getOutputStream();
            InputStream fromClient = client.getInputStream();
           
            int currentByte;
            while((currentByte=fromClient.read()) != -1){
                            toClient.write(currentByte);
            }
            toClient.flush();
            client.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
        }


    }

}