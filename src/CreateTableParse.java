import java.util.ArrayList;

public class CreateTableParse {

    final ArrayList<String> commandList;
    private int currentIndex;
    private ArrayList<String> plainAttributeList;

    public CreateTableParse(ArrayList<String> commandList)
    {
        this.commandList = commandList;
        currentIndex = 0;
    }

    public boolean isCreateTable(Database database) throws Exception
    {
        String tableName;
        if(commandList.get(currentIndex).equals("CREATE")){
            currentIndex++;
            checkCommand();
            currentIndex++;
            tableName = commandList.get(currentIndex);
            checkTableName(tableName, database);
            if(currentIndex >= commandList.size() - 1){
                return true;
            }
            currentIndex++;
            checkCommand();
            currentIndex++;
            checkEndCommand();
            checkAttributeList();
            return true;
        }
        return false;
    }

    private void checkAttributeList() throws Exception
    {
        NewAttributeList columnList;
        if(currentIndex == commandList.size() - 1){
            throw new Exception("Missing Attribute/Column List");
        }
        int lastAttribute = commandList.size() - 1;
        ArrayList<String> columnNames = new ArrayList<>(commandList.subList(currentIndex, lastAttribute));
        columnList = new NewAttributeList(columnNames);

        if(!columnList.isAttributeList(true)){
            throw new Exception("Invalid attribute list.");
        }
        plainAttributeList = columnNames;
    }

    private void checkEndCommand() throws Exception
    {
        int endIndex = commandList.size() - 1;
        if(!commandList.get(endIndex).equals(")")){
            throw new Exception("Expected ) at end of Attribute List");
        }
    }

    private void checkCommand() throws Exception
    {
        if(currentIndex == 1){
            if(!commandList.get(currentIndex).equals("TABLE")){
                throw new Exception("Expected TABLE");
            }
        }
        if(currentIndex == 3){
            if(!commandList.get(currentIndex).equals("(")){
                throw new Exception("Expected ( for Attribute List");
            }
        }
    }

    public ArrayList<String> getPlainAttributeList()
    {
        plainAttributeList.removeIf(String -> String.equals(","));
        return plainAttributeList;
    }

    private void checkTableName(String tableName, Database d1) throws Exception
    {
        if(tableName.length() == 0){
            throw new Exception("Empty value for table name not allowed");
        }

        if(!allAlphNumeric(tableName)){
            throw new Exception("Table name includes non alphanumeric characters");
        }

        if(d1.tableNameExists(tableName)){
            throw new Exception("Table name already taken. Please enter a different name");
        }

    }

    private boolean allAlphNumeric(String testString)
    {
        String matcher = "([\\W])";
        for(int i = 0; i < testString.length(); i++)
        {
            String string = testString.substring(i,i+1);
            if(string.matches(matcher)){
                return false;
            }
        }
        return true;
    }

}
