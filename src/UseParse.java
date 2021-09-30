import java.io.File;
import java.util.ArrayList;

public class UseParse {

    final ArrayList<String> userCommand;

    public UseParse(ArrayList<String> userCommand)
    {
        this.userCommand = userCommand;
    }

    public boolean isUse() throws Exception
    {
        if(userCommand.get(0).equals("USE")){
            checkCommand();
            checkLength();
            return true;
        }
        return false;
    }

    private void checkCommand() throws Exception
    {
        if(userCommand.get(1).isEmpty()){
            throw new Exception("Cant have empty strings here");
        }
        String dataBasePath = "Databases/" + userCommand.get(1);
        File file = new File(dataBasePath);
        if(!file.exists()){
            throw new Exception("Database does not exist");
        }
        if(!file.isDirectory()){
            throw new Exception("Database does not exist");
        }
    }

    private void checkLength() throws Exception
    {
        if(userCommand.size() > 2){
            throw new Exception("Excess terms for use command");
        }
    }
}
