import java.util.ArrayList;

public class ValueList {

    final ArrayList<String> stringList;
    private int currentIndex;

    public ValueList(ArrayList<String> stringList)
    {
        this.stringList = stringList;
        currentIndex = 0;
    }

    public boolean isValueList() throws Exception
    {
        if(stringList.size() == 1){
            checkIfValue(stringList.get(currentIndex));
            return true;
        }
        if(stringList.size() > 1){

            checkIfValue(stringList.get(currentIndex));

            checkEndValue(stringList.get(stringList.size()-1));

            while(currentIndex <= stringList.size() - 2){
                currentIndex++;
                checkCommand();
                currentIndex++;
                checkIfValue(stringList.get(currentIndex));
            }

            return true;
        }
        return false;
    }

    public void checkEndValue(String command) throws Exception
    {
        if(command.equals(",")){
            throw new Exception("Cannot end Value List with a ,");
        }
    }

    public void checkIfValue(String command) throws Exception
    {
        Value valueIndex = new Value(command);
        if(!valueIndex.isValue()){
            throw new Exception("Invalid Entry for Value");
        }
    }

    private void checkCommand() throws Exception
    {
        if(!stringList.get(currentIndex).equals(",")){
            throw new Exception("Expected ,");
        }
    }

    public ArrayList<String> getPlainValueList()
    {
        ArrayList<String> justValues = new ArrayList<>();
        for (String stringAtIndex : stringList) {
            if (!stringAtIndex.equals(",")) {
                justValues.add(stringAtIndex);
            }
        }
        return justValues;
    }

}
