/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HttpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Eugene
 */
public class Listener {
    
    public void run() throws IOException, Throwable
    {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected from " 
                    + socket.getRemoteSocketAddress().toString());
            new Thread(new ListenerSocket(socket)).start();
        }
    }
}
