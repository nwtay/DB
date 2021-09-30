import java.util.ArrayList;
import java.util.Arrays;

public class Tokenizer {

    final String[] stringList;
    final ArrayList<String> splitString;
    private boolean validInput = true;
    final ArrayList<Character> commandAsList;
    final String command;
    final SplitList splitList;
    private int charIndex;

    public Tokenizer(String command) throws Exception
    {
        this.command = command;
        checkTokens(command);
        commandAsList = new ArrayList<>();
        splitList = new SplitList();
        getCommandAsList();

        StringBuilder string = new StringBuilder();
        for (Character character : commandAsList) {
            string.append(character);
        }
        String splitCommand = string.toString();
        stringList = splitCommand.split("\\s+");
        splitString = new ArrayList<>(Arrays.asList(stringList));
        if(splitString.get(0).length() == 0){
            splitString.remove(0);
        }
        sortStringLiterals();
        checkJointOperators();
        checkEndingColon();
    }

    private void checkEndingColon() throws Exception
    {
        for(int i = 0; i < splitString.size(); i++){
            if(splitString.get(i).equals(";")){
                if(i != splitString.size() - 1){
                    throw new Exception("Extra text after command closing character: ;");
                }
            }
        }
    }

    private void sortStringLiterals()
    {
        int index;
        int originalIndex;
        String strLit;

        for (int i = 0; i < splitString.size(); i++) {
            if (splitString.get(i).charAt(0) == '\'') {
                if (splitString.get(i).charAt(splitString.get(i).length() - 1) != '\'') {
                    strLit = splitString.get(i);
                    originalIndex = i;
                    index = i+1;
                    addStringLiteral(index, originalIndex, strLit);
                }
            }
        }
    }

    private void addStringLiteral(int index, int originalIndex, String stringLiteral)
    {
        while (index <= splitString.size() - 1){
            if(splitString.get(index).charAt(splitString.get(index).length() - 1) != '\''){
                stringLiteral += (" " + splitString.get(index));
                splitString.remove(index);
                splitString.set(originalIndex, stringLiteral);
            }

            else{
                stringLiteral += (" " + splitString.get(index));
                splitString.remove(index);
                splitString.set(originalIndex, stringLiteral);
                // set index to this so get out of while loop
                index = splitString.size();
            }
        }
    }

    private void getCommandAsList() throws Exception
    {
        for(charIndex = 0; charIndex < command.length(); charIndex++)
        {
            Character c = command.charAt(charIndex);
            if(c == '\''){
                commandAsList.add(' ');
                commandAsList.add(c);
                // this is a string literal, so don't split stuff inside it
                charIndex++;
                skipStringLiteral();
                commandAsList.add(' ');
            }
            else{
                addCharacter(c);
            }
        }
    }

    private void skipStringLiteral() throws Exception
    {
        while(charIndex <= command.length() - 1 && command.charAt(charIndex) != '\''){
            commandAsList.add(command.charAt(charIndex));
            charIndex++;
            checkStringLitClose();
            if(command.charAt(charIndex) == '\''){
                commandAsList.add(command.charAt(charIndex));
            }
        }
    }

    private void checkStringLitClose() throws Exception
    {
        if(command.charAt(charIndex) != '\'' && charIndex == command.length() - 1){
            validInput = false;
            throw new Exception("String literal not surrounded by punctuation marks! Remember to close with '");
        }
    }

    private void addCharacter(Character character)
    {
        if(splitList.isInSplitList(character)){
            commandAsList.add(' ');
            commandAsList.add(character);
            commandAsList.add(' ');
        }
        else{
            commandAsList.add(character);
        }
    }

    private void checkJointOperators()
    {
        // since we split by >, and we also need >=, if > is followed by =, join
        // same case for !, =, <, ==
        for(charIndex = 0; charIndex < splitString.size(); charIndex++)
        {
            checkMoreThanJoin();
            checkLessThanJoin();
            checkNotEqualJoin();
            checkEqualityJoin();
        }
    }

    private void checkMoreThanJoin()
    {
        if(charIndex < splitString.size() - 1) {
            if (splitString.get(charIndex).equals(">") && splitString.get(charIndex + 1).equals("=")) {
                splitString.set(charIndex, ">=");
                splitString.remove(charIndex + 1);
            }
        }
    }

    private void checkLessThanJoin()
    {
        if(charIndex < splitString.size() - 1) {
            if (splitString.get(charIndex).equals("<") && splitString.get(charIndex + 1).equals("=")) {
                splitString.set(charIndex, "<=");
                splitString.remove(charIndex + 1);
            }
        }
    }

    private void checkNotEqualJoin()
    {
        if(charIndex < splitString.size() - 1) {
            if (splitString.get(charIndex).equals("!") && splitString.get(charIndex + 1).equals("=")) {
                splitString.set(charIndex, "!=");
                splitString.remove(charIndex + 1);
            }
        }
    }

    private void checkEqualityJoin()
    {
        if(charIndex < splitString.size() - 1) {
            if(splitString.get(charIndex).equals("=") && splitString.get(charIndex+1).equals("=")){
                splitString.set(charIndex, "==");
                splitString.remove(charIndex+1);
            }
        }
    }

    public boolean isValidInput()
    {
        return validInput;
    }

    public void checkTokens(String command) throws Exception
    {
        if(command.length() == 0)
        {
            throw new Exception("Please enter a query.");
        }
    }

    public ArrayList<String> getSplitString()
    {
        return splitString;
    }

}
