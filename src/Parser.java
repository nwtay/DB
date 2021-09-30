import java.util.ArrayList;

public class Parser {

    private ArrayList<String> splitString = new ArrayList<>();
    // class that checks if a list of commands contains the keywords it needs to
    final CommandCollection commandCollection = new CommandCollection();
    private GeneralCommand thisCommand;
    private Database currentDB = null;
    private boolean validParse = true;
    private String resultString;

    public Parser()
    {
    }

    public void ParseInput(String resultString, String userCommand) throws Exception
    {
        Tokenizer tokenizer;
        // true, until finds a reason to be false
        validParse = true;
        this.resultString = resultString;
        tokenizer = new Tokenizer(userCommand);
        if(!tokenizer.isValidInput()) {
            validParse = false;
            throw new Exception("Tokenizer failed");
        }
        if(validParse){
            splitString = tokenizer.getSplitString();
            checkKeywords();
            checkLastChar();
            checkNumberParameters();
            fullGrammarParse();
        }
    }

    public void ExecuteCommand() throws Exception
    {
        if(validParse){
            thisCommand.executeCommand();
            currentDB = thisCommand.currentDB;
        }
    }

    private void fullGrammarParse() throws Exception
    {
        FullGrammarParse fullGrammarParse;
        if(validParse) {
            fullGrammarParse = new FullGrammarParse(thisCommand, splitString);
            validParse = fullGrammarParse.isValidCommand();
        }
    }

    public boolean isValidParse()
    {
        return validParse;
    }

    private void checkLastChar() throws Exception
    {
        if(validParse) {
            if (!splitString.get(splitString.size() - 1).equals(";")) {
                validParse = false;
                throw new Exception("Command has to end with ;");
            }
            // removing the ; so can just deal with keywords now
            splitString.remove(splitString.size() - 1);
        }
    }

    private void checkNumberParameters() throws Exception
    {
        if(validParse) {
            thisCommand.currentDB = currentDB;
            if(!thisCommand.enoughParameters(splitString)){
                validParse = false;
                throw new Exception("Didn't enter the right number of terms for " + thisCommand.toString());
            }
        }
    }

    private void checkKeywords() throws Exception
    {
        thisCommand = commandCollection.identifyCommand(splitString.get(0).toUpperCase());
        if(thisCommand == null){
            validParse = false;
            throw new Exception("No command type found. Start with a command keyword");
        }
        else{
            if(validParse) {
                thisCommand.resultString = resultString;
                convertKeywordsToUpper(splitString, thisCommand);
                thisCommand.currentDB = currentDB;
                if (!thisCommand.ContainsKeywords(splitString)) {
                    validParse = false;
                    throw new Exception("Didn't enter the right amount of keywords for " + thisCommand.toString() + " command.");
                }
            }
        }
    }

    private void convertKeywordsToUpper(ArrayList<String> splitString, GeneralCommand identifiedCommand)
    {
        ArrayList<String> allKeywords = identifiedCommand.allKeywords;
        for(int i = 0; i < splitString.size(); i++)
        {
            if(allKeywords.contains(splitString.get(i).toUpperCase()))
            {
                splitString.set(i, splitString.get(i).toUpperCase());
            }
        }
    }

    public String getResultString()
    {
        this.resultString = thisCommand.resultString;
        return resultString;
    }

}
