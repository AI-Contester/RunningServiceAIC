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
                while (input != "[END]"){
                    try {
                        input = runServerController.readLineFromOutputStream();
                        if (input.contains("[PORT]"))
                        {
                            System.out.println(String.format("################################### Current port is%s",
                                    input.split("]")[1]));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
        thread.start();
    }
}
