import java.util.ArrayList;

public class FullGrammarParse {

    final GeneralCommand commandType;
    final ArrayList<String> userInput;

    public FullGrammarParse(GeneralCommand commandType, ArrayList<String> userInput)
    {
        this.commandType = commandType;
        this.userInput = userInput;
    }

    public boolean isValidCommand() throws Exception
    {
        return commandType.validCommand();
    }

}
