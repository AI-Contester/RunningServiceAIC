package runningService;

import javax.naming.NamingException;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Properties;

import static org.apache.naming.ContextBindings.getClassLoader;

/**
 * Created by Eugene on 16.10.2016.
 */
public class RunServerController {

    private Process serverProcess;
    private BufferedReader bufferedReader;
    private BufferedReader errorReader;

    public void runServer() throws IOException {

        PropertyReader propertyReader = new PropertyReader("application.properties");
        String pathtoserver = propertyReader.readProperty("pathtoserver");


        if (!Files.exists(FileSystems.getDefault().getPath(pathtoserver)))
            throw new IOException("Executable game server not found");

        ProcessBuilder processBuilder = new ProcessBuilder("java","-jar", pathtoserver,"1");
        processBuilder.directory(new File("/"));
        serverProcess = processBuilder.start();
        bufferedReader = new BufferedReader(new InputStreamReader(serverProcess.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(serverProcess.getErrorStream()));
    }

    public String readLineFromOutputStream() throws IOException {
        return bufferedReader.readLine();
    }

    public String readLineFromErrorStrem() throws IOException {
        return errorReader.readLine();
    }

    public void killProcess()
    {
        serverProcess.destroy();
    }
}
