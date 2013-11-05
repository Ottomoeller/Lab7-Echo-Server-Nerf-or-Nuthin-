package echoserver;

import java.net.*;
import java.io.*;

public class EchoClient implements Runnable{

    OutputStream toServer;
    InputStream fromServer;
    InputStream fromKeyboard;
    int mode;
    Socket socket;

    private EchoClient(OutputStream toServer, InputStream fromServer, InputStream fromKeyboard, int mode, Socket socket){
        this.toServer = toServer;
        this.fromServer = fromServer;
        this.fromKeyboard = fromKeyboard;
        this.mode = mode;
        this.socket = socket;
    }

    public static void main(String[] args) throws InterruptedException {
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


            Thread toServerThread = new Thread(new EchoClient(toServer, fromServer, fromKeyboard, 0, socket));
            Thread fromServerThread = new Thread(new EchoClient(toServer, fromServer, fromKeyboard, 1, socket));

            toServerThread.start();
            fromServerThread.start();

            toServerThread.join();
            fromServerThread.join();

            System.out.flush();
            socket.close();
        } catch (IOException ioe) {
            System.out.println("We caught an exception");
            System.err.println(ioe);
        }
    }

    @Override
    public void run() {
        if(mode == 0){
            toServerThread();
        } else {
            fromServerThread();
        }
    }

    private void toServerThread(){
        try {
            int currentByte;
            while ((currentByte = fromKeyboard.read()) != -1) {
                toServer.write(currentByte);
            }
        } catch (IOException ioe) {
            System.out.println("We caught an exception");
            System.err.println(ioe);
        }
    }

    private void fromServerThread(){
        try {
            int currentByte;
            while ((currentByte = fromServer.read()) != -1) {
                System.out.write(currentByte);
            }
        } catch (IOException ioe) {
            System.out.println("We caught an exception");
            System.err.println(ioe);
        }
    }

}