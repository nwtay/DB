import java.util.ArrayList;

public class AttributeList {

    final ArrayList<String> stringList;
    private int currentIndex;

    public AttributeList(ArrayList<String> stringList)
    {
        this.stringList = stringList;
        currentIndex = 0;
    }

    // if newFlag is true, we are testing whether a new table attribute list is valid, hence
    // cannot conduct attribute exists in table tests
    public boolean isAttributeList(Table inputTable, boolean newFlag) throws Exception
    {
        if(stringList.size() == 1){
            checkAttributeName(stringList.get(currentIndex), inputTable, newFlag);
            return true;
        }
        if(stringList.size() > 1){
            checkAttributeName(stringList.get(currentIndex), inputTable, newFlag);
            while(currentIndex <= stringList.size() - 2){
                currentIndex++;
                checkCommand();
                currentIndex++;
                checkAttributeName(stringList.get(currentIndex), inputTable, newFlag);
            }
            return true;
        }
        return false;
    }

    private void checkCommand() throws Exception
    {
        if(!stringList.get(currentIndex).equals(",")){
            throw new Exception("Expected ,");
        }
        if(currentIndex == stringList.size() - 1){
            throw new Exception("Cannot end Attribute List with a ,");
        }
    }

    private void checkAttributeName(String name, Table table, boolean newFlag) throws Exception
    {
        if(!newFlag){
            AttributeName attrName = new AttributeName(name);
            if(!attrName.isAttributeName(table))
            {
                throw new Exception("Attribute Name Does Not Exist");
            }
        }
        else{
            if(!allAlphNumeric(name)){
                throw new Exception("Attribute cannot contain non-alphanumeric characters");
            }
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

    public ArrayList<String> getPlainAttributeList()
    {
        // returns list of attributes with no columns
        ArrayList<String> plainAttributes = new ArrayList<>();
        for (String s : stringList) {
            if (!s.equals(",")) {
                plainAttributes.add(s);
            }
        }
        return plainAttributes;
    }

}
