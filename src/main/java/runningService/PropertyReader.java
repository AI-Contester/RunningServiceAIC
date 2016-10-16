package runningService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Eugene on 16.10.2016.
 */
public class PropertyReader {

    private String nameFileProperty;

    public PropertyReader(String nameFileProperty)
    {
        this.nameFileProperty = nameFileProperty;
    }

    public String readProperty(String nameProperty) throws IOException {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            String filename = nameFileProperty;
            input = RunServerController.class.getClassLoader().getResourceAsStream(filename);
            prop.load(input);
            return prop.getProperty(nameProperty);
        }finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
