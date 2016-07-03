/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runningservice;

import HttpServer.Listener;

/**
 *
 * @author Eugene
 */
public class RunningService {

    /**
     * @param args the command line arguments
     * @throws java.lang.Throwable
     */
    public static void main(String[] args) throws Throwable {
        Listener listener = new Listener(80);
        listener.run();
    }
    
}
