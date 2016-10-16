package runningService;

import javax.naming.NamingException;
import java.io.*;
import java.util.Properties;

import static org.apache.naming.ContextBindings.getClassLoader;

/**
 * Created by Eugene on 16.10.2016.
 */
public class RunServerController {

    private Process serverProcess;
    private BufferedReader bufferedReader;

    public void runServer() throws IOException {

        Properties prop = new Properties();
        InputStream input = null;
        String pathtoserver = prop.getProperty("pathtoserver");

        try {

            String filename = "application.properties";
            input = RunServerController.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                System.out.println("################################## Sorry, unable to find " + filename);
                return;
            }
            prop.load(input);
            pathtoserver = prop.getProperty("pathtoserver");

        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        ProcessBuilder processBuilder = new ProcessBuilder("java","-jar", pathtoserver);
        processBuilder.directory(new File("/"));
        serverProcess = processBuilder.start();
        bufferedReader = new BufferedReader(new InputStreamReader(serverProcess.getInputStream()));
    }

    public String readLineFromOutputStream() throws IOException {
        return bufferedReader.readLine();
    }

    public void killProcess()
    {
        serverProcess.destroy();
    }
}
