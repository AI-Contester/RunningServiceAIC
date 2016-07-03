/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HttpServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Eugene
 */
public class ListenerSocket implements Runnable {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    ListenerSocket(Socket s) throws Throwable {
        this.socket = s;
        this.inputStream = s.getInputStream();
        this.outputStream = s.getOutputStream();
    }

    public void run() {
        try {
            readInputHeaders();
            writeResponse("<html><body><h1>Hello from Habrahabr example</h1></body></html>");
        } catch (Throwable t) {
            /*do nothing*/
        } finally {
            try {
                socket.close();
            } catch (Throwable t) {
                /*do nothing*/
            }
        }
        System.err.println("Client processing finished");
    }

    private void writeResponse(String s) throws Throwable {
        String response = "HTTP/1.1 200 OK\r\n"
                + "Server: YarServer/2009-09-09\r\n"
                + "Content-Type: text/html\r\n"
                + "Content-Length: " + s.length() + "\r\n"
                + "Connection: close\r\n\r\n";
        String result = response + s;
        outputStream.write(result.getBytes());
        outputStream.flush();
    }

    private void readInputHeaders() throws Throwable {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            String s = br.readLine();
            if (s == null || s.trim().length() == 0) {
                break;
            }
        }
    }
}
