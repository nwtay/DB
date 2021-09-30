import java.io.File;
import java.io.IOException;

public class MainSystem {

    final Parser parser;

    public MainSystem() throws IOException
    {
        createDatabasesDir();
        parser = new Parser();
    }

    public void nextCommand(String input) throws Exception
    {
        String resultString = "";
        parser.ParseInput(resultString, input);
        if(parser.isValidParse()){
            parser.ExecuteCommand();
        }
    }

    private void createDatabasesDir() throws IOException
    {
        File file = new File("./Databases");
        if(!file.exists()){
            if(!file.mkdir()){
                throw new IOException("Failed to make the Databases directory");
            }
        }
    }

    public String getResultString()
    {
        return parser.getResultString();
    }

}
