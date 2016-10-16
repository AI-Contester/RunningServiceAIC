package runningService;

import org.apache.tomcat.jni.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class SessionsController {

    @Autowired
    private SessionRepository repositorySession;

    @Autowired
    private StrategiesRepository repositoryStrategies;


    @RequestMapping(value = "/sessions", method = RequestMethod.POST)
    public SessionResponse response(@RequestBody String[] strategies) throws FailedToStartSessionException, IOException {
        Session session = new Session(strategies,"running",new Date(System.currentTimeMillis()));
        repositorySession.insert(session);
        RunServerController runServerController = new RunServerController();

        try {
            runServerController.runServer();
            ParserPort parserPort = new ParserPort(runServerController);
            parserPort.waitPort();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FailedToStartSessionException();
        }

        ArrayList<String> arrayStrategies = new ArrayList<>();

        for (String strategyF : strategies)
        {
            Strategies strategy = repositoryStrategies.findOne(strategyF);
            String tempDir = Files.createTempDirectory("AIC").toString();
            Files.createDirectories(FileSystems.getDefault().getPath(tempDir+"/com/belocraft/"));
            String pathToNewFile = tempDir+"/com/belocraft/MyStrategy.class";
            FileOutputStream outputStream = new FileOutputStream(pathToNewFile);
            outputStream.write(strategy.getExecutable().getData());
            outputStream.flush();
            arrayStrategies.add(pathToNewFile);

            runStrategy(tempDir);
        }

        return new SessionResponse(session.id);
    }

    private void runStrategy(String tempDir) throws IOException
    {
        PropertyReader propertyReader = new PropertyReader("application.properties");
        ProcessBuilder processBuilder = new ProcessBuilder("java","-classpath","\""+tempDir+"\";"
                + "\"" + propertyReader.readProperty("pathtostrategytemplate") + "\""
                ,"com.belocraft.Main");
        Process process = processBuilder.start();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader bf = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String msg = "";
                try {
                    while ((msg = bf.readLine()) != null)
                    {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        Thread threadError = new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader bf = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String msg = "";
                try {
                    while ((msg = bf.readLine()) != null)
                    {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        threadError.start();
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Failed to start game server")
    class FailedToStartSessionException extends Exception
    {
    }
}