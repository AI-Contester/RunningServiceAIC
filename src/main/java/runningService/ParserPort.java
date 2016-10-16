package runningService;

import java.io.IOException;

/**
 * Created by Eugene on 16.10.2016.
 */
public class ParserPort {

    private RunServerController runServerController;

    public ParserPort (RunServerController runServerController)
    {
        this.runServerController = runServerController;
    }

    public void waitPort()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String input = "";
                while (input != null){
                    try {
                        input = runServerController.readLineFromOutputStream();
                        if (input != null && input.contains("[PORT]"))
                        {
                            System.out.println(String.format("################################### Current port is%s",
                                    input.split("]")[1]));
                        }
                        System.out.println(
                                String.format("################################### Message from server: %s",input));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        Thread threadError = new Thread(new Runnable() {
            @Override
            public void run() {
                String input = "";
                while (input != null){
                    try {
                        input = runServerController.readLineFromErrorStrem();
                        System.out.println(
                                String.format("################################### Error server: %s",input));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        threadError.start();
    }
}
