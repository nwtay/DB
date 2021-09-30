import java.io.File;
import java.util.ArrayList;

public class DropParse {

    final ArrayList<String> userCommand;

    public DropParse(ArrayList<String> userCommand)
    {
        this.userCommand = userCommand;
    }

    public boolean isDrop() throws Exception
    {
        if(userCommand.get(0).equals("DROP")){
            if(!userCommand.get(1).equals("DATABASE") && !userCommand.get(1).equals("TABLE")) {
                throw new Exception("Expected DATABASE or TABLE");
            }
            checkCommand();
            checkInputLength();
            return true;
        }
        return false;
    }

    private void checkCommand() throws Exception
    {
        if(!allAlphNumeric(userCommand.get(2))){
            throw new Exception("Structure Name Cannot Contain non-Alphanumeric characters");
        }
        if(userCommand.get(1).equals("DATABASE")){
            String path = "Databases/" + userCommand.get(2);
            File f = new File(path);
            if(!f.exists()){
                throw new Exception("Cannot drop the database " + userCommand.get(2) + " as it doesn't exist");
            }
        }
    }

    private void checkInputLength() throws Exception
    {
        if(userCommand.size() > 3){
            throw new Exception("Number of terms exceeds the limit for Drop command.");
        }
    }

    private boolean allAlphNumeric(String testString)
    {
        String matcher = "([\\W])";
        for(int i = 0; i < testString.length(); i++)
        {
            String s = testString.substring(i,i+1);
            if(s.matches(matcher)){
                return false;
            }
        }
        return true;
    }

}
