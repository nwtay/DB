import java.util.ArrayList;

abstract class GeneralCommand {

    protected String name;
    protected ArrayList<String> mandatoryKeywords = new ArrayList<>();
    protected ArrayList<String> allKeywords = new ArrayList<>();
    protected ArrayList<String> userCommand;
    protected int minNumParameters = 0;
    protected boolean validCommand = true;
    protected Database currentDB;
    protected String resultString;

    public GeneralCommand()
    {
    }

    public boolean ContainsKeywords(ArrayList<String> command)
    {
        // if the command contains the keywords stored in this class, then return true
        userCommand = command;
        for(String keyword : mandatoryKeywords){
            if(!command.contains(keyword)){
                return false;
            }
        }
        return true;
    }

    public boolean enoughParameters(ArrayList<String> command)
    {
        return command.size() >= minNumParameters;
    }

    public boolean validCommand() throws Exception
    {
        checkValidity();
        return validCommand;
    }

    public void executeCommand() throws Exception
    {
    }

    public void checkValidity() throws Exception
    {
    }

    public void checkDBSelected(Database database) throws Exception
    {
        if(database == null){
            validCommand = false;
            throw new Exception("No database selected. Try USE <database name>");
        }
    }

}