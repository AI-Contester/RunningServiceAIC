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

    private final Socket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    ListenerSocket(Socket s) throws Throwable {
        this.socket = s;
        this.inputStream = s.getInputStream();
        this.outputStream = s.getOutputStream();
    }

    @Override
    public void run() {
        try {
            readInputHeaders();
            writeResponse("<html><body><h1>If you see this page it means "
                    + "work the running service</h1></body></html>");
        } catch (Throwable t) {            
        } finally {
            try {
                socket.close();
            } catch (Throwable t) {                
            }
        }
        System.out.println("Client disconected");
    }

    private void writeResponse(String str) throws Throwable {
        String response = "HTTP/1.1 200 OK\r\n"
                + "Server: AIContester/2016-07-03\r\n"
                + "Content-Type: text/html\r\n"
                + "Content-Length: " + str.length() + "\r\n"
                + "Connection: close\r\n\r\n";
        String result = response + str;
        outputStream.write(result.getBytes());
        outputStream.flush();
    }

    private void readInputHeaders() throws Throwable {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
        String incomeRequest = "";
        while (true) {
            String readLine = bufferReader.readLine();            
            
            if (isEmptyLineRequest(readLine)) 
            {
                break;
            }
            
            incomeRequest += readLine + "\r\n";
        }
        
        System.out.println(incomeRequest);
    }
    
    private Boolean isEmptyLineRequest(String request)
    {
        return request == null || request.trim().length() == 0;
    }
}
